/*
 * 文 件 名:  DimTempPassCardDaoImpl.java
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

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.Converter;
import com.jsits.commons.db.JdbcTemplate.ParameterFeeder;
import com.jsits.commons.db.JdbcTemplate.ResultSetCallback;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimTempPassCardDao;
import com.jszt.domain.DimTempPassCard;
import com.jszt.vo.PassCardAnalyseVo;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.TempCountPo;

/**
 * 零时通行证查询数据库操作类
 * 
 * @author ChangJun
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimTempPassCardDaoImpl implements DimTempPassCardDao
{
    
    @Override
    public List<String> queryDimTempPassCardPeriodByDateKey(final int dateKey, final String plateTypeId, final Integer passLineId)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryDimTempPassCardByDateKey"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setInt(1, dateKey);
                            pst.setString(2, plateTypeId);
                            pst.setInt(3, passLineId);
                            pst.setInt(4, dateKey);
                            pst.setString(5, plateTypeId);
                            pst.setInt(6, passLineId);
                        }
                    },
                    new JdbcTemplate.Converter<String, SQLException>()
                    {
                        @Override
                        public String convert(ResultSet rs)
                            throws SQLException
                        {
                            return rs.getString("PASS_PERIOD");
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryDimTempPassCardByDateKey error!");
        }
        
    }
    
    @Override
    public List<DimTempPassCard> getTempPassCards(PassCardReq passCardReq)
    {
        StringBuilder sqlBuilder =
            new StringBuilder(
                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempPassCard"));
        
        if (StringUtils.isNotBlank(passCardReq.getOwnerName()))
        {
            sqlBuilder.append(" and t.owner_name = ?");
        }
        if (StringUtils.isNotBlank(passCardReq.getPlateTypeId()) && !passCardReq.getPlateTypeId().equals("all"))
        {
            sqlBuilder.append(" and t.plate_type_id = ?");
        }
        if (StringUtils.isNotBlank(passCardReq.getForbidType()) && !passCardReq.getForbidType().equals("all"))
        {
            sqlBuilder.append(" and t.forbid_type = ?");
        }
        if (StringUtils.isNotBlank(passCardReq.getLicensePlate()))
        {
//            sqlBuilder.append(" and t.license_plate = ?");
            sqlBuilder.append(" and t.license_plate like '%").append(passCardReq.getLicensePlate()).append("%' ");
        }
        if (!"-1".equals(passCardReq.getPassLineId()))
        {
            sqlBuilder.append(" and t.pass_line = ?");
        }
        if (StringUtils.isNotBlank(passCardReq.getIdCard()))
        {
            sqlBuilder.append(" and t.id_card = ?");
        }
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sqlBuilder.toString(),
                setParam(passCardReq),
                new JdbcTemplate.Converter<DimTempPassCard, SQLException>()
                {
                    @Override
                    public DimTempPassCard convert(ResultSet rs)
                        throws SQLException
                    {
                        DimTempPassCard passCard = new DimTempPassCard();
                        passCard.setCardId(rs.getString("CARD_ID"));
                        passCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                        passCard.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                        passCard.setForbidType(rs.getString("FORBID_TYPE"));
                        passCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                        passCard.setEndDate(rs.getInt("END_DATE"));
                        passCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                        passCard.setPassLine(rs.getString("PASS_LINE"));
                        passCard.setForbidLine(rs.getString("FORBID_LINE"));
                        passCard.setApplyType(rs.getInt("APPLY_TYPE"));
                        passCard.setOwnerName(rs.getString("OWNER_NAME"));
                        passCard.setContactPeople(rs.getString("CONTACT_PEOPLE"));
                        passCard.setAddress(rs.getString("ADDRESS"));
                        passCard.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                        passCard.setIdCard(rs.getString("ID_CARD"));
                        passCard.setPrint(rs.getInt("PRINT"));
                        passCard.setApplyDate(rs.getString("APPLY_DATE"));
                        passCard.setXszUrl(rs.getString("XSZ_URL"));
                        passCard.setJszUrl(rs.getString("JSZ_URL"));
                        passCard.setBxUrl(rs.getString("BX_URL"));
                        passCard.setSfzUrl(rs.getString("SFZ_URL"));
                        passCard.setYyzzUrl(rs.getString("YYZZ_URL"));
                        passCard.setGhhtUrl(rs.getString("GHHT_URL"));
                        passCard.setYyzUrl(rs.getString("YYZ_URL"));
                        return passCard;
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getTempPassCards error!");
        }
    }
    
    /**
     * 查询通行证构造参数 <一句话功能简述> <功能详细描述>
     * 
     * @param passCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    private ParameterFeeder setParam(final PassCardReq passCardReq)
    {
        return new ParameterFeeder()
        {
            
            @Override
            public void setParameter(PreparedStatement ps)
                throws SQLException
            {
                int index = 0;
                ps.setInt(++index, passCardReq.getBeginDate());
                ps.setInt(++index, passCardReq.getEndDate());
                
                if (StringUtils.isNotBlank(passCardReq.getOwnerName()))
                {
                    ps.setString(++index, passCardReq.getOwnerName());
                }
                if (StringUtils.isNotBlank(passCardReq.getPlateTypeId()))
                {
                    ps.setString(++index, passCardReq.getPlateTypeId());
                }
//                if (StringUtils.isNotBlank(passCardReq.getLicensePlate()))
//                {
//                    ps.setString(++index, passCardReq.getLicensePlate());
//                }
                if (!"-1".equals(passCardReq.getPassLineId()))
                {
                    ps.setString(++index, passCardReq.getPassLineId());
                }
                if (StringUtils.isNotBlank(passCardReq.getIdCard()))
                {
                    ps.setString(++index, passCardReq.getIdCard());
                }
            }
        };
    }
    
    /**
     * @param dimTempPassCard
     * @throws JsztException
     */
    @Override
    public void addTempPassCard(final DimTempPassCard dimTempPassCard)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addTempPassCard");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 0;
                    
                    pst.setString(++index, dimTempPassCard.getCardId());
                    pst.setString(++index, dimTempPassCard.getLicensePlate());
                    pst.setString(++index, dimTempPassCard.getPlateTypeId());
                    pst.setString(++index, dimTempPassCard.getForbidType());
                    pst.setInt(++index, dimTempPassCard.getBeginDate());
                    pst.setInt(++index, dimTempPassCard.getEndDate());
                    pst.setString(++index, dimTempPassCard.getPassPeriod());
                    pst.setString(++index, dimTempPassCard.getPassLine());
                    pst.setString(++index, dimTempPassCard.getForbidLine());
                    pst.setString(++index, DateUtils.dateToString(null, "yyyyMMdd HH:mm:ss"));
                    pst.setInt(++index, dimTempPassCard.getApplyType());
                    pst.setString(++index, dimTempPassCard.getOwnerName());
                    pst.setString(++index, dimTempPassCard.getContactPeople());
                    pst.setString(++index, dimTempPassCard.getAddress());
                    pst.setString(++index, dimTempPassCard.getPhoneNumber());
                    pst.setString(++index, dimTempPassCard.getIdCard());
                    pst.setInt(++index, dimTempPassCard.getPrint());
                    pst.setString(++index, dimTempPassCard.getXszUrl());
                    pst.setString(++index, dimTempPassCard.getJszUrl());
                    pst.setString(++index, dimTempPassCard.getBxUrl());
                    pst.setString(++index, dimTempPassCard.getSfzUrl());
                    pst.setString(++index, dimTempPassCard.getYyzzUrl());
                    pst.setString(++index, dimTempPassCard.getGhhtUrl());
                    pst.setString(++index, dimTempPassCard.getYyzUrl());
                    pst.setString(++index, dimTempPassCard.getDescription());
                    
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addTempPassCard error!");
        }
        
    }
    
    @Override
    public void updateTempPrint(final DimTempPassCard tempPassCard)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateTempPrint");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setInt(++index, tempPassCard.getPrint());
                    ps.setString(++index, tempPassCard.getCardId());
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateTempPrint error!");
        }
        
    }
    
    @Override
    public List<DimTempPassCard> getTempValidPassCards(final int date)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempValidPassCards");
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    ps.setInt(1, date);
                    ps.setInt(2, date);
                }
            }, new JdbcTemplate.Converter<DimTempPassCard, SQLException>()
            {
                @Override
                public DimTempPassCard convert(ResultSet rs)
                    throws SQLException
                {
                    DimTempPassCard passCard = new DimTempPassCard();
                    passCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                    passCard.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                    passCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                    passCard.setPassLine(rs.getString("PASS_LINE"));
                    return passCard;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getTempValidPassCards error!");
        }
    }
    
    @Override
    public List<DimTempPassCard> getTempCardByInterval(final int sDate, final int eDate)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempCardByInterval");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setInt(1, eDate);
                    pst.setInt(2, sDate);
                }
            }, new JdbcTemplate.Converter<DimTempPassCard, SQLException>()
            {
                @Override
                public DimTempPassCard convert(ResultSet rs)
                    throws SQLException
                {
                    DimTempPassCard tempCard = new DimTempPassCard();
                    tempCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                    tempCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                    tempCard.setEndDate(rs.getInt("END_DATE"));
                    tempCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                    tempCard.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                    
                    return tempCard;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getTempCardByInterval error!");
        }
    }
    
    @Override
    public DimTempPassCard getTempPassCardById(final String cardId)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempPassCardById");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setString(1, cardId);
                }
            }, new JdbcTemplate.ResultSetCallback<DimTempPassCard, SQLException>()
            {
                
                @Override
                public DimTempPassCard doWithResultSet(ResultSet rs)
                    throws SQLException
                {
                    DimTempPassCard tempCard = new DimTempPassCard();
                    while (rs.next())
                    {
                        tempCard.setCardId(rs.getString("CARD_ID"));
                        tempCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                        tempCard.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                        tempCard.setForbidType(rs.getString("FORBID_TYPE"));
                        tempCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                        tempCard.setEndDate(rs.getInt("END_DATE"));
                        tempCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                        tempCard.setPassLine(rs.getString("PASS_LINE"));
                        tempCard.setForbidLine(rs.getString("FORBID_LINE"));
                        tempCard.setOwnerName(rs.getString("OWNER_NAME"));
                        tempCard.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                    }
                    return tempCard;
                }
            });
        }
        catch (SQLException e)
        {
            // return null;
            throw new ServiceException(e, "getTempPassCardById error!");
        }
    }
    
    /**
     * @param passCardRequest
     * @return
     */
    @Override
    public String getTempPassCardId(PassCardRequest passCardRequest)
    {
        
        return null;
    }
    
    @Override
    public List<DimTempPassCard> getTempCardByDate(final String plateLicense, final int date)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempCardByDate");
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    ps.setInt(1, date);
                    ps.setInt(2, date);
                    ps.setString(3, plateLicense);
                }
            }, new JdbcTemplate.Converter<DimTempPassCard, SQLException>()
            {
                @Override
                public DimTempPassCard convert(ResultSet rs)
                    throws SQLException
                {
                    DimTempPassCard passCard = new DimTempPassCard();
                    passCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                    passCard.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                    passCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                    passCard.setPassLine(rs.getString("PASS_LINE"));
                    return passCard;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getTempCardByDate error!");
        }
    }
    
    /**
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public List<TempCountPo> getMonthTempCount(final int beginDate, final int endDate)
        throws ServiceException
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getMonthTempCount");
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    /*ps.setInt(1, beginDate);
                    ps.setInt(2, endDate);*/
                	ps.setString(1, String.valueOf(beginDate));
                    ps.setString(2, String.valueOf(endDate));
                }
            }, COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getMonthTempCount fail! sql=" + sql);
        }
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Converter<TempCountPo, SQLException> COUNT_CONVERTER()
    {
        return new JdbcTemplate.Converter<TempCountPo, SQLException>()
        {
            
            @Override
            public TempCountPo convert(ResultSet rs)
                throws SQLException
            {
                TempCountPo countPo = new TempCountPo();
                countPo.setApplyDate(rs.getString("applydate"));
                countPo.setCount(rs.getInt("count"));
                return countPo;
            }
        };
    }
    
    /**
     * @param beginDate
     * @param endDate
     * @return
     */
    @Override
    public List<TempCountPo> getMonthYellowTempCount(final int beginDate, final int endDate, final String plateTypeId)
        throws ServiceException
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getMonthYellowTempCount");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    /*ps.setInt(1, beginDate);
                    ps.setInt(2, endDate);*/
                	ps.setString(1, String.valueOf(beginDate));
                    ps.setString(2, String.valueOf(endDate));
                    ps.setString(3, plateTypeId);
                }
            }, COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getMonthYellowTempCount fail! sql=" + sql);
        }
    }
    
    /**
     * @param beginDate
     * @param endDate
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TempCountPo> getDayTempCount(final int beginDate, final int endDate)
        throws ServiceException
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getDayTempCount");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    /*ps.setInt(1, beginDate);
                    ps.setInt(2, endDate);*/
                	ps.setString(1,String.valueOf(beginDate));
                    ps.setString(2, String.valueOf(endDate));
                }
            }, COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getDayTempCount fail! sql=" + sql);
        }
    }
    
    /**
     * @param beginDate
     * @param endDate
     * @param plateTypeId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TempCountPo> getDayYellowTempCount(final int beginDate, final int endDate, final String plateTypeId)
        throws ServiceException
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getDayYellowTempCount");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    /*ps.setInt(1, beginDate);
                    ps.setInt(2, endDate);*/
                	ps.setString(1,String.valueOf(beginDate));
                    ps.setString(2, String.valueOf(endDate));
                    ps.setString(3, plateTypeId);
                }
            }, COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getDayYellowTempCount fail! sql=" + sql);
        }
    }

    /**
     * @param beginDate
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TempCountPo> getHourTempCount(int beginDate)
        throws ServiceException
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getHourTempCount");
        sql = StringUtils.replace(sql, "#time", String.valueOf(beginDate));
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, null, COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getHourTempCount fail! sql=" + sql);
        }
    }

    /**
     * @param beginDate
     * @param plateTypeId
     * @return
     * @throws ServiceException
     */
    @Override
    public List<TempCountPo> getHourYellowTempCount(final int beginDate,final String plateTypeId)
        throws ServiceException
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getHourYellowTempCount");
        sql = StringUtils.replace(sql, "#time", String.valueOf(beginDate));
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    ps.setString(1, plateTypeId);
                }
            },COUNT_CONVERTER());
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getHourYellowTempCount fail! sql=" + sql);
        }
    }

    
    @Override
    public String getTempPassCardSEQ()
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempPassCardSEQ");
        String ret = "-1";
        
        try
        {
            ret = ServiceUtil.getService(JdbcTemplate.class).execute(sql, null, new ResultSetCallback<String, SQLException>()
            {
                @Override
                public String doWithResultSet(ResultSet rs) throws SQLException
                {
                    String ret = "-1";
                    while (rs.next())
                    {
                        ret = rs.getString("NEXTVAL");
                    }
                    return ret;
                }
             });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getTempPassCardSEQ error!");
        }
        return ret;
    }

	@Override
	public List<PassCardAnalyseVo> getTempPassCardAnalyse(final int sDate,
			final int eDate) {
		String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getTempCardPeriodByDate");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
                    new JdbcTemplate.ParameterFeeder()
                    {

                        @Override
                        public void setParameter(PreparedStatement pst) throws SQLException
                        {
                            pst.setInt(1, eDate);
                            pst.setInt(2, sDate);
                        }
                    }, new JdbcTemplate.Converter<PassCardAnalyseVo, SQLException>()
                    {
                        @Override
                        public PassCardAnalyseVo convert(ResultSet rs) throws SQLException
                        {
                        	PassCardAnalyseVo longCard = new PassCardAnalyseVo();
                            longCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                            longCard.setCount(rs.getInt("COUNT"));
                            return longCard;
                        }
                    });
        } catch (SQLException e)
        {
            throw new ServiceException(e, "getTempPassCardAnalyse error!");
        }
	}

    @Override
    public void deleteExpirePassCard(final String date) {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteExpirePassCard");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {

                @Override
                public void setParameter(PreparedStatement pst)
                        throws SQLException
                {
                    pst.setString(1, date);
                }
            });
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

}
