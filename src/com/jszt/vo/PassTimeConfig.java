/*
 * 文件名：PassTimeConfig.java  1.0.0  2015年5月14日
 * 描述：&功能说明
 * 作者：常骏
 * 版权：江苏智通交通科技有限公司
 */

package com.jszt.vo;

import java.util.List;

 /**
 * 通行时段配额列表
 * 〈功能详细描述〉
 * @author      常骏
 * @see        [相关类/方法]
 * @since      [产品/模块版本] 
 */
public class PassTimeConfig
{

    /**
     * 通行段配额时间
     */
    private int configDate;
    
    /**
     * 通行段配额
     */
    private List<TimeConfig> timeConfig;

    public int getConfigDate()
    {
        return configDate;
    }

    public void setConfigDate(int configDate)
    {
        this.configDate = configDate;
    }

    public List<TimeConfig> getTimeConfig()
    {
        return timeConfig;
    }

    public void setTimeConfig(List<TimeConfig> timeConfig)
    {
        this.timeConfig = timeConfig;
    }
    
    
    
    
}
