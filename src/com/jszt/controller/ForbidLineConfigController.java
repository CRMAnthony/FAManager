/*
 * 文 件 名:  ForbidLineConfigController.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.domain.DimForbidLine;
import com.jszt.service.CommonService;
import com.jszt.service.ForbidLineService;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.ForbidLineReq;
import com.jszt.vo.PassCardReqByDateAndPeriod;

/**
 * 禁行线路配置
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidLineConfigController
{
	private static Logger logger = Logger.getLogger(ForbidLineConfigController.class);

	/**
	 * 禁行路线配置 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @throws RemoteException
	 * @throws ParseException
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/addForbidLine.json")
	public Response<Boolean> addForbidLine(ForbidLineReq req) throws RemoteException, ParseException
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [addForbidLine]." + req);
		}

		Response<Boolean> result = new Response<Boolean>();

		try
		{
			Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null!");
			ForbidLineService forbidLineService = ServiceUtil.getService(ForbidLineService.class);

			Assert.notEmpty(req.getLineName(), ErrorCode.PARAM_ERROR, "param [lineName] can not be null!");
			Assert.isTrue(req.getBeginTime() != 0, ErrorCode.PARAM_ERROR, "param [beginTime] can not be null!");
			Assert.isTrue(req.getEndTime() != 0, ErrorCode.PARAM_ERROR, "param [endTime] can not be null!");

			forbidLineService.addForbidLine(req);
			// 根据禁行时间判断是否通知
			//20170626 暂时把发送短信去掉
			/*boolean ret = ServiceUtil.getService(ForbidLineService.class).infoUserForBid(req);
			result.setRetMessage(ret ? "添加禁行线路成功！短信通知成功" : "添加禁行线路成功！该禁行线路内无需通知");*/
			result.setRetMessage("添加禁行线路成功！");
		} catch (JsztException e)
		{
			result.setRetCode(e.getResultCode());
			result.setRetMessage(e.getMessage());
			logger.error("addForbidLine fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [addForbidLine]." + req);
		}
		return result;
	}

	/**
	 * 禁行路线查询 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getForbidLine.json")
	public Response<List<DimForbidLine>> getForbidLine(ForbidLineReq req)
	{
		Response<List<DimForbidLine>> resp = new Response<List<DimForbidLine>>();
		try
		{
			List<DimForbidLine> list = ServiceUtil.getService(ForbidLineService.class).getForbidLine(req);
			resp.setResult(list);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("获得禁行线路数据失败！！", e);
		}

		return resp;
	}

	/**
	 * 根据日期、时段、通行证类型查询禁行路线
	 */
	@WebExporter("/Service/FAManager/getForbidLineByDateAndPeriod.json")
	public Response<List<DimForbidLine>> getForbidLineByDateAndPeriod(PassCardReqByDateAndPeriod req)
	{
		Response<List<DimForbidLine>> result = new Response<List<DimForbidLine>>(0, "成功");
		try
		{
			Assert.notNull(req, com.jszt.adapter.util.ErrorCode.PARAM_ERROR, "req can not be null");

			int startDate = req.getStartDate();
			Assert.isTrue(startDate != 0, ErrorCode.PARAM_ERROR, "beginDate can not be null");

			int endDate = req.getEndDate();
			Assert.isTrue(endDate != 0, ErrorCode.PARAM_ERROR, "endDate can not be null");

			int cardType = req.getCardType();
			Assert.isTrue(cardType != 0, ErrorCode.PARAM_ERROR, "cardType can not be null");
			Assert.isTrue(cardType == 1 || cardType == 2, ErrorCode.PARAM_ERROR, "cardType error");

			String passPeriod = req.getPassPeriod();
			Assert.notEmpty(passPeriod, ErrorCode.PARAM_ERROR, "passPeriod can not be null");

			CommonService commonService = ServiceUtil.getService(CommonService.class);
			List<DimForbidLine> forbidLineList = commonService.getForbidLinesByCard(startDate, endDate, passPeriod, cardType);

			result.setResult(forbidLineList);
		} catch (ServiceException e)
		{
			result.setRetCode(e.getErrorCode());
			result.setRetMessage(e.getMessage());
			logger.error("getForbidLineByDateAndPeriod", e);
		} catch (JsztException e)
		{
			result.setRetCode(ErrorCode.UNKNOWN_ERROR);
			result.setRetMessage(e.getMessage());
			logger.error("getForbidLineByDateAndPeriod fail!", e);
		}
		return result;
	}

	/**
	 * 删除禁行线路 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/json/delForbidLine.json")
	public Response<Boolean> delForbidLine(DelIdReq req)
	{
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			ForbidLineService forbidLineService = ServiceUtil.getService(ForbidLineService.class);
			forbidLineService.delForbidLine(req);
			resp.setRetMessage("成功");
		} catch (ServiceException e)
		{
			resp.setRetCode(ErrorCode.UNKNOWN_ERROR);
			resp.setRetMessage(e.getMessage());
			logger.error("delForbidLine fail!", e);
		}
		return resp;
	}

}
