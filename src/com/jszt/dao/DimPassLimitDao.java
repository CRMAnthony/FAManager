/*
 * 文 件 名:  DimPassLimitDao.java
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

import com.jszt.domain.DimPassLlimit;
import com.jszt.vo.PassLimitReq;

/**
 * 通行额度配置数据库操作实体
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimPassLimitDao
{
    /**
     * 查询所有的通行额度配置
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassLlimit> queryDimPassLlimit(String plateTypeId,Integer passLineId);
    
    /**
     * 通行额度配置
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void addPassLimit(List<PassLimitReq> reqList);
    
    /**
     * 删除通行额度
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void deletePassLimit(String plateTypeId,Integer passLineId);
    
    /**
     * 查询所有的通行额度配置总数
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassLlimit> queryDimPassLlimitSum();
}
