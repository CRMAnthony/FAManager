/*
 * 文 件 名:  DimForbidLineDaoImpl.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.Converter;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimForbidLineDao;
import com.jszt.domain.DimForbidLine;
import com.jszt.vo.DimPassPeriod;
import com.jszt.vo.ForbidLineReq;

/**
 * 禁行线路数据库访问
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimForbidLineDaoImpl implements DimForbidLineDao
{
	@Override
	public void addForbidLine(final DimForbidLine forbidLine)
	{

		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addForbidLine"),
					new JdbcTemplate.ParameterFeeder()
					{

						@Override
						public void setParameter(PreparedStatement pst) throws SQLException
						{
							pst.setString(1, forbidLine.getLineName());
							pst.setLong(2, forbidLine.getBeginTime());
							pst.setLong(3, forbidLine.getEndTime());
							pst.setString(4, forbidLine.getDescription());
						}
					});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "addForbidLine error!");
		}
	}

	@Override
	public List<DimForbidLine> getForbidLinesByInterval(int beginDate, int endDate)
	{
		final long beginTime = (long) beginDate * 1000000;
		final long endTime = (long) endDate * 1000000 + 235959;

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(
					((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForbidLinesByInterval"), new JdbcTemplate.ParameterFeeder()
					{

						@Override
						public void setParameter(PreparedStatement pst) throws SQLException
						{
							pst.setLong(1, endTime);
							pst.setLong(2, beginTime);
						}
					}, new JdbcTemplate.Converter<DimForbidLine, SQLException>()
					{
						@Override
						public DimForbidLine convert(ResultSet rs) throws SQLException
						{
							DimForbidLine forbidLine = new DimForbidLine();
							forbidLine.setLineId(rs.getInt("LINE_ID"));
							forbidLine.setLineName(rs.getString("LINE_NAME"));
							forbidLine.setBeginTime(rs.getLong("BEGIN_TIME"));
							forbidLine.setEndTime(rs.getLong("END_TIME"));
							forbidLine.setDescription(rs.getString("DESCRIPTION"));
							return forbidLine;
						}
					});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidLinesByIds error!");
		}
	}

	@Override
	public List<DimForbidLine> getForbidLinesByIds(String lineIds)
	{
		StringBuilder sqlBuilder = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForbidLinesByIds"));
		sqlBuilder.append(" (");
		sqlBuilder.append(lineIds);
		sqlBuilder.append(")");

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sqlBuilder.toString(), null, new JdbcTemplate.Converter<DimForbidLine, SQLException>()
			{
				@Override
				public DimForbidLine convert(ResultSet rs) throws SQLException
				{
					DimForbidLine forbidLine = new DimForbidLine();
					forbidLine.setLineId(rs.getInt("LINE_ID"));
					forbidLine.setLineName(rs.getString("LINE_NAME"));
					forbidLine.setBeginTime(rs.getLong("BEGIN_TIME"));
					forbidLine.setEndTime(rs.getLong("END_TIME"));
					forbidLine.setDescription(rs.getString("DESCRIPTION"));
					return forbidLine;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidLinesByIds error!");
		}
	}

	@Override
	public List<DimForbidLine> getForbidLine(final ForbidLineReq req)
	{
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getForBidLine"));

		if (StringUtils.isNotEmpty(req.getLineName()))
		{
			sql.append(" and f.line_name like ?");
		}
		if (req.getBeginTime() != 0)
		{
			sql.append(" and f.begin_time >= ? ");
		}
		if (req.getEndTime() != 0)
		{
			sql.append(" and f.end_time <= ?");
		}
		sql.append(" order by f.begin_time desc");

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					if (StringUtils.isNotEmpty(req.getLineName()))
					{
						pst.setString(index++, req.getLineName() + '%');
					}

					if (req.getBeginTime() != 0)
					{
						pst.setLong(index++, Long.valueOf(String.valueOf(req.getBeginTime()) + "000000"));
					}
					if (req.getEndTime() != 0)
					{
						pst.setLong(index++, Long.valueOf(String.valueOf(req.getEndTime()) + "999999"));
					}

				}
			}, new JdbcTemplate.Converter<DimForbidLine, SQLException>()
			{

				@Override
				public DimForbidLine convert(ResultSet rst) throws SQLException
				{
					DimForbidLine forbidLine = new DimForbidLine();
					forbidLine.setLineId(rst.getInt("LINE_ID"));
					forbidLine.setLineName(rst.getString("LINE_NAME"));
					forbidLine.setBeginTime(rst.getLong("BEGIN_TIME"));
					forbidLine.setEndTime(rst.getLong("END_TIME"));
					forbidLine.setDescription(rst.getString("DESCRIPTION"));
					return forbidLine;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getForbidLine error!");
		}

	}

	@Override
	public List<DimPassPeriod> getLongPassPeriod(final int begin, final int end)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongPassPeriod");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setInt(1, end);
					pst.setInt(2, begin);
				}
			}, convert());
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getLongPassPeriod error!");
		}

	}

	@Override
	public List<DimPassPeriod> getTempPassPeriod(final int begin, final int end)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempPassPeriod");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setInt(1, end);
					pst.setInt(2, begin);
				}
			}, convert());
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getTempPassPeriod error!");
		}
	}

	private Converter<DimPassPeriod, SQLException> convert()
	{
		return new JdbcTemplate.Converter<DimPassPeriod, SQLException>()
		{

			@Override
			public DimPassPeriod convert(ResultSet rs) throws SQLException
			{
				DimPassPeriod period = new DimPassPeriod();
				period.setBeginDate(rs.getInt("BEGIN_DATE"));
				period.setEndDate(rs.getInt("END_DATE"));
				period.setPassPeriod(rs.getString("PASS_PERIOD"));
				period.setPhoneNumber(rs.getString("PHONE_NUMBER"));
				period.setLicensePlate(rs.getString("LICENSE_PLATE"));
				return period;
			}
		};
	}

	/**
	 * @param id
	 */
	@Override
	public void delForbidLine(final int[] id)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delForbidLine");
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
				throw new ServiceException("delForbidLine error！", e);
			}
		}
	}

}
