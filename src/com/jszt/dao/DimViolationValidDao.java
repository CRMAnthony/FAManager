/*
 * 文 件 名:  DimViolationValidDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.sql.Connection;
import java.util.List;

import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.TempPassCardVio;

/**
 * 违法记录数据操作接口
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationValidDao
{
    /**
     * 提取违法记录
     * @param condition 提取条件
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> extractDimViolationValid(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询指定时间内的违法记录
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    int getViolationCount(String time, String licensePlate);
    
    /**
     * 获取有效问法记录图片
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    PicImageResp getViolationPic(int date, String licensePlate,String areaId);
    
    /**
     * 插入违法有效信息
     * @param data 
     * @see [类、类#方法、类#成员]
     */
    void insertViolationValid(Connection con,IllegalApproval data);
    
    /**
     * 删除违法有效信息（根据时间和车牌）
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void deleteViolationValid(Connection con,IllegalApproval data);
    
    /**
     * 获取违法信息总数
     * @return
     * @see [类、类#方法、类#成员]
     */
    int getViolationValidCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询违法数据（分页）
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryDimViolationValid(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询有效表违法次数
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryViolationCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询有效过车车辆数量（导出时候使用）
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    int queryValidPassRecordCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 
     * <功能详细描述>
     * @param dateKey
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TempPassCardVio> queryTempPassCardViolation(String dateKey);
    
    /**
     * 根据rowId删除过车记录
     * <功能详细描述>
     * @param rowId
     * @see [类、类#方法、类#成员]
     */
    void delTempPassCardViolation(String rowId);
}
