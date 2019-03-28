/*
 * 文 件 名:  SpecialVehicleController.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import com.jsits.commons.collections.ListUtils;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.AdapterContains;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.boulevard.webservice.export.Excel2003Response;
import com.jszt.service.SpecialVehicleService;
import com.jszt.vo.SpecialVehicleReq;
import com.jszt.vo.ViolationSpecialVehicle;

/**
 * 特殊车辆信息配置 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SpecialVehicleController
{
	private static Logger logger = Logger.getLogger(SpecialVehicleController.class);

	/**
	 * 特殊车辆信息查询 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/getSpecialVehicle.json")
	public Response<List<ViolationSpecialVehicle>> getSpecialVehicle(SpecialVehicleReq req)
	{
		Response<List<ViolationSpecialVehicle>> resp = new Response<List<ViolationSpecialVehicle>>();
		try
		{
			SpecialVehicleService specialVehicleService = ServiceUtil.getService(SpecialVehicleService.class);
			List<ViolationSpecialVehicle> voList = specialVehicleService.getSpecialVehicle(req);

			if (voList != null)
			{
				// 是否满足分页条件
				if (AdapterContains.NEGATIVE_ONE != req.getPageIndex() && AdapterContains.NEGATIVE_ONE != req.getPageCount())
				{
					Page page = new Page(req.getPageIndex(), req.getPageCount(), voList.size());
					resp.setPage(page);
					voList = ListUtils.subList(voList, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount() - 1);
				}
			}

			resp.setResult(voList);
		} catch (ServiceException e)
		{
			resp.setRetCode(e.getErrorCode());
			resp.setRetMessage(e.getMessage());
			logger.error("getSpecialVehicle fail!", e);
		}
		return resp;
	}

	/**
	 * 新增特殊车辆信息 <功能详细描述>
	 * 
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	@WebExporter("/Service/FAManager/addSpecialVehicle.json")
	public Response<Boolean> addSpecialVehicle(SpecialVehicleReq req)
	{
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			// 校检参数
			checkParam(req);
			ServiceUtil.getService(SpecialVehicleService.class).addSpecialVehicle(req);
		} catch (ServiceException e)
		{
			resp.setRetCode(e.getErrorCode());
			resp.setRetMessage(e.getMessage());
			logger.error("addSpecialVehicle fail!", e);
		}
		return resp;
	}

	/**
	 * 删除特殊车辆接口
	 * @param req
	 * @return
	 */
	@WebExporter("/Service/FAManager/deleteSpecialVehicle.json")
	public Response<Boolean> deleteSpecialVehicle(ViolationSpecialVehicle req)
	{
		Response<Boolean> resp = new Response<Boolean>();
		try
		{
			ServiceUtil.getService(SpecialVehicleService.class).deleteSpecialVehicle(req);
		} catch (ServiceException e)
		{
			resp.setRetCode(e.getErrorCode());
			resp.setRetMessage(e.getMessage());
			logger.error("deleteSpecialVehicle fail!", e);
		}
		return resp;
	}

	/**
	 * 特殊车辆EXCEL导出
	 * @param req
	 * @return
	 */
	@WebExporter("/Service/FAManager/specialVehicleExcel.export")
	public Excel2003Response buildSpecialVehicleExcel(SpecialVehicleReq req)
	{
		if (logger.isDebugEnabled())
		{
			logger.debug("Enter Func [buildSpecialVehicleExcel].");
		}
		Excel2003Response response = new Excel2003Response();
		SpecialVehicleService specialVehicleService;
		List<ViolationSpecialVehicle> voList;
		try
		{
			response.setFileName("导出特殊车辆信息Excel");
			specialVehicleService = ServiceUtil.getService(SpecialVehicleService.class);
			voList = specialVehicleService.getSpecialVehicle(req);
			response.setByteArrayOutputStream(specialVehicleService.buildSpecialVehicleExcelBody(voList));
		} catch (ServiceException e)
		{
			logger.error("buildSpecialVehicleExcel fail!", e);
		}
		if (logger.isDebugEnabled())
		{
			logger.debug("Exit Func [buildSpecialVehicleExcel]");
		}
		return response;
	}

	/**
	 * 校检参数 <功能详细描述>
	 * 
	 * @param req
	 * @see [类、类#方法、类#成员]
	 */
	@SuppressWarnings("static-access")
	private void checkParam(SpecialVehicleReq req)
	{
		ServiceAssert.notBlank(req.getForbidType(), ErrorCode.PARAM_ERROR, "重点车辆类型为空！！");
		ServiceAssert.notBlank(req.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号码为空！！");
		ServiceAssert.notBlank(req.getOwnerName(), ErrorCode.PARAM_ERROR, "车主姓名为空！！");
		String regex = "1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";
		Pattern p = Pattern.compile(regex);
		ServiceAssert.isTrue(p.matches(regex, req.getPhoneNumber()), ErrorCode.PARAM_ERROR, "车主号码格式错误");
		ServiceAssert.notBlank(req.getPhoneNumber(), ErrorCode.PARAM_ERROR, "手机号为空！！");
		ServiceAssert.notBlank(req.getPlateTypeId(), ErrorCode.PARAM_ERROR, "车辆类型为空");
	}

}
