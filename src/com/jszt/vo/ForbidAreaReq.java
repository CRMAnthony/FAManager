/*
 * 文 件 名:  ForbidAreaReq.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 禁行区域请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidAreaReq
{
	/**
	 * 区域编号
	 */
	private int areaId ;
	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 区域类型0:货车类型1:黄标车类型
	 */
	private int areaType;

	/**
	 * 交叉口列表
	 */
	private String intList;

	/**
	 * 设备列表安装地点
	 */
	private String devices;

	/**
	 * 区域范围
	 */
	private String areaPoints;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 账号
	 */
	private String accountList;

	/**
	 * @return 返回 areaName
	 */
	public String getAreaName()
	{
		return areaName;
	}

	/**
	 * @param 对areaName进行赋值
	 */
	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

	/**
	 * @return 返回 areaType
	 */
	public int getAreaType()
	{
		return areaType;
	}

	/**
	 * @param 对areaType进行赋值
	 */
	public void setAreaType(int areaType)
	{
		this.areaType = areaType;
	}

	/**
	 * @return 返回 intList
	 */
	public String getIntList()
	{
		return intList;
	}

	/**
	 * @param 对intList进行赋值
	 */
	public void setIntList(String intList)
	{
		this.intList = intList;
	}

	/**
	 * @return 返回 areaPoints
	 */
	public String getAreaPoints()
	{
		return areaPoints;
	}

	/**
	 * @param 对areaPoints进行赋值
	 */
	public void setAreaPoints(String areaPoints)
	{
		this.areaPoints = areaPoints;
	}

	/**
	 * @return 返回 description
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @param 对description进行赋值
	 */
	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return 返回 accountList
	 */
	public String getAccountList()
	{
		return accountList;
	}

	/**
	 * @param 对accountList进行赋值
	 */
	public void setAccountList(String accountList)
	{
		this.accountList = accountList;
	}

	/**
	 * @return 返回 devices
	 */
	public String getDevices()
	{
		return devices;
	}

	/**
	 * @param 对devices进行赋值
	 */
	public void setDevices(String devices)
	{
		this.devices = devices;
	}

	
	/**
	 * @return 返回 areaId
	 */
	public int getAreaId()
	{
		return areaId;
	}

	/**
	 * @param 对areaId进行赋值
	 */
	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
	}

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
