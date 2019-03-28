/*
 * 文 件 名:  IllegalRecordController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.collections.ListUtils;
import com.jsits.commons.util.Assert;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.boulevard.webservice.export.Excel2003Response;
import com.jszt.service.IllegalRecordService;
import com.jszt.vo.DeleteRecordReq;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.Image;
import com.jszt.vo.ImageFormedReq;
import com.jszt.vo.PicImageReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;

/**
 * 违法信息审核调用层
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IllegalRecordController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(IllegalRecordController.class);
    
    
//    @WebExporter("/Service/FAManager/sendMessage.json")
//    public Response<Boolean> sendMessage(IllegalRecordQueryConditionReq req)
//    {
//        Response<Boolean> ret = new Response<Boolean>();
//        try
//        {
//            ret.setResult(ServiceUtil.getService(CommonService.class).smsInfo("xxxx", req.getCardType()));
//        }
//        catch (Exception e)
//        {
//            ret.setRetMessage(ExceptionUtils.getStackTrace(e));
//            ret.setRetCode(-1);
//        }
//        
//        return ret;
//    }
    
    /**
     * 数据提取
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/illegalExtraction.json")
    public Response<List<IllegalRecord>> illegalExtraction(IllegalRecordQueryConditionReq req)
    {
        Response<List<IllegalRecord>> rsps = new Response<List<IllegalRecord>>();
        try
        {
            List<IllegalRecord> ret = ServiceUtil.getService(IllegalRecordService.class).extractRecord(req);
            rsps.setResult(ret);
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("illegalExtraction error!", e);
        }
        return rsps;
    }
    
    /**
     * 数据查询
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/queryRecords.json")
    public Response<List<IllegalRecord>> queryRecods(IllegalRecordQueryConditionReq req)
    {
        Response<List<IllegalRecord>> rsps = new Response<List<IllegalRecord>>();
        try
        {
            IllegalRecordService service = ServiceUtil.getService(IllegalRecordService.class);
            List<IllegalRecord> ret = service.queryRecord(req);
            rsps.setResult(ret);
            Page page = new Page();
            page.setCurrentPage(req.getPageIndex());
            page.setPageRecords(req.getPageCount());
            // 查询总数的时候不需要分页
            req.setPageCount(0);
            page.setTotalRecords(service.getRecordCount(req));
            rsps.setPage(page);
            
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("queryRecords error!", e);
        }
        return rsps;
    }
    
    /**
     * 闯禁数据校正（即删除功能）
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/deleteRecords.json")
    public Response<Void> deleteRecords(DeleteRecordReq req)
    {
        Response<Void> rsps = new Response<Void>();
        try
        {
            IllegalRecordService service = ServiceUtil.getService(IllegalRecordService.class);
            service.deleteRecord(req.getDatas(), req.getProcessState());
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("deleteRecords error!", e);
        }
        return rsps;
    }
    
    /**
     * 获取图片
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getApprovalPics.json")
    public Response<PicImageResp> getApprovalPics(PicImageReq req)
    {
        Response<PicImageResp> rsps = new Response<PicImageResp>();
        try
        {
            PicImageResp resp = ServiceUtil.getService(IllegalRecordService.class).getPic(req);
            
            int pageIndex = req.getPageIndex();
            int pageCount = req.getPageCount();
            if (resp != null && CollectionUtils.isNotEmpty(resp.getImgList()))
            {
                Page page = new Page(req.getPageIndex(), req.getPageCount(), resp.getImgList().size());
                rsps.setPage(page);
                resp.setImgList(ListUtils.subList(resp.getImgList(), pageIndex * pageCount, (pageIndex + 1) * pageCount
                    - 1));
                // 删除毫秒
                for (Image im : resp.getImgList())
                {
                    im.setPicTime(StringUtils.substring(im.getPicTime(), 0, 14));
                }
            }
            
            rsps.setResult(resp);
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("getApprovalPics error!", e);
        }
        return rsps;
    }
    
    /**
     * 统计违法次数
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/queryViolationCount.json")
    public Response<List<IllegalRecord>> queryViolationCount(IllegalRecordQueryConditionReq req)
    {
        Response<List<IllegalRecord>> resp = new Response<List<IllegalRecord>>();
        try
        {
            List<IllegalRecord> countList = ServiceUtil.getService(IllegalRecordService.class).queryViolationCount(req);
            
            Page page = new Page(req.getPageIndex(), req.getPageCount(), countList.size());
            resp.setPage(page);
            resp.setResult(ListUtils.subList(countList, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount()
                - 1));
        }
        catch (Exception e)
        {
            resp.setRetMessage(e.getMessage());
            resp.setRetCode(-1);
            logger.error("queryViolationCount error!", e);
        }
        return resp;
    }
    
    /**
     * 导出违法次数统计
     */
    @WebExporter("/Service/FaManager/exportViolationCount.export")
    public Excel2003Response exportViolationCount(IllegalRecordQueryConditionReq req)
    {
        Excel2003Response response = new Excel2003Response();
        response.setFileName("违法次数统计");
        try
        {
            response.setByteArrayOutputStream(ServiceUtil.getService(IllegalRecordService.class).exportViolationCount(req));
        }
        catch (Exception e)
        {
            logger.error("exportViolationCount error!", e);
        }
        
        return response;
    }
    
    
    /**
     * 闯禁数据统计
     */
    @WebExporter("/Service/FaManager/exportRecord.export")
    public Excel2003Response exportRecord(IllegalRecordQueryConditionReq req)
    {
        Excel2003Response response = new Excel2003Response();
        response.setFileName("闯禁数据统计");
        try
        {
            response.setByteArrayOutputStream(ServiceUtil.getService(IllegalRecordService.class).exportRecord(req));
        }
        catch (Exception e)
        {
            logger.error("exportViolationCount error!", e);
        }
        
        return response;
    }
    
    /**
     * 校验导出过车记录是否过长 
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/checkExportRecord.json")
    public Response<Void> checkExportRecord(IllegalRecordQueryConditionReq req)
    {
        Response<Void> rsps = new Response<Void>();
        try
        {
            ServiceUtil.getService(IllegalRecordService.class).checkExportRecord(req);
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
           logger.error("illegalApproval error!", e);
        }
        return rsps;
    }
    
    /**
     * 违法审核数据提交
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/illegalApproval.json")
    public Response<Void> illegalApproval(IllegalApproval req)
    {
        Response<Void> rsps = new Response<Void>();
        try
        {
            ServiceUtil.getService(IllegalRecordService.class).illegalApproval(req);
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("illegalApproval error!", e);
        }
        return rsps;
    }
    
    /**
     * 图片合成
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/imageFormed.json")
    public Response<String> imageFormed(ImageFormedReq req)
    {
        Response<String> rsps = new Response<String>();
        try
        {
            ServiceAssert.isNotNull(req.getPicUrls(), -1, "imageURL is empty");
            
            rsps.setResult(ServiceUtil.getService(IllegalRecordService.class)
                .imageFormed(StringUtils.split(req.getPicUrls(), ','),
                    req.getForbidType(),
                    req.getDateKey(),
                    req.getAreaId()));
        }
        catch (ServiceException e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("imageFormed error!", e);
        }
        return rsps;
    }
    
    /**
     * 违法次数统计
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/queryViolationRecords.json")
    public Response<List<ViolationPassRecordVo>> queryViolationRecord(ViolationRecordReq req)
    {
        Response<List<ViolationPassRecordVo>> rsps = new Response<List<ViolationPassRecordVo>>();
        try
        {
        	Assert.notNull(req.getAnalyseType(), ErrorCode.PARAM_ERROR, "统计类型不能为空");
        	Assert.notNull(req.getBeginDate(), ErrorCode.PARAM_ERROR, "统计开始日期不能为空");
        	Assert.notNull(req.getEndDate(), ErrorCode.PARAM_ERROR, "统计结束日期不能为空");
            IllegalRecordService service = ServiceUtil.getService(IllegalRecordService.class);
            List<ViolationPassRecordVo> ret = service.queryViolationRecord(req);
            rsps.setResult(ret);
        }
        catch (Exception e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("queryViolationRecord error!", e);
        }
        return rsps;
    }
    /**
     * 实时查询违法信息
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/queryViolationRecordCamera.json")
    public Response<List<ViolationCameraVo>> queryViolationRecordCamera()
    {
        Response<List<ViolationCameraVo>> rsps = new Response<List<ViolationCameraVo>>();
        try
        {
            IllegalRecordService service = ServiceUtil.getService(IllegalRecordService.class);
            List<ViolationCameraVo> ret = service.queryViolationRecordCamera(0);
            rsps.setResult(ret);
        }
        catch (Exception e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("queryViolationRecordCamera error!", e);
        }
        return rsps;
    }
    /**
     * 违法地点次数统计
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/queryViolationRecordCameraCount.json")
    public Response<List<ViolationCameraVo>> queryViolationRecordCameraCount()
    {
        Response<List<ViolationCameraVo>> rsps = new Response<List<ViolationCameraVo>>();
        try
        {
            IllegalRecordService service = ServiceUtil.getService(IllegalRecordService.class);
            List<ViolationCameraVo> ret = service.queryViolationRecordCamera(1);
            rsps.setResult(ret);
        }
        catch (Exception e)
        {
            rsps.setRetMessage(e.getMessage());
            rsps.setRetCode(-1);
            logger.error("queryViolationRecordCameraCount error!", e);
        }
        return rsps;
    }
    
}
