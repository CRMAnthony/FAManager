/*
 * 文 件 名:  DimSurveillanceCamera.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年11月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

/**
 * 视频监控PO
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年11月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimSurveillanceCamera
{
    /**
     * 外部视频监控摄像头编号
     */
    private String surCameraNumber;
    
    /**
     * 摄像机编号
     */
    private String surCameraId;
    
    /**
     * 路网编号
     */
    private String networkId;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 路段/交叉口编号
     */
    private String segIntId;
    
    /**
     * 所在路线编号
     */
    private String roadId;
    
    /**
     * 主干道编号
     */
    private String mainlineId;
    
    /**
     * 路网编号
     */
    private String networkworkId;
    
    /**
     * 摄像机型号
     */
    private String surCameraType;
    
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
    private double installationHeight;
    
    /**
     * 立柱
     */
    private String pillarHeight;
    
    /**
     * 横臂
     */
    private String crossarmLength;
    
    /**
     * 安装位置
     */
    private String installationPosition;
    
    /**
     * 安装位置拼音
     */
    private String positionSpell;
    
    /**
     * 路口路段或者公里数
     */
    private double mileMarker;
    
    /**
     * 纬度
     */
    private double latitude;
    
    /**
     * 经度
     */
    private double longitude;
    
    /**
     * 检测区域
     */
    private String surCameraArea;
    
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
    private String getway;
    
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
    private int openDete;
    
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
     * 视频通道编号
     */
    private int channel;
    
    /**
     * 设备编号
     */
    private String facilityId;
    
    /**
     * 违法地点编号
     */
    private String facilityAddress;
    

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

    public String getSurCameraNumber()
    {
        return surCameraNumber;
    }

    public void setSurCameraNumber(String surCameraNumber)
    {
        this.surCameraNumber = surCameraNumber;
    }

    public String getSurCameraId()
    {
        return surCameraId;
    }

    public void setSurCameraId(String surCameraId)
    {
        this.surCameraId = surCameraId;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getSegIntId()
    {
        return segIntId;
    }

    public void setSegIntId(String segIntId)
    {
        this.segIntId = segIntId;
    }

    public String getRoadId()
    {
        return roadId;
    }

    public void setRoadId(String roadId)
    {
        this.roadId = roadId;
    }

    public String getMainlineId()
    {
        return mainlineId;
    }

    public void setMainlineId(String mainlineId)
    {
        this.mainlineId = mainlineId;
    }

    public String getNetworkworkId()
    {
        return networkworkId;
    }

    public void setNetworkworkId(String networkworkId)
    {
        this.networkworkId = networkworkId;
    }

    public String getSurCameraType()
    {
        return surCameraType;
    }

    public void setSurCameraType(String surCameraType)
    {
        this.surCameraType = surCameraType;
    }

    public int getInstallationDate()
    {
        return installationDate;
    }

    public void setInstallationDate(int installationDate)
    {
        this.installationDate = installationDate;
    }

    public String getInstallationMode()
    {
        return installationMode;
    }

    public void setInstallationMode(String installationMode)
    {
        this.installationMode = installationMode;
    }

    public double getInstallationHeight()
    {
        return installationHeight;
    }

    public void setInstallationHeight(double installationHeight)
    {
        this.installationHeight = installationHeight;
    }

    public String getPillarHeight()
    {
        return pillarHeight;
    }

    public void setPillarHeight(String pillarHeight)
    {
        this.pillarHeight = pillarHeight;
    }

    public String getCrossarmLength()
    {
        return crossarmLength;
    }

    public void setCrossarmLength(String crossarmLength)
    {
        this.crossarmLength = crossarmLength;
    }

    public String getInstallationPosition()
    {
        return installationPosition;
    }

    public void setInstallationPosition(String installationPosition)
    {
        this.installationPosition = installationPosition;
    }
    
    public String getPositionSpell()
    {
        return positionSpell;
    }

    public void setPositionSpell(String positionSpell)
    {
        this.positionSpell = positionSpell;
    }

    public double getMileMarker()
    {
        return mileMarker;
    }

    public void setMileMarker(double mileMarker)
    {
        this.mileMarker = mileMarker;
    }

    public double getLatitude()
    {
        return latitude;
    }

    public void setLatitude(double latitude)
    {
        this.latitude = latitude;
    }

    public double getLongitude()
    {
        return longitude;
    }

    public void setLongitude(double longitude)
    {
        this.longitude = longitude;
    }

    public String getSurCameraArea()
    {
        return surCameraArea;
    }

    public void setSurCameraArea(String surCameraArea)
    {
        this.surCameraArea = surCameraArea;
    }

    public String getPowerMode()
    {
        return powerMode;
    }

    public void setPowerMode(String powerMode)
    {
        this.powerMode = powerMode;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getGetway()
    {
        return getway;
    }

    public void setGetway(String getway)
    {
        this.getway = getway;
    }

    public String getAddressTermination()
    {
        return addressTermination;
    }

    public void setAddressTermination(String addressTermination)
    {
        this.addressTermination = addressTermination;
    }

    public String getOpenStatus()
    {
        return openStatus;
    }

    public void setOpenStatus(String openStatus)
    {
        this.openStatus = openStatus;
    }

    public int getOpenDete()
    {
        return openDete;
    }

    public void setOpenDete(int openDete)
    {
        this.openDete = openDete;
    }

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getVideoNumber()
    {
        return videoNumber;
    }

    public void setVideoNumber(String videoNumber)
    {
        this.videoNumber = videoNumber;
    }

    public int getChannel()
    {
        return channel;
    }

    public void setChannel(int channel)
    {
        this.channel = channel;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }
}
