/*
 * 文 件 名:  DimViolationFirstDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao.Impl;

import java.sql.Connection;
import java.util.List;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationFirstDao;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;

/**
 * 外地车首次违法信息数据库操作
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationFirstDaoImpl extends IllegalRecordUtilsDaoImpl implements DimViolationFirstDao
{
    
    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> extractViolationFirst(IllegalRecordQueryConditionReq condition)
    {
        return super.extractRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
            .getConfig("sql.extractViolationFirst"), condition);
    }
    
    @Override
    public PicImageResp getFirstPic(int date, String licensePlate)
    {
        return null;
//        return super.getPicImageItem(date, licensePlate, 0,((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
//            .getConfig("sql.getFirstPic"));
    }


    @Override
    public void insertViolationFirst(Connection con,IllegalApproval data)
    {
        super.insertRecodrs(con, data);
    }

    @Override
    public void deleteViolationFirst(Connection con,IllegalApproval data)
    {
        super.deleteRecodrs(con, data);
    }



    /**
     * @return
     */
    @Override
    int getIllegalRecordType()
    {
        return 4;
    }
    
}
