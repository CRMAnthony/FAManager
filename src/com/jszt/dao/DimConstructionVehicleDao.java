/*
 * 文 件 名:  ConstructionVehicleDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.sql.Connection;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.vo.ConstructionVehicleReq;

/**
 * 渣土车数据库操作
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimConstructionVehicleDao
{
    /**
     * 新增渣土车信息
     * @param dimConstructionVehicle
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
     void addConstructionVehicle(DimConstructionVehicle dimConstructionVehicle);

     /** 
      * 根据车牌号、使用人、手机号查询渣土车信息列表
      * @param req
      * @return
      * @see [类、类#方法、类#成员]
      */
     List<DimConstructionVehicle> getConstructionVehicle(ConstructionVehicleReq req);
     
     /**
      * 获取某一天有效的（可通行）的渣土车
      * <一句话功能简述>
      * <功能详细描述>
      * @param date
      * @return
      * @see [类、类#方法、类#成员]
      */
     List<DimConstructionVehicle> getValidConVehicles(int date);

     /** 
      * 根据车牌号删除渣土车
      * @param strings
      * @see [类、类#方法、类#成员]
      */
     void deleteConstructionVehicle(String[] strings);

     /** 
      * 根据车牌号更新渣土车信息
      * @param vehicle
      * @see [类、类#方法、类#成员]
      */
     void updateConstructionVehicle(DimConstructionVehicle vehicle);

     /** 
      * 删除渣土车
      * @param conn
      * @see [类、类#方法、类#成员]
      */
     void deleteConstructionVehicle(Connection conn);

     /** 
      * 批量新增渣土车
      * @param conn
      * @param retList
      * @see [类、类#方法、类#成员]
      */
     void addBatchConstructionVehicle(Connection conn, List<DimConstructionVehicle> retList);

     /** 
      * 查询所有渣土车车牌号
      * @return
      * @see [类、类#方法、类#成员]
      */
     List<String> getLicensePlates();

     /**
      * 根据公司名稱查詢屬於該公司的渣土车
      * @param vehicleReq
      * @return
      * @see [类、类#方法、类#成员]
      */
    List<DimConstructionVehicle> getConsVehicleByCompanyName(ConstructionVehicleReq vehicleReq);

    /**
     * 查詢所有公司(顯示公司名稱)
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getCompanyNames();
}
