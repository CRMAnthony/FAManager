/*
 * 文 件 名:  DimCardUserInfo.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 参数表 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class Param extends EhsObject implements Serializable
{

	/**
	 * 注释内容
	 */
	private static final long serialVersionUID = -638118564424539570L;

	/**
	 * 参数key
	 */
	private String key;

	/**
	 * 参数值
	 */
	private String value;

	/**
	 * 参数名称
	 */
	private String name;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
