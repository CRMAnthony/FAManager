/**
 * 
 */
package com.jszt.domain;

import java.io.Serializable;
import java.util.Date;

import com.jsits.commons.domain.EhsObject;

/**
*  违章无效实体
* @author      gxj
* @see        [相关类/方法]
* @since      [产品/模块版本] 
*/
public class DimViolationInvalid extends EhsObject implements Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = -7419399376975721570L;
    
    /**
     * 违法记录编号
     */
    private String violationId;                   
    
    /**
     * 采集设备编号
     */
    private String collectionDeviceId;            
    
    /**
     * 设备类型
     */
    private String deviceType;                    
    
    /**
     * 白名单标识
     */
    private String isWhiteList;                   
    
    /**
     * 方向信息
     */
    private String direction;                     
    
    /**
     * 违法时间
     */
    private Date violationTime;                   
    
    /**
     * 车牌号码
     */
    private String licensePlate;                  
   
    /**
     * 车牌类型
     */
    private String plateTypeId;                   
    
    /**
     * 交通违法行为
     */
    private String violationType;                 
   
    /**
     * 采集机构
     */
    private String agencyId;                      
    
    /**
     * 审核人员
     */
    private String verifyPerson;                  
    
    /**
     * 审核时间
     */
    private Date verifyTime;                      
    
    /**
     * 详细描述
     */
    private String description;                   
    
    public String getViolationId()
    {
        return violationId;
    }
    
    public void setViolationId(String violationId)
    {
        this.violationId = violationId;
    }
    
    public String getCollectionDeviceId()
    {
        return collectionDeviceId;
    }
    
    public void setCollectionDeviceId(String collectionDeviceId)
    {
        this.collectionDeviceId = collectionDeviceId;
    }
    
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
    
    public String getIsWhiteList()
    {
        return isWhiteList;
    }
    
    public void setIsWhiteList(String isWhiteList)
    {
        this.isWhiteList = isWhiteList;
    }
    
    public String getDirection()
    {
        return direction;
    }
    
    public void setDirection(String direction)
    {
        this.direction = direction;
    }
    
    public Date getViolationTime()
    {
        return violationTime;
    }
    
    public void setViolationTime(Date violationTime)
    {
        this.violationTime = violationTime;
    }
    
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
    
    public String getViolationType()
    {
        return violationType;
    }
    
    public void setViolationType(String violationType)
    {
        this.violationType = violationType;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getVerifyPerson()
    {
        return verifyPerson;
    }
    
    public void setVerifyPerson(String verifyPerson)
    {
        this.verifyPerson = verifyPerson;
    }
    
    public Date getVerifyTime()
    {
        return verifyTime;
    }
    
    public void setVerifyTime(Date verifyTime)
    {
        this.verifyTime = verifyTime;
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
