/*
 * 文 件 名:  PassCardController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLDecoder;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import org.apache.log4j.Logger;

import com.jsits.commons.collections.ListUtils;
import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jsits.commons.util.StringUtils;
import com.jszt.adapter.util.AdapterContains;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.boulevard.webservice.bean.UploadFile;
import com.jszt.boulevard.webservice.export.Excel2003Response;
import com.jszt.service.CommonService;
import com.jszt.service.ConstructionVehicleService;
import com.jszt.service.PassCardService;
import com.jszt.service.UnstandardVehicleService;
import com.jszt.vo.PassCard;
import com.jszt.vo.PassCardAnalyseReq;
import com.jszt.vo.PassCardAnalyseRes;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.TempPassCardCount;
import com.jszt.vo.TempPassCardCountReq;
import com.jszt.vo.UpdatePassCardReq;
import com.jszt.vo.UploadPictureReq;

/**
 * 通行证
 * 
 * @author gxj
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassCardController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(PassCardController.class);
    
    /**
     * 通行证录入
     * 
     * @param passCardRequest
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/inputPassCard.json")
    public Response<String> inputPassCard(PassCardRequest passCardRequest)
        throws RemoteException, ParseException, MalformedURLException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [addPassCard]. passCardRequest=" + StringUtils.toString(passCardRequest) + ".");
        }

        Response<String> result = new Response<String>();
        try
        {
            checkParam(passCardRequest, result);

            //黄标车车牌号列表
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            List<String> licensePlateList = unstandardVehicleService.getLicensePlates();

            //渣土车车牌号列表
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<String> licenPlateList = constructionVehicleService.getLicensePlates();

            String licensePlate = passCardRequest.getLicensePlate();
            int cardType = passCardRequest.getCardType();
            if(cardType == 1)//临时通行证
            {
                if(licensePlateList.contains(licensePlate))
                {
                    Assert.isTrue(!licensePlateList.contains(licensePlate), ErrorCode.PARAM_ERROR, "黄标车不在申请范围之内");
                }
                if( licenPlateList.contains(licensePlate))
                {
                    Assert.isTrue(!licenPlateList.contains(licensePlate), ErrorCode.PARAM_ERROR, "渣土车不在申请范围之内");
                }
            }

            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            String cardId = passCardService.addPassCard(passCardRequest);
            // 返回通行证编号
            result.setResult(cardId);

            //查询参数表param是否配置发送短信
            boolean flag = ServiceUtil.getService(PassCardService.class).smsNotice(passCardRequest);

            // 设置返回值
            result.setRetMessage(flag ? "申请通行证成功！短信通知成功" : "申请通行证成功！短信通知失败");
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("addPassCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(-1);
            result.setRetMessage(e.getMessage());
            logger.error("addPassCard fail!", e);
        }
        catch (RemoteException e)
        {
            result.setRetMessage("申请通行证成功！短信通知失败!");
            logger.error("sms notice fail!", e);
        }
        catch (MalformedURLException e)
        {
            result.setRetMessage("申请通行证成功！短信通知失败!");
            logger.error("sms notice fail!", e);
        }
        catch (Exception e)
        {
            logger.error("input temp pass card fail!", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [addPassCard].");
        }
        return result;
    }

    /**
     * 通行证录入
     * 
     * @param passCardRequest
     * @return
     * @throws MalformedURLException
     * @throws RemoteException
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/test.json")
    public Response<String> test(PassCardRequest passCardRequest)
        throws RemoteException, ParseException, MalformedURLException
    {

        logger.error("Enter Func [testAddPassCard]. passCardRequest=" + StringUtils.toString(passCardRequest) + ".");
        
        Response<String> result = new Response<String>();
        try
        {
            // 设置返回值
            result.setRetMessage(passCardRequest.getOwnerName());
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("testAddPassCard fail!", e);
        }
        logger.error("Exit Func [testaddPassCard].");
        
        return result;
    }
    
    
    @WebExporter("/Service/FAManager/inputPassCard2.json")
    public Response<String> inputPassCard2(PassCardRequest passCardRequest)
        throws RemoteException, ParseException, MalformedURLException
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [addPassCard]. passCardRequest=" + StringUtils.toString(passCardRequest) + ".");
        }

        Response<String> result = new Response<String>();
        try
        {
            checkParam(passCardRequest, result);

            try {
				passCardRequest.setLicensePlate(URLDecoder.decode(passCardRequest.getLicensePlate(),"utf-8"));
				passCardRequest.setOwnerName(URLDecoder.decode(passCardRequest.getOwnerName(),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

            //黄标车车牌号列表
            UnstandardVehicleService unstandardVehicleService = ServiceUtil.getService(UnstandardVehicleService.class);
            List<String> licensePlateList = unstandardVehicleService.getLicensePlates();

            //渣土车车牌号列表
            ConstructionVehicleService constructionVehicleService = ServiceUtil.getService(ConstructionVehicleService.class);
            List<String> licenPlateList = constructionVehicleService.getLicensePlates();

            String licensePlate = passCardRequest.getLicensePlate();
            int cardType = passCardRequest.getCardType();
            if(cardType == 1)//临时通行证
            {
                if(licensePlateList.contains(licensePlate))
                {
                    Assert.isTrue(!licensePlateList.contains(licensePlate), ErrorCode.PARAM_ERROR, "黄标车不在申请范围之内");
                }
                if( licenPlateList.contains(licensePlate))
                {
                    Assert.isTrue(!licenPlateList.contains(licensePlate), ErrorCode.PARAM_ERROR, "渣土车不在申请范围之内");
                }
            }

            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            String cardId = passCardService.addPassCard(passCardRequest);
            // 返回通行证编号
            result.setResult(cardId);

            boolean flag = ServiceUtil.getService(PassCardService.class).smsNotice(passCardRequest);

            // 设置返回值
            result.setRetMessage(flag ? "申请通行证成功！短信通知成功" : "申请通行证成功！短信通知失败");

        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("addPassCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(-1);
            result.setRetMessage(e.getMessage());
            logger.error("addPassCard fail!", e);
        }
        catch (RemoteException e)
        {
            result.setRetMessage("申请通行证成功！短信通知失败!");
            logger.error("sms notice fail!", e);
        }
        catch (MalformedURLException e)
        {
            result.setRetMessage("申请通行证成功！短信通知失败!");
            logger.error("sms notice fail!", e);
        }

        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [addPassCard].");
        }
        return result;
    }
    
    /**
     * 查询长期通行证延长历史
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/longPassCardHis.json")
    public Response<List<PassCard>> longPassCardHis(PassCardReq req)
    {
        Response<List<PassCard>> result = new Response<List<PassCard>>();
        try
        {
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<PassCard> passCardList = passCardService.queryLongPassCardExtendHis(req.getCardId());
            result.setResult(passCardList);
        }
        catch (Exception e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("longPassCardHis fail!", e);
        }
        return result;
    }

    /**
     * 校验参数
     * 
     * @param passCardRequest
     * @param result
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    private void checkParam(PassCardRequest passCardRequest, Response<String> result)
        throws JsztException
    {
        Assert.notNull(passCardRequest, ErrorCode.PARAM_ERROR, "入参对象不能为空");
        
        int cardType = passCardRequest.getCardType();
        Assert.isTrue(cardType != 0, ErrorCode.PARAM_ERROR, "通行证类型不能为空");
        Assert.isTrue(cardType == 1 || cardType == 2, ErrorCode.PARAM_ERROR, "通行证类型错误");
        int applyType = passCardRequest.getApplyType();
        Assert.isTrue(applyType == 0 || applyType == 1, ErrorCode.PARAM_ERROR, "申请类型不能为空或者申请类型错误");
        Assert.notEmpty(passCardRequest.getOwnerName(), ErrorCode.PARAM_ERROR, "车主姓名不能为空");
        Assert.notEmpty(passCardRequest.getAddress(), ErrorCode.PARAM_ERROR, "地址不能为空");
        Assert.notEmpty(passCardRequest.getPhoneNumer(), ErrorCode.PARAM_ERROR, "手机号不能为空");
        Assert.notEmpty(passCardRequest.getIdCard(), ErrorCode.PARAM_ERROR, "身份证号不能为空");
        Assert.notEmpty(passCardRequest.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号不能为空");
        Assert.notEmpty(passCardRequest.getPlateTypeId(), ErrorCode.PARAM_ERROR, "车牌种类不能为空");
        Assert.notEmpty(passCardRequest.getForbidType(), ErrorCode.PARAM_ERROR, "重点车辆类型不能为空");
        
        Assert.notEmpty(passCardRequest.getDrivingLicenseUrl(), ErrorCode.PARAM_ERROR, "行驶证扫描件不能为空");
        Assert.notEmpty(passCardRequest.getDiverLicenseUrl(), ErrorCode.PARAM_ERROR, "驾驶证扫描件不能为空");
        // Assert.notEmpty(passCardRequest.getInsuranceUrl(),
        // ErrorCode.PARAM_ERROR, "insuranceUrl can not be null");
        
        int beginDate = passCardRequest.getBeginDate();
        Assert.isTrue(beginDate != 0, ErrorCode.PARAM_ERROR, "开始日期不能为空");
        
        int endDate = passCardRequest.getEndDate();
        Assert.isTrue(endDate != 0, ErrorCode.PARAM_ERROR, "结束日期不能为空");
        
        Assert.notEmpty(passCardRequest.getPassPeriod(), ErrorCode.PARAM_ERROR, "通行时段不能为空");
        
//        String passLine = passCardRequest.getPassLine();
        Assert.notEmpty(passCardRequest.getPassLine(), ErrorCode.PARAM_ERROR, "通行路线不能为空");
    }
    
    /**
     * 通行证查询 <一句话功能简述> <功能详细描述>
     * 
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getPassCard.json")
    public Response<List<PassCard>> getPassCard(PassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getPassCard].");
        }
        
        Response<List<PassCard>> result = new Response<List<PassCard>>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardType(), ErrorCode.PARAM_ERROR, "通行证类型不能为空!");
            Assert.notNull(req.getBeginDate(), ErrorCode.PARAM_ERROR, "开始日期不能为空!");
            Assert.notNull(req.getEndDate(), ErrorCode.PARAM_ERROR, "结束日期不能为空!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<PassCard> passCardList = passCardService.getPassCard(req);
            
            // 是否满足分页条件
            if (AdapterContains.NEGATIVE_ONE != req.getPageIndex()
                && AdapterContains.NEGATIVE_ONE != req.getPageCount())
            {
                Page page = new Page(req.getPageIndex(), req.getPageCount(), passCardList.size());
                result.setPage(page);
                passCardList =
                    ListUtils.subList(passCardList, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1)
                        * req.getPageCount() - 1);
            }
            
            result.setResult(passCardList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getPassCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getPassCard fail!", e);
        }
        
        return result;
    }
    
    /**
     * 更新长期通行证时间段 <功能详细描述>
     * 
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateLongPassCard.json")
    public Response<Object> updateLongPassCard(PassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateLongPassCard.]");
        }
        Response<Object> response = new Response<Object>();
        try
        {
            ServiceUtil.getService(PassCardService.class).updateLongPassCard(req);
        }
        catch (JsztException e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("updateLongPassCard fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [updateLongPassCard.]");
        }
        return response;
    }
    
    /**
     * 通行证秩序科审批 <一句话功能简述> <功能详细描述>
     * 
     * @param req
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateZXKProveResult.json")
    public Response<Object> updateZXKProveResult(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateZXKProveResult].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardId(), ErrorCode.PARAM_ERROR, "param [cardid] can not be null!");
            Assert.notNull(req.getResult(), ErrorCode.PARAM_ERROR, "param [result] can not be null!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            passCardService.updateZXKProve(req);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateZXKProveResult fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("updateZXKProveResult fail!", e);
        }
        
        return result;
    }
    
    /**
     * 通行证大队领导审批 <一句话功能简述> <功能详细描述>
     * 
     * @param req
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateDDProveResult.json")
    public Response<Object> updateDDProveResult(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateDDProveResult].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardId(), ErrorCode.PARAM_ERROR, "param [cardid] can not be null!");
            Assert.notNull(req.getResult(), ErrorCode.PARAM_ERROR, "param [result] can not be null!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            passCardService.updateDDProve(req);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateDDProveResult fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("updateDDProveResult fail!", e);
        }
        
        return result;
    }
    
    /**
     * 更新通行证打印状态 
     * 
     * @param req
     * @param request
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateCardPrint.json")
    public Response<Object> updateCardPrint(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateCardPrint].");
        }
        
        Response<Object> result = new Response<Object>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardId(), ErrorCode.PARAM_ERROR, "param [cardid] can not be null!");
            Assert.notNull(req.getPrint(), ErrorCode.PARAM_ERROR, "param [print] can not be null!");
            Assert.notNull(req.getCardTypeId(), ErrorCode.PARAM_ERROR, "param [carttypeid] can not be null!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            passCardService.updatePassCardPrint(req);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateCardPrint fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("updateCardPrint fail!", e);
        }
        
        return result;
    }
    
    /**
     * 获取通行证图片url <一句话功能简述> <功能详细描述>
     * 
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getCardPrintUrl.json")
    public Response<List<String>> getCardPrintUrl(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getCardPrintUrl].");
        }
        
        Response<List<String>> result = new Response<List<String>>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardId(), ErrorCode.PARAM_ERROR, "param [cardid] can not be null!");
            Assert.notNull(req.getCardTypeId(), ErrorCode.PARAM_ERROR, "param [carttypeid] can not be null!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<String> urlList = passCardService.passCardPrint(req.getCardId(), req.getCardTypeId());
            result.setResult(urlList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getCardPrintUrl fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getCardPrintUrl fail!", e);
        }
        
        return result;
    }
    
    /**
     * 获取微信通行证图片url <一句话功能简述> <功能详细描述>
     * 
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getWeiXinPrintUrl.json")
    public Response<List<String>> getWeiXinPrintUrl(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getWeiXinPrintUrl].");
        }
        
        Response<List<String>> result = new Response<List<String>>();
        
        try
        {
            // 参数校验
            Assert.notNull(req.getCardId(), ErrorCode.PARAM_ERROR, "param [cardid] can not be null!");
            Assert.notNull(req.getCardTypeId(), ErrorCode.PARAM_ERROR, "param [carttypeid] can not be null!");
            
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<String> urlList = passCardService.weiXinPrint(req.getCardId(), req.getCardTypeId());
            result.setResult(urlList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getWeiXinPrintUrl fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getWeiXinPrintUrl fail!", e);
        }
        
        return result;
    }
    
    /**
     * 图片上传 <一句话功能简述> <功能详细描述>
     * 
     * @param uploadPictureReq
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/uploadPicture.upload")
    public Response<String> testupload(UploadPictureReq uploadPictureReq)
    {
        Response<UploadFile> tempRet = uploadPictureReq.saveFile();
        Response<String> ret = new Response<String>();
        ret.setResult(tempRet.getResult().getReleasePath());
        ret.setRetCode(tempRet.getRetCode());
        ret.setRetMessage(tempRet.getRetMessage());
        return ret;
    }
    
    /**
     * 导出通行证.excel
     */
    @WebExporter("/Service/FAManager/passCardExcel.export")
    public Excel2003Response buildPassCardResultExcel(PassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [passCardeExcel].");
        }
        
        Excel2003Response response = new Excel2003Response();
        response.setFileName("导出通行证excel");
        
        try
        {
            // 参数校验
            Assert.notNull(req, ErrorCode.PARAM_ERROR, "入参对象不能 为空!");
            // 业务处理
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<PassCard> passCardList = passCardService.getPassCard(req);
            // 结果封装
            response.setByteArrayOutputStream(passCardService.buildPassCardExcelBody(passCardList));
        }
        catch (JsztException e)
        {
            logger.error("passCardExcel fail!", e);
        }
        catch (Exception e)
        {
            logger.error("passCardExcel fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [passCardExcel]");
        }
        return response;
    }
    
    /**
     * 统计临时通行证 <功能详细描述>
     * 
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getTempPassCardCount.json")
    public Response<List<TempPassCardCount>> getTempPassCardCount(TempPassCardCountReq req)
    {
        Response<List<TempPassCardCount>> response =new Response<List<TempPassCardCount>>();
        try
        {
            List<TempPassCardCount> countPassCard = ServiceUtil.getService(PassCardService.class).getTempPassCardCount(req);
           
            if (countPassCard != null)
            {
                // 是否满足分页条件
                if (AdapterContains.NEGATIVE_ONE != req.getPageIndex() && AdapterContains.NEGATIVE_ONE != req.getPageCount())
                {
                    Page page = new Page(req.getPageIndex(), req.getPageCount(), countPassCard.size());
                    response.setPage(page);
                    countPassCard = ListUtils.subList(countPassCard, req.getPageIndex() * req.getPageCount(), (req.getPageIndex() + 1) * req.getPageCount() - 1);
                }
            }
            
            response.setResult(countPassCard);
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getTempPassCardCount fail!", e);
        }
        
        return response;
    }
    
    
    /**
     * 导出统计临时通行证申请数据
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/buildTempCardCount.export")
    public Excel2003Response buildTempCardCount(TempPassCardCountReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [buildTempCardCount].");
        }
        
        Excel2003Response response =null;
        try
        {
            response = new Excel2003Response();
            response.setFileName("导出统计临时通行证申请数量excel");
            List<TempPassCardCount> countPassCard = ServiceUtil.getService(PassCardService.class).getTempPassCardCount(req);
            
            //结果封装
            response.setByteArrayOutputStream(ServiceUtil.getService(PassCardService.class).buildTempPassCardCount(countPassCard));
        }
        catch (Exception e)
        {
            logger.error("buildTempCardCount fail!", e);
        }
        return response;
    }
    /**
     * 查询长期（临时）通行证申请数量
     * @param req
     * @return
     */
    @WebExporter("/Service/FAManager/getPassCardNum.json")
    public Response<List<PassCardAnalyseRes>> getPassCardNum(PassCardAnalyseReq req)
    {
        Response<List<PassCardAnalyseRes>> result = new Response<List<PassCardAnalyseRes>>();
        try
        {
        	// 参数校验
            Assert.notNull(req.getBeginDate(), ErrorCode.PARAM_ERROR, "param [beginDate] can not be null!");
            Assert.notNull(req.getEndDate(), ErrorCode.PARAM_ERROR, "param [endDate] can not be null!");
            Assert.notNull(req.getCardTypeId(), ErrorCode.PARAM_ERROR, "param [cardTypeId] can not be null!");
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            List<PassCardAnalyseRes> passCardList = passCardService.getPassCard(req);
            result.setResult(passCardList);
        }
        catch (Exception e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("getPassCardNum fail!", e);
        }
        return result;
    }
    
}
