package com.jszt.vo;

public class ViolationRecordReq {
	/**
     * 查询类型（0-按号牌号码、号牌种类统计，1-按重点车辆类型统计）
     */
    private int analyseType;
	
	/**
     * 查询的开始日期
     */
    private int beginDate;
    
    /**
     * 查询的结束日期
     */
    private int endDate;
    
    

	public int getAnalyseType() {
		return analyseType;
	}

	public void setAnalyseType(int analyseType) {
		this.analyseType = analyseType;
	}

	public int getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(int beginDate) {
		this.beginDate = beginDate;
	}

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}


    
    

}
