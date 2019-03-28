/*
 * 文 件 名:  DimViolationSpecialVehicle.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 特殊车辆信息数据字典 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimViolationSpecialVehicle extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1007038635698573955L;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 名单类型（1、表示白名单 2、表示黑名单）
	 */
	private Integer type;

	/**
	 * 车牌号码
	 */
	private String licensePlate;

	/**
	 * 车辆类型 （1、表示大型车 2、表示小型车）
	 */
	private String plateTypeId;

	/**
	 * 重点车辆类型 （1、表示货车） （2、表示危险品车辆） （3、表示黄标车）
	 */
	private String forbidType;

	/**
	 * 开始时间
	 */
	private Integer startDate;

	/**
	 * 结束时间
	 */
	private Integer endDate;

	/**
	 * 车主姓名
	 */
	private String ownerName;

	/**
	 * 联系电话
	 */
	private String phoneNumber;

	/**
	 * @return 返回 id
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param 对id进行赋值
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return 返回 type
	 */
	public Integer getType()
	{
		return type;
	}

	/**
	 * @param 对type进行赋值
	 */
	public void setType(Integer type)
	{
		this.type = type;
	}

	/**
	 * @return 返回 licensePlate
	 */
	public String getLicensePlate()
	{
		return licensePlate;
	}

	/**
	 * @param 对licensePlate进行赋值
	 */
	public void setLicensePlate(String licensePlate)
	{
		this.licensePlate = licensePlate;
	}

	/**
	 * @return 返回 plateTypeId
	 */
	public String getPlateTypeId()
	{
		return plateTypeId;
	}

	/**
	 * @param 对plateTypeId进行赋值
	 */
	public void setPlateTypeId(String plateTypeId)
	{
		this.plateTypeId = plateTypeId;
	}

	/**
	 * @return 返回 forbidType
	 */
	public String getForbidType()
	{
		return forbidType;
	}

	/**
	 * @param 对forbidType进行赋值
	 */
	public void setForbidType(String forbidType)
	{
		this.forbidType = forbidType;
	}

	/**
	 * @return 返回 startDate
	 */
	public Integer getStartDate()
	{
		return startDate;
	}

	/**
	 * @param 对startDate进行赋值
	 */
	public void setStartDate(Integer startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return 返回 endDate
	 */
	public Integer getEndDate()
	{
		return endDate;
	}

	/**
	 * @param 对endDate进行赋值
	 */
	public void setEndDate(Integer endDate)
	{
		this.endDate = endDate;
	}

	/**
	 * @return 返回 ownerName
	 */
	public String getOwnerName()
	{
		return ownerName;
	}

	/**
	 * @param 对ownerName进行赋值
	 */
	public void setOwnerName(String ownerName)
	{
		this.ownerName = ownerName;
	}

	/**
	 * @return 返回 phoneNumber
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param 对phoneNumber进行赋值
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

}
