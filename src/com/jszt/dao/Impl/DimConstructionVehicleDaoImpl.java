/*
 * 文 件 名:  ConstructionVehicleDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年1月6日
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
import com.jszt.dao.DimConstructionVehicleDao;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.vo.ConstructionVehicleReq;

/**
 * 渣土车数据库操作
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年1月6日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimConstructionVehicleDaoImpl implements DimConstructionVehicleDao
{
    /**
     * 新增渣土车信息
     * @param dimConstructionVehicle
     */
    @Override
    public void addConstructionVehicle(final DimConstructionVehicle dimConstructionVehicle)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addConstructionVehicle");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 1;
                    pst.setString(index++, dimConstructionVehicle.getLicensePlate());
                    pst.setString(index++, dimConstructionVehicle.getPlateTypeId());
                    pst.setString(index++, dimConstructionVehicle.getVehicleBrand());
                    pst.setString(index++, dimConstructionVehicle.getIdCard());
                    pst.setString(index++, dimConstructionVehicle.getOwnerName());
                    pst.setString(index++, dimConstructionVehicle.getPhoneNumber());
                    pst.setString(index++, dimConstructionVehicle.getAddress());
                    pst.setInt(index++, dimConstructionVehicle.getRegisterDate());
                    pst.setInt(index++, dimConstructionVehicle.getBeginDate());
                    pst.setInt(index++, dimConstructionVehicle.getEndDate());
                    pst.setString(index++, dimConstructionVehicle.getPassPeriod());
                    pst.setString(index++, dimConstructionVehicle.getRegisterPeople());
                    pst.setString(index++, dimConstructionVehicle.getDescription());
                    pst.setString(index++, dimConstructionVehicle.getCompanyName());
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addConstructionVehicle error!");
        }
    }
    
    /**
     * 根据车牌号、使用人、手机号查询渣土车信息列表
     * @param req
     * @return
     */
    @Override
    public List<DimConstructionVehicle> getConstructionVehicle(final ConstructionVehicleReq req)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getConstructionVehicle");
        StringBuilder sqlBuilder = new StringBuilder(sql);
        
        if (StringUtils.isNotBlank(req.getLicensePlate()))
        {
            sqlBuilder.append(" and d.LICENSE_PLATE = ?");
        }
        if (StringUtils.isNotBlank(req.getOwnerName()))
        {
            sqlBuilder.append(" and d.OWNER_NAME = ?");
        }
        if (StringUtils.isNotBlank(req.getPhoneNumber()))
        {
            sqlBuilder.append(" and d.PHONE_NUMBER = ?");
        }
        if(StringUtils.isNotBlank(req.getCompanyName()))
        {
            sqlBuilder.append(" and d.COMPANYNAME = ?");
        }
        
        try
        { 
            return ServiceUtil.getService(JdbcTemplate.class).getList(sqlBuilder.toString(),
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        int index = 0;
                        if (StringUtils.isNotBlank(req.getLicensePlate()))
                        {
                            index++;
                            pst.setString(index, req.getLicensePlate());
                        }
                        if (StringUtils.isNotBlank(req.getOwnerName()))
                        {
                            index++;
                            pst.setString(index, req.getOwnerName());
                        }
                        if (StringUtils.isNotBlank(req.getPhoneNumber()))
                        {
                            index++;
                            pst.setString(index, req.getPhoneNumber());
                        }
                        if (StringUtils.isNotBlank(req.getCompanyName()))
                        {
                            index++;
                            pst.setString(index, req.getCompanyName());
                        }
                    }
                },
                CONVERTER);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getConstructionVehicle error!");
        }
    }


    private static final JdbcTemplate.Converter<DimConstructionVehicle, SQLException> CONVERTER =
        new JdbcTemplate.Converter<DimConstructionVehicle, SQLException>()
        {
            @Override
            public DimConstructionVehicle convert(ResultSet rs)
                throws SQLException
            {
                DimConstructionVehicle dimConstructionVehicle = new DimConstructionVehicle();
                
                dimConstructionVehicle.setLicensePlate(rs.getString("LICENSE_PLATE"));
                dimConstructionVehicle.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                dimConstructionVehicle.setVehicleBrand(rs.getString("VEHICLE_BRAND"));
                dimConstructionVehicle.setIdCard(rs.getString("ID_CARD"));
                dimConstructionVehicle.setOwnerName(rs.getString("OWNER_NAME"));
                dimConstructionVehicle.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                dimConstructionVehicle.setAddress(rs.getString("ADDRESS"));
                dimConstructionVehicle.setRegisterDate(rs.getInt("REGISTER_DATE"));
                dimConstructionVehicle.setBeginDate(rs.getInt("BEGIN_DATE"));
                dimConstructionVehicle.setEndDate(rs.getInt("END_DATE"));
                dimConstructionVehicle.setPassPeriod(rs.getString("PASS_PERIOD"));
                dimConstructionVehicle.setRegisterPeople(rs.getString("REGISTER_PEOPLE"));
                dimConstructionVehicle.setDescription(rs.getString("DESCRIPTION"));
                dimConstructionVehicle.setCompanyName(rs.getString("COMPANYNAME"));
                
                return dimConstructionVehicle;
            }
        };
    
    /**
     * 根据车牌号删除渣土车信息，支持多删除
     * @param strings
     */
    @Override
    public void deleteConstructionVehicle(final String[] licensePlates)
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteConstructionVehicle");
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (licensePlates.length > 0)
        {
            for (int i = 0; i < licensePlates.length; i++)
            {
                sb.append("?,");
            }
            sb.deleteCharAt(sb.toString().length() - 1);
            sb.append(")");
            try
            {
                ServiceUtil.getService(JdbcTemplate.class).execute(sb.toString(), new JdbcTemplate.ParameterFeeder()
                {

                    @Override
                    public void setParameter(PreparedStatement pst) throws SQLException
                    {
                        int index = 1;
                        for (String licensePlate : licensePlates)
                        {
                            pst.setString(index++, licensePlate);
                        }
                    }
                });
            } catch (SQLException e)
            {
                throw new ServiceException("deleteConstructionVehicle error!", e);
            }
        }
    }
    
    /**
     * 根据车牌号修改渣土车信息
     * @param dimConstructionVehicle
     */
    @Override
    public void updateConstructionVehicle(final DimConstructionVehicle dimConstructionVehicle)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateConstructionVehicle");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    
                    int index = 1;
                    
                    pst.setString(index++, dimConstructionVehicle.getPlateTypeId());
                    pst.setString(index++, dimConstructionVehicle.getVehicleBrand());
                    pst.setString(index++, dimConstructionVehicle.getIdCard());
                    pst.setString(index++, dimConstructionVehicle.getOwnerName());
                    pst.setString(index++, dimConstructionVehicle.getPhoneNumber());
                    pst.setString(index++, dimConstructionVehicle.getAddress());
                    pst.setInt(index++, dimConstructionVehicle.getRegisterDate());
                    pst.setInt(index++, dimConstructionVehicle.getBeginDate());
                    pst.setInt(index++, dimConstructionVehicle.getEndDate());
                    pst.setString(index++, dimConstructionVehicle.getPassPeriod());
                    pst.setString(index++, dimConstructionVehicle.getRegisterPeople());
                    pst.setString(index++, dimConstructionVehicle.getDescription());
                    pst.setString(index++, dimConstructionVehicle.getCompanyName());
                    pst.setString(index++, dimConstructionVehicle.getLicensePlate());
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateConstructionVehicle error!");
        }
    }
    
    /**
     * 
     * @param conn
     */
    @Override
    public void deleteConstructionVehicle(Connection conn)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delConstructionVehicle");
        
        try
        {
            JdbcTemplate.execute(conn, sql, null);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "delConstructionVehicle error!");
        }
    }
    
    /**
     * 批量新增渣土车信息
     * @param conn
     * @param retList
     */
    @Override
    public void addBatchConstructionVehicle(Connection conn, final List<DimConstructionVehicle> retList)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addConstructionVehicle");
        try
        {
            JdbcTemplate.executeBatch(conn, sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                   for(DimConstructionVehicle dimConstructionVehicle : retList)
                   {
                       int index = 1;
                       
                       pst.setString(index++, dimConstructionVehicle.getLicensePlate());
                       pst.setString(index++, dimConstructionVehicle.getPlateTypeId());
                       pst.setString(index++, dimConstructionVehicle.getVehicleBrand());
                       pst.setString(index++, dimConstructionVehicle.getIdCard());
                       pst.setString(index++, dimConstructionVehicle.getOwnerName());
                       pst.setString(index++, dimConstructionVehicle.getPhoneNumber());
                       pst.setString(index++, dimConstructionVehicle.getAddress());
                       pst.setInt(index++, dimConstructionVehicle.getRegisterDate());
                       pst.setInt(index++, dimConstructionVehicle.getBeginDate());
                       pst.setInt(index++, dimConstructionVehicle.getEndDate());
                       pst.setString(index++, dimConstructionVehicle.getPassPeriod());
                       pst.setString(index++, dimConstructionVehicle.getRegisterPeople());
                       pst.setString(index++, dimConstructionVehicle.getDescription());
                       pst.setString(index++, dimConstructionVehicle.getCompanyName());
                       
                       pst.addBatch();
                   }
                   pst.executeBatch(); 
                }
                
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addBatchConstructionVehicle error!");
        }
    }
    
    /**
     * 查询所有渣土车车牌号
     * @return
     */
    @Override
    public List<String> getLicensePlates()
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLicensePlates");
        try
        { 
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, null, new JdbcTemplate.Converter<String, SQLException>(){

                @Override
                public String convert(ResultSet rs)
                    throws SQLException
                {
                    String licensePlate = rs.getString("LICENSE_PLATE");
                    return licensePlate;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getLicensePlates error!");
        }
    }

    @Override
    public List<DimConstructionVehicle> getValidConVehicles(final int date)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getValidConVehicles");
        
        try
        { 
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        int index = 0;
                        pst.setInt(++index, date);
                        pst.setInt(++index, date);
                    }
                },
                CONVERTER);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getValidConVehicles error!");
        }
    }

    /**
     * 根据公司名稱查詢屬於該公司的渣土车
     * @param vehicleReq
     * @return
     */
    @Override
    public List<DimConstructionVehicle> getConsVehicleByCompanyName(final ConstructionVehicleReq vehicleReq)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getConsVehicleByCompanyName");
        StringBuilder sqlBuilder = new StringBuilder(sql);
        
        if(StringUtils.isNotBlank(vehicleReq.getCompanyName()))
        {
            sqlBuilder.append(" and d.COMPANYNAME = ?");
        }
        
        try
        { 
            return ServiceUtil.getService(JdbcTemplate.class).getList(sqlBuilder.toString(),
                new JdbcTemplate.ParameterFeeder()
                {
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        if(StringUtils.isNotBlank(vehicleReq.getCompanyName()))
                        {
                            pst.setString(1, vehicleReq.getCompanyName());
                        }
                    }
                },
                CONVERTER);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getConsVehicleByCompanyName error!");
        }
    }

    /**
     * 查詢所有公司(顯示公司名稱)
     * @return
     */
    @Override
    public List<String> getCompanyNames()
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getCompanyNames");
        try
        { 
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, null, new JdbcTemplate.Converter<String, SQLException>(){

                @Override
                public String convert(ResultSet rs)
                    throws SQLException
                {
                    String companyName = rs.getString("COMPANYNAME");
                    return companyName;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getCompanyNames error!");
        }
    }
}
