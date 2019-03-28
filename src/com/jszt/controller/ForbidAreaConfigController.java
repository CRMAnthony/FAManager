/*
 * 文 件 名:  ForbidAreaConfigController.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.jsits.commons.collections.ListUtils;
import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.AdapterContains;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.domain.DimForbidArea;
import com.jszt.service.ForbidAreaService;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.ForbidAreaInfoReq;
import com.jszt.vo.ForbidAreaReq;
import com.jszt.vo.ForbidAreaVo;

/**
 * 禁行区域配置 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidAreaConfigController
{
	private static Logger logger = Logger.getLogger(ForbidAreaConfigController.class);

	/**
	 * 新增禁行区域配置 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/addForbidArea.json")
	public Response<Boolean> addForbidArea(ForbidAreaReq req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [addForbidArea]." + req);
		}
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			resp.setRetMessage("成功!");
			int areaType = req.getAreaType();
			Assert.notEmpty(req.getAreaName(), ErrorCode.PARAM_ERROR, "param [areaName] can not be null!");
			Assert.isTrue(areaType == 0 || areaType == 1, ErrorCode.PARAM_ERROR, "applyType can not be null");
			ServiceUtil.getService(ForbidAreaService.class).addForbidArea(req);

		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("addForbidArea fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [addForbidArea].");
		}
		return resp;
	}

	/**
	 * 获得禁行区域信息 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getForbidArea.json")
	public Response<List<ForbidAreaVo>> getForbidArea(ForbidAreaInfoReq req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [getForbidArea]." + req);
		}
		Response<List<ForbidAreaVo>> resp = new Response<List<ForbidAreaVo>>();
		try
		{
			Assert.notNull(req, ErrorCode.PARAM_ERROR, "param [req] can not be null!");
			List<ForbidAreaVo> forbidAreas = ServiceUtil.getService(ForbidAreaService.class).getForbidArea(req);

			// 是否满足分页条件
			if (AdapterContains.NEGATIVE_ONE != req.getPageIndex() && AdapterContains.NEGATIVE_ONE != req.getPageCount())
			{
				Page page = new Page(req.getPageIndex(), req.getPageCount(), forbidAreas.size());
				resp.setPage(page);
				forbidAreas = ListUtils.subList(forbidAreas, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount() - 1);
			}

			resp.setResult(forbidAreas);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("获得禁行区域失败", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getForbidArea].");
		}
		return resp;
	}

	/**
	 * 获得禁行区域ID <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getForbidAreaId.json")
	public Response<List<DimForbidArea>> getForbidAreaId()
	{
		Response<List<DimForbidArea>> resp = new Response<List<DimForbidArea>>();
		try
		{
			List<DimForbidArea> forbidAreaIds = ServiceUtil.getService(ForbidAreaService.class).getForbidAreaId();
			resp.setResult(forbidAreaIds);
			resp.setRetMessage("成功！");
		} catch (JsztException e)
		{
			resp.setRetCode(e.getResultCode());
			resp.setRetMessage(e.getMessage());
			logger.error("获得禁行区域编号ID失败", e);
		}

		return resp;
	}

	/**
	 * 删除禁行区域 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/json/delForbidArea.json")
	public Response<Boolean> delForbidArea(DelIdReq req)
	{
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			ForbidAreaService forbidAreaService = ServiceUtil.getService(ForbidAreaService.class);
			forbidAreaService.delForbidArea(req);
			resp.setRetMessage("成功!");
		} catch (ServiceException e)
		{
			resp.setRetCode(-1);
			resp.setRetMessage(e.getMessage());
			logger.error("delForbidArea fail!", e);
		}
		return resp;

	}
	
	
	/**
	 * 更新禁行区域配置
	 * <功能详细描述>
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/json/updateForbidArea.json")
	public Response<Boolean> updateForbidArea (ForbidAreaReq req)
	{
		Response<Boolean> response =new Response<Boolean>();
		if (logger.isDebugEnabled())
		{
			logger.debug("enter [func] updateForbidArea");
		}
		ForbidAreaService forbidAreaService = ServiceUtil.getService(ForbidAreaService.class);
		try
		{
			forbidAreaService.updateForbidArea(req);
			response.setRetMessage("成功!");
		} catch (JsztException e)
		{
			response.setRetCode(-1);
			response.setRetMessage(e.getMessage());
			logger.error("updateForbidArea fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("exit [func] updateForbidArea");
		}
		return response;
	}
	
	/**
	 * 获取设备信息
	 * <功能详细描述>
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/json/getMonitorDevices.json")
	public Response<Map<String, Set<String>>> getMonitorDevices()
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [getMonitorDevices].");
		}
		Response<Map<String, Set<String>>> response = new Response<Map<String, Set<String>>>();
		try
		{
			Map<String, Set<String>> retMap = ServiceUtil.getService(ForbidAreaService.class).getMonitorDevices();
			response.setResult(retMap);
		} catch (JsztException e)
		{
			response.setRetCode(-1);
			response.setRetMessage(e.getMessage());
			logger.error("getMonitorDevices fail!", e);
		}

		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [getMonitorDevices].");
		}

		return response;
	}

}
