/*
 * 文 件 名:  DimConstructionVehicle.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 渣土车po
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimConstructionVehicle extends EhsObject implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -4869754969938785517L;
    
    /**
     * 车牌号
     */
    private String licensePlate;       
    
    /**
     * 车牌种类
     */
    private String plateTypeId;        
    
    /**
     * 车辆品牌
     */
    private String vehicleBrand;       
    
    /**
     * 身份证号
     */
    private String idCard; 
    
    /**
     * 车主姓名
     */
    private String ownerName;          
    
    /**
     * 联系电话
     */
    private String phoneNumber;  
    
    /**
     * 地址
     */
    private String address;            
    
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
     * 登记老板
     */
    private String registerPeople;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 公司名稱
     */
    private String companyName;

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    public String getVehicleBrand()
    {
        return vehicleBrand;
    }

    public void setVehicleBrand(String vehicleBrand)
    {
        this.vehicleBrand = vehicleBrand;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
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

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
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

    public String getRegisterPeople()
    {
        return registerPeople;
    }

    public void setRegisterPeople(String registerPeople)
    {
        this.registerPeople = registerPeople;
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
    
}
