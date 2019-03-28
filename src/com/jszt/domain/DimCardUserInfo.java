/*
 * 文 件 名:  DimCardUserInfo.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 通行证绑定信息 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimCardUserInfo extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -638118564424039570L;

	/**
	 * 主键 UUID
	 */
	private String id;

	/**
	 * 车牌号
	 */
	private String licensePlate;

	/**
	 * 车牌颜色id
	 */
	private String plateTypeId;

	/**
	 * 驾驶员姓名
	 */
	private String ownerName;

	/**
	 * 手机号
	 */
	private String phoneNumber;

	/**
	 * 身份证号
	 */
	private String idCard;

	/**
	 * 行驶证url
	 */
	private String xszUrl;

	/**
	 * 驾驶证url
	 */
	private String jszUrl;

	/**
	 * 审核状态 0:待秩序科审核 2:审核通过 3:审核不通过
	 */
	private Integer status;

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

	/**
	 * @return 返回 idCard
	 */
	public String getIdCard()
	{
		return idCard;
	}

	/**
	 * @param 对idCard进行赋值
	 */
	public void setIdCard(String idCard)
	{
		this.idCard = idCard;
	}

	/**
	 * @return 返回 xszUrl
	 */
	public String getXszUrl()
	{
		return xszUrl;
	}

	/**
	 * @param 对xszUrl进行赋值
	 */
	public void setXszUrl(String xszUrl)
	{
		this.xszUrl = xszUrl;
	}

	/**
	 * @return 返回 jszUrl
	 */
	public String getJszUrl()
	{
		return jszUrl;
	}

	/**
	 * @param 对jszUrl进行赋值
	 */
	public void setJszUrl(String jszUrl)
	{
		this.jszUrl = jszUrl;
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

}
