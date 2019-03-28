/*
 * 文 件 名:  ConstructionSpeCardService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.vo.ConstructionSpeCardReq;

/**
 * 渣土车特殊通行證相关业务接口
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ConstructionSpeCardService
{
    /**
     * 新增渣土车信息
     * @param dimConstructionVehicle
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void addConstructionSpeCard(ConstructionSpeCardReq speCardReq);

    /** 
     * 根据车牌号、使用人、手机号查询渣土车
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimConstructionSpeCard> getConstructionSpeCard(ConstructionSpeCardReq req);

    /** 
     * 根据车牌号删除渣土车
     * @param licensePlate
     * @see [类、类#方法、类#成员]
     */
    void deleteConstructionSpeCard(ConstructionSpeCardReq req);
    
    /** 
     * 根据车牌号修改渣土车信息
     * @param vehicle
     * @see [类、类#方法、类#成员]
     */
    void updateConstructionSpeCard(ConstructionSpeCardReq speCardReq);
    
    /**
     * 获取某一天有效的渣土车特殊通行证
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimConstructionSpeCard> getValidConSpeCards(int date);
}
