package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimLongPassCard;
import com.jszt.vo.PassCardAnalyseVo;
import com.jszt.vo.PassCardReq;

/**
 * 
 * 长期通行证数据库操作接口
 * @author  gxj
 * @version  [版本号, 2015年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimLongPassCardDao
{
    /**
     * 查询长期通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @param passCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimLongPassCard> getLongPassCards(PassCardReq passCardReq);
    
    /**
     * 根据编号获取通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @param cardId
     * @return
     * @see [类、类#方法、类#成员]
     */
    DimLongPassCard getLongPassCardById(String cardId);
    
    /**
     * 获取某一天有效的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimLongPassCard> getLongValidPassCards(int date);
    
    /**
     * 录入长期通行证
     * @param dimLongPassCard
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void addLongPassCard(DimLongPassCard dimLongPassCard);
    
    /**
     * 秩序科审批
     * <一句话功能简述>
     * <功能详细描述>
     * @param longPassCard
     * @see [类、类#方法、类#成员]
     */
    void updateZXKProve(DimLongPassCard longPassCard);
    
    /**
     * 大队领导审批
     * <一句话功能简述>
     * <功能详细描述>
     * @param longPassCard
     * @see [类、类#方法、类#成员]
     */
    void updateDDProve(DimLongPassCard longPassCard);
    
    /**
     * 通行证打印
     * <一句话功能简述>
     * <功能详细描述>
     * @param longPassCard
     * @see [类、类#方法、类#成员]
     */
    void updateLongPrint(DimLongPassCard longPassCard);
    
    /**
     * 获取与某一段时间内有交叉的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @param sDate
     * @param eDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimLongPassCard> getLongCardByInterval(int sDate,int eDate);
    
    /**
     * 更新长期通行证时间
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void updateLongPassCard (PassCardReq req);
    
    /**
     * 查询长期通行证延长历史
     * @param cardId 当前通行证编号
     * @return
     */
    List<DimLongPassCard> queryLongPassCardExtendHis(String cardId);
    
    /**
     * 長期通行證圖片編號主键自增
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getLongPassCardSEQ();
    
    /**
     * 查询长期通行证申请数量
     * <一句话功能简述>
     * <功能详细描述>
     * @param passCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PassCardAnalyseVo> getLongPassCardAnalyse(int sDate,int eDate);
    
    /**
     * 更新指定长期通行证中的auth_id字段
     * @param noAuthLineIds
     * @param authId
     */
    void updateLongPassCardAuthId(List<Integer> noAuthLineIds, String authId);
    
    /**
     * 更新指定长期通行证中的auth_id字段
     * @param lineId
     * @param authId
     */
    void updateLongPassCardByReduceAuthId(Integer lineId, String authId);
}
