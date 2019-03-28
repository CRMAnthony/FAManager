/*
 * 文 件 名:  DimViolationInvalidDao.java
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

/**
 * 违法无效信息业务接口处理类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationInvalidDao
{
    /**
     * 提取违法无效信息数据库
     * @param condition 查询条件
     * @return 违法无效信息
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> extractViolationInvalid(IllegalRecordQueryConditionReq condition);
    
    /**
     * 获取无效记录图片
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    PicImageResp getInvalidPic(int date, String licensePlate,String areaId);
    
    /**
     * 插入违法无效信息
     * @param data 
     * @see [类、类#方法、类#成员]
     */
    void insertViolationInvalid(Connection con,IllegalApproval data);
    
    /**
     * 删除违法无效信息（根据时间和车牌）
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void deleteViolationInvalid(Connection con,IllegalApproval data);
    
    /**
     * 获取违法无效信息总数
     * @return 总数
     * @see [类、类#方法、类#成员]
     */
    int getViolationInvalidCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 获取违法信息（分页）
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryViolationInvalid(IllegalRecordQueryConditionReq condition);
    
    /**
     * 根据违法时间和车牌查询
     * <功能详细描述>
     * @param time
     * @param licensePlate
     * @return
     * @see [类、类#方法、类#成员]
     */
    IllegalRecord queryViolationInvalid(String time, String licensePlate,String areaId);
    
    /**
     * 查询无效过车车辆数量（导出时候使用）
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    int queryInvalidPassRecordCount(IllegalRecordQueryConditionReq condition);
}
