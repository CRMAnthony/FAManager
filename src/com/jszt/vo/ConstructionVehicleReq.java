/*
 * 文 件 名:  ConstructionVehicleReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 渣土车请求入参
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionVehicleReq extends EhsObject implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -9100375025896865372L;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 所属单位
     */
    private String ownerName; 
    
    /**
     * 联系电话
     */
    private String phoneNumber;
    
    /**
     * 车牌号
     */
    private String[] licensePlates;
    
    /**
     * 所屬公司名稱
     */
    private String companyName;
    
    /**
     * 当前页面
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageCount;

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String[] getLicensePlates()
    {
        return licensePlates;
    }

    public void setLicensePlates(String[] licensePlates)
    {
        this.licensePlates = licensePlates;
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
