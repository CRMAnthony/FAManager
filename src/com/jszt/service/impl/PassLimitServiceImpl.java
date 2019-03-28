/*
 * 文 件 名:  PassLimitServiceImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jasig.cas.client.util.CollectionUtils;

import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.dao.DimPassLimitDao;
import com.jszt.dao.DimPassLimitDateDao;
import com.jszt.domain.DimPassLlimit;
import com.jszt.service.PassLimitService;
import com.jszt.vo.PassLimitReq;

/**
 * 通行额度相应业务 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLimitServiceImpl implements PassLimitService
{
    
    @Override
    public void addPassLimit(String limit, String plateTypeId,String limitDate,Integer passLineId)
        throws JsztException
    {
        if (StringUtils.isNotEmpty(limit) && StringUtils.isNotEmpty(plateTypeId))
        {
            
            String limitArray[] = limit.split(",");
            Assert.isTrue(limitArray.length == 24, ErrorCode.PARAM_ERROR, "参数错误！！！");
            List<PassLimitReq> passLimit = new ArrayList<PassLimitReq>(24);
        	if(StringUtils.isEmpty(limitDate))
        	{
        		ServiceUtil.getService(DimPassLimitDao.class).deletePassLimit(plateTypeId, passLineId);
                for (int i = 0; i < 24; i++)
                {
                    passLimit.add(new PassLimitReq(i * 100, Integer.valueOf(limitArray[i]), plateTypeId, passLineId));
                }
                ServiceUtil.getService(DimPassLimitDao.class).addPassLimit(passLimit);
        	}
        	else
        	{
        		ServiceUtil.getService(DimPassLimitDateDao.class).deletePassLimit(plateTypeId,limitDate, passLineId);
                for (int i = 0; i < 24; i++)
                {
                    passLimit.add(new PassLimitReq(i * 100, Integer.valueOf(limitArray[i]), plateTypeId,limitDate, passLineId));
                }
                ServiceUtil.getService(DimPassLimitDateDao.class).addPassLimit(passLimit);
        	}
        }
        
    }
    
    /**
     * 初始化查询额度配置
     * 
     * @return
     * @throws JsztException
     */
    @Override
    public List<DimPassLlimit> getPassLimit(String plateTypeId,String limitDate,Integer passLineId)
        throws JsztException
    {
    	List<DimPassLlimit> limitList = null;
    	if(StringUtils.isNotEmpty(limitDate))
    	{
    		limitList = ServiceUtil.getService(DimPassLimitDateDao.class).queryDimPassLlimit(plateTypeId,limitDate,passLineId);
    	}
    	if(CollectionUtils.isEmpty(limitList))
    	{
    		limitList = ServiceUtil.getService(DimPassLimitDao.class).queryDimPassLlimit(plateTypeId,passLineId);
    	}
        return limitList;
    }

	@Override
	public List<DimPassLlimit> getPassLimitSum() throws JsztException {
		return ServiceUtil.getService(DimPassLimitDao.class).queryDimPassLlimitSum();
	}
    
}
