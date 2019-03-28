package com.jszt.vo;

import com.jszt.domain.DimLongPassCard;
import com.jszt.domain.DimTempPassCard;

/**
 * 因禁行线路需通知的通行证类
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-6-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class NoticePassCard
{
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
     * 联系电话
     */
    private String phoneNumber;

    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 状态（长期通行证有）
     */
    private int status;
    
    /**
     * 通行证类型（1-临时，2-长期）
     */
    private int cardType;
    
    public  NoticePassCard()
    {
        
    }
    
    public  NoticePassCard(DimLongPassCard longCard)
    {
        this.beginDate = longCard.getBeginDate();
        this.endDate = longCard.getEndDate();
        this.passPeriod = longCard.getPassPeriod();
        this.phoneNumber = longCard.getPhoneNumber();
        this.licensePlate = longCard.getLicensePlate();
        this.status = longCard.getStatus();
        this.cardType = 2;
    }
    
    public  NoticePassCard(DimTempPassCard tempCard)
    {
        this.beginDate = tempCard.getBeginDate();
        this.endDate = tempCard.getEndDate();
        this.passPeriod = tempCard.getPassPeriod();
        this.phoneNumber = tempCard.getPhoneNumber();
        this.licensePlate = tempCard.getLicensePlate();
        this.cardType = 1;
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

    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    public String getLicensePlate()
    {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    public int getCardType()
    {
        return cardType;
    }

    public void setCardType(int cardType)
    {
        this.cardType = cardType;
    }
    
    
}
