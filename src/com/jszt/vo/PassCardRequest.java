/*
 * 文 件 名:  PassCardRequest.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  gxj
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassCardRequest
{
    /**
     * 通行证类型
     */
    private int cardType;
   
    /**
     * 车牌号
     */
    private String licensePlate;

    /**
     * 车牌种类
     */
    private String plateTypeId;

    /**
     * 重点车辆类型 '1'表示货车,'2'表示危险品车辆,'3'表示黄标车
     */
    private String forbidType;

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
     * 通行路线， 如有多个线路用","隔开
     */
    private String passLine;

    /**
     * 禁行路线 如有多个线路用","隔开
     */
    private String forbidLine;
    
    /**
     * 申请类型
     */
    private int applyType;
    
    /**
     * 车主姓名
     */
    private String ownerName;

    /**
     * 联系人姓名
     */
    private String contactPeople;

    /**
     * 地址
     */
    private String address;

    /**
     * 联系电话
     */
    private String phoneNumer;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 录入人
     */
    private String accountId;
    
    /**
     * 行驶证扫描件
     */
    private String drivingLicenseUrl;

    /**
     * 驾驶证扫描件
     */
    private String diverLicenseUrl;

    /**
     * 保险保单扫描件
     */
    private String insuranceUrl;

    /**
     * 身份证扫描件
     */
    private String idCardUrl;

    /**
     * 营业执照扫描件
     */
    private String businessLicenceUrl;

    /**
     * 供货合同扫描件
     */
    private String supplyContractUrl;

    /**
     * 押运员押运证扫描件
     */
    private String escortCardUrl;

    /**
     * 状态描述
     */
    private String description;
    
    /**
     * 延长通行证父通行证编号
     */
    private String parentCardId;

    
    public String getParentCardId() {
		return parentCardId;
	}

	public void setParentCardId(String parentCardId) {
		this.parentCardId = parentCardId;
	}

	/**
     * @return 返回 cardType
     */
    public int getCardType()
    {
        return cardType;
    }

    /**
     * @param 对cardType进行赋值
     */
    public void setCardType(int cardType)
    {
        this.cardType = cardType;
    }
    
    /**
     * @return 返回 licensePlate
     */
    public String getLicensePlate()
    {
        return licensePlate;
    }

    /**
     * @param 对licensePlate进行赋值
     */
    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    /**
     * @return 返回 plateTypeId
     */
    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    /**
     * @param 对plateTypeId进行赋值
     */
    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    /**
     * @return 返回 forbidType
     */
    public String getForbidType()
    {
        return forbidType;
    }

    /**
     * @param 对forbidType进行赋值
     */
    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }

    /**
     * @return 返回 beginDate
     */
    public int getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param 对beginDate进行赋值
     */
    public void setBeginDate(int beginDate)
    {
        this.beginDate = beginDate;
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

    /**
     * @return 返回 passPeriod
     */
    public String getPassPeriod()
    {
        return passPeriod;
    }

    /**
     * @param 对passPeriod进行赋值
     */
    public void setPassPeriod(String passPeriod)
    {
        this.passPeriod = passPeriod;
    }

    /**
     * @return 返回 passLine
     */
    public String getPassLine()
    {
        return passLine;
    }

    /**
     * @param 对passLine进行赋值
     */
    public void setPassLine(String passLine)
    {
        this.passLine = passLine;
    }

    /**
     * @return 返回 forbidLine
     */
    public String getForbidLine()
    {
        return forbidLine;
    }

    /**
     * @param 对forbidLine进行赋值
     */
    public void setForbidLine(String forbidLine)
    {
        this.forbidLine = forbidLine;
    }
    
    /**
     * @return 返回 applyType
     */
    public int getApplyType()
    {
        return applyType;
    }

    /**
     * @param 对applyType进行赋值
     */
    public void setApplyType(int applyType)
    {
        this.applyType = applyType;
    }

    /**
     * @return 返回 ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * @param 对ownerName进行赋值
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return 返回 contactPeople
     */
    public String getContactPeople()
    {
        return contactPeople;
    }

    /**
     * @param 对contactPeople进行赋值
     */
    public void setContactPeople(String contactPeople)
    {
        this.contactPeople = contactPeople;
    }

    /**
     * @return 返回 address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * @param 对address进行赋值
     */
    public void setAddress(String address)
    {
        this.address = address;
    }

    /**
     * @return 返回 phoneNumber
     */
    public String getPhoneNumer()
    {
        return phoneNumer;
    }

    /**
     * @param 对phoneNumber进行赋值
     */
    public void setPhoneNumer(String phoneNumer)
    {
        this.phoneNumer = phoneNumer;
    }

    /**
     * @return 返回 idCard
     */
    public String getIdCard()
    {
        return idCard;
    }

    /**
     * @param 对idCard进行赋值
     */
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    /**
     * @return 返回 drivingLicenseUrl
     */
    public String getDrivingLicenseUrl()
    {
        return drivingLicenseUrl;
    }

    /**
     * @param 对drivingLicenseUrl进行赋值
     */
    public void setDrivingLicenseUrl(String drivingLicenseUrl)
    {
        this.drivingLicenseUrl = drivingLicenseUrl;
    }

    /**
     * @return 返回 diverLicenseUrl
     */
    public String getDiverLicenseUrl()
    {
        return diverLicenseUrl;
    }

    /**
     * @param 对diverLicenseUrl进行赋值
     */
    public void setDiverLicenseUrl(String diverLicenseUrl)
    {
        this.diverLicenseUrl = diverLicenseUrl;
    }

    /**
     * @return 返回 insuranceUrl
     */
    public String getInsuranceUrl()
    {
        return insuranceUrl;
    }

    /**
     * @param 对insuranceUrl进行赋值
     */
    public void setInsuranceUrl(String insuranceUrl)
    {
        this.insuranceUrl = insuranceUrl;
    }

    /**
     * @return 返回 idCardUrl
     */
    public String getIdCardUrl()
    {
        return idCardUrl;
    }

    /**
     * @param 对idCardUrl进行赋值
     */
    public void setIdCardUrl(String idCardUrl)
    {
        this.idCardUrl = idCardUrl;
    }

    /**
     * @return 返回 businessLicenceUrl
     */
    public String getBusinessLicenceUrl()
    {
        return businessLicenceUrl;
    }

    /**
     * @param 对businessLicenceUrl进行赋值
     */
    public void setBusinessLicenceUrl(String businessLicenceUrl)
    {
        this.businessLicenceUrl = businessLicenceUrl;
    }

    /**
     * @return 返回 supplyContractUrl
     */
    public String getSupplyContractUrl()
    {
        return supplyContractUrl;
    }

    /**
     * @param 对supplyContractUrl进行赋值
     */
    public void setSupplyContractUrl(String supplyContractUrl)
    {
        this.supplyContractUrl = supplyContractUrl;
    }

    /**
     * @return 返回 escortCardUrl
     */
    public String getEscortCardUrl()
    {
        return escortCardUrl;
    }

    /**
     * @param 对escortCardUrl进行赋值
     */
    public void setEscortCardUrl(String escortCardUrl)
    {
        this.escortCardUrl = escortCardUrl;
    }

    /**
     * @return 返回 description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description)
    {
        this.description = description;
    } 
    
}
