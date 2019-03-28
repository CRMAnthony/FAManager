package com.jszt.vo;

import java.util.List;

/**
 * 有效的通行证（用于判断违法过车记录）
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ValidPassCard
{
    /**
     * 车牌号
     */
    private String plateLicense;
    
    /**
     * 车辆类型
     */
    private String plateTypeId;
    
    /**
     * 通行时段
     */
    private String period;
    
    /**
     * 通行证类型
     */
    private String cardType;
    
    /**
     * 所在线路对应的设备
     */
    private List<String> devList;

    public String getPlateLicense()
    {
        return plateLicense;
    }

    public void setPlateLicense(String plateLicense)
    {
        this.plateLicense = plateLicense;
    }

    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    public String getPeriod()
    {
        return period;
    }

    public void setPeriod(String period)
    {
        this.period = period;
    }

    public String getCardType()
    {
        return cardType;
    }

    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    public List<String> getDevList()
    {
        return devList;
    }

    public void setDevList(List<String> devList)
    {
        this.devList = devList;
    }

    @Override
    public String toString()
    {
        return "ValidPassCard [plateLicense=" + plateLicense + ", period=" + period + ", cardType=" + cardType + "]";
    }
}
