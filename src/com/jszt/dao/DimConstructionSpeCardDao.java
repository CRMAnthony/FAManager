/*
 * 文 件 名:  DimConstructionSpeCardDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.vo.ConstructionSpeCardReq;

/**
 * 渣土車特殊通行證數據庫訪問接口
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimConstructionSpeCardDao
{
    /**
     * 新增渣土车特殊通行證信息
     * @param dimConstructionVehicle
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
     void addConstructionSpeCard(DimConstructionSpeCard dimConstructionSpeCard);
     
     /** 
      * 批量新增渣土车特殊通行證
      * @param conn
      * @param retList
      * @see [类、类#方法、类#成员]
      */
     void addBatchConstructionSpeCard(List<DimConstructionSpeCard> retList);

     /** 
      * 根据车牌号、公司名稱查询渣土车特殊通行證信息列表
      * @param req
      * @return
      * @see [类、类#方法、类#成员]
      */
     List<DimConstructionSpeCard> getConstructionSpeCard(ConstructionSpeCardReq req);
     
     /** 
      * 根据车牌号删除渣土车特殊通行證
      * @param strings
      * @see [类、类#方法、类#成员]
      */
     void deleteConstructionSpeCard(String[] ids);

     /** 
      * 根据车牌号更新渣土车特殊通行證信息
      * @param vehicle
      * @see [类、类#方法、类#成员]
      */
     void updateConstructionSpeCard(ConstructionSpeCardReq speCardReq);
     
     /**
      * 渣土车特殊通行證ID主键自增
      * @return
      * @see [类、类#方法、类#成员]
      */
     String getConstructionSpeCardSEQ();

     /**
      * 获取某一天有效的渣土车特殊通行证
      * @param date
      * @return
      * @see [类、类#方法、类#成员]
      */
     List<DimConstructionSpeCard> getValidConSpeCards(int date);
}
