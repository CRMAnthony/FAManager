/*
 * 文 件 名:  PassLimitReq.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 通行额度配置请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLimitReq
{
    /**
     * 时间段 （传入时间范围从（0:00开始 23:00结束 24个时间段）0表示0:00-1:00,100表示1:00-2:00
     */
    private int timeKey;
    
    /**
     * 额度
     */
    private int limit;
    
    
    private String plateTypeId;
    
    private String limitDate;
    
    /**
     * 通行线路ID
     */
    private Integer passLineId;

    public Integer getPassLineId() {
		return passLineId;
	}

	public void setPassLineId(Integer passLineId) {
		this.passLineId = passLineId;
	}
    
	public PassLimitReq(int i, Integer valueOf,String plateTypeId,Integer passLineId)
	{
		this.timeKey=i;
		this.limit=valueOf;
		this.plateTypeId = plateTypeId;
		this.passLineId = passLineId;
	}
	
	public PassLimitReq(int i, Integer valueOf,String plateTypeId,String limitDate,Integer passLineId)
	{
		this.timeKey=i;
		this.limit=valueOf;
		this.plateTypeId = plateTypeId;
		this.limitDate = limitDate;
		this.passLineId = passLineId;
	}

	/**
     * @return 返回 timeKey
     */
    public int getTimeKey()
    {
        return timeKey;
    }
    
    /**
     * @param 对timeKey进行赋值
     */
    public void setTimeKey(int timeKey)
    {
        this.timeKey = timeKey;
    }
    
    /**
     * @return 返回 limit
     */
    public int getLimit()
    {
        return limit;
    }
    
    /**
     * @param 对limit进行赋值
     */
    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    /**
     * @return 返回 plateTypeId
     */
    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    /**
     * @param 对plateTypeId进行赋值
     */
    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
    
    
}
