/*
 * 文 件 名:  IllegalRecordService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;

/**
 * 违法记录业务操作类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface IllegalRecordService
{
    
    /**
     * 违法记录提取
     * @param condition 提交条件
     * @return 问法记录
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> extractRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 图片查询接口
     * @param date 日期
     * @param licensePlate 车牌
     * @param processState 处理状态
     * @return 查询图片列表
     * @see [类、类#方法、类#成员]
     */
    PicImageResp getPic(PicImageReq req);
    
    /**
     * 违法审核数据提交
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void illegalApproval(IllegalApproval data);
    
    /**
     * 图片合成地址
     * 合成图片地址
     * @param pic
     * @param time 第一张图片时间
     * @param type 车辆类型
     * @param areaId  违法区域编号
     * @return 图片服务器地址
     * @see [类、类#方法、类#成员]
     */
    String imageFormed(String[] pic, String type, String time,String areaId);
    
    /**
     * 统计违法次数
     * 
     */
    void statisticsIllegalCount();
    
    /**
     * 删除不合格的违法记录（定时器）
     * @see [类、类#方法、类#成员]
     */
    void deleteUnqualifiedIllegalRecord();
    
    /**
     * 查询记录（分页）
     * @param condition
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 获取查询记录总数
     * @return
     * @see [类、类#方法、类#成员]
     */
    int getRecordCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 删除记录
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void deleteRecord(List<IllegalApproval> datas,int processState);
    
    /**
     * 查询违法次数统计
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> queryViolationCount(IllegalRecordQueryConditionReq condition);

    /**
     * 导出违法次数统计
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream exportViolationCount(IllegalRecordQueryConditionReq condition);
    
    /**
     * 导出过车记录
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream exportRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 校验导出过车记录是否过长
     * <功能详细描述>
     * @param condition
     * @see [类、类#方法、类#成员]
     */
    void checkExportRecord(IllegalRecordQueryConditionReq condition);
    
    /**
     * 按车牌号或重点车辆类型统计违法次数
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ViolationPassRecordVo> queryViolationRecord(ViolationRecordReq req);
    
    /**
     * 实时统计违法信息或按设备地点统计违法次数
     * <功能详细描述>
     * @param type  0:实时统计,1:按设备地点统计违法次数
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<ViolationCameraVo> queryViolationRecordCamera(int type);
    
    /**
     * 删除前30天统计数据
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    void deleteNoticeCount();
    
}
