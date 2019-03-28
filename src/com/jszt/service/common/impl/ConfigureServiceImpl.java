/*
 * 文 件 名:  ConfigureServiceSpi.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  lvwenbin
 * 修改时间:  2013年10月12日
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common.impl;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import com.jsits.commons.ioc.TypeAssemblee;
import com.jsits.commons.util.CloseUtils;
import com.jszt.service.common.ConfigureService;

/**
 * 配置服务实现 <功能详细描述>
 * 
 * @author lvwenbin
 * @version [版本号, 2013年10月12日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
@TypeAssemblee(ConfigureService.class)
public class ConfigureServiceImpl implements ConfigureService
{
	/**
	 * 日志
	 */
	private static Logger logger = Logger.getLogger(ConfigureServiceImpl.class);

	/**
	 * 配置信息缓存器
	 */
	private Properties props = new Properties();

	/**
	 * 配置项缓存
	 * 
	 * @param res
	 */
	@Override
	public void setResource(Resource[] res)
	{
		// 如果数组不为空可以执行
		if (ArrayUtils.isNotEmpty(res))
		{
			InputStream in = null;

			try
			{
				for (Resource resource : res)
				{
					in = resource.getInputStream();
					props.load(in);
				}
			} catch (Exception e)
			{
				logger.error("config error. ", e);
			} finally
			{
				CloseUtils.close(in);
			}
		}
	}

	/**
	 * 获取配置
	 * 
	 * @param key
	 * @return
	 */
	@Override
	public String getConfig(String key)
	{
		return props.getProperty(key);
	}
}
