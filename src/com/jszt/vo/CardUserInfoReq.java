/*
 * 文 件 名:  CardUserInfoReq.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月8日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CardUserInfoReq
{
	/**
	 * 车牌号
	 */
	private String licensePlate;

	/**
	 * 车牌颜色id
	 */
	private String plateTypeId;

	/**
	 * 审核状态 0:待秩序科审核 2:审核通过 3:审核不通过
	 */
	private Integer status;
	
	/**
	 * 手机号
	 */
	private String phoneNumber;
	
	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 当前页面
	 */
	private int pageIndex;

	/**
	 * 每页记录数
	 */
	private int pageCount;

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
	 * @return 返回 status
	 */
	public Integer getStatus()
	{
		return status;
	}

	/**
	 * @param 对status进行赋值
	 */
	public void setStatus(Integer status)
	{
		this.status = status;
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
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

}
