/*
 * 文 件 名:  ConfigureService.java
 * 版    权:  江苏智通交通科技
 * 描    述:  配置服务 此服务可以取到常用的配置信息，以及SQL信息
 * 修 改 人:  lvwenbin
 * 修改时间:  2013年10月12日
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common;

import org.springframework.core.io.Resource;

/**
 * 配置服务 此服务可以取到常用的配置信息，以及SQL信息 <功能详细描述>
 * 
 * @author lvwenbin
 * @version [版本号, 2013年10月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ConfigureService
{
	/**
	 * 设定配置资源
	 * 
	 * @param res
	 * @see [类、类#方法、类#成员]
	 */
	public void setResource(Resource[] res);

	/**
	 * 根据key值，取到对应的value信息
	 * 
	 * @param key
	 *            键值
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	public String getConfig(String key);
}
