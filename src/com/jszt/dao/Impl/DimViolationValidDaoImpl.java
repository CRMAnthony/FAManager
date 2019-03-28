/*
 * 文 件 名:  DimViolationValidDaoImpl.java
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationValidDao;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.TempPassCardVio;

/**
 * 违法记录数据操作类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationValidDaoImpl extends IllegalRecordUtilsDaoImpl implements DimViolationValidDao
{
    
    @Override
    public List<IllegalRecord> extractDimViolationValid(IllegalRecordQueryConditionReq condition)
    {
        return super.extractRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.extractDimViolationValid"),
            condition);
    }
    
    
    @Override
    public int getViolationCount(String time, String licensePlate)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationCount"),
                    time,
                    licensePlate,
                    time,
                    licensePlate)
                .intValue();
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getViolationCount error!");
        }
    }

    @Override
    public PicImageResp getViolationPic(int date, String licensePlate,String areaId)
    {
        return super.getPicImageItem(date,
            licensePlate,
            areaId,
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationPic"));
    }


    /**
     * @param con
     * @param data
     */
    @Override
    public void insertViolationValid(Connection con, IllegalApproval data)
    {
        super.insertRecodrs(con, data);
    }


    /**
     * @param con
     * @param data
     */
    @Override
    public void deleteViolationValid(Connection con, IllegalApproval data)
    {
        super.deleteRecodrs(con, data);
    }
    

    /**
     * @return
     */
    @Override
    public int getViolationValidCount(IllegalRecordQueryConditionReq condition)
    {
        return super.getRecordCount(condition);
    }


    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryDimViolationValid(IllegalRecordQueryConditionReq condition)
    {
        return super.queryRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.extractDimViolationValid"),
            condition);
    }

    
    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryViolationCount(final IllegalRecordQueryConditionReq condition)
    {
        StringBuilder tempSql = new StringBuilder(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryViolationCount"));
        if (StringUtils.isNotEmpty(condition.getLicensePlate()))
        {
            tempSql.append(" and License_Plate = ?");
        }
        
        if (StringUtils.isNotEmpty(condition.getCardType()))
        {
            tempSql.append(" and Card_Type = ?");
        }
        
        if (StringUtils.isNotEmpty(condition.getForbidType()))
        {
            tempSql.append(" and Forbid_Type = ?");
        }
        
        if (StringUtils.isNotEmpty(condition.getPlateTypeId()))
        {
            tempSql.append(" and Plate_Type_Id = ?");
        }
        
        tempSql.append(" group by License_Plate,CARD_TYPE,PLATE_TYPE_ID,FORBID_TYPE");
        
        String sql = tempSql.toString();
        if (condition.getIllegalCount() > 0)
        {
            sql = StringUtils.replace("select * from (#table) where cou >= ?", "#table", tempSql.toString());
        } 
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 1;
                    pst.setString(index++, String.valueOf(condition.getStartDate()));
                    pst.setString(index++, String.valueOf(condition.getEndDate()));
                    
                    if (StringUtils.isNotEmpty(condition.getLicensePlate()))
                    {
                        pst.setString(index++, condition.getLicensePlate());
                    }
                    
                    if (StringUtils.isNotEmpty(condition.getCardType()))
                    {
                        pst.setString(index++, condition.getCardType());
                    }
                    
                    if (StringUtils.isNotEmpty(condition.getForbidType()))
                    {
                        pst.setString(index++, condition.getForbidType());
                    }
                    
                    if (StringUtils.isNotEmpty(condition.getPlateTypeId()))
                    {
                        pst.setString(index++, condition.getPlateTypeId());
                    }
                    if (condition.getIllegalCount() > 0)
                    {
                        pst.setInt(index++, condition.getIllegalCount());
                    }
                    
                }
            }, new JdbcTemplate.Converter<IllegalRecord, SQLException>()
            {
                @Override
                public IllegalRecord convert(ResultSet rs)
                    throws SQLException
                {
                    IllegalRecord record = new IllegalRecord();
                    record.setLicensePlate(rs.getString("LICENSE_PLATE"));
                    record.setCardType(rs.getString("CARD_TYPE"));
                    record.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                    record.setForbidType(rs.getString("FORBID_TYPE"));
                    record.setIllegalCount(rs.getInt("cou"));
                    return record;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryViolationCount error!");
        }
    }
    
    
    /**
     * @param condition
     * @return
     */
    @Override
    public int queryValidPassRecordCount(IllegalRecordQueryConditionReq condition)
    {
        return super.getDataCount(condition, ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.extractDimViolationValid"));
    }


    @Override
    int getIllegalRecordType()
    {
        return 2;
    }


    /**
     * @param dateKey
     * @return
     */
    @Override
    public List<TempPassCardVio> queryTempPassCardViolation(final String dateKey)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTemoPassCardViolation");
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setString(1, dateKey);
                }
            }, new JdbcTemplate.Converter<TempPassCardVio, SQLException>()
            {

                @Override
                public TempPassCardVio convert(ResultSet rs)
                    throws SQLException
                {
                    TempPassCardVio cardVio =new TempPassCardVio();
                    cardVio.setRowId(rs.getString("rowid"));
                    cardVio.setTimeKey(rs.getInt("time_key"));
                    cardVio.setPassPeriod(rs.getString("pass_period"));
                    return cardVio;
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param rowId
     */
    @Override
    public void delTempPassCardViolation(final String rowId)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delViolation");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setString(1, rowId);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        
    }


    
}
