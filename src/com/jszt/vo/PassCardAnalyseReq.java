package com.jszt.vo;
/**
 * 通行证查询请求对象 <一句话功能简述> <功能详细描述>
 * 
 * @author lcf
 * @version [版本号, 2017-1-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassCardAnalyseReq {
	/**
     * 通行证类型（1-临时，2-长期）
     */
    private Integer cardTypeId;
	
	/**
     * 查询的开始日期
     */
    private Integer beginDate;
    
    /**
     * 查询的结束日期
     */
    private Integer endDate;

    
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

	public int getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(int cardTypeId) {
		this.cardTypeId = cardTypeId;
	}



    
}
