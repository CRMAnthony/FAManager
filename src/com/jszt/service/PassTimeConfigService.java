/*
 * 文 件 名:  PassTimeConfigService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.util.List;

import com.jszt.vo.PassTimeConfig;

/**
 * 通行时段相关业务接口
 * <功能详细描述>
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PassTimeConfigService
{
    /**
     * 根据时间段查询通行时段配额
     * @param startDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PassTimeConfig> QueryPassTimeConfigByDate(int startDate, int endDate, String plateTypeId, Integer passLineId);
}
