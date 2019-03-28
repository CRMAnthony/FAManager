package com.jszt.vo;

public class LPCameraVo {

	/**
     * 安装位置
     */
    private String installationPosition;
    
    
    /**
     * 设备编号
     */
    private String facilityId;
    
    /**
     * 违法地点编号
     */
    private String facilityAddress;
    
    /**
     * 纬度
     */
    private double latitude;
    
    /**
     * 经度
     */
    private double longitude;

	public String getInstallationPosition() {
		return installationPosition;
	}

	public void setInstallationPosition(String installationPosition) {
		this.installationPosition = installationPosition;
	}

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

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
    
    

}
