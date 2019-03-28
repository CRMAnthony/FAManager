/*
 * 文 件 名:  ViolationSpecialVehicle.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 特殊车辆信息VO <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ViolationSpecialVehicle
{

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 编号
	 */
	private String id;

	/**
	 * 名单类型（1、表示白名单 2、表示黑名单）
	 */
	private Integer type;

	/**
	 * 名单
	 */
	private String typeName;

	/**
	 * 车牌号码
	 */
	private String licensePlate;

	/**
	 * 车辆类型 （1、表示大型车 2、表示小型车）
	 */
	private String plateTypeId;

	/**
	 * 车辆类型名称
	 */
	private String plateTypeName;

	/**
	 * 重点车辆类型 （1、表示货车） （2、表示危险品车辆） （3、表示黄标车）
	 */
	private String forbidType;

	/**
	 * 重点车辆名称
	 */
	private String forbidTypeName;

	/**
	 * 开始时间
	 */
	private String startDate;

	/**
	 * 结束时间
	 */
	private String endDate;

	/**
	 * 车主姓名
	 */
	private String ownerName;

	/**
	 * 联系电话
	 */
	private String phoneNumber;

	/**
	 * @return 返回 status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * @param 对status进行赋值
	 */
	public void setStatus(String status)
	{
		this.status = status;
	}

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
	 * @return 返回 typeName
	 */
	public String getTypeName()
	{
		return typeName;
	}

	/**
	 * @param 对typeName进行赋值
	 */
	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

	/**
	 * @return 返回 plateTypeName
	 */
	public String getPlateTypeName()
	{
		return plateTypeName;
	}

	/**
	 * @param 对plateTypeName进行赋值
	 */
	public void setPlateTypeName(String plateTypeName)
	{
		this.plateTypeName = plateTypeName;
	}

	/**
	 * @return 返回 forbidTypeName
	 */
	public String getForbidTypeName()
	{
		return forbidTypeName;
	}

	/**
	 * @param 对forbidTypeName进行赋值
	 */
	public void setForbidTypeName(String forbidTypeName)
	{
		this.forbidTypeName = forbidTypeName;
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
	public String getStartDate()
	{
		return startDate;
	}

	/**
	 * @param 对startDate进行赋值
	 */
	public void setStartDate(String startDate)
	{
		this.startDate = startDate;
	}

	/**
	 * @return 返回 endDate
	 */
	public String getEndDate()
	{
		return endDate;
	}

	/**
	 * @param 对endDate进行赋值
	 */
	public void setEndDate(String endDate)
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
