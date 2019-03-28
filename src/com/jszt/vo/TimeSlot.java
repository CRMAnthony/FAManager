package com.jszt.vo;

/**
 * 时间段
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-6-19]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class TimeSlot
{
    private long startTime;
    private long endTime;
    
    public  TimeSlot()
    {
        
    }
    
    public TimeSlot(long startTime, long endTime)
    {
        super();
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public long getStartTime()
    {
        return startTime;
    }
    public void setStartTime(long startTime)
    {
        this.startTime = startTime;
    }
    public long getEndTime()
    {
        return endTime;
    }
    public void setEndTime(long endTime)
    {
        this.endTime = endTime;
    }
    
    
}
