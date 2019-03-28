/*
 * 文 件 名:  TempPassCardVio.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2016年2月23日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年2月23日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TempPassCardVio
{
    /**
     * 编号
     */
    private String rowId;
    
    /**
     * 日期
     */
    private int timeKey;
    
    /**
     * 时段
     */
    private String passPeriod;
    
    /**
     * @return 返回 rowId
     */
    public String getRowId()
    {
        return rowId;
    }
    
    /**
     * @param 对rowId进行赋值
     */
    public void setRowId(String rowId)
    {
        this.rowId = rowId;
    }
    
    /**
     * @return 返回 timeKey
     */
    public int getTimeKey()
    {
        return timeKey;
    }
    
    /**
     * @param 对timeKey进行赋值
     */
    public void setTimeKey(int timeKey)
    {
        this.timeKey = timeKey;
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
    
}
