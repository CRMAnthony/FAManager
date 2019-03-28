/*
 * 文 件 名:  SepicalVehicleService.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.jszt.vo.SpecialVehicleReq;
import com.jszt.vo.ViolationSpecialVehicle;

/**
 * 特殊车辆信息service层 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface SpecialVehicleService
{
	/**
	 * 获得特殊车辆信息
	 */
	List<ViolationSpecialVehicle> getSpecialVehicle(SpecialVehicleReq req);

	/**
	 * 新增特殊车辆信息
	 */
	void addSpecialVehicle(SpecialVehicleReq req);

	/**
	 * 删除特殊车辆信息
	 */
	void deleteSpecialVehicle(ViolationSpecialVehicle req);

	/**
	 * 特殊车辆信息Excel封装
	 */
	ByteArrayOutputStream buildSpecialVehicleExcelBody(List<ViolationSpecialVehicle> voList);

}
