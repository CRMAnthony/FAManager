/*
 * 文件名：TimeConfig.java  1.0.0  2015年5月14日
 * 描述：&功能说明
 * 作者：常骏
 * 版权：江苏智通交通科技有限公司
 */

package com.jszt.vo;

 /**
 * 通行时段配额
 * 〈功能详细描述〉
 * @author      常骏
 * @see        [相关类/方法]
 * @since      [产品/模块版本] 
 */
public class TimeConfig
{
	/**
	 * 总额度
	 */
	private int limit;
	
	/**
	 * 已用
	 */
	private int used;
	
	
	public TimeConfig()
	{
	    
	}

	
	public void usedAdd()
	{
	    used++;
	}
	
    public TimeConfig(int limit)
    {
        super();
        this.limit = limit;
    }

    public int getLimit()
	{
		return limit;
	}

	public void setLimit(int limit)
	{
		this.limit = limit;
	}

	public int getUsed()
	{
		return used;
	}

	public void setUsed(int used)
	{
		this.used = used;
	}

	public int getRemainder()
	{
		return limit - used;
	}

	
	
}
