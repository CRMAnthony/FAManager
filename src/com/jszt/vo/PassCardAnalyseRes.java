package com.jszt.vo;
/**
 * 通行证查询返回对象 <一句话功能简述> <功能详细描述>
 * 
 * @author lcf
 * @version [版本号, 2017-1-16]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassCardAnalyseRes {
	
	/**
     * 通行证类型（1-临时，2-长期）
     */
    private int cardTypeId;
    
    /**
     * 小时
     */
    private int hour;
    
    /**
     * 申请数量
     */
    private int count;

    /**
     * 临时通行证申请限额
     */
    private int limit;
    
	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getCardTypeId() {
		return cardTypeId;
	}

	public void setCardTypeId(int cardTypeId) {
		this.cardTypeId = cardTypeId;
	}
    
    

}
