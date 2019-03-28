package com.jszt.vo;

/**
 * 通行证打印内容
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-7-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CardPrintInfo
{
    /**
     * 通行证编号
     */
    private String cardId;
    
    /**
     * 单位
     */
    private String owner;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 车牌类型
     */
    private String plateType;
    
    /**
     * 禁行区域名称
     */
    private String forbidAreaName;
    
    /**
     * 开始日期
     */
    private int beginDate;
    /**
     * 开始日期（格式：2015年07月06日）
     */
    private String startDate;
    
    /**
     * 结束日期（格式：2015年07月06日）
     */
    private String endDate;
    
    /**
     * 通行时段
     */
    private String period;
    
    /**
     * 禁行线路
     */
    private String forbidInfo;
    
    /**
     * 通行线路
     */
    private String lineInfo;
    
    /**
     * 长期通行证当前编号
     */
    private String longPassCardSeq;
    
    /**
     * 临时通行证当前编号
     */
    private String tempPassCardSeq;
    

    public String getOwner()
    {
        return owner;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public String getStartDate()
    {
        return startDate;
    }

    public void setStartDate(String startDate)
    {
        this.startDate = startDate;
    }

    public String getEndDate()
    {
        return endDate;
    }

    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getForbidInfo()
    {
        return forbidInfo;
    }

    public void setForbidInfo(String forbidInfo)
    {
        this.forbidInfo = forbidInfo;
    }

    public String getLineInfo()
    {
        return lineInfo;
    }

    public void setLineInfo(String lineInfo)
    {
        this.lineInfo = lineInfo;
    }

    public String getCardId()
    {
        return cardId;
    }

    public void setCardId(String cardId)
    {
        this.cardId = cardId;
    }

    public int getBeginDate()
    {
        return beginDate;
    }

    public void setBeginDate(int beginDate)
    {
        this.beginDate = beginDate;
    }

    public String getPlateType()
    {
        return plateType;
    }

    public void setPlateType(String plateType)
    {
        this.plateType = plateType;
    }

    public String getForbidAreaName()
    {
        return forbidAreaName;
    }

    public void setForbidAreaName(String forbidAreaName)
    {
        this.forbidAreaName = forbidAreaName;
    }
    
    public String getLongPassCardSeq()
    {
        return longPassCardSeq;
    }

    public void setLongPassCardSeq(String longPassCardSeq)
    {
        this.longPassCardSeq = longPassCardSeq;
    }

    public String getTempPassCardSeq()
    {
        return tempPassCardSeq;
    }

    public void setTempPassCardSeq(String tempPassCardSeq)
    {
        this.tempPassCardSeq = tempPassCardSeq;
    }
}
