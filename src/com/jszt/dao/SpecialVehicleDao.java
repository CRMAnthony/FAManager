/*
 * 文 件 名:  SepicalVehiclesDao.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.vo.SpecialVehicleReq;

/**
 * 特殊车辆信息Dao层
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2015年8月13日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface SpecialVehicleDao
{
	/**
	 * 特殊车辆信息查询
	 * <功能详细描述>
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DimViolationSpecialVehicle> getSpecialVehicle();
	
	/**
	 * 获取货车的黑白名单
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DimViolationSpecialVehicle> getSpecialTruck();
	
	/**
	 * 新增特殊车辆信息
	 * <功能详细描述>
	 * @param req
	 * @see [类、类#方法、类#成员]
	 */
	void addSpecialVehicle (SpecialVehicleReq req);
	
	/**
	 * 删除特殊车辆信息
	 * <功能详细描述>
	 * @param req
	 * @see [类、类#方法、类#成员]
	 */
	void deleteSpecialVehicle(String [] id);
	
	/**
	 * 根据时间查询特殊车辆信息
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DimViolationSpecialVehicle> getSpecialVehicleByTime(SpecialVehicleReq req);
	
	
}
