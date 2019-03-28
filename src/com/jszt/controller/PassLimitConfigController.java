/*
 * 文 件 名:  PassLimitConfigController.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.domain.DimPassLlimit;
import com.jszt.service.PassLimitService;
import com.jszt.vo.PassLimitRequest;

/**
 * 临时通行证通行额度配置 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLimitConfigController
{
	private static Logger logger = Logger.getLogger(ForbidLineConfigController.class);

	/**
	 * 通行额度配置 <功能详细描述>
	 * 
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FaManager/addPassLimit.json")
	public Response<Boolean> addPassLimit(PassLimitRequest req) throws JsztException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [addPassLimit]." + req);
		}

		Response<Boolean> result = new Response<Boolean>();

		try
		{
			Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null!");
			PassLimitService limitService = ServiceUtil.getService(PassLimitService.class);

			// 判断额度不为空
			String limit = req.getLimit();
			String plateTypeId = req.getPlateTypeId();
			Integer passLineId = req.getPassLineId();
			Assert.notNull(passLineId, ErrorCode.PARAM_ERROR, "param [passLineId] can not be null!");
			Assert.notEmpty(limit, ErrorCode.PARAM_ERROR, "param [limit] can not be null!");
			limitService.addPassLimit(limit,plateTypeId,req.getLimitDate(),passLineId);
			result.setRetMessage("成功!");
		} catch (JsztException e)
		{
			result.setRetCode(e.getResultCode());
			result.setRetMessage(e.getMessage());
			logger.error("addPassLimit fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [addPassLimit].");
		}
		return result;
	}

	/**
	 * 初始化查询额度配置
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FaManager/getPassLimit.json")
	public Response<List<DimPassLlimit>> getPassLimit(PassLimitRequest req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [getPassLimit].");
		}
		Response<List<DimPassLlimit>> result = new Response<List<DimPassLlimit>>();
		PassLimitService limitService = ServiceUtil.getService(PassLimitService.class);
		try
		{
			Integer passLineId = req.getPassLineId();
			Assert.notNull(passLineId, ErrorCode.PARAM_ERROR, "param [passLineId] can not be null!");
			List<DimPassLlimit> list = limitService.getPassLimit(req.getPlateTypeId(),req.getLimitDate(),passLineId);
			result.setResult(list);
			result.setRetMessage("成功!");
		} catch (JsztException e)
		{
			result.setRetCode(e.getResultCode());
			result.setRetMessage(e.getMessage());
			logger.error("获得初始化额度信息失败！！", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getPassLimit].");
		}
		return result;
	}
}
