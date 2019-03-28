package com.jszt.vo;

public class ViolationPassRecordVo {

	/**
     * 车牌种类
     */
    private String plateTypeId;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 重点车辆类型
     */
    private String forbidType;
    
    /**
     * 次数
     */
    private int count;

	public String getPlateTypeId() {
		return plateTypeId;
	}

	public void setPlateTypeId(String plateTypeId) {
		this.plateTypeId = plateTypeId;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getForbidType() {
		return forbidType;
	}

	public void setForbidType(String forbidType) {
		this.forbidType = forbidType;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
    
    
}
