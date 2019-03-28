/*
 * 文 件 名:  DimElectricPoliceCamera.java
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
 * 电子警察基本信息 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimElectricPoliceCamera extends EhsObject implements Serializable
{
	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = 1550322704733758939L;

	/**
	 * 外部电子警察编号
	 */
	private String electricPoliceNumber;

	/**
	 * ELECTRIC_POLICE_ID
	 */
	private String electricPoliceId;

	/**
	 * 设备类型
	 */
	private char deviceType;

	/**
	 * 所在交叉口编号
	 */
	private String intersectionid;

	/**
	 * 进口道编号
	 */
	private String entranceid;

	/**
	 * 检测车道数
	 */
	private int laneNumber;

	/**
	 * 检测规则
	 */
	private String detectRule;

	/**
	 * 所在路线编号
	 */
	private String roadId;

	/**
	 * 主干道编号
	 */
	private String mainlineId;

	/**
	 * 所在路网编号
	 */
	private String networkId;

	/**
	 * 获取方式
	 */
	private char accessWay;

	/**
	 * 摄像机像素
	 */
	private String pixelValue;

	/**
	 * 安装日期
	 */
	private int installationDate;

	/**
	 * 安装方式
	 */
	private String installationMode;

	/**
	 * 安装高度
	 */
	private int installationHeight;

	/**
	 * 立柱
	 */
	private String pillarHeight;

	/**
	 * 横臂
	 */
	private String crossarmlength;

	/**
	 * 安装位置
	 */
	private String installationPosition;

	/**
	 * 路口路段或公里数
	 */
	private int mileMarker;

	/**
	 * 纬度，与GIS匹配
	 */
	private int latitude;

	/**
	 * 经度，与GIS匹配
	 */
	private int longitude;

	/**
	 * 取电方式
	 */
	private String powerMode;

	/**
	 * 通信地址
	 */
	private String address;

	/**
	 * 网关
	 */
	private String getWay;

	/**
	 * 原始IP终止地址
	 */
	private String addressTermination;

	/**
	 * 机房开通情况
	 */
	private String openStatus;

	/**
	 * 开通日期
	 */
	private int openDate;

	/**
	 * 管理机构
	 */
	private String agencyId;

	/**
	 * 状态描述
	 */
	private String description;

	/**
	 * 视频编号
	 */
	private String videoNumber;

	/**
	 * 通道号
	 */
	private int chanNel;

	/**
	 * 设备编号
	 */
	private String facilityId;

	/**
	 * 设备地点
	 */
	private String facilityAddress;

	/**
	 * 缩写
	 */
	private String positionSpell;

	/**
	 * @return 返回 electricPoliceNumber
	 */
	public String getElectricPoliceNumber()
	{
		return electricPoliceNumber;
	}

	/**
	 * @param 对electricPoliceNumber进行赋值
	 */
	public void setElectricPoliceNumber(String electricPoliceNumber)
	{
		this.electricPoliceNumber = electricPoliceNumber;
	}

	/**
	 * @return 返回 electricPoliceId
	 */
	public String getElectricPoliceId()
	{
		return electricPoliceId;
	}

	/**
	 * @param 对electricPoliceId进行赋值
	 */
	public void setElectricPoliceId(String electricPoliceId)
	{
		this.electricPoliceId = electricPoliceId;
	}

	/**
	 * @return 返回 deviceType
	 */
	public char getDeviceType()
	{
		return deviceType;
	}

	/**
	 * @param 对deviceType进行赋值
	 */
	public void setDeviceType(char deviceType)
	{
		this.deviceType = deviceType;
	}

	/**
	 * @return 返回 intersectionid
	 */
	public String getIntersectionid()
	{
		return intersectionid;
	}

	/**
	 * @param 对intersectionid进行赋值
	 */
	public void setIntersectionid(String intersectionid)
	{
		this.intersectionid = intersectionid;
	}

	/**
	 * @return 返回 entranceid
	 */
	public String getEntranceid()
	{
		return entranceid;
	}

	/**
	 * @param 对entranceid进行赋值
	 */
	public void setEntranceid(String entranceid)
	{
		this.entranceid = entranceid;
	}

	/**
	 * @return 返回 laneNumber
	 */
	public int getLaneNumber()
	{
		return laneNumber;
	}

	/**
	 * @param 对laneNumber进行赋值
	 */
	public void setLaneNumber(int laneNumber)
	{
		this.laneNumber = laneNumber;
	}

	/**
	 * @return 返回 detectRule
	 */
	public String getDetectRule()
	{
		return detectRule;
	}

	/**
	 * @param 对detectRule进行赋值
	 */
	public void setDetectRule(String detectRule)
	{
		this.detectRule = detectRule;
	}

	/**
	 * @return 返回 roadId
	 */
	public String getRoadId()
	{
		return roadId;
	}

	/**
	 * @param 对roadId进行赋值
	 */
	public void setRoadId(String roadId)
	{
		this.roadId = roadId;
	}

	/**
	 * @return 返回 mainlineId
	 */
	public String getMainlineId()
	{
		return mainlineId;
	}

	/**
	 * @param 对mainlineId进行赋值
	 */
	public void setMainlineId(String mainlineId)
	{
		this.mainlineId = mainlineId;
	}

	/**
	 * @return 返回 networkId
	 */
	public String getNetworkId()
	{
		return networkId;
	}

	/**
	 * @param 对networkId进行赋值
	 */
	public void setNetworkId(String networkId)
	{
		this.networkId = networkId;
	}

	/**
	 * @return 返回 accessWay
	 */
	public char getAccessWay()
	{
		return accessWay;
	}

	/**
	 * @param 对accessWay进行赋值
	 */
	public void setAccessWay(char accessWay)
	{
		this.accessWay = accessWay;
	}

	/**
	 * @return 返回 pixelValue
	 */
	public String getPixelValue()
	{
		return pixelValue;
	}

	/**
	 * @param 对pixelValue进行赋值
	 */
	public void setPixelValue(String pixelValue)
	{
		this.pixelValue = pixelValue;
	}

	/**
	 * @return 返回 installationDate
	 */
	public int getInstallationDate()
	{
		return installationDate;
	}

	/**
	 * @param 对installationDate进行赋值
	 */
	public void setInstallationDate(int installationDate)
	{
		this.installationDate = installationDate;
	}

	/**
	 * @return 返回 installationMode
	 */
	public String getInstallationMode()
	{
		return installationMode;
	}

	/**
	 * @param 对installationMode进行赋值
	 */
	public void setInstallationMode(String installationMode)
	{
		this.installationMode = installationMode;
	}

	/**
	 * @return 返回 installationHeight
	 */
	public int getInstallationHeight()
	{
		return installationHeight;
	}

	/**
	 * @param 对installationHeight进行赋值
	 */
	public void setInstallationHeight(int installationHeight)
	{
		this.installationHeight = installationHeight;
	}

	/**
	 * @return 返回 pillarHeight
	 */
	public String getPillarHeight()
	{
		return pillarHeight;
	}

	/**
	 * @param 对pillarHeight进行赋值
	 */
	public void setPillarHeight(String pillarHeight)
	{
		this.pillarHeight = pillarHeight;
	}

	/**
	 * @return 返回 crossarmlength
	 */
	public String getCrossarmlength()
	{
		return crossarmlength;
	}

	/**
	 * @param 对crossarmlength进行赋值
	 */
	public void setCrossarmlength(String crossarmlength)
	{
		this.crossarmlength = crossarmlength;
	}

	/**
	 * @return 返回 installationPosition
	 */
	public String getInstallationPosition()
	{
		return installationPosition;
	}

	/**
	 * @param 对installationPosition进行赋值
	 */
	public void setInstallationPosition(String installationPosition)
	{
		this.installationPosition = installationPosition;
	}

	/**
	 * @return 返回 mileMarker
	 */
	public int getMileMarker()
	{
		return mileMarker;
	}

	/**
	 * @param 对mileMarker进行赋值
	 */
	public void setMileMarker(int mileMarker)
	{
		this.mileMarker = mileMarker;
	}

	/**
	 * @return 返回 latitude
	 */
	public int getLatitude()
	{
		return latitude;
	}

	/**
	 * @param 对latitude进行赋值
	 */
	public void setLatitude(int latitude)
	{
		this.latitude = latitude;
	}

	/**
	 * @return 返回 longitude
	 */
	public int getLongitude()
	{
		return longitude;
	}

	/**
	 * @param 对longitude进行赋值
	 */
	public void setLongitude(int longitude)
	{
		this.longitude = longitude;
	}

	/**
	 * @return 返回 powerMode
	 */
	public String getPowerMode()
	{
		return powerMode;
	}

	/**
	 * @param 对powerMode进行赋值
	 */
	public void setPowerMode(String powerMode)
	{
		this.powerMode = powerMode;
	}

	/**
	 * @return 返回 address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param 对address进行赋值
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return 返回 getWay
	 */
	public String getGetWay()
	{
		return getWay;
	}

	/**
	 * @param 对getWay进行赋值
	 */
	public void setGetWay(String getWay)
	{
		this.getWay = getWay;
	}

	/**
	 * @return 返回 addressTermination
	 */
	public String getAddressTermination()
	{
		return addressTermination;
	}

	/**
	 * @param 对addressTermination进行赋值
	 */
	public void setAddressTermination(String addressTermination)
	{
		this.addressTermination = addressTermination;
	}

	/**
	 * @return 返回 openStatus
	 */
	public String getOpenStatus()
	{
		return openStatus;
	}

	/**
	 * @param 对openStatus进行赋值
	 */
	public void setOpenStatus(String openStatus)
	{
		this.openStatus = openStatus;
	}

	/**
	 * @return 返回 openDate
	 */
	public int getOpenDate()
	{
		return openDate;
	}

	/**
	 * @param 对openDate进行赋值
	 */
	public void setOpenDate(int openDate)
	{
		this.openDate = openDate;
	}

	/**
	 * @return 返回 agencyId
	 */
	public String getAgencyId()
	{
		return agencyId;
	}

	/**
	 * @param 对agencyId进行赋值
	 */
	public void setAgencyId(String agencyId)
	{
		this.agencyId = agencyId;
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
	 * @return 返回 videoNumber
	 */
	public String getVideoNumber()
	{
		return videoNumber;
	}

	/**
	 * @param 对videoNumber进行赋值
	 */
	public void setVideoNumber(String videoNumber)
	{
		this.videoNumber = videoNumber;
	}

	/**
	 * @return 返回 chanNel
	 */
	public int getChanNel()
	{
		return chanNel;
	}

	/**
	 * @param 对chanNel进行赋值
	 */
	public void setChanNel(int chanNel)
	{
		this.chanNel = chanNel;
	}

	/**
	 * @return 返回 facilityId
	 */
	public String getFacilityId()
	{
		return facilityId;
	}

	/**
	 * @param 对facilityId进行赋值
	 */
	public void setFacilityId(String facilityId)
	{
		this.facilityId = facilityId;
	}

	/**
	 * @return 返回 facilityAddress
	 */
	public String getFacilityAddress()
	{
		return facilityAddress;
	}

	/**
	 * @param 对facilityAddress进行赋值
	 */
	public void setFacilityAddress(String facilityAddress)
	{
		this.facilityAddress = facilityAddress;
	}

	/**
	 * @return 返回 positionSpell
	 */
	public String getPositionSpell()
	{
		return positionSpell;
	}

	/**
	 * @param 对positionSpell进行赋值
	 */
	public void setPositionSpell(String positionSpell)
	{
		this.positionSpell = positionSpell;
	}

	/**
	 * @return 返回 serialversionuid
	 */
	public static long getSerialversionuid()
	{
		return serialVersionUID;
	}
	
	
}
