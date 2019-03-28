/*
 * 文 件 名:  ForbidAreaVo.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年12月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.jsits.commons.domain.EhsObject;

/**
 * 通行线路Vo信息 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年12月3日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLineVo extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -748550919778098668L;

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
	 * 禁行区域编号
	 */
	private String areaId ;
	
	/**
	 * 操作权限账户(多个用逗号分隔)
	 */
	private String authId;

	/**
	 * 禁行区域描述
	 */
	private String description;

	/**
	 * 临时通行证使用(0:不可用，1：可用)
	 */
	private int tempCardFlag;
	
	/**
	 * 设备列表
	 */
	private String deviceList;

	/**
	 * 设备以及部门集合
	 */
	private List<Map<String, String>> deivceList;

	public String getDeviceList() {
		return deviceList;
	}

	public void setDeviceList(String deviceList) {
		this.deviceList = deviceList;
	}

	public List<Map<String, String>> getDeivceList() {
		return deivceList;
	}

	public void setDeivceList(List<Map<String, String>> deivceList) {
		this.deivceList = deivceList;
	}

	public int getLineId() {
		return lineId;
	}

	public void setLineId(int lineId) {
		this.lineId = lineId;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getIntList() {
		return intList;
	}

	public void setIntList(String intList) {
		this.intList = intList;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getTempCardFlag() {
		return tempCardFlag;
	}

	public void setTempCardFlag(int tempCardFlag) {
		this.tempCardFlag = tempCardFlag;
	}

}
