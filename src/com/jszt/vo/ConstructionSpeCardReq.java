/*
 * 文 件 名:  ConstructionSpeCardReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 渣土车特殊通行证请求入参
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionSpeCardReq extends EhsObject implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 6926680851747991969L;
    
    /**
     * 編號
     */
    private String id;
    
    /**
     * 編號
     */
    private String[] ids;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 所屬公司名稱
     */
    private String companyName;
    
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
    
    /**
     * 当前页面
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageCount;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String[] getIds()
    {
        return ids;
    }

    public void setIds(String[] ids)
    {
        this.ids = ids;
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

    public String getCompanyName()
    {
        return companyName;
    }

    public void setCompanyName(String companyName)
    {
        this.companyName = companyName;
    }

    public int getPageIndex()
    {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    public int getPageCount()
    {
        return pageCount;
    }

    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }
    
}
