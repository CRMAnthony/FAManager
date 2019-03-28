package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.ParameterFeeder;
import com.jsits.commons.db.JdbcTemplate.ResultSetCallback;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimLongPassCardDao;
import com.jszt.domain.DimLongPassCard;
import com.jszt.vo.PassCardAnalyseVo;
import com.jszt.vo.PassCardReq;

/**
 * 
 * 长期通行证数据库操作
 * @author  gxj
 * @version  [版本号, 2015年6月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimLongPassCardDaoImpl implements DimLongPassCardDao
{
  //长期通行证转换列表
    public final JdbcTemplate.Converter<DimLongPassCard, SQLException> LONG_PASS_CARD_CONVERTER =
        new JdbcTemplate.Converter<DimLongPassCard, SQLException>()
        {
            @Override
            public DimLongPassCard convert(ResultSet rs)
                throws SQLException
            {
                DimLongPassCard passCard = new DimLongPassCard();
                
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
                passCard.setAccountId(rs.getString("ACCOUNT_ID"));
                passCard.setStatus(rs.getInt("STATUS"));
                passCard.setPrint(rs.getInt("PRINT"));
                passCard.setApplyDate(rs.getString("APPLY_DATE"));
                passCard.setZxkDate(rs.getString("ZXK_DATE"));
                passCard.setDdDate(rs.getString("DD_DATE"));
                passCard.setZxkOpinion(rs.getString("ZXK_OPINION"));
                passCard.setDdOpinion(rs.getString("DD_OPINION"));
                passCard.setXszUrl(rs.getString("XSZ_URL"));
                passCard.setJszUrl(rs.getString("JSZ_URL"));
                passCard.setBxUrl(rs.getString("BX_URL"));
                passCard.setSfzUrl(rs.getString("SFZ_URL"));
                passCard.setYyzzUrl(rs.getString("YYZZ_URL"));
                passCard.setGhhtUrl(rs.getString("GHHT_URL"));
                passCard.setYyzUrl(rs.getString("YYZ_URL"));
                passCard.setAgencyId(rs.getString("AGENCY_ID"));
                passCard.setParentCardId(rs.getString("PARENT_CARD_ID"));
                
                return passCard;
            }
        };
    
    @Override
    public List<DimLongPassCard> getLongPassCards(PassCardReq passCardReq)
    {
        StringBuilder sqlBuilder = new StringBuilder(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
            .getConfig("sql.getLongPassCard"));
        
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
            sqlBuilder.append(" and t.license_plate like '%").append(passCardReq.getLicensePlate()).append("%' ");
        }
        if (StringUtils.isNotBlank(passCardReq.getPassLineId()) && !"-1".equals(passCardReq.getPassLineId()))
        {
            sqlBuilder.append(" and t.pass_line = ?");
        }
        if (passCardReq.getStatus() != -1)
        {    
            sqlBuilder.append(" and t.status = ?");  
        }
        if (StringUtils.isNotBlank(passCardReq.getIdCard()))
        {
            sqlBuilder.append(" and t.id_card = ?");
        }
        if (StringUtils.isNotBlank(passCardReq.getAgencyId()))
        {
            sqlBuilder.append(" and t.agency_id = ?");
        }
        
        if (StringUtils.isNotBlank(passCardReq.getParentCardId()))
        {
            sqlBuilder.append(" and t.Parent_Card_Id = ?");
        }
        
        if (StringUtils.isNotBlank(passCardReq.getAccount()))
        {
            sqlBuilder.append(" and instr(t.AUTH_ID, ',").append(passCardReq.getAccount()).append(",')>0");
        }
        System.out.println("getLongPassCards SQL："+sqlBuilder.toString());
        
        try
        {
           return ServiceUtil.getService(JdbcTemplate.class).getList(sqlBuilder.toString(),
                setParam(passCardReq), LONG_PASS_CARD_CONVERTER);
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getLongPassCards error!");
        }
    }
    
    /**
     * 查询通行证构造参数
     * <一句话功能简述>
     * <功能详细描述>
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
                if (StringUtils.isNotBlank(passCardReq.getPassLineId()) && !"-1".equals(passCardReq.getPassLineId()))
                {
                    ps.setString(++index, passCardReq.getPassLineId());
                }
                if (passCardReq.getStatus() != -1)
                {
                    ps.setInt(++index, passCardReq.getStatus());
                }
                if (StringUtils.isNotBlank(passCardReq.getIdCard()))
                {
                    ps.setString(++index, passCardReq.getIdCard());
                }
                if (StringUtils.isNotBlank(passCardReq.getAgencyId()))
                {
                    ps.setString(++index, passCardReq.getAgencyId());
                }
                
                if (StringUtils.isNotBlank(passCardReq.getParentCardId()))
                {
                    ps.setString(++index, passCardReq.getParentCardId());
                }
            }
        };
    }
   
    /**
     * 构造新增长期通行证参数
     * 
     * @param dimLongPassCard
     * @return
     * @see [类、类#方法、类#成员]
     */
    private ParameterFeeder setAddParam(final DimLongPassCard dimLongPassCard)
    {
        return new ParameterFeeder()
        {
            @Override
            public void setParameter(PreparedStatement pst)
                throws SQLException
            {
                int index = 0;
                pst.setString( ++index, dimLongPassCard.getCardId());
                pst.setString( ++index, dimLongPassCard.getLicensePlate());
                pst.setString( ++index, dimLongPassCard.getPlateTypeId());
                pst.setString( ++index, dimLongPassCard.getForbidType());
                pst.setInt( ++index, dimLongPassCard.getBeginDate());
                pst.setInt( ++index, dimLongPassCard.getEndDate());
                pst.setString( ++index, dimLongPassCard.getPassPeriod());
                pst.setString( ++index, dimLongPassCard.getPassLine());
                pst.setString( ++index, dimLongPassCard.getForbidLine());
                pst.setString(++index, DateUtils.dateToString(null, "yyyyMMdd HH:mm:ss"));
                pst.setInt( ++index, dimLongPassCard.getApplyType());
                pst.setString( ++index, dimLongPassCard.getOwnerName());
                pst.setString( ++index, dimLongPassCard.getContactPeople());
                pst.setString( ++index, dimLongPassCard.getAddress());
                pst.setString( ++index, dimLongPassCard.getPhoneNumber());
                pst.setString( ++index, dimLongPassCard.getIdCard());
                pst.setString( ++index, dimLongPassCard.getAccountId());
                pst.setInt( ++index, dimLongPassCard.getStatus());
                pst.setInt( ++index, dimLongPassCard.getPrint());
                //pst.setString(++index, DateUtils.dateToString(null, "yyyyMMdd HH:mm:ss"));
                //pst.setString(++index, DateUtils.dateToString(null, "yyyyMMdd HH:mm:ss"));
                pst.setString( ++index, dimLongPassCard.getZxkOpinion());
                pst.setString( ++index, dimLongPassCard.getDdOpinion());
                pst.setString( ++index, dimLongPassCard.getXszUrl());
                pst.setString( ++index, dimLongPassCard.getJszUrl());
                pst.setString( ++index, dimLongPassCard.getBxUrl());
                pst.setString( ++index, dimLongPassCard.getSfzUrl());
                pst.setString( ++index, dimLongPassCard.getYyzzUrl());
                pst.setString( ++index, dimLongPassCard.getGhhtUrl());
                pst.setString( ++index, dimLongPassCard.getYyzUrl());
                pst.setString( ++index, dimLongPassCard.getDescription());
                pst.setString( ++index, dimLongPassCard.getAgencyId());
                pst.setString( ++index, dimLongPassCard.getParentCardId());
                pst.setString( ++index, dimLongPassCard.getAuthId());
            }
        };
    }
    
    /**
     * 
     * @param dimLongPassCard
     */
    @Override
    public void addLongPassCard(final DimLongPassCard dimLongPassCard)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addLongPassCard");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, setAddParam(dimLongPassCard));
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addLongPassCard error!");
        }  
 
    }

    @Override
    public void updateZXKProve(final DimLongPassCard longPassCard)
    {
        final String zxkDateString = DateUtils.formater(longPassCard.getZxkDate(), "yyyy-MM-dd HH:mm:ss");
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateZXKProve");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setString(++index, zxkDateString );
                    ps.setString(++index, longPassCard.getZxkOpinion());
                    ps.setInt(++index, longPassCard.getStatus());
                    ps.setString(++index, longPassCard.getCardId());
                } 
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateZXKProve error!");
        }  
    }

    @Override
    public void updateDDProve(final DimLongPassCard longPassCard)
    {
        final String ddDateString = DateUtils.formater(longPassCard.getDdDate(), "yyyy-MM-dd HH:mm:ss");
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateDDProve");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setString(++index, ddDateString);
                    ps.setString(++index, longPassCard.getDdOpinion());
                    ps.setInt(++index, longPassCard.getStatus());
                    ps.setString(++index, longPassCard.getCardId());
                } 
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateDDProve error!");
        }  
    }
    
    @Override
    public void updateLongPrint(final DimLongPassCard longPassCard)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateLongPrint");
        
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setInt(++index, longPassCard.getPrint());
                    ps.setString(++index, longPassCard.getCardId());
                } 
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateLongPrint error!");
        }  
        
    }

    @Override
    public List<DimLongPassCard> getLongValidPassCards(final int date)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongValidPassCards");
        
        try
        {
           return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
               new JdbcTemplate.ParameterFeeder(){

               @Override
               public void setParameter(PreparedStatement ps)
                   throws SQLException
               {
                   ps.setInt(1, date);
                   ps.setInt(2, date);
               } 
           },
                new JdbcTemplate.Converter<DimLongPassCard, SQLException>()
                {
                    @Override
                    public DimLongPassCard convert(ResultSet rs)
                        throws SQLException
                    {
                        DimLongPassCard passCard = new DimLongPassCard();
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
           throw new ServiceException(e, "getLongValidPassCards error!");
        }
    }

    @Override
    public List<DimLongPassCard> getLongCardByInterval(final int sDate, final int eDate)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongCardByInterval");
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
                    }, new JdbcTemplate.Converter<DimLongPassCard, SQLException>()
                    {
                        @Override
                        public DimLongPassCard convert(ResultSet rs) throws SQLException
                        {
                            DimLongPassCard longCard = new DimLongPassCard();
                            longCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                            longCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                            longCard.setEndDate(rs.getInt("END_DATE"));
                            longCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                            longCard.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                            longCard.setStatus(rs.getInt("STATUS"));
                            
                            return longCard;
                        }
                    });
        } catch (SQLException e)
        {
            throw new ServiceException(e, "getLongCardByInterval error!");
        }
    }

    @Override
    public DimLongPassCard getLongPassCardById(final String cardId)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongPassCardById");
        try{
            return ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
                {
                    @Override
                    public void setParameter(PreparedStatement pst) throws SQLException
                    {
                        pst.setString(1, cardId);
                    }
                }, new JdbcTemplate.ResultSetCallback<DimLongPassCard, SQLException>()
                {

                    @Override
                    public DimLongPassCard doWithResultSet(ResultSet rs)
                        throws SQLException
                    {
                        DimLongPassCard longCard = new DimLongPassCard();
                        while (rs.next())
                        { 
                            longCard.setCardId(rs.getString("CARD_ID"));
                            longCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                            longCard.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                            longCard.setForbidType(rs.getString("FORBID_TYPE"));
                            longCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                            longCard.setEndDate(rs.getInt("END_DATE"));
                            longCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                            longCard.setPassLine(rs.getString("PASS_LINE"));
                            longCard.setForbidLine(rs.getString("FORBID_LINE"));
                            longCard.setOwnerName(rs.getString("OWNER_NAME"));
                            longCard.setPhoneNumber(rs.getString("PHONE_NUMBER"));
                            longCard.setStatus(rs.getInt("STATUS"));
                        }
                        return longCard;
                    }
                });
            
        }
        catch(SQLException e){
            //return null;
            throw new ServiceException(e, "getLongPassCardById error!");
        }
    }

	/**
	 * @param req
	 */
	@Override
	public void updateLongPassCard(final PassCardReq req)
	{
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateLongPassCard");
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setInt(1, req.getBeginDate());
					pst.setInt(2, req.getEndDate());
					pst.setString(3, req.getCardId());
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "updateLongPassCard error!");
		}
	}

    @Override
    public List<DimLongPassCard> queryLongPassCardExtendHis(final String cardId)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryLongPassCardExtendHis"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setString(1, cardId);
                        }
                    },
                    LONG_PASS_CARD_CONVERTER);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "queryLongPassCardExtendHis error!");
        }
    }

    @Override
    public String getLongPassCardSEQ()
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongPassCardSEQ");
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
            throw new ServiceException(e, "getLongPassCardSEQ error!");
        }
        return ret;
    }

	@Override
	public List<PassCardAnalyseVo> getLongPassCardAnalyse(final int sDate, final int eDate) {
		String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLongCardByDate");
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
                            pst.setInt(3, eDate);
                            pst.setInt(4, sDate);
                            pst.setInt(5, eDate);
                            pst.setInt(6, sDate);
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
            throw new ServiceException(e, "getLongPassCardAnalyse error!");
        }
	}

	@Override
	public void updateLongPassCardAuthId(List<Integer> noAuthLineIds, final String authId) {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateLongPassCardAuthId"));
		sql.append(" and instr(','||t.AUTH_ID||',' , ',").append(authId).append(",')=0");
		for (Integer noAuthLineId : noAuthLineIds) {
			sql.append(" and instr(','||t.PASS_LINE||',' , ',").append(noAuthLineId).append(",')=0");
		}
		System.out.println("updateLongPassCardAuthId sql: " + sql.toString());
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, authId);
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "updateLongPassCardAuthId error!");
		}
	}

	@Override
	public void updateLongPassCardByReduceAuthId(Integer lineId, String authId) {
		StringBuilder sql = new StringBuilder(((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateLongPassCardByReduceAuthId"));
		sql.append(" replace(t.AUTH_ID, ',").append(authId).append(",', ',,')")
		.append(" where instr(','||t.PASS_LINE||',' , ',").append(lineId).append(",')>0");
		System.out.println("updateLongPassCardByReduceAuthId sql: " + sql.toString());
		try
		{
			ServiceUtil.getService(JdbcTemplate.class).execute(sql.toString(), new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
//					pst.setString(1, authId);
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "updateLongPassCardByReduceAuthId error!");
		}
	}
    
}
