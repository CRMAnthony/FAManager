/*
 * 文 件 名:  ConstructionSpeCardController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

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
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.service.ConstructionSpeCardService;
import com.jszt.vo.ConstructionSpeCardReq;

/**
 * 渣土车特殊通行证管理
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ConstructionSpeCardController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ConstructionVehicleController.class);
    
    /**
     * 新增渣土车信息
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/addConstructionSpeCard.json")
    public Response<String> addConstructionSpeCard(ConstructionSpeCardReq speCardReq)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [addConstructionSpeCard]. speCardReq=" + StringUtils.toString(speCardReq) + ".");
        }
        Response<String> result = new Response<String>(0,"成功");
        
        try
        {
            checkParam(speCardReq);
            
            ConstructionSpeCardService constructionSpeCardService = ServiceUtil.getService(ConstructionSpeCardService.class);
            constructionSpeCardService.addConstructionSpeCard(speCardReq);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(-1);
            logger.error("addConstructionSpeCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(-1);
            result.setRetMessage(e.getMessage());
            logger.error("addConstructionSpeCard fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [addConstructionSpeCard].");
        }
        
        return result;
    }

    /**
     * 根据车牌号、公司名称查询渣土车特殊通行证信息列表
     * 
     * @param speCardReq
     * @return
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/getConstructionSpeCard.json")
    public Response<List<DimConstructionSpeCard>> getConstructionSpeCard(ConstructionSpeCardReq speCardReq)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getConstructionSpeCard]." + StringUtils.toString(speCardReq) + ".");
        }
        Response<List<DimConstructionSpeCard>> result = new Response<List<DimConstructionSpeCard>>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(speCardReq, ErrorCode.PARAM_ERROR, "param [speCardReq] can not be null");
            ConstructionSpeCardService constructionSpeCardService = ServiceUtil.getService(ConstructionSpeCardService.class);
            List<DimConstructionSpeCard> constructionSpeCardList = constructionSpeCardService.getConstructionSpeCard(speCardReq);
           
            // 是否满足分页条件
            if (AdapterContains.NEGATIVE_ONE != speCardReq.getPageIndex() && AdapterContains.NEGATIVE_ONE != speCardReq.getPageCount())
            {
                Page page = new Page(speCardReq.getPageIndex(), speCardReq.getPageCount(), constructionSpeCardList.size());
                result.setPage(page);
                constructionSpeCardList = ListUtils.subList(constructionSpeCardList, speCardReq.getPageIndex() * speCardReq.getPageCount(), (speCardReq.getPageIndex() + 1) * speCardReq.getPageCount() - 1);
            }
            
            result.setResult(constructionSpeCardList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getConstructionSpeCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getConstructionSpeCard fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getConstructionSpeCard]");
        }
        return result;
    }
    
    /**
     * 根据车牌号删除渣土车
     * 
     * @param speCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/deleteConstructionSpeCard.json")
    public Response<String> deleteConstructionVehicle(ConstructionSpeCardReq speCardReq)
    {
        Response<String> result = new Response<String>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(speCardReq, ErrorCode.PARAM_ERROR, "param [speCardReq] can not be null");
            ConstructionSpeCardService constructionSpeCardService = ServiceUtil.getService(ConstructionSpeCardService.class);
            constructionSpeCardService.deleteConstructionSpeCard(speCardReq);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("deleteConstructionSpeCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("deleteConstructionSpeCard fail!", e);
        }
        
        return result;
    }
    
    /**
     * 根据车牌号修改渣土车
     * 
     * @param vehicle
     * @return
     * @see [类、类#方法、类#成员]
     */                              
    @WebExporter("/Service/FaManager/updateConstructionSpeCard.json")
    public Response<String> updateConstructionSpeCard(ConstructionSpeCardReq speCardReq)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [updateConstructionSpeCard]. speCardReq=" + StringUtils.toString(speCardReq) + ".");
        }
        
        Response<String> result = new Response<String>(0, "成功");
        
        try
       {
            //参数校验
            checkParam(speCardReq);
            
            ConstructionSpeCardService constructionSpeCardService = ServiceUtil.getService(ConstructionSpeCardService.class);
            constructionSpeCardService.updateConstructionSpeCard(speCardReq);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("updateConstructionSpeCard fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetCode(e.getResultCode());
            result.setRetMessage(e.getMessage());
            logger.error("updateConstructionSpeCard fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [updateConstructionSpeCard].");
        }
        
        return result;
    }
    
    //参数校验
    private void checkParam(ConstructionSpeCardReq speCardReq)
        throws JsztException
    {
        // 参数校验
        Assert.notNull(speCardReq, ErrorCode.PARAM_ERROR, "speCardReq can not be null");
        
        Assert.notEmpty(speCardReq.getLicensePlate(), ErrorCode.PARAM_ERROR, "车牌号不能为空");
        
        int RegisterDate = speCardReq.getRegisterDate();
        Assert.isTrue(RegisterDate != 0, ErrorCode.PARAM_ERROR, "登记日期不能为空");
        
        int beginDate = speCardReq.getBeginDate();
        Assert.isTrue(beginDate != 0, ErrorCode.PARAM_ERROR, "开始日期不能为空");
        
        int endDate = speCardReq.getEndDate();
        Assert.isTrue(endDate != 0, ErrorCode.PARAM_ERROR, "结束日期不能为空");
        
        Assert.notEmpty(speCardReq.getPassPeriod(), ErrorCode.PARAM_ERROR, "通行时段不能为空");
    }
    
    /**
     * 测试-----获取某一天有效的渣土车特殊通行证
     * @param speCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FaManager/getValidConSpeCards.json")
    public Response<List<DimConstructionSpeCard>> getValidConSpeCards(ConstructionSpeCardReq speCardReq)
    {
        Response<List<DimConstructionSpeCard>> result = new Response<List<DimConstructionSpeCard>>(0, "成功");
        
        try
        {
            // 参数校验
            Assert.notNull(speCardReq, ErrorCode.PARAM_ERROR, "param [speCardReq] can not be null");
            ConstructionSpeCardService constructionSpeCardService = ServiceUtil.getService(ConstructionSpeCardService.class);
            List<DimConstructionSpeCard> constructionSpeCardList = constructionSpeCardService.getValidConSpeCards(speCardReq.getBeginDate());
            
            result.setResult(constructionSpeCardList);
        }
        catch (ServiceException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(e.getErrorCode());
            logger.error("getValidConSpeCards fail!", e);
        }
        catch (JsztException e)
        {
            result.setRetMessage(e.getMessage());
            result.setRetCode(ErrorCode.UNKNOWN_ERROR);
            logger.error("getValidConSpeCards fail!", e);
        }
        return result;
    }
}
