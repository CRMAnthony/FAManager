/*
 * 文 件 名:  DimUnstandardVehicleDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.sql.Connection;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.vo.UnstandardVehicleReq;

/**
 * 黄标车数据库操作
 * @author  gxj
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimUnstandardVehicleDao
{
   /**
    * 录入黄标车
    * @param dimUnstandardVehicle
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
     * @param strings
     * @see [类、类#方法、类#成员]
     */
    void deleteUnstandardVehicle(String[] strings);

    /** 
     * 根据车牌号更新黄标车信息
     * @param vehicle
     * @see [类、类#方法、类#成员]
     */
    void updateUnstandardVehicle(DimUnstandardVehicle vehicle);

    /** 
     * 删除黄标车
     * @param conn
     * @see [类、类#方法、类#成员]
     */
    void deleteUnstandardVehicle(Connection conn);

    /** 
     * 批量添加黄标车
     * @param conn
     * @param retList
     * @see [类、类#方法、类#成员]
     */
    void addBatchUnstandardVehicle(Connection conn, List<DimUnstandardVehicle> retList);

    /** 
     * 查询所有黄标车车牌号
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getLicensePlates();
}
