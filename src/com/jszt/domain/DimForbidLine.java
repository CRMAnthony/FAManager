/*
 * 文 件 名:  DimForbidLine.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 禁行线路数据 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimForbidLine extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -6032519910049837933L;
    
    /**
     * 禁行线路编号
     */
    private int lineId;
    
    /**
     * 线路名称
     */
    private String lineName;
    
    /**
     * 开始时间
     */
    private long beginTime;
    
    /**
     * 结束时间
     */
    private long endTime;
    
    /**
     * 禁行线路描述
     */
    private String description;
    
    /**
     * @return 返回 lineId
     */
    public int getLineId()
    {
        return lineId;
    }
    
    /**
     * @param 对lineId进行赋值
     */
    public void setLineId(int lineId)
    {
        this.lineId = lineId;
    }
    
    /**
     * @return 返回 lineName
     */
    public String getLineName()
    {
        return lineName;
    }
    
    /**
     * @param 对lineName进行赋值
     */
    public void setLineName(String lineName)
    {
        this.lineName = lineName;
    }
    
    /**
     * @return 返回 beginTime
     */
    public long getBeginTime()
    {
        return beginTime;
    }
    
    /**
     * @param 对beginTime进行赋值
     */
    public void setBeginTime(long beginTime)
    {
        this.beginTime = beginTime;
    }
    
    /**
     * @return 返回 endTime
     */
    public long getEndTime()
    {
        return endTime;
    }
    
    /**
     * @param 对endTime进行赋值
     */
    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }
    
    /**
     * @return 返回 description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    /**
     * @return 返回 serialversionuid
     */
    public static long getSerialversionuid()
    {
        return serialVersionUID;
    }
    
}
