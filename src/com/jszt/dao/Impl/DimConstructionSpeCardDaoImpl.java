/*
 * 文 件 名:  DimConstructionSpeCardDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  gaoxiaojuan
 * 修改时间:  2016年4月12日
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
import com.jsits.commons.db.JdbcTemplate.ResultSetCallback;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimConstructionSpeCardDao;
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.vo.ConstructionSpeCardReq;

/**
 * 渣土車特殊通行證數據庫訪問接口實現
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2016年4月12日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimConstructionSpeCardDaoImpl implements DimConstructionSpeCardDao
{
    //渣土車特殊通行證列表轉換
    private static final JdbcTemplate.Converter<DimConstructionSpeCard, SQLException> CONVERTER =
        new JdbcTemplate.Converter<DimConstructionSpeCard, SQLException>()
        {
            @Override
            public DimConstructionSpeCard convert(ResultSet rs)
                throws SQLException
            {
                DimConstructionSpeCard dimConstructionSpeCard = new DimConstructionSpeCard();
                
                dimConstructionSpeCard.setId(rs.getString("ID"));
                dimConstructionSpeCard.setLicensePlate(rs.getString("LICENSE_PLATE"));
                dimConstructionSpeCard.setRegisterDate(rs.getInt("REGISTER_DATE"));
                dimConstructionSpeCard.setBeginDate(rs.getInt("BEGIN_DATE"));
                dimConstructionSpeCard.setEndDate(rs.getInt("END_DATE"));
                dimConstructionSpeCard.setPassPeriod(rs.getString("PASS_PERIOD"));
                dimConstructionSpeCard.setDescription(rs.getString("DESCRIPTION"));
                
                return dimConstructionSpeCard;
            }
        };
    
    /**
     * @param dimConstructionSpeCard
     */
    @Override
    public void addConstructionSpeCard(final DimConstructionSpeCard dimConstructionSpeCard)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addConstructionSpeCard");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 1;
                    
                    pst.setString(index++, dimConstructionSpeCard.getLicensePlate());
                    pst.setInt(index++, dimConstructionSpeCard.getRegisterDate());
                    pst.setInt(index++, dimConstructionSpeCard.getBeginDate());
                    pst.setInt(index++, dimConstructionSpeCard.getEndDate());
                    pst.setString(index++, dimConstructionSpeCard.getPassPeriod());
                    pst.setString(index++, dimConstructionSpeCard.getDescription());
                    
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addConstructionVehicle error!");
        }
    }
    
    /**
     * @param conn
     * @param retList
     */
    @Override
    public void addBatchConstructionSpeCard(final List<DimConstructionSpeCard> retList)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addConstructionSpeCard");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).executeBatch(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                   for(DimConstructionSpeCard dimConstructionSpeCard : retList)
                   {
                       int index = 1;
                       
                       pst.setString(index++, dimConstructionSpeCard.getId());
                       pst.setString(index++, dimConstructionSpeCard.getLicensePlate());
                       pst.setInt(index++, dimConstructionSpeCard.getRegisterDate());
                       pst.setInt(index++, dimConstructionSpeCard.getBeginDate());
                       pst.setInt(index++, dimConstructionSpeCard.getEndDate());
                       pst.setString(index++, dimConstructionSpeCard.getPassPeriod());
                       pst.setString(index++, dimConstructionSpeCard.getDescription());
                       
                       pst.addBatch();
                   }
                   pst.executeBatch(); 
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addBatchConstructionSpeCard error!");
        }
    }
    
    /**
     * 根据车牌号、公司名稱查询渣土车特殊通行證信息列表
     * @param req
     * @return
     */
    @Override
    public List<DimConstructionSpeCard> getConstructionSpeCard(final ConstructionSpeCardReq req)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getConstructionSpeCard");
        StringBuilder sqlBuilder = new StringBuilder(sql);
        
        if (StringUtils.isNotBlank(req.getLicensePlate()))
        {
            sqlBuilder.append(" and d.LICENSE_PLATE = ?");
        }
        if (StringUtils.isNotBlank(req.getCompanyName()))
        {
            sqlBuilder.append(" and t.COMPANYNAME = ?");
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
            throw new ServiceException(e, "getConstructionSpeCard error!");
        }
    }
    
    /**
     * 根据车牌号删除渣土车特殊通行證
     * @param strings
     */
    @Override
    public void deleteConstructionSpeCard(final String[] ids)
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteConstructionSpeCard");
        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        if (ids.length > 0)
        {
            for (int i = 0; i < ids.length; i++)
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
                        for (String id : ids)
                        {
                            pst.setString(index++, id);
                        }
                    }
                });
            } catch (SQLException e)
            {
                throw new ServiceException("deleteConstructionSpeCard fail!", e);
            }
        }
    }
    
    /**
     * 根据车牌号更新渣土车特殊通行證信息
     * @param dimConstructionSpeCard
     */
    @Override
    public void updateConstructionSpeCard(final ConstructionSpeCardReq speCardReq)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateConstructionSpeCard");
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    int index = 1;
                    pst.setString(index++, speCardReq.getLicensePlate());
                    pst.setInt(index++, speCardReq.getRegisterDate());
                    pst.setInt(index++, speCardReq.getBeginDate());
                    pst.setInt(index++, speCardReq.getEndDate());
                    pst.setString(index++, speCardReq.getPassPeriod());
                    pst.setString(index++, speCardReq.getDescription());
                    pst.setString(index++, speCardReq.getId());
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateConstructionSpeCard error!");
        }
    }

    /**
     * 渣土车特殊通行證ID主键自增
     * @return
     */
    @Override
    public String getConstructionSpeCardSEQ()
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getConstructionSpeCardSEQ");
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
            throw new ServiceException(e, "getConstructionSpeCardSEQ error!");
        }
        return ret;
    }

    /**
     * 获取某一天有效的渣土车特殊通行证
     * @param date
     * @return
     */
    @Override
    public List<DimConstructionSpeCard> getValidConSpeCards(final int date)
    {
        String sql =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getValidConSpeCards");
        
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
            throw new ServiceException(e, "getValidConSpeCards error!");
        }
    }
}
