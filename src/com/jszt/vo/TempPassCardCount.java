/*
 * 文 件 名:  TempPassCardCount.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2016年3月16日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 临时通行证申请数量统计 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年3月16日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TempPassCardCount
{
    /**
     * 申请时间
     */
    private String applyDate;
    
    /**
     * 黄牌车总数
     */
    private int yelloCount;
    
    /**
     * 蓝牌车总数
     */
    private int blueCount;
    
    /**
     * 总计
     */
    private int total;
    
    
    
    /**
     * @return 返回 applyDate
     */
    public String getApplyDate()
    {
        return applyDate;
    }

    /**
     * @param 对applyDate进行赋值
     */
    public void setApplyDate(String applyDate)
    {
        this.applyDate = applyDate;
    }

    /**
     * @return 返回 yelloCount
     */
    public int getYelloCount()
    {
        return yelloCount;
    }
    
    /**
     * @param 对yelloCount进行赋值
     */
    public void setYelloCount(int yelloCount)
    {
        this.yelloCount = yelloCount;
    }
    
    /**
     * @return 返回 blueCount
     */
    public int getBlueCount()
    {
        return blueCount;
    }
    
    /**
     * @param 对blueCount进行赋值
     */
    public void setBlueCount(int blueCount)
    {
        this.blueCount = blueCount;
    }
    
    /**
     * @return 返回 total
     */
    public int getTotal()
    {
        return total;
    }
    
    /**
     * @param 对total进行赋值
     */
    public void setTotal(int total)
    {
        this.total = total;
    }
    
}
