/*
 * 文 件 名:  CardUserInfoController.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
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
import com.jszt.adapter.util.AdapterContains;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Page;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.dao.DimCardUserInfoDao;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.service.PassCardService;
import com.jszt.vo.CardUserInfoReq;
import com.jszt.vo.DeleteCardUserInfo;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.UpdatePassCardReq;

/**
 * 通行证用户信息controller层 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CardUserInfoController
{
    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(CardUserInfoController.class);
    
    /**
     * 新增用户绑定 <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/addCardUserInfo.json")
    public Response<String> addCardUserInfo(PassCardRequest passCardRequest)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter [func] addCardUserInfo.");
        }
        Response<String> resp = new Response<String>();
        try
        {
            PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
            String cardId = passCardService.boundCard(passCardRequest);
            resp.setResult(cardId);
            resp.setRetMessage("成功");
        }
        catch (Exception e)
        {
            resp.setRetMessage(e.getMessage());
            resp.setRetCode(-1);
            logger.error("addCardUserInfo fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit [func] addCardUserInfo.");
        }
        
        return resp;
    }
    
    /**
     * 更新通行证绑定信息 <功能详细描述>
     * 
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateCardUserInfo.json")
    public Response<Void> updateCardUserInfo(PassCardRequest passCardRequest)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter func [updateCardUserInfo].");
        }
        Response<Void> response = new Response<Void>(0, "成功");
        PassCardService passCardService = ServiceUtil.getService(PassCardService.class);
        try
        {
            passCardService.updateCardUserInfo(passCardRequest);
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("updateCardUserInfo fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit func [updateCardUserInfo].");
        }
        return response;
    }
    
    /**
     * 获取通行证绑定信息 <功能详细描述>
     * 
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getCardUserInfo.json")
    public Response<DimCardUserInfo> getCardUserInfo(PassCardRequest passCardRequest)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter func [getCardUserInfo].");
        }
        Response<DimCardUserInfo> response = new Response<DimCardUserInfo>();
        try
        {
            check(passCardRequest);
            DimCardUserInfo cardUserInfo =
                ServiceUtil.getService(PassCardService.class).getDimCardUserInfo(passCardRequest);
            response.setResult(cardUserInfo);
            response.setRetMessage("成功");
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("getCardUserInfo fail!", e);
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit func [getCardUserInfo].");
        }
        
        return response;
    }
    
    /**
     * 删除通行证绑定信息 <功能详细描述>
     * 
     * @param info
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/delCardUserInfo.json")
    public Response<Void> delCardUserInfo(DeleteCardUserInfo info)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter func [delCardUserInfo].");
        }
        Response<Void> response = new Response<Void>();
        try
        {
            ServiceUtil.getService(PassCardService.class).delCardUserInfo(info);
            response.setRetMessage("成功");
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("delCardUserInfo fail!", e);
            
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit func [delCardUserInfo].");
        }
        return response;
    }
    
    /**
     * 绑定用户审核 <功能详细描述>
     * 
     * @param info
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/updateProveResult.json")
    public Response<Void> updateProveResult(UpdatePassCardReq req)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter func [updateZXKProveResult]");
        }
        Response<Void> response = new Response<Void>();
        try
        {
            ServiceUtil.getService(PassCardService.class).updateProveResult(req);
            response.setRetMessage("成功");
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            logger.error("updateProveResult fail!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit func [updateZXKProveResult]");
        }
        
        return response;
    }
    
    /**
     * 查询临时通行证用户绑定信息
     * @param info
     * @return
     */
    @WebExporter("/Service/FAManager/getAllDimCardUserInfo.json")
    public Response<List<DimCardUserInfo>> getAllDimCardUserInfo(CardUserInfoReq info)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter func [getAllDimCardUserInfo]");
        }
        Response<List<DimCardUserInfo>> response = new Response<List<DimCardUserInfo>>();
        try
        {
            List<DimCardUserInfo> infos = ServiceUtil.getService(PassCardService.class).getAllCardUserInfo(info);
            String count = ServiceUtil.getService(DimCardUserInfoDao.class).getCardUserInfoCount(info);
            // 是否满足分页条件
            if (AdapterContains.NEGATIVE_ONE != info.getPageIndex()
                && AdapterContains.NEGATIVE_ONE != info.getPageCount())
            {
                Page page = new Page(info.getPageIndex(), info.getPageCount(), Integer.valueOf(count));
                response.setPage(page);
            }
            
            response.setResult(infos);
        }
        catch (JsztException e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            e.printStackTrace();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit func [getAllDimCardUserInfo]");
        }
        return response;
    }
    
    /**
     * 获取已经绑定的用户信息 <功能详细描述>
     * 
     * @param cardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    @WebExporter("/Service/FAManager/getSameCardUserInfo.json")
    public Response<List<DimCardUserInfo>> getSameCardUserInfo(PassCardRequest cardRequest)
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [getSameCardUserInfo.]");
        }
        Response<List<DimCardUserInfo>> response = new Response<List<DimCardUserInfo>>();
        try
        {
            List<DimCardUserInfo> cardUserInfo =
                ServiceUtil.getService(PassCardService.class).getSameCardUserInfo(cardRequest);
            response.setResult(cardUserInfo);
        }
        catch (Exception e)
        {
            response.setRetMessage(e.getMessage());
            response.setRetCode(-1);
            e.printStackTrace();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [getSameCardUserInfo.]");
        }
        return response;
    }
    
    /**
     * 校检参数 <功能详细描述>
     * 
     * @param passCardRequest
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    private void check(PassCardRequest passCardRequest)
        throws JsztException
    {
        Assert.notNull(passCardRequest.getLicensePlate(), ErrorCode.PARAM_ERROR, "param [车牌号] can not be null");
    }
    
}
