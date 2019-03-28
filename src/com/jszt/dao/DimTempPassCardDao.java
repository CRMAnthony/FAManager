/*
 * 文 件 名:  DimTempPassCard.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jszt.domain.DimTempPassCard;
import com.jszt.vo.PassCardAnalyseVo;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.TempCountPo;

/**
 * 零时通行证数据库访问接口
 * 
 * @author ChangJun
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface DimTempPassCardDao
{
    /**
     * 根据时间查询通行证查询通行时段
     * 
     * @param DateKey 通行证时间
     * @param plateTypeId 车牌种类
     * @return 时间段
     * @see [类、类#方法、类#成员]
     */
    List<String> queryDimTempPassCardPeriodByDateKey(int dateKey, String plateTypeId, Integer passLineId);
    
    /**
     * 根据编号获取通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param cardId
     * @return
     * @see [类、类#方法、类#成员]
     */
    DimTempPassCard getTempPassCardById(String cardId);
    
    /**
     * 查询临时通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param passCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimTempPassCard> getTempPassCards(PassCardReq passCardReq);
    
    /**
     * 获取某一天有效的临时通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimTempPassCard> getTempValidPassCards(int date);
    
    /**
     * 录入临时通行证
     * 
     * @param dimTempPassCard
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void addTempPassCard(DimTempPassCard dimTempPassCard);
    
    /**
     * 通行证打印 <一句话功能简述> <功能详细描述>
     * 
     * @param tempPassCard
     * @see [类、类#方法、类#成员]
     */
    void updateTempPrint(DimTempPassCard tempPassCard);
    
    /**
     * 获取与某一段时间内有交叉的通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param sdate
     * @param eDate
     * @see [类、类#方法、类#成员]
     */
    List<DimTempPassCard> getTempCardByInterval(int sDate, int eDate);
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getTempPassCardId(PassCardRequest passCardRequest);
    
    /**
     * 获取某辆车某一天的通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param plateLicense
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimTempPassCard> getTempCardByDate(String plateLicense, int date);
    
   
    /**
     * 按月统计申请总数
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getMonthTempCount(int beginDate,int endDate) throws ServiceException;
    
    /**
     * 按月统计申请黄牌车总数
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getMonthYellowTempCount(int beginDate,int endDate,String plateTypeId)throws ServiceException;
    
    /**
     * 按天统计申请总数
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @return
     * @throws ServiceException
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getDayTempCount(int beginDate,int endDate) throws ServiceException;
    
    /**
     * 按天统计申请黄牌车总数
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @param plateTypeId
     * @return
     * @throws ServiceException
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getDayYellowTempCount(int beginDate,int endDate,String plateTypeId) throws ServiceException;
    
    
    /**
     * 按照小时统计申请总数
     * <功能详细描述>
     * @param beginDate
     * @return
     * @throws ServiceException
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getHourTempCount(int beginDate) throws ServiceException;
    
    /**
     * 按照小时统计申请黄牌车总数
     * <功能详细描述>
     * @param beginDate
     * @param plateTypeId
     * @return
     * @throws ServiceException
     * @see [类、类#方法、类#成员]
     */
    List<TempCountPo> getHourYellowTempCount(int beginDate,String plateTypeId) throws ServiceException;
    
    /**
     * 臨時通行證圖片編號主键自增
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getTempPassCardSEQ();
    
    /**
     * 查询临时通行证申请数量
     * <一句话功能简述>
     * <功能详细描述>
     * @param sDate
     * @param eDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PassCardAnalyseVo> getTempPassCardAnalyse(int sDate,int eDate);

    void deleteExpirePassCard(String date);
}
