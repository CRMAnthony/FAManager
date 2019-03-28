/*
 * 文 件 名:  DimPassLineDaoImpl.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
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

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimPassLineDao;
import com.jszt.domain.DimPassLine;
import com.jszt.vo.PassLineInfoReq;

/**
 * 通行线路数据库访问
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimPassLineDaoImpl implements DimPassLineDao
{

	@Override
	public void addPassLine(final DimPassLine passLine)
	{
		try
		{
			String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addPassLine");
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setInt(1, passLine.getLineId());
					pst.setString(2, passLine.getIntList());
					pst.setString(3, passLine.getLineName());
//					pst.setString(3, passLine.getDeviceList());
					pst.setBlob(4, new ByteArrayInputStream(passLine.getDeviceList().getBytes()));
					pst.setString(5, passLine.getDescription());
					pst.setString(6, passLine.getAuthId());
					pst.setInt(7, passLine.getTempCardFlag());
					pst.setString(8, passLine.getAreaId());
				}
			});
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public Integer getAddPassLineId()
	{
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getAddPassLineId"), null,
					new JdbcTemplate.Converter<Integer, SQLException>()
					{
						@Override
						public Integer convert(ResultSet rs) throws SQLException
						{
							int id = rs.getInt("nextval");
							return id;
						}
					}).get(0);
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLine error!");
		}
	}

	@Override
	public List<DimPassLine> getPassLine()
	{
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassLine"), null,
					new JdbcTemplate.Converter<DimPassLine, SQLException>()
					{
						@Override
						public DimPassLine convert(ResultSet rs) throws SQLException
						{
							DimPassLine passLine = new DimPassLine();
							passLine.setLineId(rs.getInt("LINE_ID"));
							passLine.setLineName(rs.getString("LINE_NAME"));
							passLine.setIntList(rs.getString("INT_LIST"));
//							passLine.setDeviceList(rs.getString("DEVICE_LIST"));
							InputStream steam = rs.getBlob("DEVICE_LIST").getBinaryStream();
							try
							{
								passLine.setDeviceList(IOUtils.toString(steam));
							} catch (IOException e)
							{
								throw new ServiceException(e);
							}
							return passLine;
						}
					});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLine error!");
		}
	}

	@Override
	public List<DimPassLine> getPassLineByLineName(final PassLineInfoReq req)
	{
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassLineByLineName"));

		if (req.getLineId() != 0)
		{
			sql.append(" and p.line_id = ?");
		}

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					if (req.getLineId() != 0)
					{
						pst.setInt(1, req.getLineId());
					}
				}
			}, new JdbcTemplate.Converter<DimPassLine, SQLException>()
			{

				@Override
				public DimPassLine convert(ResultSet rst) throws SQLException
				{	
					DimPassLine passLine = new DimPassLine();
					passLine.setLineId(rst.getInt("LINE_ID"));
					passLine.setLineName(rst.getString("LINE_NAME"));
					passLine.setIntList(rst.getString("INT_LIST"));
//					passLine.setDeviceList(rst.getString("DEVICE_LIST"));
					InputStream steam = rst.getBlob("DEVICE_LIST").getBinaryStream();
					try
					{
						passLine.setDeviceList(IOUtils.toString(steam));
					} catch (IOException e)
					{
						throw new ServiceException(e);
					}
					passLine.setAreaId(rst.getString("AREA_ID"));
					passLine.setAuthId(rst.getString("AUTH_ID"));
					passLine.setTempCardFlag(rst.getInt("TEMP_CARD_FLAG"));
					passLine.setDescription(rst.getString("DESCRIPTION"));
					return passLine;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLineByLineName error!");
		}
	}

	/**
	 * @param req
	 */
	@Override
	public void delPassline(final int[] req)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delPassLine");
		StringBuilder sb = new StringBuilder();
		sb.append(sql);
		if (req.length > 0)
		{
			for (int i = 0; i < req.length; i++)
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
						for (Integer lineId : req)
						{
							pst.setInt(index++, lineId);
						}
					}
				});
			} catch (SQLException e)
			{
				throw new ServiceException("delPassline fail!", e);
			}
		}
	}

	@Override
	public List<DimPassLine> getPassLineByAccountName(final String account) {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassLineByAccountName"));
		sql.append(" and instr(','||p.AUTH_ID||',' , ',").append(account).append(",')>0");

		System.out.println("getPassLineByAccountName SQL:" + sql.toString());
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
//					pst.setString(1, account);
				}
			}, new JdbcTemplate.Converter<DimPassLine, SQLException>()
			{

				@Override
				public DimPassLine convert(ResultSet rst) throws SQLException
				{
					DimPassLine passLine = new DimPassLine();
					passLine.setLineId(rst.getInt("LINE_ID"));
					passLine.setLineName(rst.getString("LINE_NAME"));
					passLine.setIntList(rst.getString("INT_LIST"));
//					passLine.setDeviceList(rst.getString("DEVICE_LIST"));
					InputStream steam = rst.getBlob("DEVICE_LIST").getBinaryStream();
					try
					{
						passLine.setDeviceList(IOUtils.toString(steam));
					} catch (IOException e)
					{
						throw new ServiceException(e);
					}
					passLine.setDescription(rst.getString("DESCRIPTION"));
					return passLine;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLineByAccountName error!");
		}
	}

	@Override
	public List<String> getPassLineAuthIdsByIds(String passLineIds) throws JsztException {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassLineAuthIdsByIds"));
		String [] passLineArray = passLineIds.split(",");
		sql.append("(");
		for(int i=0; i<passLineArray.length; i++){
			sql.append("'").append(passLineArray[i]).append("'").append(",");
		}
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
//					pst.setString(1, account);
				}
			}, new JdbcTemplate.Converter<String, SQLException>()
			{

				@Override
				public String convert(ResultSet rst) throws SQLException
				{
					return rst.getString("AUTHIDS");
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLineAuthIdsByIds error!");
		}
	}

	@Override
	public List<DimPassLine> getPassLineByWeChat() {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassLineByWeChat"));

		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					
				}
			}, new JdbcTemplate.Converter<DimPassLine, SQLException>()
			{

				@Override
				public DimPassLine convert(ResultSet rst) throws SQLException
				{	
					DimPassLine passLine = new DimPassLine();
					passLine.setLineId(rst.getInt("LINE_ID"));
					passLine.setLineName(rst.getString("LINE_NAME"));
					passLine.setIntList(rst.getString("INT_LIST"));
					InputStream steam = rst.getBlob("DEVICE_LIST").getBinaryStream();
					try
					{
						passLine.setDeviceList(IOUtils.toString(steam));
					} catch (IOException e)
					{
						throw new ServiceException(e);
					}
					passLine.setAreaId(rst.getString("AREA_ID"));
					passLine.setAuthId(rst.getString("AUTH_ID"));
					passLine.setTempCardFlag(rst.getInt("TEMP_CARD_FLAG"));
					passLine.setDescription(rst.getString("DESCRIPTION"));
					passLine.setPictureUrl(rst.getString("PICTURE_URL"));
					return passLine;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getPassLineByWeChat error!");
		}
	}

	@Override
	public void updatePassLine(final DimPassLine passLine) {

		try
		{
			String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updatePassLine");
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, passLine.getLineName());
					pst.setString(2, passLine.getIntList());
					pst.setString(3, passLine.getDescription());
					pst.setString(4, passLine.getAuthId());
					pst.setInt(5, passLine.getTempCardFlag());
					pst.setString(6, passLine.getAreaId());
					pst.setBlob(7, new ByteArrayInputStream(passLine.getDeviceList().getBytes()));
					pst.setInt(8, passLine.getLineId());
				}
			});
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		String str = "xy1,";
		String [] array = str.split(",");
		for (String string : array) {
			System.out.println(string);
		}
	}

	@Override
	public List<Integer> getNoAuthPassLineByAccountName(Integer lineId, String account) {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getNoAuthPassLineByAccountName"));
		sql.append(" and instr(','||p.AUTH_ID||',' , ',").append(account).append(",')=0").append(" and p.line_id != ").append(lineId);

		System.out.println("getNoAuthPassLineByAccountName SQL:" + sql.toString());
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
//					pst.setString(1, account);
				}
			}, new JdbcTemplate.Converter<Integer, SQLException>()
			{

				@Override
				public Integer convert(ResultSet rst) throws SQLException
				{
					Integer lineId = rst.getInt("LINE_ID");
					
					return lineId;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getNoAuthPassLineByAccountName error!");
		}
	}

}
