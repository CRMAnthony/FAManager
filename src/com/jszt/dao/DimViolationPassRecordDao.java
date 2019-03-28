/*
 * 文 件 名:  DimViolationPassRecordDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.sql.Connection;
import java.util.List;

import com.jszt.domain.DimViolationPassRecord;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;

/**
 * 过车记录查询表
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationPassRecordDao
{
    /**
     * 根据条件提取过车记录
     * @param condition 条件
     * @return 合并过的过车记录记录
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> extractPassRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 更新提取标识
     * @param record 记录
     * @param mark 标识
     * @see [类、类#方法、类#成员]
     */
    void updateVerifyMark(List<IllegalRecord> record, int mark);

    /**
     * 初始化提取标识
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void initVerifyMark();
    
    /**
     * 获取待审核记录图片
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    PicImageResp getPassPic(int date, String licensePlate,String area_id);
    
    /**
     * 删除过车记录有效信息（根据时间和车牌）
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void deletePassRecord(Connection con,IllegalApproval data);
    
    /**
     * 新增违法过车记录
     * <一句话功能简述>
     * <功能详细描述>
     * @param record
     * @see [类、类#方法、类#成员]
     */
    void addVioRecord(DimViolationPassRecord record);
    
    /**
     * 根据时间删除前一天只有一张图片的记录
     * @param dateKey
     * @see [类、类#方法、类#成员]
     */
    void deleteRecordOnlyOnePic(int dateKey);
    
    /**
     * 根据时间获取外地车辆过车记录信息
     * @param dateKey
     * @see [类、类#方法、类#成员]
     */
    List<String> getNonlocalRecord(int dateKey);
    
    /**
     * 指定时间内wjwf库中是否有指定的过车记录
     * @param startDate 起始日期
     * @param endDate 结束日期
     * @param licensePlate 车牌
     * @return 是否有数据  true 没有数据,此次是第一次进入该城市
     * @see [类、类#方法、类#成员]
     */
    boolean isPassRecordFromWjwf(int startDate, int endDate, String licensePlate);
    
    /**
     * 批量删除违法记录信息
     * @param licensePlates 车牌
     * @param dateKey 时间
     * @see [类、类#方法、类#成员]
     */
    void deleteRecord(List<String> licensePlates,int dateKey);
    
    /**
     * 查询违法记录总数
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    int getViolationPassRecordCout(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询违法过车记录（分页）
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryPassRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 查询过车记录数量（导出时候使用）
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    int queryPassRecordCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 删除重复数据
     * @param datekey 时间
     * @see [类、类#方法、类#成员]
     */
    void delteRepeatingPicData(int datekey);
    
    /**
     * 按车牌号或重点车辆类型统计违法次数
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ViolationPassRecordVo> queryViolationRecord(ViolationRecordReq req);
    
    /**
     * 实时统计违法信息
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ViolationCameraVo> queryViolationRecordCamera();
    
    /**
     * 按设备地点统计违法次数
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ViolationCameraVo> queryViolationRecordCameraCount();
    
    void deltePassRecordByDate(int datekey);
    
    void delteHisPassRecordByDate(int datekey);
    
    
    void rebuildIndex(String buildSql);
}
