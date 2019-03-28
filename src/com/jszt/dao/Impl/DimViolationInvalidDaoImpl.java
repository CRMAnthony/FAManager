/*
 * 文 件 名:  DimViolationInvalidDaoImpl.java
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
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationInvalidDao;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;

/**
 * 违法无效信息
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationInvalidDaoImpl extends IllegalRecordUtilsDaoImpl implements DimViolationInvalidDao
{

    
    @Override
    int getIllegalRecordType()
    {
        return 3;
    }

    @Override
    public List<IllegalRecord> extractViolationInvalid(IllegalRecordQueryConditionReq condition)
    {
        return super.extractRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
            .getConfig("sql.extractViolationInvalid"),
            condition);
    }

    /**
     * @param date
     * @param licensePlate
     * @return
     */
    @Override
    public PicImageResp getInvalidPic(int date, String licensePlate,String areaId)
    {
        return super.getPicImageList(date,
            licensePlate,
            areaId,
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getInvalidPic"));
    }

    /**
     * @param con
     * @param data
     */
    @Override
    public void insertViolationInvalid(Connection con, IllegalApproval data)
    {
        super.insertRecodrs(con, data);
    }

    /**
     * @param con
     * @param data
     */
    @Override
    public void deleteViolationInvalid(Connection con, IllegalApproval data)
    {
        super.deleteRecodrs(con, data);
    }

    /**
     * @return
     */
    @Override
    public int getViolationInvalidCount(IllegalRecordQueryConditionReq condition)
    {
        return super.getRecordCount(condition);
    }

    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryViolationInvalid(IllegalRecordQueryConditionReq condition)
    {
        return super.queryRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
            .getConfig("sql.extractViolationInvalid"),
            condition);
    }

    /**
     * @param time
     * @param licensePlate
     * @return
     */
    @Override
    public IllegalRecord queryViolationInvalid(final String time, final String licensePlate, final String areaId)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getItem(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryViolationInvalid"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setString(1, time);
                            pst.setString(2, licensePlate);
                            pst.setString(3, areaId);
                        }
                    },
                    super.record_converter);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryViolationInvalid error!");
        }
    }

    /**
     * @param condition
     * @return
     */
    @Override
    public int queryInvalidPassRecordCount(IllegalRecordQueryConditionReq condition)
    {
        return super.getDataCount(condition,((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
            .getConfig("sql.extractViolationInvalid"));
    }
    
    
    
    
    
}
