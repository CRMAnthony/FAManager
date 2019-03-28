package com.jszt.vo;


/**
 * 通行证查询返回
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassCard
{
    /**
     * 通行证编号
     */
    private String cardId;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 通行证类型编号（0-临时，1-长期）
     */
    private int cardTypeId;
    
    /**
     * 通行证类型
     */
    private String cardType;
    
    /**
     * 车辆类型
     */
    private String plateTypeId;
    
    /**
     * 重点车辆类型
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
     * 通行时段编号
     */
    private String passPeriodIds;
    
    /**
     * 通行线路
     */
    private String passLine;
    
    /**
     * 线路编号
     */
    private String passLineId;

    /**
     * 禁行线路
     */
    private String forbidLine;
    
    /**
     * 禁行线路
     */
    private String forbidLineIds;
    
    /**
     * 申请类型
     */
    private String applyType;
    
    /**
     * 单位
     */
    private String ownerName;
    
    /**
     * 联系人
     */
    private String contactPeople;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 电话
     */
    private String phoneNumber;
    
    /**
     * 身份证
     */
    private String idCard;
    
    /**
     * 录入人编号
     */
    private String accountId;
    
    /**
     * 录入人名
     */
    private String accountName;
    
    /**
     * 状态
     */
    private int status;

    /**
     * 打印状态
     */
    private String print;
    
    /**
     * 申请时间
     */
    private long applyDate;
    
    /**
     * 秩序科审核时间
     */
    private long zxkDate;

    /**
     * 大队领导审核时间
     */
    private long ddDate;

    /**
     * 秩序科审核意见
     */
    private String zxkOpinion;

    /**
     * 大队领导审核意见
     */
    private String ddOpinion;

    /**
     * 行驶证扫描件
     */
    private String xszUrl;

    /**
     * 驾驶证扫描件
     */
    private String jszUrl;

    /**
     * 保险保单扫描件
     */
    private String bxUrl;

    /**
     * 身份证扫描件
     */
    private String sfzUrl;

    /**
     * 营业执照扫描件
     */
    private String yyzzUrl;

    /**
     * 供货合同扫描件
     */
    private String ghhtUrl;

    /**
     * 押运员押运证扫描件
     */
    private String yyzUrl;
    
    /**
     * 状态描述
     */
    private String description;
    
    /**
     * 录入人的部门编号
     */
    private String agencyId;
    
    /**
     * 录入人的部门名称
     */
    private String agencyName;
    
    /**
     * 长期通行证延长父节点编号
     */
    private String parentCardId;
    
    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getForbidLineIds()
    {
        return forbidLineIds;
    }

    public void setForbidLineIds(String forbidLineIds)
    {
        this.forbidLineIds = forbidLineIds;
    }

    public String getParentCardId()
    {
        return parentCardId;
    }

    public void setParentCardId(String parentCardId)
    {
        this.parentCardId = parentCardId;
    }

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public int getCardTypeId()
    {
        return cardTypeId;
    }

    public void setCardTypeId(int cardTypeId)
    {
        this.cardTypeId = cardTypeId;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    public String getPassPeriod()
    {
        return passPeriod;
    }

    public void setPassPeriod(String passPeriod)
    {
        this.passPeriod = passPeriod;
    }

    public String getPassLine()
    {
        return passLine;
    }

    public void setPassLine(String passLine)
    {
        this.passLine = passLine;
    }

    public String getForbidLine()
    {
        return forbidLine;
    }

    public void setForbidLine(String forbidLine)
    {
        this.forbidLine = forbidLine;
    }

    public String getApplyType()
    {
        return applyType;
    }

    public void setApplyType(String applyType)
    {
        this.applyType = applyType;
    }

    public String getOwnerName()
    {
        return ownerName;
    }

    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    public String getContactPeople()
    {
        return contactPeople;
    }

    public void setContactPeople(String contactPeople)
    {
        this.contactPeople = contactPeople;
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

    public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getPrint()
    {
        return print;
    }

    public void setPrint(String print)
    {
        this.print = print;
    }

    public String getForbidType()
    {
        return forbidType;
    }

    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }

    public String getPassLineId()
    {
        return passLineId;
    }

    public void setPassLineId(String passLineId)
    {
        this.passLineId = passLineId;
    }

    public String getZxkOpinion()
    {
        return zxkOpinion;
    }

    public void setZxkOpinion(String zxkOpinion)
    {
        this.zxkOpinion = zxkOpinion;
    }

    public String getDdOpinion()
    {
        return ddOpinion;
    }

    public void setDdOpinion(String ddOpinion)
    {
        this.ddOpinion = ddOpinion;
    }

    public String getXszUrl()
    {
        return xszUrl;
    }

    public void setXszUrl(String xszUrl)
    {
        this.xszUrl = xszUrl;
    }

    public String getJszUrl()
    {
        return jszUrl;
    }

    public void setJszUrl(String jszUrl)
    {
        this.jszUrl = jszUrl;
    }

    public String getBxUrl()
    {
        return bxUrl;
    }

    public void setBxUrl(String bxUrl)
    {
        this.bxUrl = bxUrl;
    }

    public String getSfzUrl()
    {
        return sfzUrl;
    }

    public void setSfzUrl(String sfzUrl)
    {
        this.sfzUrl = sfzUrl;
    }

    public String getYyzzUrl()
    {
        return yyzzUrl;
    }

    public void setYyzzUrl(String yyzzUrl)
    {
        this.yyzzUrl = yyzzUrl;
    }

    public String getGhhtUrl()
    {
        return ghhtUrl;
    }

    public void setGhhtUrl(String ghhtUrl)
    {
        this.ghhtUrl = ghhtUrl;
    }

    public String getYyzUrl()
    {
        return yyzUrl;
    }

    public void setYyzUrl(String yyzUrl)
    {
        this.yyzUrl = yyzUrl;
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

    public long getZxkDate()
    {
        return zxkDate;
    }

    public void setZxkDate(long zxkDate)
    {
        this.zxkDate = zxkDate;
    }

    public long getDdDate()
    {
        return ddDate;
    }

    public void setDdDate(long ddDate)
    {
        this.ddDate = ddDate;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public long getApplyDate()
    {
        return applyDate;
    }

    public void setApplyDate(long applyDate)
    {
        this.applyDate = applyDate;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }

    public String getPassPeriodIds()
    {
        return passPeriodIds;
    }

    public void setPassPeriodIds(String passPeriodIds)
    {
        this.passPeriodIds = passPeriodIds;
    }
    
    
}
