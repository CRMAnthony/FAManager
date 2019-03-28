package com.jszt.vo;

public class ViolationCameraVo {
	/**
     * 摄像头编号
     */
    private String lpCameraId;
    
    /**
     * 过车记录图片的链接
     */
    private String licensePlateLink1; 
    
    /**
     * 车牌号码
     */
    private String licensePlate; 
    
    
    /**
     * 时间
     */
    private String time; 
    
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
    
    /**
     * 违法次数
     */
    private int count;
    

	public String getLpCameraId() {
		return lpCameraId;
	}

	public void setLpCameraId(String lpCameraId) {
		this.lpCameraId = lpCameraId;
	}


	public String getInstallationPosition() {
		return installationPosition;
	}

	public void setInstallationPosition(String installationPosition) {
		this.installationPosition = installationPosition;
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

	public String getLicensePlateLink1() {
		return licensePlateLink1;
	}

	public void setLicensePlateLink1(String licensePlateLink1) {
		this.licensePlateLink1 = licensePlateLink1;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
    
}
