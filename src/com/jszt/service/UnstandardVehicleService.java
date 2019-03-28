/*
 * 文 件 名:  UnstandardVehicleService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.vo.DeleLicensePlatesReq;
import com.jszt.vo.UnstandardVehicleReq;

/**
 * 黄标车相关业务接口
 * 
 * @author  gxj
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface UnstandardVehicleService
{
    /**
     * 录入黄标车信息
     * @param UnstandardVehicle
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void addUnstandardVehicle(DimUnstandardVehicle dimUnstandardVehicle);

    /** 
     * 查询黄标车
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimUnstandardVehicle> getUnstandardVehicle(UnstandardVehicleReq req);

    /** 
     * 根据车牌号删除黄标车
     * @param licensePlate
     * @see [类、类#方法、类#成员]
     */
    void deleteUnstandardVehicle(DeleLicensePlatesReq req);
   
    /**
     * 黄标车Excel封装
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream buildUnstandardVehicleExcelBody(List<DimUnstandardVehicle> retList);

    /** 
     * 根據車牌號更新黄标车信息
     * @param vehicle
     * @see [类、类#方法、类#成员]
     */
    void updateUnstandardVehicle(DimUnstandardVehicle vehicle);
    
    

    /** 
     * 处理导出黄标车的业务
     * @param retList
     * @see [类、类#方法、类#成员]
     */
    void dealUnstandardVehicle(List<DimUnstandardVehicle> retList);

    /**
     * 查询所有黄标车车牌号
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getLicensePlates();
}
