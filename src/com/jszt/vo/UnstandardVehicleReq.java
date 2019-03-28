/*
 * 文 件 名:  UnstandardVehicleReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 黄标车信息请求类
 * 
 * @author  gxj
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UnstandardVehicleReq extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -3633785140952324249L;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 所属单位
     */
    private String ownerName; 
    
    /**
     * 联系电话
     */
    private String phoneNumber;
    
    /**
     * 当前页面
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageCount;

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
     * @return 返回 phoneNumber
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * @param 对phoneNumber进行赋值
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }

    /**
     * @return 返回 pageIndex
     */
    public int getPageIndex()
    {
        return pageIndex;
    }

    /**
     * @param 对pageIndex进行赋值
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    /**
     * @return 返回 pageCount
     */
    public int getPageCount()
    {
        return pageCount;
    }

    /**
     * @param 对pageCount进行赋值
     */
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    } 
    
}
