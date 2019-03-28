/*
 * 文 件 名:  UpdateVerifyMarkTask.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.util.List;
import java.util.TimerTask;



import org.apache.commons.collections4.CollectionUtils;

import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationPassRecordDao;
import com.jszt.vo.IllegalRecord;

/**
 * 更新过车记录类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UpdateVerifyMarkTask extends TimerTask
{
    
    private List<IllegalRecord> records;
    
    public UpdateVerifyMarkTask(List<IllegalRecord> records)
    {
        this.records = records;
    }
    
    @Override
    public void run()
    {
        if (CollectionUtils.isNotEmpty(records))
        {
            ServiceUtil.getService(DimViolationPassRecordDao.class).updateVerifyMark(records, 0);
        }
    }
    
}
