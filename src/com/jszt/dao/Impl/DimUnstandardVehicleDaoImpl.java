/*
 * 文 件 名:  DimUnstandardVehicleDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年5月19日
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
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimUnstandardVehicleDao;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.vo.UnstandardVehicleReq;

/**
 * 黄标车数据访问
 * 
 * @author gxj
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimUnstandardVehicleDaoImpl implements DimUnstandardVehicleDao
{
    /**
     * 录入黄标车
     * 
     * @param dimUnstandardVehicle
     * @throws JsztException
     */
    @Override
    public void addUnstandardVehicle(final DimUnstandardVehicle dimUnstandardVehicle)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addUnstandardVehicle");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 1;
                    pst.setString(index++, dimUnstandardVehicle.getLicensePlate());
                    pst.setString(index++, dimUnstandardVehicle.getPlateTypeId());
                    pst.setString(index++, dimUnstandardVehicle.getVehicleBrand());
                    pst.setString(index++, dimUnstandardVehicle.getOwnerName());
                    pst.setString(index++, dimUnstandardVehicle.getAddress());
                    pst.setString(index++, dimUnstandardVehicle.getPhoneNumber());
                    pst.setString(index++, dimUnstandardVehicle.getIdCard());
                    pst.setString(index++, dimUnstandardVehicle.getDescription());
                    
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addUnstandardVehicle error!");
        }
    }
    
    /**
     * 查询黄标车
     * 
     * @param req
     * @return
     */
    @Override
    public List<DimUnstandardVehicle> getUnstandardVehicle(final UnstandardVehicleReq req)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getUnstandardVehicle");
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
                    }
                },
                CONVERTER);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getUnstandardVehicle error!");
        }
    }
    
    private static final JdbcTemplate.Converter<DimUnstandardVehicle, SQLException> CONVERTER =
        new JdbcTemplate.Converter<DimUnstandardVehicle, SQLException>()
        {
            @Override
            public DimUnstandardVehicle convert(ResultSet rs)
                throws SQLException
            {
                DimUnstandardVehicle dimUnstandardVehicle = new DimUnstandardVehicle();
                
                dimUnstandardVehicle.setLicensePlate(rs.getString("LICENSE_PLATE"));
                dimUnstandardVehicle.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                dimUnstandardVehicle.setVehicleBrand(rs.getString("VEHICLE_BRAND"));
                dimUnstandardVehicle.setOwnerName(rs.getString("OWNER_NAME"));
                dimUnstandardVehicle.setAddress(rs.getString("ADDRESS"));
                dimUnstandardVehicle.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                dimUnstandardVehicle.setIdCard(rs.getString("ID_CARD"));
                dimUnstandardVehicle.setDescription(rs.getString("DESCRIPTION"));
                
                return dimUnstandardVehicle;
            }
        };
    
    /**
     * 根据车牌号删除黄标车
     * 
     * @param licensePlate
     */
      @Override
      public void deleteUnstandardVehicle(final String[] licensePlates)
      {
          String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteUnstandardVehicle");
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
                  throw new ServiceException("delUnstandardVehicle fail!", e);
              }
          }
      }
            
    /**
     * 根据车牌号修改相应信息
     * 
     * @param vehicle
     */
    @Override
    public void updateUnstandardVehicle(final DimUnstandardVehicle vehicle)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateUnstandardVehicle");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    
                    int index = 0;
                    pst.setString(++index, vehicle.getPlateTypeId());
                    pst.setString(++index, vehicle.getVehicleBrand());
                    pst.setString(++index, vehicle.getOwnerName());
                    pst.setString(++index, vehicle.getAddress());
                    pst.setString(++index, vehicle.getPhoneNumber());
                    pst.setString(++index, vehicle.getIdCard());
                    pst.setString(++index, vehicle.getDescription());
                    pst.setString(++index, vehicle.getLicensePlate());
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateUnstandardVehicle error!");
        }
    }

    /**
     * 
     */
    @Override
    public void deleteUnstandardVehicle(Connection conn)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delUnstandardVehicle");
        
        try
        {
            JdbcTemplate.execute(conn, sql, null);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "delUnstandardVehicle error!");
        }
    }

    /**
     * @param conn
     * @param retList
     */
    @Override
    public void addBatchUnstandardVehicle(Connection conn, final List<DimUnstandardVehicle> retList)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addUnstandardVehicle");
        try
        {
            JdbcTemplate.executeBatch(conn, sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                   for(DimUnstandardVehicle unstandard : retList)
                   {
                       int index = 1;
                       pst.setString(index++, unstandard.getLicensePlate());
                       pst.setString(index++, unstandard.getPlateTypeId());
                       pst.setString(index++, unstandard.getVehicleBrand());
                       pst.setString(index++, unstandard.getOwnerName());
                       pst.setString(index++, unstandard.getAddress());
                       pst.setString(index++, unstandard.getPhoneNumber());
                       pst.setString(index++, unstandard.getIdCard());
                       pst.setString(index++, unstandard.getDescription());
                       
                       pst.addBatch();
                   }
                   pst.executeBatch(); 
                }
                
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addBatchUnstandardVehicle error!");
        }
    }
    
    /**
     * @return
     */
    @Override
    public List<String> getLicensePlates()
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.LicensePlates");
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
}
