/*
 * 文 件 名:  PassLimitRequest.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 通行额度请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLimitRequest
{
    /**
     * 通行额度
     */
    private String limit;
    
    /**
     * 车牌颜色类型id
     */
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
    
    /**
     * @return 返回 limit
     */
    public String getLimit()
    {
        return limit;
    }
    
    /**
     * @param 对limit进行赋值
     */
    public void setLimit(String limit)
    {
        this.limit = limit;
    }
    
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

	public String getLimitDate() {
		return limitDate;
	}

	public void setLimitDate(String limitDate) {
		this.limitDate = limitDate;
	}
    
    
}
