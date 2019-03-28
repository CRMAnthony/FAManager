/*
 * 文 件 名:  ConstructionVehicleService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.vo.ConstructionVehicleReq;

/**
 * 渣土车相关业务接口
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConstructionVehicleService
{
    /**
     * 新增渣土车信息
     * @param dimConstructionVehicle
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void addConstructionVehicle(DimConstructionVehicle dimConstructionVehicle);

    /** 
     * 根据车牌号、使用人、手机号查询渣土车
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimConstructionVehicle> getConstructionVehicle(ConstructionVehicleReq req);

    /** 
     * 根据车牌号删除渣土车
     * @param licensePlate
     * @see [类、类#方法、类#成员]
     */
    void deleteConstructionVehicle(ConstructionVehicleReq req);
   
    /**
     * 渣土车Excel封装
     * 
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream buildConstructionVehicleExcelBody(List<DimConstructionVehicle> retList);

    /** 
     * 根据车牌号修改渣土车信息
     * @param vehicle
     * @see [类、类#方法、类#成员]
     */
    void updateConstructionVehicle(DimConstructionVehicle vehicle);
    
    /** 
     * 处理导出渣土车的业务
     * @param retList
     * @see [类、类#方法、类#成员]
     */
    void dealConstructionVehicle(List<DimConstructionVehicle> retList);

    /**
     * 查询所有渣土车车牌号
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getLicensePlates();

    /**
     * 根据公司名稱查詢屬於該公司的渣土车
     * @param vehicleReq
     * @see [类、类#方法、类#成员]
     */
    List<DimConstructionVehicle> getConsVehicleByCompanyName(ConstructionVehicleReq vehicleReq);

    /**
     * 查詢所有公司(顯示公司名稱)
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimConstructionVehicle> getCompanyNames();
}
