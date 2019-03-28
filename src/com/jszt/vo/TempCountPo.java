/*
 * 文 件 名:  TempCountPo.java
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
 * 统计po <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年4月8日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class TempCountPo
{
    /**
     * 申请时间
     */
    private String applyDate;
    
    /**
     * 统计数目
     */
    private int count;
    
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
    
}
