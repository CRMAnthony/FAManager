/*
 * 文 件 名:  DimCardUserInfoDaoImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
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
import com.jsits.commons.util.DbUtil;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimCardUserInfoDao;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.vo.CardUserInfoReq;
import com.jszt.vo.PassCardRequest;

/**
 * 通行证用户信息绑定数据库层实现 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2016年1月7日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimCardUserInfoDaoImpl implements DimCardUserInfoDao
{

	/**
	 * 新增用户绑定信息
	 * 
	 * @param info
	 * @throws JsztException
	 */
	@Override
	public void addDimCardUserInfo(final DimCardUserInfo info) throws JsztException
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addCardUserInfo");

		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					pst.setString(index++, info.getId());
					pst.setString(index++, info.getLicensePlate());
					pst.setString(index++, info.getPlateTypeId());
					pst.setString(index++, info.getOwnerName());
					pst.setString(index++, info.getPhoneNumber());
					pst.setString(index++, info.getIdCard());
					pst.setString(index++, info.getXszUrl());
					pst.setString(index++, info.getJszUrl());
					pst.setInt(index++, info.getStatus());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "addDimCardUserInfo error!");
		}
	}

	/**
	 * 删除用户绑定信息
	 * 
	 * @param info
	 * @throws JsztException
	 */
	@Override
	public void deleteDimCardUserInfo(final String id) throws JsztException
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteCardUserInfo");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, id);
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "deleteDimCardUserInfo fail!");
		}
	}

	/**
	 * 更新用户绑定信息
	 * 
	 * @param info
	 * @throws JsztException
	 */
	@Override
	public void updateDimcardUserInfo(final DimCardUserInfo info) throws JsztException
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateCardUserInfo");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					pst.setInt(index++, info.getStatus());
					pst.setString(index++, info.getId());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "updateDimcardUserInfo fail!");
		}

	}

	/**
	 * 根据条件获取用户绑定信息
	 * 
	 * @param info
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimCardUserInfo getDimCardUserInfo(final DimCardUserInfo info) throws JsztException
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getCardUserInfo");
		StringBuilder sb = new StringBuilder(sql);
		if (StringUtils.isNotEmpty(info.getPhoneNumber()))
		{
			sb.append(" and u.phone_number =?");
		}
		if (StringUtils.isNotEmpty(info.getIdCard()))
		{
			sb.append(" and u.id_card =?");
		}
		sb.append(" and u.status =2");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getItem(sb.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					pst.setString(index++, info.getLicensePlate());
					if (StringUtils.isNotEmpty(info.getPhoneNumber()))
					{
						pst.setString(index++, info.getPhoneNumber());
					}
					if (StringUtils.isNotEmpty(info.getIdCard()))
					{
						pst.setString(index++, info.getIdCard());
					}
				}
			}, CONVERTER());
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getDimCardUserInfo fail!!!");
		}
	}

	/**
	 * <一句话功能简述> <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	private Converter<DimCardUserInfo, SQLException> CONVERTER()
	{
		return new JdbcTemplate.Converter<DimCardUserInfo, SQLException>()
		{

			@Override
			public DimCardUserInfo convert(ResultSet rs) throws SQLException
			{
				DimCardUserInfo userInfo = new DimCardUserInfo();
				userInfo.setId(rs.getString("ID"));
				userInfo.setLicensePlate(rs.getString("LICENSE_PLATE"));
				userInfo.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
				userInfo.setOwnerName(rs.getString("OWNER_NAME"));
				userInfo.setPhoneNumber(rs.getString("PHONE_NUMBER"));
				userInfo.setIdCard(rs.getString("ID_CARD"));
				userInfo.setXszUrl(rs.getString("XSZ_URL"));
				userInfo.setJszUrl(rs.getString("JSZ_URL"));
				userInfo.setStatus(rs.getInt("STATUS"));
				return userInfo;
			}
		};
	}

	/**
	 * @param info
	 * @return
	 * @throws JsztException
	 */
	@Override
	public List<DimCardUserInfo> getAllDimcardUserInfo(final CardUserInfoReq info) throws JsztException
	{
		StringBuilder sb = new StringBuilder("SELECT * FROM (SELECT A.*, ROWNUM RN FROM (");
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getAllCardUserInfo");
		sb.append(sql);
		if (StringUtils.isNotEmpty(info.getLicensePlate()))
		{
			sb.append(" and u.license_plate = ?");
		}
		if (StringUtils.isNotEmpty(info.getPlateTypeId()))
		{
			sb.append(" and u.plate_type_id = ?");
		}
		if (info.getStatus() != null)
		{
			sb.append(" and u.status = ?");
		}
		if (StringUtils.isNotEmpty(info.getPhoneNumber()))
		{
			sb.append(" and u.phone_number = ?");
		}
		if (StringUtils.isNotEmpty(info.getIdCard()))
		{
			sb.append(" and u.id_card = ?");
		}
		
		sb.append(") A WHERE ROWNUM <= ?) WHERE RN > ?");
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getList(sb.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					int index = 1;
					if (StringUtils.isNotEmpty(info.getLicensePlate()))
					{
						pst.setString(index++, info.getLicensePlate());
					}
					if (StringUtils.isNotEmpty(info.getPlateTypeId()))
					{
						pst.setString(index++, info.getPlateTypeId());
					}
					if (info.getStatus() != null)
					{
						pst.setInt(index++, info.getStatus());
					}
					if (StringUtils.isNotEmpty(info.getPhoneNumber()))
					{
						pst.setString(index++, info.getPhoneNumber());
					}
					if (StringUtils.isNotEmpty(info.getIdCard()))
					{
						pst.setString(index++, info.getIdCard());
					}
					if (info.getPageCount() != 0)
					{
						pst.setInt(index++, (15 * (info.getPageIndex() + 1)));
					}
//					pst.setInt(index++, (15 * (info.getPageIndex() + 1)));
					pst.setInt(index++, 15 * info.getPageIndex());	
				}
			}, CONVERTER());
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getAllDimcardUserInfo fail!!");
		}

	}

	/**
	 * @param info
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimCardUserInfo getSameCardUserInfo(final DimCardUserInfo info) throws JsztException
	{
	    String sql = "select u.* from dim_card_user_info u where u.license_plate = ? and u.id_card = ? and u.phone_number = ? and u.status = 2";
	    
	    try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getItem(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, info.getLicensePlate());
					pst.setString(2, info.getIdCard());
					pst.setString(3, info.getPhoneNumber());
				}
			}, CONVERTER());
		} catch (SQLException e)
		{
			throw new JsztException("getSameCardUserInfo fail!!", e);
		}

	}

	/**
	 * @param id
	 * @return
	 * @throws JsztException
	 */
	@Override
	public DimCardUserInfo getDimCardUserInfoByKey(final String id) throws JsztException
	{
		String sql = "select u.* from dim_card_user_info u where u.id = ?";
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getItem(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, id);
				}
			}, CONVERTER());
		} catch (SQLException e)
		{
			throw new JsztException("getDimCardUserInfoByKey fail!!", e);
		}
	}

	/**
	 * @param info
	 * @return
	 * @throws JsztException
	 */
	@Override
	public String getCardUserInfoCount(CardUserInfoReq info) throws JsztException
	{
		StringBuilder sql = new StringBuilder("select count(*) from dim_card_user_info h where 1=1");
		
		if (StringUtils.isNotEmpty(info.getLicensePlate()))
		{
			sql.append(" and h.license_plate = '"+info.getLicensePlate()+"'");
		}

		if (StringUtils.isNotEmpty(info.getPlateTypeId()))
		{
			sql.append(" and h.plate_type_id = '"+info.getPlateTypeId()+"'");
		}
		if (info.getStatus() != null)
		{
			sql.append(" and h.status= "+info.getStatus());
		}
		if (StringUtils.isNotEmpty(info.getPhoneNumber()))
		{
			sql.append(" and h.phone_number = '"+info.getPhoneNumber()+"'");
		}
		if (StringUtils.isNotEmpty(info.getIdCard()))
		{
			sql.append(" and h.id_card = '"+info.getIdCard()+"'");
		}
				
		return DbUtil.getStr(sql.toString());
	}

    /**
     * @param cardRequest
     * @return
     * @throws JsztException
     */
    @Override
    public List<DimCardUserInfo> checkBound(final PassCardRequest cardRequest)
        throws JsztException
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getAllCardUserInfo");
        StringBuilder sb =new StringBuilder(sql);
        if (StringUtils.isNotEmpty(cardRequest.getLicensePlate()))
        {
            sb.append(" and u.license_plate =?");
        }
        if (StringUtils.isNotEmpty(cardRequest.getIdCard()))
        {
            sb.append(" and u.id_card =?");
        }
        if (StringUtils.isNotEmpty(cardRequest.getPhoneNumer()))
        {
            sb.append(" and u.phone_number =?");
        }
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sb.toString(), new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index =1;
                    if (StringUtils.isNotEmpty(cardRequest.getLicensePlate()))
                    {
                        pst.setString(index++, cardRequest.getLicensePlate());
                    }
                    if (StringUtils.isNotEmpty(cardRequest.getIdCard()))
                    {
                        pst.setString(index++, cardRequest.getIdCard());
                    }
                    if (StringUtils.isNotEmpty(cardRequest.getPhoneNumer()))
                    {
                        pst.setString(index++, cardRequest.getPhoneNumer());
                    }
                }
            }, CONVERTER());
        }
        catch (SQLException e)
        {
            throw new JsztException("getDimCardUserInfoByKey fail!!", e);
        }
    }

}
