/*
 * 文 件 名:  IllegalApproval.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.StringUtils;

/**
 * 审核提交
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IllegalApproval extends IllegalRecord
{
    
    /**
     * 车牌号码
     */
    private String newLicensePlate;
    
    /**
     * 审核类型
     */
    private int approvalType;
    
    /**
     * 原始记录审核类型
     */
    private int sourceApprovalType;
    
    /**
     * 合成图片地址
     */
    private String pic;
    
    /**
     * 原始图片地址
     */
    private String picUrls;
    
    
    /**
     * id
     */
    private String violationId;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 白名单标识
     */
    private String isWhiteList;
    
    /**
     * 采集设备编号
     */
    private String collectionDeviceId;
    
    /**
     * 违法类型
     */
    private String violationType;
    
    /**
     * 采集机构编号
     */
    private String agencyId;
    
    /**
     * @return 返回 newLicensePlate
     */
    public String getNewLicensePlate()
    {
        return newLicensePlate;
    }

    /**
     * @param 对newLicensePlate进行赋值
     */
    public void setNewLicensePlate(String newLicensePlate)
    {
        this.newLicensePlate = newLicensePlate;
    }

    public String getTempDateKey()
    {
        return StringUtils.substring(super.getDateKey(), 0,8);
    }
    
    /**
     * @return 返回 agencyId
     */
    public String getAgencyId()
    {
        return agencyId;
    }

    /**
     * @param 对agencyId进行赋值
     */
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    /**
     * @return 返回 approvalType
     */
    public int getApprovalType()
    {
        return approvalType;
    }

    /**
     * @param 对approvalType进行赋值
     */
    public void setApprovalType(int approvalType)
    {
        this.approvalType = approvalType;
    }

    /**
     * @return 返回 sourceApprovalType
     */
    public int getSourceApprovalType()
    {
        return sourceApprovalType;
    }

    /**
     * @param 对sourceApprovalType进行赋值
     */
    public void setSourceApprovalType(int sourceApprovalType)
    {
        this.sourceApprovalType = sourceApprovalType;
    }

    /**
     * @return 返回 pic
     */
    public String getPic()
    {
        return pic;
    }

    /**
     * @param 对pic进行赋值
     */
    public void setPic(String pic)
    {
        this.pic = pic;
    }

    /**
     * @return 返回 picUrls
     */
    public String[] getPicUrls()
    {
        return StringUtils.split(picUrls, ',');
    }

    /**
     * @param 对picUrls进行赋值
     */
    public void setPicUrls(String picUrls)
    {
        this.picUrls = picUrls;
    }

    /**
     * @return 返回 violationId
     */
    public String getViolationId()
    {
        return violationId;
    }

    /**
     * @param 对violationId进行赋值
     */
    public void setViolationId(String violationId)
    {
        this.violationId = violationId;
    }

    /**
     * @return 返回 deviceType
     */
    public String getDeviceType()
    {
        return deviceType;
    }

    /**
     * @param 对deviceType进行赋值
     */
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    /**
     * @return 返回 isWhiteList
     */
    public String getIsWhiteList()
    {
        return isWhiteList;
    }

    /**
     * @param 对isWhiteList进行赋值
     */
    public void setIsWhiteList(String isWhiteList)
    {
        this.isWhiteList = isWhiteList;
    }

    /**
     * @return 返回 collectionDeviceId
     */
    public String getCollectionDeviceId()
    {
        return collectionDeviceId;
    }

    /**
     * @param 对collectionDeviceId进行赋值
     */
    public void setCollectionDeviceId(String collectionDeviceId)
    {
        this.collectionDeviceId = collectionDeviceId;
    }

    /**
     * @return 返回 violationType
     */
    public String getViolationType()
    {
        return violationType;
    }

    /**
     * @param 对violationType进行赋值
     */
    public void setViolationType(String violationType)
    {
        this.violationType = violationType;
    }
    
    
    
}
