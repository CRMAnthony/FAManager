/*
 * 文 件 名:  DimSurveillanceCameraDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年11月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import com.jszt.domain.DimSurveillanceCamera;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年11月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimSurveillanceCameraDao
{
    /**
     * 查询卡口列表
     * <功能详细描述>
     * @return
     * @throws TrafficException
     * @see [类、类#方法、类#成员]
     */
    DimSurveillanceCamera getSCByNum(String cameraNumber); 
    
}
