package com.jszt.dao;

import com.jszt.domain.DimViolationPassRecordHis;

/**
 * 违法过车记录历史表dao
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationPassRecordHisDao
{
    /**
     * 新增违法过车历史记录
     * <一句话功能简述>
     * <功能详细描述>
     * @param recordHis
     * @see [类、类#方法、类#成员]
     */
    void addVioRecordHis(DimViolationPassRecordHis recordHis);
}
