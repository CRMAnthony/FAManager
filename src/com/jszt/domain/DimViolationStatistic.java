/*
 * 文 件 名:  DimViolationStatistic.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年6月1日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 违法未通知次数数据
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationStatistic extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 8239288266961776733L;

    /**
     * 车牌号码
     */
    private String licensePlate;
    
    /**
     * 车牌种类
     */
    private String plateTypeId;
    
    /**
     * 重点车辆类型
     */
    private String forbidType;
    
    /**
     * 重点车辆类型
     */
    private String cardType;
    
    /**
     * 违法次数
     */
    private int count;
    
    /**
     * 最后通知时间
     */
    private String lastNotify;

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
     * @return 返回 cardType
     */
    public String getCardType()
    {
        return cardType;
    }

    /**
     * @param 对cardType进行赋值
     */
    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    /**
     * @return 返回 count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param 对count进行赋值
     */
    public void setCount(int count)
    {
        this.count = count;
    }

    /**
     * @return 返回 lastNotify
     */
    public String getLastNotify()
    {
        return lastNotify;
    }

    /**
     * @param 对lastNotify进行赋值
     */
    public void setLastNotify(String lastNotify)
    {
        this.lastNotify = lastNotify;
    }
    
    

}
