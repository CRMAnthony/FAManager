/*
 * 文 件 名:  QueryPassTimeConfigReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import com.jszt.boulevard.webservice.bean.Request;

/**
 * 查询通行时段配额请求类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class QueryPassTimeConfigReq extends Request
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 5689949454028355653L;

    /**
     * 起始时间
     */
    private int startDate;
    
    /**
     * 结束时间
     */
    private int endDate;
    
    /**
     * 车牌种类编号
     */
    private String plateTypeId;
    
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

    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    /**
     * @return 返回 startDate
     */
    public int getStartDate()
    {
        return startDate;
    }

    /**
     * @param 对startDate进行赋值
     */
    public void setStartDate(int startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return 返回 endDate
     */
    public int getEndDate()
    {
        return endDate;
    }

    /**
     * @param 对endDate进行赋值
     */
    public void setEndDate(int endDate)
    {
        this.endDate = endDate;
    }
    
    
}
