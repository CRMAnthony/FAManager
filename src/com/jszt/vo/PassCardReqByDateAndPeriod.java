/*
 * 文 件 名:  PassCardReqByDateAndPeriod.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月22日
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
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassCardReqByDateAndPeriod
{
    /**
     * 开始日期
     */
    private int startDate;

    /**
     * 结束日期
     */
    private int endDate;

    /**
     * 通行时段
     */
    private String passPeriod;
    
    /**
     * 通行证类型
     */
    private int cardType;

    /**
     * @return 返回 startDate
     */
    public int getStartDate()
    {
        return startDate;
    }

    /**
     * @param 对startDate进行赋值
     */
    public void setStartDate(int startDate)
    {
        this.startDate = startDate;
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
    
}
