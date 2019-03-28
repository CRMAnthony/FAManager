/*
 * 文 件 名:  ForbidLineReq.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 禁行线路配置请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidLineReq
{
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
	 * 描述
	 */
	private String description;

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

	public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
}
