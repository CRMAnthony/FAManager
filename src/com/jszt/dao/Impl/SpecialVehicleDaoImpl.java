/*
 * 文 件 名:  SpecialVehicleDaoImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年8月13日
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
import com.jszt.dao.SpecialVehicleDao;
import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.vo.SpecialVehicleReq;

/**
 * <一句话功能简述> <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年8月13日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class SpecialVehicleDaoImpl implements SpecialVehicleDao
{

	/**
	 * 获得特殊车辆信息
	 * 
	 * @param req
	 * @return
	 */
	@Override
	public List<DimViolationSpecialVehicle> getSpecialVehicle()
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getSpecialVehicle");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql, CONVERTER);
		} catch (SQLException e)
		{
			throw new ServiceException("getSpecialVehicle error！", e);
		}
	}

	/**
	 * 新增特殊车辆信息数据
	 * 
	 * @param req
	 */
	@Override
	public void addSpecialVehicle(final SpecialVehicleReq req)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addSpecialVehicle");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					pst.setInt(index++, req.getType());
					pst.setString(index++, req.getLicensePlate());
					pst.setString(index++, req.getPlateTypeId());
					pst.setString(index++, req.getForbidType());
					pst.setInt(index++, req.getStartDate());
					pst.setInt(index++, req.getEndDate());
					pst.setString(index++, req.getOwnerName());
					pst.setString(index++, req.getPhoneNumber());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException("addSpecialVehicle error!"+e.getMessage(), e);
		}
	}

	/**
	 * 删除特殊车辆信息数据
	 * 
	 * @param req
	 */
	@Override
	public void deleteSpecialVehicle(final String[] id)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteSpecialVehicle");
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if (id.length > 0)
		{
			for (int i = 0; i < id.length; i++)
			{
				sb.append("?,");
			}
			sb.deleteCharAt(sb.length() - 1);
			sb.append(")");
			try
			{
				ServiceUtil.getService(JdbcTemplate.class).execute(sb.toString(), new JdbcTemplate.ParameterFeeder()
				{

					@Override
					public void setParameter(PreparedStatement pst) throws SQLException
					{
						int index = 1;
						for (String string : id)
						{
							pst.setString(index++, string);
						}
					}
				});
			} catch (SQLException e)
			{
				throw new ServiceException("deleteSpecialVehicle error！", e);
			}
		}

	}

	/**
	 * 根据时间查询特殊车辆信息
	 * 
	 * @return
	 */
	@Override
	public List<DimViolationSpecialVehicle> getSpecialVehicleByTime(final SpecialVehicleReq req)
	{
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getSpecialVehicle"));
		if (req.getType() != null)
		{
			sql.append(" and d.type=?");
		}
		if (req.getStartDate() != null)
		{
			sql.append(" and d.start_date >=?");
		}
		if (req.getEndDate() != null)
		{
			sql.append(" and d.end_date <=?");
		}
		sql.append(" order by d.start_date desc");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					if (req.getType() != null)
					{
						pst.setInt(index++, req.getType());
					}
					if (req.getStartDate() != null)
					{
						pst.setInt(index++, req.getStartDate());
					}
					if (req.getEndDate() != null)
					{
						pst.setInt(index++, req.getEndDate());
					}
				}
			}, CONVERTER);
		} catch (SQLException e)
		{
			throw new ServiceException("getSpecialVehicleByTime error!", e);
		}
	}

	private static final JdbcTemplate.Converter<DimViolationSpecialVehicle, SQLException> CONVERTER = new JdbcTemplate.Converter<DimViolationSpecialVehicle, SQLException>()
	{

		@Override
		public DimViolationSpecialVehicle convert(ResultSet rs) throws SQLException
		{
			DimViolationSpecialVehicle vehicle = new DimViolationSpecialVehicle();
			vehicle.setId(rs.getString("ID"));
			vehicle.setType(rs.getInt("TYPE"));
			vehicle.setLicensePlate(rs.getString("LICENSE_PLATE"));
			vehicle.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
			vehicle.setForbidType(rs.getString("FORBID_TYPE"));
			vehicle.setStartDate(rs.getInt("START_DATE"));
			vehicle.setEndDate(rs.getInt("END_DATE"));
			vehicle.setOwnerName(rs.getString("OWNER_NAME"));
			vehicle.setPhoneNumber(rs.getString("PHONE_NUMBER"));
			return vehicle;
		}
	};

    @Override
    public List<DimViolationSpecialVehicle> getSpecialTruck()
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getSpecialTruck");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, CONVERTER);
        } catch (SQLException e)
        {
            throw new ServiceException("getSpecialTruck error！", e);
        }
    }

}
