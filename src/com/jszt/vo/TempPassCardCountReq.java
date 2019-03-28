/*
 * 文 件 名:  TempPassCardCountReq.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2016年4月8日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2016年4月8日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TempPassCardCountReq
{
    /**
     * 开始日期
     */
    private String beginDate;
    
    /**
     * 结束日期
     */
    private String endDate;
    
    /**
     * 统计类型 1：表示按小时统计 2:表示按天统计 3:表示按月统计
     */
    private int countType;
    
    /**
     * 当前页面
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageCount;
    
    
    /**
     * @return 返回 beginDate
     */
    public String getBeginDate()
    {
        return beginDate;
    }

    /**
     * @param 对beginDate进行赋值
     */
    public void setBeginDate(String beginDate)
    {
        this.beginDate = beginDate;
    }

    /**
     * @return 返回 endDate
     */
    public String getEndDate()
    {
        return endDate;
    }

    /**
     * @param 对endDate进行赋值
     */
    public void setEndDate(String endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return 返回 countType
     */
    public int getCountType()
    {
        return countType;
    }

    /**
     * @param 对countType进行赋值
     */
    public void setCountType(int countType)
    {
        this.countType = countType;
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
