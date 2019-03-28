package com.jszt.vo;

public class ViolationTimeReq {

	/**
     * 日期
     */
    private int dateKey;                        
    
    /**
     * 最小时间
     */
    private int minTime; 
    
    /**
     * 最大时间
     */
    private int maxTime; 
    
    /**
     * 日期时间
     */
    private String time;

	public int getDateKey() {
		return dateKey;
	}

	public void setDateKey(int dateKey) {
		this.dateKey = dateKey;
	}

	public int getMinTime() {
		return minTime;
	}

	public void setMinTime(int minTime) {
		this.minTime = minTime;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ViolationTimeReq(int dateKey, int minTime, int maxTime, String time) {
		super();
		this.dateKey = dateKey;
		this.minTime = minTime;
		this.maxTime = maxTime;
		this.time = time;
	}

	public ViolationTimeReq() {
		super();
	} 

    
}
