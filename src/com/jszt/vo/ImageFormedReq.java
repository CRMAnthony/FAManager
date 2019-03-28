/*
 * 文 件 名:  ImageFormedReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
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
 * @author  ChangJun
 * @version  [版本号, 2015年5月22日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ImageFormedReq
{
    private String picUrls;

    
    private String forbidType;
    
    /**
     * 违法时间
     */
    private String dateKey;
    
    /**
     * 禁行区域编号
     */
    private String areaId;
    
    /**
     * @return 返回 areaId
     */
    public String getAreaId()
    {
        return areaId;
    }

    /**
     * @param 对areaId进行赋值
     */
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    /**
     * @return 返回 picUrls
     */
    public String getPicUrls()
    {
        return picUrls;
    }

    /**
     * @param 对picUrls进行赋值
     */
    public void setPicUrls(String picUrls)
    {
        this.picUrls = picUrls;
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
     * @return 返回 dateKey
     */
    public String getDateKey()
    {
        return dateKey;
    }

    /**
     * @param 对dateKey进行赋值
     */
    public void setDateKey(String dateKey)
    {
        this.dateKey = dateKey;
    }
    
    
    
    
}
