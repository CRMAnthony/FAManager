/*
 * 文 件 名:  PicImageReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 违法审核页面图片查询接口
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PicImageReq
{
    /**
     * 页码
     */
    private int pageIndex;
    
    /**
     * 每页数量
     */
    private int pageCount;
    
    /**
     * 车牌
     */
    private String licensePlate;
    
    /**
     * 日期
     */
    private int date;
    
    /**
     * 处理状态
     */
    private int processState;
    
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
     * @return 返回 processState
     */
    public int getProcessState()
    {
        return processState;
    }

    /**
     * @param 对processState进行赋值
     */
    public void setProcessState(int processState)
    {
        this.processState = processState;
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
     * @return 返回 date
     */
    public int getDate()
    {
        return date;
    }

    /**
     * @param 对date进行赋值
     */
    public void setDate(int date)
    {
        this.date = date;
    }
    
    
}
