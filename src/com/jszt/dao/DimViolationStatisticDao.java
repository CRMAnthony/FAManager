/*
 * 文 件 名:  DimViolationStatisticDao.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年6月1日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jszt.domain.DimViolationStatistic;

/**
 * 违法未通知次数数据
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimViolationStatisticDao
{
    /**
     * 查询不再统计表中的数据(统计次数)
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimViolationStatistic> queryUnStatistic();
    
    /**
     * 查询统计表中数据(统计次数)
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimViolationStatistic> queryStatistic();
    
    /**
     * 向统计表中插入数据
     * @param datas
     * @see [类、类#方法、类#成员]
     */
    void insertStatistic(List<DimViolationStatistic> datas);
    
    /**
     * 更新统计表中数据
     * @param datas
     * @see [类、类#方法、类#成员]
     */
    void updateStatistic(List<DimViolationStatistic> datas);
}
