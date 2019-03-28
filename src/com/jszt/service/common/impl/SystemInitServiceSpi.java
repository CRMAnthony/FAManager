/*
 * 文 件 名:  SystemInitServiceSpi.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationPassRecordDao;

/**
 * 系统初始化类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SystemInitServiceSpi implements InitializingBean
{

    private static Logger logger = Logger.getLogger(SystemInitServiceSpi.class);
    
    // 所有的bean初始化完成后
    @Override
    public void afterPropertiesSet()
        throws Exception
    {
        
        try
        {
            ServiceUtil.getService(DimViolationPassRecordDao.class).initVerifyMark();
        }
        catch (ServiceException e)
        {
            logger.error(e);
        }
    }
    
}
