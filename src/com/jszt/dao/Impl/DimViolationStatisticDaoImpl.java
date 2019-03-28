/*
 * 文 件 名:  DimViolationStatisticDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年6月1日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationStatisticDao;
import com.jszt.domain.DimViolationStatistic;

/**
 * 违法未通知次数数据
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationStatisticDaoImpl implements DimViolationStatisticDao
{

    /**
     * @return
     */
    @Override
    public List<DimViolationStatistic> queryUnStatistic()
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.queryUnStatistic"),
                null,
                new JdbcTemplate.Converter<DimViolationStatistic, SQLException>()
                {
                    @Override
                    public DimViolationStatistic convert(ResultSet rs)
                        throws SQLException
                    {
                        DimViolationStatistic data = new DimViolationStatistic();
                        data.setCardType(rs.getString("CARD_TYPE"));
                        data.setCount(rs.getInt("cou"));
                        data.setForbidType(rs.getString("FORBID_TYPE"));
                        data.setLicensePlate(rs.getString("LICENSE_PLATE"));
                        data.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                        data.setLastNotify(rs.getString("time"));
                        return data;
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException("queryUnStatistic error！", e);
        }
    }

    /**
     * @return
     */
    @Override
    public List<DimViolationStatistic> queryStatistic()
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.queryStatistic"),
                null,
                new JdbcTemplate.Converter<DimViolationStatistic, SQLException>()
                {
                    @Override
                    public DimViolationStatistic convert(ResultSet rs)
                        throws SQLException
                    {
                        DimViolationStatistic data = new DimViolationStatistic();
                        data.setCount(rs.getInt("cou"));
                        data.setLicensePlate(rs.getString("LICENSE_PLATE"));
                        data.setLastNotify(rs.getString("LAST_NOTIFY"));
                        return data;
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException("queryStatistic error！", e);
        }
    }

    /**
     * @param datas
     */
    @Override
    public void insertStatistic(final List<DimViolationStatistic> datas)
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).executeBatch(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.insertStatistic"), new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        for (DimViolationStatistic dimViolationStatistic : datas)
                        {
                            int index = 1;
                            pst.setString(index++, dimViolationStatistic.getLicensePlate());
                            pst.setString(index++, dimViolationStatistic.getPlateTypeId());
                            pst.setString(index++, dimViolationStatistic.getForbidType());
                            pst.setString(index++, dimViolationStatistic.getCardType());
                            pst.setInt(index++, dimViolationStatistic.getCount());
                            pst.setString(index++, dimViolationStatistic.getLastNotify());
                            
                            pst.addBatch();
                        }
                        
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException("insertStatistic error！", e);
        }
        
    }

    
    
    /**
     * @param datas
     */
    @Override
    public void updateStatistic(final List<DimViolationStatistic> datas)
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).executeBatch(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.updateStatistic"), new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        for (DimViolationStatistic dimViolationStatistic : datas)
                        {
                            int index = 1;
                            pst.setInt(index++, dimViolationStatistic.getCount());
                            pst.setString(index++, dimViolationStatistic.getLastNotify());
                            pst.setString(index++, dimViolationStatistic.getLicensePlate());
                            
                            pst.addBatch();
                        }
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException("updateStatistic error！", e);
        }
        
        
    }
    
}
