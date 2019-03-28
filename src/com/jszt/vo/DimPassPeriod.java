/*
 * 文 件 名:  DimLongPassPeriod.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月25日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 通行证通行时间段 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月25日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimPassPeriod extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -8306125017202258053L;

	/**
	 * 开始日期
	 */
	private int beginDate;

	/**
	 * 结束日期
	 */
	private int endDate;

	/**
	 * 通行时段
	 */
	private String passPeriod;

	/**
	 * 联系电话
	 */
	private String phoneNumber;

	/**
	 * 车牌号
	 */
	private String licensePlate;

	/**
	 * @return 返回 beginDate
	 */
	public int getBeginDate()
	{
		return beginDate;
	}

	/**
	 * @param 对beginDate进行赋值
	 */
	public void setBeginDate(int beginDate)
	{
		this.beginDate = beginDate;
	}

	/**
	 * @return 返回 endDate
	 */
	public int getEndDate()
	{
		return endDate;
	}

	/**
	 * @param 对endDate进行赋值
	 */
	public void setEndDate(int endDate)
	{
		this.endDate = endDate;
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

	/**
	 * @return 返回 phoneNumber
	 */
	public String getPhoneNumber()
	{
		return phoneNumber;
	}

	/**
	 * @param 对phoneNumber进行赋值
	 */
	public void setPhoneNumber(String phoneNumber)
	{
		this.phoneNumber = phoneNumber;
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

}
