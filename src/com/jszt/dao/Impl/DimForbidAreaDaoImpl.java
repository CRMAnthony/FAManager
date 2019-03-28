/*
 * 文 件 名:  DimForbidAreaDaoImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao.Impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.ParameterFeeder;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimForbidAreaDao;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimViolationAgency;

/**
 * 禁行区域数据库配置 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimForbidAreaDaoImpl implements DimForbidAreaDao
{

	@Override
	public void addForbidArea(final DimForbidArea area)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addForbidArea");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, area.getAreaName());
					pst.setInt(2, area.getAreaType());
					pst.setString(3, area.getIntList());
					pst.setBlob(4, new ByteArrayInputStream(area.getDeviceList().getBytes()));
					pst.setString(5, area.getAreaPoints());
					pst.setString(6, area.getDescription());
					pst.setString(7, area.getAccountList());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "addForbidArea error!");
		}

	}

	@Override
	public List<DimForbidArea> getForbidArea(final int areaId)
	{
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForbidArea"));
		if (areaId != 0)
		{
			sql.append(" and a.area_id =? ");
		}

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					if (areaId != 0)
					{
						pst.setInt(1, areaId);
					}
				}
			}, CONVERTER);
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidArea error!");
		}

	}

	private static final JdbcTemplate.Converter<DimForbidArea, SQLException> CONVERTER =

	new JdbcTemplate.Converter<DimForbidArea, SQLException>()
	{
		@Override
		public DimForbidArea convert(ResultSet rst) throws SQLException
		{
			DimForbidArea forbidArea = new DimForbidArea();
			forbidArea.setAreaId(rst.getInt("AREA_ID"));
			forbidArea.setAreaName(rst.getString("AREA_NAME"));
			forbidArea.setAreaType(rst.getInt("AREA_TYPE"));
			forbidArea.setIntList(rst.getString("INT_LIST"));

			InputStream steam = rst.getBlob("DEVICE").getBinaryStream();
			try
			{
				forbidArea.setDeviceList(IOUtils.toString(steam));
			} catch (IOException e)
			{
				throw new ServiceException(e);
			}
			forbidArea.setAreaPoints(rst.getString("AREA_POINTS"));
			forbidArea.setDescription(rst.getString("DESCRIPTION"));
			forbidArea.setAccountList(rst.getString("ACCOUNT_LIST"));
			return forbidArea;
		}
	};

	/**
	 * 根据禁区ID获得禁行区域信息
	 */
	@Override
	public List<DimForbidArea> getForbidAreaById(final String areaId) {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForbidAreaById"));
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{
				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, areaId);
				}
			}, CONVERTER);
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidArea error!");
		}
	}

	/**
	 * @param areaId
	 * @param areaType
	 * @return
	 */
	@Override
	public List<DimForbidArea> getForbidArea(final int areaId, final String areaType)
	{
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForbidArea"));
		if (areaId != 0)
		{
			sql.append(" and a.area_id =? ");
		}
		if (StringUtils.isNotBlank(areaType))
		{
			sql.append(" and a.area_type = ? ");
		}
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{
				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 0;
					if (areaId != 0)
					{
						pst.setInt(++index, areaId);
					}
					if (StringUtils.isNotBlank(areaType))
					{
						pst.setInt(++index, Integer.parseInt(areaType));
					}
				}
			}, CONVERTER);
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidArea error!");
		}
	}

	/**
	 * @param id
	 */
	@Override
	public void delForbidArea(final int[] id)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delForbidArea");
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
						for (Integer string : id)
						{
							pst.setInt(index++, string);
						}
					}
				});
			} catch (SQLException e)
			{
				throw new ServiceException("delForbidArea error！", e);
			}
		}
	}

	/**
	 * @param agencyId
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimViolationAgency getViolationAgency(final String agencyId)
	{
		//本地
//		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("wjzhjtFactory");
		//线网
		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("violationConnFactory");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
		try
		{
			return jdbcTemplate.getItem(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationAgency"), new ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement ps) throws SQLException
				{
					ps.setString(1, agencyId);
				}
			}, new JdbcTemplate.Converter<DimViolationAgency, Exception>()
			{

				@Override
				public DimViolationAgency convert(ResultSet rs) throws SQLException
				{
					DimViolationAgency agency = new DimViolationAgency();
					agency.setAgencyId(rs.getString("AGENCY_ID"));
					agency.setAgencyName(rs.getString("AGENCY_NAME"));
					return agency;
				}
			});
		} catch (SQLException e)
		{
		    throw new ServiceException(e, "getViolationAgency error!");
		}
	}

	/**
	 * 根据地点查找所在部门id
	 * 
	 * @param position
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimElectricPoliceCamera getCamera(final String position) throws JsztException
	{
		//线网
		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("basicFactory");
		//本地测试
//		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("wjzhjtConnFactory");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
		try
		{
			return jdbcTemplate.getItem(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getCamera"), new ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement ps) throws SQLException
				{
					ps.setString(1, position);
				}
			}, new JdbcTemplate.Converter<DimElectricPoliceCamera, Exception>()
			{

				@Override
				public DimElectricPoliceCamera convert(ResultSet rs) throws SQLException
				{
					DimElectricPoliceCamera camera = new DimElectricPoliceCamera();
					camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
					camera.setAgencyId(rs.getString("AGENCY_ID"));
					return camera;
				}
			});
		} catch (SQLException e)
		{
			throw new JsztException();
		}
	}

	/**
	 * 根据id获取地点
	 * 
	 * @param cameraId
	 * @return
	 * @throws JsztException
	 */
	@Override
	public List<DimElectricPoliceCamera> getCameraById() throws JsztException
	{
		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("basicFactory");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
		try
		{
			return jdbcTemplate.getList(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getCameraById"), null, new JdbcTemplate.Converter<DimElectricPoliceCamera, Exception>()
			{

				@Override
				public DimElectricPoliceCamera convert(ResultSet rs) throws SQLException
				{
					DimElectricPoliceCamera camera = new DimElectricPoliceCamera();
					camera.setElectricPoliceNumber(rs.getString("ELECTRIC_POLICE_NUMBER"));
					camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
					return camera;
				}
			});
		} catch (SQLException e)
		{
			throw new JsztException("getCameraById fail!!");
		}
	}

	/**
	 * @param camreaId
	 * @return
	 * @throws JsztException
	 */
	@Override
	public List<DimLicenseCamera> getlicenseCameraById() throws JsztException
	{
		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("basicFactory");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
		try
		{
			return jdbcTemplate.getList(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getlicenseCameraById"), null, new JdbcTemplate.Converter<DimLicenseCamera, Exception>()
			{

				@Override
				public DimLicenseCamera convert(ResultSet rs) throws SQLException
				{
					DimLicenseCamera camera = new DimLicenseCamera();
					camera.setLpCameraNumber(rs.getString("LP_CAMERA_NUMBER"));
					camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
					return camera;
				}
			});
		} catch (SQLException e)
		{
			throw new JsztException("getlicenseCamera fail!!");
		}
	}

	/**
	 * @param position
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimLicenseCamera getLicenseCamera(final String position) throws JsztException
	{
		ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("basicFactory");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
		try
		{
			return jdbcTemplate.getItem(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getlicenseCamera"), new ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement ps) throws SQLException
				{
					ps.setString(1, position);
				}
			}, new JdbcTemplate.Converter<DimLicenseCamera, Exception>()
			{

				@Override
				public DimLicenseCamera convert(ResultSet rs) throws SQLException
				{
					DimLicenseCamera camera = new DimLicenseCamera();
					camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
					camera.setAgencyId(rs.getString("AGENCY_ID"));
					return camera;
				}
			});
		} catch (SQLException e)
		{
			throw new JsztException();
		}
	}

	/**
	 * 更新禁行区域实现
	 * 
	 * @param area
	 * @throws JsztException
	 */
	@Override
	public void updateDimForbidArea(final DimForbidArea area) throws JsztException
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateForbidArea");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, area.getAreaName());
					pst.setInt(2, area.getAreaType());
					pst.setString(3, area.getIntList());
					pst.setBlob(4, new ByteArrayInputStream(area.getDeviceList().getBytes()));
					pst.setString(5, area.getAreaPoints());
					pst.setString(6, area.getDescription());
					pst.setString(7, area.getAccountList());
					pst.setInt(8, area.getAreaId());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "updateDimForbidArea error!");
		}
	}

}
