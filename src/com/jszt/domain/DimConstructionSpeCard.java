/*
 * 文 件 名:  DimConstructionSpeCard.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 渣土车特殊通行证PO
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimConstructionSpeCard extends EhsObject implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7917438240271938179L;
    
    /**
     * 主鍵序列
     */
    private String id;
    
    /**
     * 车牌号
     */
    private String licensePlate; 
    
    /**
     * 登记日期
     */
    private int registerDate;
    
    /**
     * 开始日期
     */
    private int beginDate;
    
    /**
     * 结束日期
     */
    private int endDate;
    
    /**
     * 通行时段
     */
    private String passPeriod; 
    
    /**
     * 描述
     */
    private String description;
    
    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public int getRegisterDate()
    {
        return registerDate;
    }

    public void setRegisterDate(int registerDate)
    {
        this.registerDate = registerDate;
    }

    public int getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(int beginDate)
    {
        this.beginDate = beginDate;
    }

    public int getEndDate()
    {
        return endDate;
    }

    public void setEndDate(int endDate)
    {
        this.endDate = endDate;
    }

    public String getPassPeriod()
    {
        return passPeriod;
    }

    public void setPassPeriod(String passPeriod)
    {
        this.passPeriod = passPeriod;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
