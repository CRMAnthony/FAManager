package com.jszt.controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.client.PassRecord;
import com.jszt.domain.DimPassLine;
import com.jszt.domain.DimPlateType;
import com.jszt.domain.DimSysConfig;
import com.jszt.service.CommonService;
import com.jszt.service.PassCardService;
import com.jszt.service.VioPassRecordService;
import com.jszt.vo.NoticeReq;
import com.jszt.vo.NoticeVo;
import com.jszt.vo.UpdateConfigReq;

/**
 * 公共接口 <一句话功能简述> <功能详细描述>
 * 
 * @author ling
 * @version [版本号, 2015-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonController
{
    private static Logger logger = Logger.getLogger(CommonController.class);
    
    /**
     * 获取车辆类型 <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getPlateType.json")
    public Response<List<DimPlateType>> getPlateType(HttpServletRequest request)
    {
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getPlateType].");
        }
        Response<List<DimPlateType>> result = new Response<List<DimPlateType>>();
        
        try
        {
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            List<DimPlateType> plateTypeList = commonService.getPlateType();
            
            result.setResult(plateTypeList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getPlateType fail!", e);
        }
        
        return result;
    }
    
    /**
     * 获取通行线路 <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getPassLine.json")
    public Response<List<DimPassLine>> getPassline(HttpServletRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getPassline].");
        }
        
        Response<List<DimPassLine>> result = new Response<List<DimPassLine>>();
        
        try
        {
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            List<DimPassLine> passLineList = commonService.getPassLine();
            
            result.setResult(passLineList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getPassline fail!", e);
        }
        
        return result;
    }
    
    /**
     * 长期通行证时段（全时段，避高峰） <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getLongPeriod.json")
    public Response<List<DimSysConfig>> getLongPeriod(HttpServletRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getLongPeriod].");
        }
        
        Response<List<DimSysConfig>> result = new Response<List<DimSysConfig>>();
        
        try
        {
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            List<DimSysConfig> longPeriodList = commonService.getLongPeriod();
            
            result.setResult(longPeriodList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getLongPeriod fail!", e);
        }
        
        return result;
    }
    
    /**
     * 更新静态数据 <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/refreshData.json")
    public Response<Object> refreshData(HttpServletRequest request)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [refreshData].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            commonService.refreshData();
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("refreshData fail!", e);
        }
        
        return result;
    }
    
    @WebExporter("/Service/FAManager/updateAvoidPeak.json")
    public Response<Object> updateAvoidPeak(UpdateConfigReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateAvoidPeak].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getConfigValue(), ErrorCode.PARAM_ERROR, "param [configValue] can not be null!");
            
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            commonService.updateAvoidPeak(req.getConfigValue());
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateAvoidPeak fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("updateAvoidPeak fa0il!", e);
        }
        
        return result;
    }
    
    @WebExporter("/Service/FAManager/updateVioTimes.json")
    public Response<Object> updateVioTimes(UpdateConfigReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateVioTimes].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getConfigValue(), ErrorCode.PARAM_ERROR, "param [configValue] can not be null!");
            
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            commonService.updateVioTimes(req.getConfigValue());
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateVioTimes fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("updateVioTimes fail!", e);
        }
        
        return result;
    }
    
    @WebExporter("/Service/FAManager/testPassrecord.json")
    public Response<Object> testPassrecord(HttpServletRequest request)
    {
        // if (logger.isDebugEnabled())
        // {
        // logger.debug("Enter Func [refreshData].");
        // }
        
        Response<Object> result = new Response<Object>();
        
        // CommonService commonService = ServiceUtil.getService(CommonService.class);
        // List<DimForbidLine> list = commonService.getForbidLinesByCard(20150601, 20150801, "9,10", 2);
        
        try
        {
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            passCardService.passCardPrint("", 2);
            VioPassRecordService recordService = ServiceUtil.getService(VioPassRecordService.class);
            // recordService.updateValidCard();
            // recordService.syncValidCard();
            PassRecord passRecord = new PassRecord();
            passRecord.setBrand("xxx");
            passRecord.setColor("01");
            passRecord.setDeviceId("D17322031");
            passRecord.setDeviceType("01");
            passRecord.setDirection("WB");
            passRecord.setLane("1");
            passRecord.setLength("500");
            passRecord.setPlate("苏D8CP33");
            passRecord.setSpeed("60");
            passRecord.setTime("20150608151232000");
            passRecord.setType("1");
            passRecord.setUrl1("http");
            passRecord.setUrl2("http");
            passRecord.setUrl3("http");
            recordService.addVioPassRecord(passRecord);
            
            // 线路违规
            passRecord.setDeviceId("D17322051");
            passRecord.setPlate("苏D8CP33");
            passRecord.setTime("20150608151232000");
            recordService.addVioPassRecord(passRecord);
            
            // 时间违规
            passRecord.setDeviceId("D17322031");
            passRecord.setPlate("苏D8CP33");
            passRecord.setTime("20150608061232000");
            recordService.addVioPassRecord(passRecord);
            
            // 无通行证
            passRecord.setDeviceId("D17322031");
            passRecord.setPlate("苏D8CP3T");
            passRecord.setTime("20150608061232000");
            recordService.addVioPassRecord(passRecord);
            
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("testPassrecord fail!", e);
        }
        
        return result;
    }
    
    @WebExporter("/Service/FAManager/infoUserUpdate.json")
    public Response<Void> infoUserUpdate()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [infoUserUpdate.]");
        }
        Response<Void> response = new Response<Void>();
        //20170626 暂时去掉发送短信
        /*try
        {
            ServiceUtil.getService(CommonService.class).infoUserUpdate();
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("infoUserUpdate fail!", e);
        }*/
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [infoUserUpdate.]");
        }
        return response;
    }
    
    /**
     * 该方法为昆山重点车辆定制（吴江不使用）
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getPolices.json")
    public Response<Map<String, Set<String>>> getPolices()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getPolices.]");
        }
        Response<Map<String, Set<String>>> response = new Response<Map<String, Set<String>>>();
        try
        {
            response.setResult(ServiceUtil.getService(CommonService.class).getPolices());
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("getPolices fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getPolices.]");
        }
        return response;
    }
    @WebExporter("/Service/FAManager/getNotice.json")
    public Response<List<NoticeVo>> getNotice(NoticeReq req)
    {
    	if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getNotice].");
        }
        
        Response<List<NoticeVo>> result = new Response<List<NoticeVo>>();
        
        try
        {
        	// 参数校验
            Assert.notNull(req.getBeginTime(), ErrorCode.PARAM_ERROR, "param [beginTime] can not be null!");
            Assert.notNull(req.getEndTime(), ErrorCode.PARAM_ERROR, "param [endTime] can not be null!");
            CommonService commonService = ServiceUtil.getService(CommonService.class);
            List<NoticeVo> noticeVoList = commonService.getNotice(req);
            result.setResult(noticeVoList);
        }
        catch (Exception e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("getNotice fail!", e);
        }
        
        return result;
    }
    // @WebExporter("/Service/FAManager/test.json")
    // public Response<String> test(HttpServletRequest request)
    // {
    // Response<String> resp = new Response<String>();
    // try
    // {
    // resp.setResult(ServiceUtil.getService(CommonService.class).getCarOwnerPhone("12313123"));
    //
    // }
    // catch (Exception e)
    // {
    // // TODO Auto-generated catch block
    // e.printStackTrace();
    // }
    // return resp;
    // }
}
