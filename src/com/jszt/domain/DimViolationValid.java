/**
 * 
 */
package com.jszt.domain;

import java.io.Serializable;
import java.util.Date;

import com.jsits.commons.domain.EhsObject;

/**
*  违法有效实体
* @author      gxj
* @see        [相关类/方法]
* @since      [产品/模块版本] 
*/
public class DimViolationValid extends EhsObject implements Serializable
{
    
    /**
     * 
     */
    private static final long serialVersionUID = -5889974653282323294L;

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
     * 实测值
     */
    private int realValue;                        
    
    /**
     * 标准值
     */
    private int standardValue;                    
    
    /**
     * 车牌类型
     */
    private String plateTypeId;                   
    
    /**
     * 车牌号码
     */
    private String licensePlate;                  
    
    /**
     * 交通违法行为
     */
    private String violationType;                 
    
    /**
     * 采集机构
     */
    private String agencyId;                      
    
    /**
     * 车道编号
     */
    private String lane;                          
    
    /**
     * 审核人员
     */
    private String verifyPerson;                  
    
    /**
     * 审核时间
     */
    private Date verifyTime;                      
    
    /**
     * 原始图片总数
     */
    private int pictureNumber;                    
    
    /**
     * 合成图片链接
     */
    private String licensePlateLink0;             
    
    /**
     * 车辆图片链接1
     */
    private String licensePlateLink1;             
    
    /**
     * 车辆图片链接2
     */
    private String licensePlateLink2;             
    
    /**
     * 车辆图片链接3
     */
    private String licensePlateLink3;             
    
    /**
     * 车辆图片链接4
     */
    private String licensePlateLink4;             
    
    /**
     * 视频文件链接
     */
    private String videoLink;                     
    
    /**
     * 采集用户
     */
    private String userId;                        
    
    /**
     * 传输状态
     */
    private String transferStatus;                
    
    /**
     * 通知状态
     */
    private String noticeStatus;                  
    
    /**
     * 车辆颜色
     */
    private String vehicleColor;                  
    
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
    
    public int getRealValue()
    {
        return realValue;
    }
    
    public void setRealValue(int realValue)
    {
        this.realValue = realValue;
    }
    
    public int getStandardValue()
    {
        return standardValue;
    }
    
    public void setStandardValue(int standardValue)
    {
        this.standardValue = standardValue;
    }
    
    public String getPlateTypeId()
    {
        return plateTypeId;
    }
    
    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }
    
    public String getLicensePlate()
    {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
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
    
    public String getLane()
    {
        return lane;
    }
    
    public void setLane(String lane)
    {
        this.lane = lane;
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
    
    public int getPictureNumber()
    {
        return pictureNumber;
    }
    
    public void setPictureNumber(int pictureNumber)
    {
        this.pictureNumber = pictureNumber;
    }
    
    public String getLicensePlateLink0()
    {
        return licensePlateLink0;
    }
    
    public void setLicensePlateLink0(String licensePlateLink0)
    {
        this.licensePlateLink0 = licensePlateLink0;
    }
    
    public String getLicensePlateLink1()
    {
        return licensePlateLink1;
    }
    
    public void setLicensePlateLink1(String licensePlateLink1)
    {
        this.licensePlateLink1 = licensePlateLink1;
    }
    
    public String getLicensePlateLink2()
    {
        return licensePlateLink2;
    }
    
    public void setLicensePlateLink2(String licensePlateLink2)
    {
        this.licensePlateLink2 = licensePlateLink2;
    }
    
    public String getLicensePlateLink3()
    {
        return licensePlateLink3;
    }
    
    public void setLicensePlateLink3(String licensePlateLink3)
    {
        this.licensePlateLink3 = licensePlateLink3;
    }
    
    public String getLicensePlateLink4()
    {
        return licensePlateLink4;
    }
    
    public void setLicensePlateLink4(String licensePlateLink4)
    {
        this.licensePlateLink4 = licensePlateLink4;
    }
    
    public String getVideoLink()
    {
        return videoLink;
    }
    
    public void setVideoLink(String videoLink)
    {
        this.videoLink = videoLink;
    }
    
    public String getUserId()
    {
        return userId;
    }
    
    public void setUserId(String userId)
    {
        this.userId = userId;
    }
    
    public String getTransferStatus()
    {
        return transferStatus;
    }
    
    public void setTransferStatus(String transferStatus)
    {
        this.transferStatus = transferStatus;
    }
    
    public String getNoticeStatus()
    {
        return noticeStatus;
    }
    
    public void setNoticeStatus(String noticeStatus)
    {
        this.noticeStatus = noticeStatus;
    }
    
    public String getVehicleColor()
    {
        return vehicleColor;
    }
    
    public void setVehicleColor(String vehicleColor)
    {
        this.vehicleColor = vehicleColor;
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
