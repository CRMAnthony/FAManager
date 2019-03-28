/*
 * 文 件 名:  DimLicenseCamera.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  lvwenbin
 * 修改时间:  2013年11月21日
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 卡口摄像头PO
 * <功能详细描述>
 * 
 * @author  lvwenbin
 * @version  [版本号, 2013年11月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimLicenseCamera extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -3775234406992322805L;
    
    /**
     * 外部卡口摄像头编号
     */
    private String lpCameraNumber;
    
    /**
     * 摄像头编号
     */
    private String lpCameraid;
    
    /**
     * 路网编号
     */
    private String networkId;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 所在路段编号
     */
    private String segmentId;
    
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
    private String networkworkId;
    
    /**
     * 检测车道数
     */
    private int laneNumber;
    
    /**
     * 检测规则
     */
    private String detectRule;
    
    /**
     * 摄像头类型
     */
    private String cameraType;
    
    /**
     * 摄像机像素
     */
    private String pixelValue;
    
    /**
     * 品牌
     */
    private String cameraBrand;
    
    /**
     * 生产厂家
     */
    private String cameraManuf;
    
    /**
     * 行车方向
     */
    private String direction;
    
    /**
     * 监测车道
     */
    private int lanes;
    
    /**
     * 车道类型
     */
    private String laneType;
    
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
    private String installationHeight;
    
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
     * 路口路段公里数
     */
    private String mileMarker;
    
    /**
     * 纬度
     */
    private double latitude;
    
    /**
     * 经度
     */
    private double longitude;
    
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
     * 机房开头情况
     */
    private String openStatus;
    
    /**
     * 管理机构
     */
    private String agencyName;
    
    /**
     * 管理机构编号
     */
    private String agencyId;
    
    /**
     * 摄像头位置描述
     */
    private String description;
    
    /**
     * 视频编号
     */
    private String videoNumber;
    
    /**
     * 设备编号
     */
    private String facilityId;
    
    /**
     * 违法地点代码
     */
    private String facilityAddress;
    
    public String getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(String facilityId) {
		this.facilityId = facilityId;
	}

	public String getFacilityAddress() {
		return facilityAddress;
	}

	public void setFacilityAddress(String facilityAddress) {
		this.facilityAddress = facilityAddress;
	}

	public String getLpCameraNumber()
    {
        return lpCameraNumber;
    }

    public void setLpCameraNumber(String lpCameraNumber)
    {
        this.lpCameraNumber = lpCameraNumber;
    }

    public String getLpCameraid()
    {
        return lpCameraid;
    }

    public void setLpCameraid(String lpCameraid)
    {
        this.lpCameraid = lpCameraid;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getSegmentId()
    {
        return segmentId;
    }

    public void setSegmentId(String segmentId)
    {
        this.segmentId = segmentId;
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

    public int getLaneNumber()
    {
        return laneNumber;
    }

    public void setLaneNumber(int laneNumber)
    {
        this.laneNumber = laneNumber;
    }

    public String getDetectRule()
    {
        return detectRule;
    }

    public void setDetectRule(String detectRule)
    {
        this.detectRule = detectRule;
    }

    public String getCameraType()
    {
        return cameraType;
    }

    public void setCameraType(String cameraType)
    {
        this.cameraType = cameraType;
    }

    public String getPixelValue()
    {
        return pixelValue;
    }

    public void setPixelValue(String pixelValue)
    {
        this.pixelValue = pixelValue;
    }

    public String getCameraBrand()
    {
        return cameraBrand;
    }

    public void setCameraBrand(String cameraBrand)
    {
        this.cameraBrand = cameraBrand;
    }

    public String getCameraManuf()
    {
        return cameraManuf;
    }

    public void setCameraManuf(String cameraManuf)
    {
        this.cameraManuf = cameraManuf;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public int getLanes()
    {
        return lanes;
    }

    public void setLanes(int lanes)
    {
        this.lanes = lanes;
    }

    public String getLaneType()
    {
        return laneType;
    }

    public void setLaneType(String laneType)
    {
        this.laneType = laneType;
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

    public String getInstallationHeight()
    {
        return installationHeight;
    }

    public void setInstallationHeight(String installationHeight)
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

    public String getMileMarker()
    {
        return mileMarker;
    }

    public void setMileMarker(String mileMarker)
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

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
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

	public String getNetworkId() {
		return networkId;
	}

	public void setNetworkId(String networkId) {
		this.networkId = networkId;
	}

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
}
