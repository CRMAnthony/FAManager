package com.jszt.service;

import com.jszt.client.PassRecord;
import com.jszt.domain.DimTempPassCard;

/**
 * 违法过车记录提取
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface VioPassRecordService
{
    /**
     * 提取违法过车记录并保存
     * <一句话功能简述>
     * <功能详细描述>
     * @param passRecord
     * @see [类、类#方法、类#成员]
     */
    void addVioPassRecord(PassRecord passRecord);
    
    /**
     * 程序开始运行时初始化有效的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void initValidCard();
    
    /**
     * 更新某一天有效的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void updateValidCard();
    
    /**
     * 同步有效的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void syncValidCard();
    
    /**
     * 新增临时通行证缓存
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void updateTempValidCard(DimTempPassCard tempPassCard);
    
    /**
     * 删除过车记录中临时通行证
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void delViolationTempPassCard();
    
}
