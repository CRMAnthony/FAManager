/*
 * 文 件 名:  DimPassLlimit.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import com.jsits.commons.domain.EhsObject;

/**
 * 通行额度配置
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimPassLlimit extends EhsObject
{
    /**
     * 时间段
     */
    private int timeKey;
    
    /**
     * 额度
     */
    private int limit;
    
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
    
    
}
