/*
 * 文 件 名:  DimPassLine.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 通行线路数据 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimPassLine extends EhsObject implements Serializable
{
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -704875442146066651L;

	/**
	 * 通行线路编号
	 */
	private int lineId;

	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 交叉口列表
	 */
	private String intList;

	/**
	 * 设备列表
	 */
	private String deviceList;

	/**
	 * 禁行区域编号
	 */
	private String areaId ;
	
	/**
	 * 操作权限账户(多个用逗号分隔)
	 */
	private String authId;
	
	/**
	 * 临时通行证使用(0:不可用，1：可用)
	 */
	private int tempCardFlag;
	
	/**
	 * 描述
	 */
	private String description;
	
	/**
	 * 线路图片URL(临时通行证使用)
	 */
	private String pictureUrl;

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public int getTempCardFlag() {
		return tempCardFlag;
	}

	public void setTempCardFlag(int tempCardFlag) {
		this.tempCardFlag = tempCardFlag;
	}

	/**
	 * @return 返回 lineId
	 */
	public int getLineId()
	{
		return lineId;
	}

	/**
	 * @param 对lineId进行赋值
	 */
	public void setLineId(int lineId)
	{
		this.lineId = lineId;
	}

	/**
	 * @return 返回 lineName
	 */
	public String getLineName()
	{
		return lineName;
	}

	/**
	 * @param 对lineName进行赋值
	 */
	public void setLineName(String lineName)
	{
		this.lineName = lineName;
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
	 * @return 返回 deviceList
	 */
	public String getDeviceList()
	{
		return deviceList;
	}

	/**
	 * @param 对deviceList进行赋值
	 */
	public void setDeviceList(String deviceList)
	{
		this.deviceList = deviceList;
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
	 * @return 返回 serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}

}
