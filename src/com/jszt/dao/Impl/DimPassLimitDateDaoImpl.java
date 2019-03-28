/*
 * 文 件 名:  DimPassLimitDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
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

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimPassLimitDateDao;
import com.jszt.domain.DimPassLlimit;
import com.jszt.vo.PassLimitReq;

/**
 * 
 * 通行时段配置数据库访问
 * 
 * @author ChangJun
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimPassLimitDateDaoImpl implements DimPassLimitDateDao
{

    @Override
    public List<DimPassLlimit> queryDimPassLlimit(final String plateTypeId,final String limitDate,final Integer passLineId)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryDimPassLlimitDate"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                        	pst.setString(1, plateTypeId);
                        	pst.setInt(2, Integer.parseInt(limitDate));
                        	pst.setInt(3, passLineId);
                        }
                    },
                    new JdbcTemplate.Converter<DimPassLlimit, SQLException>()
                    {
                        @Override
                        public DimPassLlimit convert(ResultSet rs)
                            throws SQLException
                        {
                            DimPassLlimit limit = new DimPassLlimit();
                            limit.setLimit(rs.getInt("Limit"));
                            limit.setTimeKey(rs.getInt("Time_Key"));
                            limit.setPassLineId(rs.getInt("PASS_LINE_ID"));
                            return limit;
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryDimPassLlimit error!");
        }
	}

	@Override
	public void addPassLimit(final List<PassLimitReq> reqList)
	{
		try
		{
			String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addPassLimitDate");
			ServiceUtil.getService(JdbcTemplate.class).executeBatch(sql, reqList, PassLimitReq.class, "timeKey", "limit","plateTypeId","limitDate","passLineId");
		} catch (SQLException e)
		{
			throw new ServiceException(e, "addPassLimit error!");
		}
	}

	@Override
	public void deletePassLimit(final String plateTypeId,final String limitDate,final Integer passLineId)
	{
		try
		{
			String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deletePassLimitDate");

			ServiceUtil.getService(JdbcTemplate.class).execute(sql,new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    ps.setString(1, plateTypeId);
                    ps.setInt(2, Integer.parseInt(limitDate));
                    ps.setInt(3, passLineId);
                }
            });
		} catch (SQLException e)
		{
			throw new ServiceException(e, "deletePassLimit error!");
		}
	}

	@Override
	public List<DimPassLlimit> queryDimPassLlimitSum(final String limitDate) {
		try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryDimPassLlimitSum"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                        	pst.setInt(1, Integer.parseInt(limitDate));
                        }
                    },
                    new JdbcTemplate.Converter<DimPassLlimit, SQLException>()
                    {
                        @Override
                        public DimPassLlimit convert(ResultSet rs)
                            throws SQLException
                        {
                            DimPassLlimit limit = new DimPassLlimit();
                            limit.setLimit(rs.getInt("Limit"));
                            limit.setTimeKey(rs.getInt("Time_Key"));
                            return limit;
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryDimPassLlimitSum error!");
        }
	}

}
