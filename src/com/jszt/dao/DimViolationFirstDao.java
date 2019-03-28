/*
 * 文 件 名:  DimViolationFirstDao.java
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
 * 外地车首次违法数据库
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationFirstDao 
{
    /**
     * 提取外地车首次违法记录
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<IllegalRecord> extractViolationFirst(IllegalRecordQueryConditionReq condition);
    
    /**
     * 获取首次违法记录图片
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    PicImageResp getFirstPic(int date, String licensePlate);
    
    /**
     * 插入外地车首次违法信息
     * @param data 
     * @see [类、类#方法、类#成员]
     */
    void insertViolationFirst(Connection con,IllegalApproval data);
    
    /**
     * 删除外地车首次违法信息（根据时间和车牌）
     * @param data
     * @see [类、类#方法、类#成员]
     */
    void deleteViolationFirst(Connection con,IllegalApproval data);
}
