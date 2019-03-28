/*
 * 文 件 名:  SpecialVehicleReq.java
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
 * 特殊车辆信息请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SpecialVehicleReq
{
	/**
	 * 名单类型
	 */
	private Integer type;

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
	 * 车牌号码
	 */
	private String licensePlate;

	/**
	 * 车辆类型（“1”表示大型车辆，“2”表示小型车辆）
	 */
	private String plateTypeId;

	/**
	 * 重点车辆类型（1、表示货车） （2、表示危险品车辆） （3、表示黄标车）
	 */
	private String forbidType;

	/**
	 * 当前页面
	 */
	private int pageIndex;

	/**
	 * 每页记录数
	 */
	private int pageCount;

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
	 * @return 返回 ownName
	 */
	public String getOwnerName()
	{
		return ownerName;
	}

	/**
	 * @param 对ownName进行赋值
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
	 * @return 返回 pageIndex
	 */
	public int getPageIndex()
	{
		return pageIndex;
	}

	/**
	 * @param 对pageIndex进行赋值
	 */
	public void setPageIndex(int pageIndex)
	{
		this.pageIndex = pageIndex;
	}

	/**
	 * @return 返回 pageCount
	 */
	public int getPageCount()
	{
		return pageCount;
	}

	/**
	 * @param 对pageCount进行赋值
	 */
	public void setPageCount(int pageCount)
	{
		this.pageCount = pageCount;
	}

}
