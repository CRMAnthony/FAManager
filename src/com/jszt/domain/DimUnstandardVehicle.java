/**
 * 
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
*  黄标车实体
* @author      gxj
* @see        [相关类/方法]
* @since      [产品/模块版本] 
*/
public class DimUnstandardVehicle extends EhsObject implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -8921854427367293578L;
    
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
     * 车主姓名
     */
    private String ownerName;          
    
    /**
     * 地址
     */
    private String address;            
    
    /**
     * 联系电话
     */
    private String phoneNumber;        
    
    /**
     * 身份证号
     */
    private String idCard;             
    
    /**
     * 描述
     */
    private String description;        
   
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

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    public String getIdCard()
    {
        return idCard;
    }
    
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
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
