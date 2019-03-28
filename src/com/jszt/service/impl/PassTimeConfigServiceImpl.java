/*
 * 文 件 名:  PassTimeConfigServiceImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimPassLimitDao;
import com.jszt.dao.DimPassLimitDateDao;
import com.jszt.dao.DimTempPassCardDao;
import com.jszt.domain.DimPassLlimit;
import com.jszt.service.PassTimeConfigService;
import com.jszt.vo.PassTimeConfig;
import com.jszt.vo.TimeConfig;

/**
 * 通行时段业务处理
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassTimeConfigServiceImpl implements PassTimeConfigService
{
    
    @Override
    public List<PassTimeConfig> QueryPassTimeConfigByDate(int startDate, int endDate, String plateTypeId, Integer passLineId)
    {
        
        // 查询通行时段配置
        List<DimPassLlimit> startlimits = ServiceUtil.getService(DimPassLimitDateDao.class).queryDimPassLlimit(plateTypeId, String.valueOf(startDate), passLineId);
        if(CollectionUtils.isEmpty(startlimits))
        {
        	startlimits = ServiceUtil.getService(DimPassLimitDao.class).queryDimPassLlimit(plateTypeId, passLineId);
        }
        
        // 没有配置通行时间段则抛出异常
        //ServiceAssert.notEmpty(limits, -1, "DimPassLlimit is empty");
        
        // 没有同行配置返回没有限额的24个值
        List<PassTimeConfig> ret = new ArrayList<PassTimeConfig>(2);
        
        
        if(startDate<20180620 || startDate>20180625)
        {
        	calcPassTimeConfig(startDate, startlimits, ret, plateTypeId, passLineId);
        }
        // 起始时间和结束时间不一样表示表示2天,则查询第二天数据
        if ((startDate != endDate) && (endDate<20180620 || endDate>20180625))
        {
        	// 查询通行时段配置
            List<DimPassLlimit> endlimits = ServiceUtil.getService(DimPassLimitDateDao.class).queryDimPassLlimit(plateTypeId, String.valueOf(endDate), passLineId);
            if(CollectionUtils.isEmpty(endlimits))
            {
            	endlimits = ServiceUtil.getService(DimPassLimitDao.class).queryDimPassLlimit(plateTypeId, passLineId);
            }
            calcPassTimeConfig(endDate, endlimits, ret, plateTypeId, passLineId);
        }
        return ret;
    }

    private void calcPassTimeConfig(int date, List<DimPassLlimit> limits,List<PassTimeConfig> ret,String plateTypeId, Integer passLineId)
    {
        List<String> periods =
            ServiceUtil.getService(DimTempPassCardDao.class).queryDimTempPassCardPeriodByDateKey(date, plateTypeId, passLineId);
        
        // 将字符串20141213：1，2，3；20141214：4，5，6这样格式以;分割成当前时间点的[1,3,4,5,3,2,1]
        List<Integer> times = periodsSplit(periods, date);
        
        // 根据时间段统计
        ret.add(buildPassTimeConfig(times, limits, date));
    }

    private PassTimeConfig buildPassTimeConfig(List<Integer> times, List<DimPassLlimit> limits, int startDate)
    {
        PassTimeConfig config = new PassTimeConfig();
        
        config.setConfigDate(startDate);
        
        List<TimeConfig> ret = new ArrayList<TimeConfig>(24);
        
        // 没有值就初始化24个值
        if (CollectionUtils.isEmpty(limits))
        {
            for (int i = 0; i < 24; i++)
            {
                ret.add(new TimeConfig(0));
            }
        }
        else
        {
            // 初始24个时间段,并设置总额度
            for (DimPassLlimit limit : limits)
            {
                ret.add(new TimeConfig(limit.getLimit()));
            }
            // 统计是每个点的时间段
            for (Integer time : times)
            {
            	if (time.intValue() >= 24)
            	{
            		continue;
            	}
                ret.get(time).usedAdd();
            }
        }
        config.setTimeConfig(ret);
        
        return config;
    }

    private List<Integer> periodsSplit(List<String> periods, int startDate)
    {
        List<Integer> ret = new ArrayList<Integer>(periods.size() * 2);
        for (String str : periods)
        {
            if (StringUtils.isNotEmpty(str))
            {
                // 将时间段以;分割201401003:1.4.6;201401004:0.4.6
                String[] period = StringUtils.split(str, ';');
                for (String string : period)
                {
                    // 包含当前日期
                    if (string.indexOf(String.valueOf(startDate)) >= 0)
                    {
                        getIntTime(string,ret);
                    }
                }
            }
        }
        
        return ret;
        
    }

    private void getIntTime(String string,List<Integer> ret)
    {
        String[] times = string.split(":|,");
        
        // 数组第一位数日期去出如期获取后面时间段
        for (int i = 1; i < times.length; i++)
        {
        	if (times[i].length() > 8)
        	{
        		ret.add(ConvertUtils.parseInt(times[i].substring(0, times[i].length() - 8), -1));
        		continue;
        	}
            ret.add(ConvertUtils.parseInt(times[i], -1));
        }
        
    }
}
