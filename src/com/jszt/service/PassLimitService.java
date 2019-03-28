/*
 * 文 件 名:  PassLimitService.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimPassLlimit;

/**
 * 通行额度相关业务接口
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PassLimitService
{
	/**
	 * 通行额度配置
	 * <功能详细描述>
	 * @param limit
	 * @see [类、类#方法、类#成员]
	 */
	void addPassLimit(String limit,String plateTypeId,String limitDate,Integer passLineId) throws JsztException;
	
	/**
	 * 初始化查询额度配置
	 * <功能详细描述>
	 * @param plateTypeId 车牌颜色种类
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimPassLlimit> getPassLimit(String plateTypeId,String limitDate,Integer passLineId) throws JsztException;
	
	/**
	 * 查询额度配置总数
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimPassLlimit> getPassLimitSum() throws JsztException;
}
