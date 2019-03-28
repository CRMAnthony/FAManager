/*
 * 文 件 名:  PassTimeConfigController.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.controller;

import java.util.List;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.boulevard.service.exporter.WebExporter;
import com.jszt.boulevard.webservice.bean.Response;
import com.jszt.service.PassTimeConfigService;
import com.jszt.vo.PassTimeConfig;
import com.jszt.vo.QueryPassTimeConfigReq;

/**
 * 通行时段配额
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassTimeConfigController
{
    private static Logger logger = Logger.getLogger(PassTimeConfigController.class);
    
    @WebExporter("/Service/FAManager/queryPassTimeConfig.json")
    public Response<List<PassTimeConfig>> queryPassTimeConfig(QueryPassTimeConfigReq req)
    {
        Response<List<PassTimeConfig>> res = new Response<List<PassTimeConfig>>();
        try
        {
            List<PassTimeConfig> ret =
                ServiceUtil.getService(PassTimeConfigService.class).QueryPassTimeConfigByDate(req.getStartDate(),
                    req.getEndDate(),
                    req.getPlateTypeId(), req.getPassLineId());
            res.setResult(ret);
        }
        catch (ServiceException e)
        {
            res.setRetCode(-1);
            res.setRetMessage(ExceptionUtils.getStackTrace(e));
            logger.error("查询通行时段配置失败" + e.getMessage());
        }
        
        return res;
    }
}
