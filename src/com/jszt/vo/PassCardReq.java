package com.jszt.vo;

/**
 * 通行证查询请求对象 <一句话功能简述> <功能详细描述>
 * 
 * @author ling
 * @version [版本号, 2015-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassCardReq
{
    /**
     * 通行证id
     */
    private String cardId;
    
    /**
     * 查询的开始日期
     */
    private int beginDate;
    
    /**
     * 查询的结束日期
     */
    private int endDate;
    
    /**
     * 所属单位
     */
    private String ownerName;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 车辆类型
     */
    private String plateTypeId;
    
    /**
     * 重点车辆类型
     */
    private String forbidType;
    
    /**
     * 通行证类型
     */
    private int cardType;
    
    /**
     * 通行线路，多个用逗号分隔
     */
    private String passLineId;
    
    /**
     * 通行证状态
     */
    private int status;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 当前页面
     */
    private int pageIndex;
    
    /**
     * 每页记录数
     */
    private int pageCount;
    
    /**
     * 当前录入人的部门单位编号
     */
    private String agencyId;
    
    /**
     * 延长通行证父通行证编号
     */
    private String parentCardId;
    
    /**
     * 当前登录用户账号
     */
    private String account;
    
    public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public PassCardReq()
    {
        // super();
        beginDate = -1;
        endDate = -1;
        passLineId = "-1";
        status = -1;
    }
    
    public String getParentCardId()
    {
        return parentCardId;
    }

    public void setParentCardId(String parentCardId)
    {
        this.parentCardId = parentCardId;
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
    
    public String getOwnerName()
    {
        return ownerName;
    }
    
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
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
    
    public int getCardType()
    {
        return cardType;
    }
    
    public void setCardType(int cardType)
    {
        this.cardType = cardType;
    }
    
    public String getPassLineId()
    {
        return passLineId;
    }
    
    public void setPassLineId(String passLineId)
    {
        this.passLineId = passLineId;
    }
    
    public int getStatus()
    {
        return status;
    }
    
    public void setStatus(int status)
    {
        this.status = status;
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
    
    public String getForbidType()
    {
        return forbidType;
    }
    
    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }
    
    public String getIdCard()
    {
        return idCard;
    }
    
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }
    
    /**
     * @return 返回 cardId
     */
    public String getCardId()
    {
        return cardId;
    }
    
    /**
     * @param 对cardId进行赋值
     */
    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
}
