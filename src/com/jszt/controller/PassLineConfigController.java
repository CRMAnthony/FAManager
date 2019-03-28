/*
 * 文 件 名:  PassLineConfigController.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;

import org.apache.log4j.Logger;

import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.domain.DimPassLine;
import com.jszt.service.PassLineService;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.PassLineInfoAccoutReq;
import com.jszt.vo.PassLineInfoReq;
import com.jszt.vo.PassLineReq;
import com.jszt.vo.PassLineVo;

/**
 * 通行线路配置
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLineConfigController
{
	private static Logger logger = Logger.getLogger(PassLineConfigController.class);

	/**
	 * 通行线路配置 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/addPassLine.json")
	public Response<Boolean> addPassLine(PassLineReq req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [addPassLine]." + req);
		}

		Response<Boolean> result = new Response<Boolean>();
		try
		{
			Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null!");
			PassLineService passLineService = ServiceUtil.getService(PassLineService.class);

			// 判断线路名称不为空
			String lineName = req.getLineName();
			Assert.notEmpty(lineName, ErrorCode.PARAM_ERROR, "param [lineName] can not be null!");
			// 判断authId操作权限用户不能为空
			String authId = req.getAuthId();
			Assert.notEmpty(authId, ErrorCode.PARAM_ERROR, "param [authId] can not be null!");

			// 判断设备列表安装地点不为空
//			String devices = req.getDevices();
//			Assert.notEmpty(devices, ErrorCode.PARAM_ERROR, "param [devices] can not be null!");
			passLineService.addPassLine(req);
			result.setRetMessage("成功!");
		} catch (JsztException e)
		{
			result.setRetCode(e.getResultCode());
			result.setRetMessage(e.getMessage());
			logger.error("addPassLine fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [addPassLine].");
		}
		return result;
	}
	
	/**
	 * 编辑通行线路 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/updatePassLine.json")
	public Response<Boolean> updatePassLine(PassLineReq req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [updatePassLine]." + req);
		}

		Response<Boolean> result = new Response<Boolean>();
		try
		{
			Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null!");
			PassLineService passLineService = ServiceUtil.getService(PassLineService.class);

			// 判断线路名称不为空
			String lineName = req.getLineName();
			Assert.notEmpty(lineName, ErrorCode.PARAM_ERROR, "param [lineName] can not be null!");
			// 判断authId操作权限用户不能为空
			String authId = req.getAuthId();
			Assert.notEmpty(authId, ErrorCode.PARAM_ERROR, "param [authId] can not be null!");

			passLineService.updatePassLine(req);
			result.setRetMessage("成功!");
		} catch (JsztException e)
		{
			result.setRetCode(e.getResultCode());
			result.setRetMessage(e.getMessage());
			logger.error("addPassLine fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [updatePassLine].");
		}
		return result;
	}

	/**
	 * 查询通行线路 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getPassLineByLineId.json")
	public Response<List<PassLineVo>> getPassLineByLineId(PassLineInfoReq req)
	{
		Response<List<PassLineVo>> resp = new Response<List<PassLineVo>>();
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Enter Func [getPassLine]." + req);
			}
			List<PassLineVo> list = ServiceUtil.getService(PassLineService.class).getPassLine(req);
			resp.setResult(list);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("查询通行线路配置失败", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getPassLine].");
		}
		return resp;
	}
	
	/**
	 * 微信端查询所有通行线路 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getPassLineByWeChat.json")
	public Response<List<DimPassLine>> getPassLineByWeChat()
	{
		Response<List<DimPassLine>> resp = new Response<List<DimPassLine>>();
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Enter Func [getPassLineByWeChat].");
			}
			List<DimPassLine> list = ServiceUtil.getService(PassLineService.class).getPassLineByWeChat();
			resp.setResult(list);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("微信端查询所有通行线路失败", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getPassLineByWeChat].");
		}
		return resp;
	}
	
	/**
	 * 查询指定用户可以申请的所有通行线路 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getPassLineByAccountName.json")
	public Response<List<DimPassLine>> getPassLineByAccountName(PassLineInfoAccoutReq req)
	{
		Response<List<DimPassLine>> resp = new Response<List<DimPassLine>>();
		try
		{
			if (logger.isDebugEnabled())
			{
				logger.debug("Enter Func [getPassLineByAccountName]." + req);
			}
			List<DimPassLine> list = ServiceUtil.getService(PassLineService.class).getPassLineByAccountName(req);
			resp.setResult(list);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("查询指定用户可以申请的所有通行线路", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getPassLineByAccountName].");
		}
		return resp;
	}

	/**
	 * 获得通行线路编号Id <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getPassLineId.json")
	public Response<List<DimPassLine>> getPassLineId()
	{
		Response<List<DimPassLine>> resp = new Response<List<DimPassLine>>();
		try
		{
			List<DimPassLine> lineIdList = ServiceUtil.getService(PassLineService.class).getPassLineId();
			resp.setResult(lineIdList);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("查询通行线路ID失败", e);
		}
		return resp;
	}

	/**
	 * 删除通行线路
	 * @param req
	 * @return
	 */
	@WebExporter("/Service/FAManager/json/delPassLine.json")
	public Response<Boolean> delPassLine(DelIdReq req)
	{
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			PassLineService passLineService = ServiceUtil.getService(PassLineService.class);
			passLineService.delPassLine(req);
			resp.setRetMessage("成功!");
		} catch (ServiceException e)
		{
			resp.setRetCode(-1);
			resp.setRetMessage(e.getMessage());
			logger.error("delPassLine fail!", e);
		}
		return resp;

	}

}
