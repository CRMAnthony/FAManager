/*
 * 文 件 名:  PassLineReq.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通行线路配置请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLineReq
{
	/**
	 * 通行线路编号
	 */
	private int lineId;
	
	/**
	 * 交叉口列表
	 */
	private String intList;
	
	/**
	 * 禁行区域编号
	 */
	private String areaId ;

	/**
	 * 线路名称
	 */
	private String lineName;

	/**
	 * 设备列表安装地点
	 */
	private String devices;
	
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
	
	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getDevices() {
		return devices;
	}

	public void setDevices(String devices) {
		this.devices = devices;
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

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
