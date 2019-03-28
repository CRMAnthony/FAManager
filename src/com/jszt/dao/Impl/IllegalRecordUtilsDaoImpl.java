/*
 * 文 件 名:  IllegalRecordQuey.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.Converter;
import com.jsits.commons.db.JdbcTemplate.ParameterFeeder;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.Image;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;
import com.jszt.vo.ViolationTimeReq;

/**
 * 违法记录查询sql语句帮助dao
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public abstract class IllegalRecordUtilsDaoImpl
{
    
	private static Logger logger=Logger.getLogger(IllegalRecordUtilsDaoImpl.class);
    
	private static final String OTHER_STRING = ",PICTURE_NUMBER,LICENSE_PLATE_LINK1,LICENSE_PLATE_LINK2,LICENSE_PLATE_LINK3,LICENSE_PLATE_LINK4,LICENSE_PLATE_LINK0,TRANSFER_STATUS,NOTICE_STATUS";
    
    private static final String FLAG = ",?,?,?,?,?,?,?,?";
    
    private static final String DIM_VIOLATION_VALID = "Dim_violation_valid";
    
    private static final String DIM_VIOLATION_FIRST = "DIM_VIOLATION_FIRST";
    
    private static final String DIM_VIOLATION_INVALID = "DIM_VIOLATION_INVALID";
    
    
    
    public final JdbcTemplate.Converter<IllegalRecord, SQLException> record_converter =
        new JdbcTemplate.Converter<IllegalRecord, SQLException>()
        {
            @Override
            public IllegalRecord convert(ResultSet rs)
                throws SQLException
            {
                IllegalRecord record = new IllegalRecord(getIllegalRecordType());
                record.setDateKey(rs.getString("date_key"));
                record.setLicensePlate(rs.getString("LICENSE_PLATE"));
                record.setCardType(rs.getString("CARD_TYPE"));
                record.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                record.setForbidType(rs.getString("FORBID_TYPE"));
                record.setAreaId(rs.getString("area_id"));
                
                if (getIllegalRecordType() == 4 || getIllegalRecordType() == 2)
                {
                    record.setUploadState(rs.getString("TRANSFER_STATUS"));
                    record.setNoticeState(rs.getString("NOTICE_STATUS"));
                }
                if (getIllegalRecordType() == 2 || getIllegalRecordType() == 3)
                {
                	record.setVerifyPerson(rs.getString("VERIFY_PERSON"));
                }
                
                return record;
            }
        };
    
    /**
     * 获取违法记录类型记录类型1.过车记录，2违法有效记录，3.违法无效记录，4外地车首次违法记录
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    abstract int getIllegalRecordType();
    
    public List<IllegalRecord> extractRecord(String sql, final IllegalRecordQueryConditionReq condition)
    {
        condition.setPageCount(0);
        String retSql = buildSql(condition, sql);
        logger.info("FAManextract sql:"+retSql);
        logger.info("condition:"+condition.toString());
        try
        {
            
            return ServiceUtil.getService(JdbcTemplate.class).getList(retSql,
                buildParmterFeeder(condition),
                record_converter);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "extractPassRecord error! where sql：" + sql);
        }
    }
    
    public int getRecordCount(IllegalRecordQueryConditionReq condition)
    {
        StringBuilder sb = new StringBuilder(getRecordCountSql());
        sb.append(" where 1=1");
        buildWhereSql(condition, sb);
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getItem(sb.toString(), buildParmterFeeder(condition), new JdbcTemplate.Converter<Integer, SQLException>()
            {
                /**
                 * @param paramResultSet
                 * @return
                 * @throws SQLException
                 */
                @Override
                public Integer convert(ResultSet rs)
                    throws SQLException
                {
                    return rs.getInt(1);
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getRecordCount error! ");
        }
    }
    
    
    public List<IllegalRecord> queryRecord(String sql, final IllegalRecordQueryConditionReq condition)
    {
        String retSql = buildSqlPage(condition, sql);
        logger.info("货车违闯禁查询sql:"+retSql);
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(retSql,
                buildParmterFeeder(condition),
                record_converter);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "extractPassRecord error! where sql：" + sql);
        }
    }
    
    private String getRecordCountSql()
    {
        String sql = "select count(*) from #table";
        
        if (getIllegalRecordType() == 1)
        {
            sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.passRecordCount");
        }
        else if (getIllegalRecordType() == 2)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_VALID);
        }
        else if (getIllegalRecordType() == 3)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_INVALID);
        }
        else if (getIllegalRecordType() == 4)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_FIRST);
        }
        return sql;
    }
    
    /**
     * 该方法针对有效违法记录查询图片和待审核的记录查询图片
     * @param date 日期
     * @param licensePlate 车牌 
     * @param sql sql语句
     * @return
     * @see [类、类#方法、类#成员]
     */
    public PicImageResp getPicImageItem(final int date, final String licensePlate,final String areaId, String sql)
    {
        
        if (StringUtils.isEmpty(areaId))
        {
            StringUtils.replace(sql, "and area_id=?", StringUtils.EMPTY);
            StringUtils.replace(sql, "and COLLECTION_DEVICE_ID=?", StringUtils.EMPTY);
        }
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getItem(sql, new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setInt(1, date);
                    pst.setString(2, licensePlate);
                    if (StringUtils.isNotEmpty(areaId))
                    {
                        if (getIllegalRecordType() == 1)
                        {
                            pst.setInt(3, Integer.parseInt(areaId));
                        }
                        else
                        {
                            pst.setString(3, areaId);
                        }
                    }
                }
            }, new JdbcTemplate.Converter<PicImageResp, SQLException>()
            {
                @Override
                public PicImageResp convert(ResultSet rs)
                    throws SQLException
                {
                    PicImageResp resp = new PicImageResp();
                    resp.setComposeImage(rs.getString("LICENSE_PLATE_LINK0"));
                    
                    List<Image> imgs = new ArrayList<Image>(2);
                    resp.setSourceUrl(imgs);
                    String img = null;
                    for (int i = 1; i < 5; i++)
                    {
                        img = rs.getString("LICENSE_PLATE_LINK" + i);
                        if (StringUtils.isNotEmpty(img))
                        {
                            imgs.add(new Image(img));
                        }
                    }
                    
                    return resp;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getPicImageItem error! where sql：" + sql);
        }
    }
    
    /**
     * 此方法针对无效记录和待审核记录查询公共方法
     * @param date 时间
     * @param licensePlate 车牌
     * @param sql sql语句
     * @return 查询结果
     * @see [类、类#方法、类#成员]
     */
    public PicImageResp getPicImageList(final int date, final String licensePlate, final String areaId, String sql)
    {
        PicImageResp resp = new PicImageResp();
        final int tempAreaId = ConvertUtils.parseInt(areaId, -99);
        if (StringUtils.isEmpty(areaId) || tempAreaId == -99)
        {
            sql = StringUtils.replace(sql, "and area_id=?", StringUtils.EMPTY);
            sql = StringUtils.replace(sql, "and COLLECTION_DEVICE_ID=?", StringUtils.EMPTY);
        }
        try
        {
            List<Image> imgs =
                ServiceUtil.getService(JdbcTemplate.class).getList(sql, new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        pst.setInt(1, date);
                        pst.setString(2, licensePlate);
                        
                        if (StringUtils.isNotEmpty(areaId) && tempAreaId != -99)
                        {
                            
                            if (getIllegalRecordType() == 1 || getIllegalRecordType() == 3)
                            {
                                pst.setInt(3, tempAreaId);
                            }
                            else
                            {
                                pst.setString(3, areaId);
                            }
                        }
                    }
                }, new JdbcTemplate.Converter<Image, SQLException>()
                {
                    @Override
                    public Image convert(ResultSet rs)
                        throws SQLException
                    {
                        Image im = new Image();
                        im.setPicTime(getTime(rs.getInt("DATE_KEY"), rs.getInt("TIME_KEY")));
                        im.setPicUrl(rs.getString("LICENSE_PLATE_LINK1"));
                        return im;
                    }
                });
            resp.setImgList(imgs);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getPicImageList error! where sql：" + sql);
        }
        return resp;
    }
    
    private String getTime(int date, int time)
    {
        StringBuilder ret = new StringBuilder();
        ret.append(date);
        String timeStr = String.valueOf(time);
        for (int i = 0; i < 9 - timeStr.length(); i++)
        {
            ret.append("0");
        }
        ret.append(time);
        return ret.toString();
    }
    
    /**
     * 删除违法记录公共方法
     * @param con
     * @param data
     * @see [类、类#方法、类#成员]
     */
    public void deleteRecodrs(Connection con, IllegalApproval data)
    {
        String sql = buildDeleteRecodrs();
        
        try
        {
            PreparedStatement sta = con.prepareStatement(sql);
            logger.info("删除违法记录sql:"+sql);
            if (getIllegalRecordType() == 1)
            {
                sta.setInt(1, Integer.parseInt(data.getTempDateKey()));
            }
            else 
            {
                sta.setString(1, data.getTempDateKey());
            }
            logger.info("删除违法记录,参数1:"+data.getTempDateKey());
            sta.setString(2, data.getLicensePlate());
            logger.info("删除违法记录,参数2:"+data.getLicensePlate());
            sta.setString(3, String.valueOf(data.getAreaId()));
            logger.info("删除违法记录,参数3:"+data.getAreaId());
            
            sta.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ServiceException(e,"deleteRecodrs error! where sql = " + sql+";message:"+e.getMessage());
        }
    }
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String buildDeleteRecodrs()
    {
        
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteRecord");
        if (getIllegalRecordType() == 1)
        {
            sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deletePassRecord");
        }
        else if (getIllegalRecordType() == 2)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_VALID);
        }
        else if (getIllegalRecordType() == 3)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_INVALID);
        }
        else if (getIllegalRecordType() == 4)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_FIRST);
        }
        return sql;
    }

    /**
     * 插入违法记录公共方法
     * <功能详细描述>
     * @param con
     * @param data
     * @see [类、类#方法、类#成员]
     */
    public void insertRecodrs(Connection con, IllegalApproval data)
    {
        String sql = buildInsertRecodrsSql();
        try
        {
            PreparedStatement sta = con.prepareStatement(sql);
            
            int index  = 1; 
            sta.setString(index++, data.getAgencyId());
            sta.setString(index++, data.getViolationId());
            sta.setString(index++, String.valueOf(data.getAreaId()));
            sta.setString(index++, data.getDeviceType());
            sta.setString(index++, data.getIsWhiteList());
            sta.setString(index++, String.valueOf(data.getDateKey()));
            sta.setString(index++, data.getPlateTypeId());
            sta.setString(index++, data.getForbidType());
            sta.setString(index++, data.getCardType());
            sta.setString(index++, data.getNewLicensePlate());
            sta.setString(index++, data.getViolationType());
            sta.setString(index++, data.getVerifyPerson());
            sta.setString(index++, data.getVerifyTime());
            
            // 插入有效和外地车第一次违法
            if (getIllegalRecordType() != 3)
            {
                if (ArrayUtils.isEmpty(data.getPicUrls()))
                {
                    sta.setInt(index++, 0);
                    sta.setString(index++, null);
                    sta.setString(index++, null);
                    sta.setString(index++, null);
                    sta.setString(index++, null);
                }
                else
                {
                    sta.setInt(index++, data.getPicUrls().length);
                    for (String str : data.getPicUrls())
                    {
                        sta.setString(index++, str);
                    }
                    
                    for (int i = 0; i < 4 - data.getPicUrls().length; i++)
                    {
                        sta.setString(index++, null);
                    }
                }
                sta.setString(index++, data.getPic());
                sta.setString(index++, data.getUploadState());
                sta.setString(index++, data.getNoticeState());
            }
            
            sta.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new ServiceException(e,"insertRecodrs error! where sql = " + sql+"message:"+e.getMessage());
        }
    }
    

    /** <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String buildInsertRecodrsSql()
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.inserRecords");
        
        if (getIllegalRecordType() == 2)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_VALID);
            sql = StringUtils.replace(sql, "#other", OTHER_STRING);
            sql = StringUtils.replace(sql, "#flag", FLAG);
        }
        else if (getIllegalRecordType() == 4)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_FIRST);
            sql = StringUtils.replace(sql, "#other", OTHER_STRING);
            sql = StringUtils.replace(sql, "#flag", FLAG);
        }
        else if (getIllegalRecordType() == 3)
        {
            sql = StringUtils.replace(sql, "#table", DIM_VIOLATION_INVALID);
            sql = StringUtils.replace(sql, "#other", StringUtils.EMPTY);
            sql = StringUtils.replace(sql, "#flag", StringUtils.EMPTY);
        }
        return sql;
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    private ParameterFeeder buildParmterFeeder(final IllegalRecordQueryConditionReq condition)
    {
        return new JdbcTemplate.ParameterFeeder()
        {
            @Override
            public void setParameter(PreparedStatement pst)
                throws SQLException
            {
                int index = 1;
                if (StringUtils.isNotBlank(condition.getCardType()))
                {
                    pst.setString(index++, condition.getCardType());
                }
                if (StringUtils.isNotEmpty(condition.getForbidType()))
                {
                    pst.setString(index++, condition.getForbidType());
                }
                if(StringUtils.isNotEmpty(condition.getLicensePlate()))
                {
                    pst.setString(index++, condition.getLicensePlate());
                }
                
                if(StringUtils.isNotEmpty(condition.getPlateTypeId()))
                {
                    pst.setString(index++, condition.getPlateTypeId());
                }
                if (condition.getStartDate() != 0)
                {
                    pst.setInt(index++, condition.getStartDate());
                }
                if (condition.getEndDate() != 0)
                {
                    pst.setInt(index++, condition.getEndDate());
                }
                if(getIllegalRecordType() != 1 && StringUtils.isNotEmpty(condition.getApproveUser()))
                {
                	pst.setString(index++, condition.getApproveUser());
                }
                
                // 表示需要分页
                if (condition.getPageCount() != 0)
                {
                    pst.setInt(index++, (condition.getPageIndex() + 1) * condition.getPageCount());
                    pst.setInt(index++, condition.getPageIndex() * condition.getPageCount() + 1);
                }
            }
        };
    }

    
    private String buildSqlPage(final IllegalRecordQueryConditionReq condition,String sql)
    {
        StringBuilder sb = new StringBuilder(sql);
        buildWhereSql(condition, sb);
        sb.append(" order by date_key ");
        
        // 判断是否需要分页
        if (condition.getPageCount() != 0)
        {
            return StringUtils.replace(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.pagingCommon"), "#TABLE", sb.toString());
        }
        return sb.toString();
    }
    
    public Integer getDataCount(final IllegalRecordQueryConditionReq condition,String sql)
    {
        String countSql = "select count(*) from (#table)";
        sql = StringUtils.replace(countSql, "#table", buildSqlPage(condition, sql));
        
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getItem(sql,
                buildParmterFeeder(condition),
                new JdbcTemplate.Converter<Integer, Exception>()
                {
                    @Override
                    public Integer convert(ResultSet rs)
                        throws SQLException
                    {
                        return rs.getInt(1);
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "sql:" + sql);
        }
    }
    
    /** <一句话功能简述>
     * <功能详细描述>
     * @param condition
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String buildSql(final IllegalRecordQueryConditionReq condition,String sql)
    {
        StringBuilder sb = new StringBuilder("select * from (");
        sb.append(sql);
        
        buildWhereSql(condition, sb);
        
        sb.append(" ) where rownum <=");
        sb.append(condition.getCount());
        return sb.toString();
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param condition
     * @param sb
     * @see [类、类#方法、类#成员]
     */
    private void buildWhereSql(final IllegalRecordQueryConditionReq condition, StringBuilder sb)
    {
        if (StringUtils.isNotBlank(condition.getCardType()))
        {
            sb.append(" and Card_Type = ?");
        }
        if (StringUtils.isNotEmpty(condition.getForbidType()))
        {
            sb.append(" and Forbid_Type = ?");
        }
        if(StringUtils.isNotEmpty(condition.getLicensePlate()))
        {
            sb.append(" and License_Plate = ?");
        }
        
        if(StringUtils.isNotEmpty(condition.getPlateTypeId()))
        {
            sb.append(" and Plate_Type_Id = ?");
        }
        
        
        if (getIllegalRecordType() == 1)
        {
            if (condition.getStartDate() != 0)
            {
                sb.append(" and date_key >= ?");
                
            }
            if (condition.getEndDate() != 0)
            {
                sb.append(" and date_key <= ?");
            }
            
            if (CollectionUtils.isNotEmpty(condition.getAreaIds()))
            {
                sb.append(" and area_id in ('");
                sb.append(StringUtils.join(condition.getAreaIds(),"','"));
                sb.append("')");
            }
        }
        else
        {
            if (CollectionUtils.isNotEmpty(condition.getAreaIds()))
            {
                sb.append(" and COLLECTION_DEVICE_ID in ('");
                sb.append(StringUtils.join(condition.getAreaIds(),"','"));
                sb.append("')");
            }
            
            if (condition.getStartDate() != 0)
            {
                sb.append(" and to_number(to_char(VIOLATION_TIME,'yyyyMMdd'))  >= ?");
            }
            if (condition.getEndDate() != 0)
            {
                sb.append(" and to_number(to_char(VIOLATION_TIME,'yyyyMMdd'))  <= ?");
            }
            if(StringUtils.isNotEmpty(condition.getApproveUser()))
            {
            	sb.append(" and VERIFY_PERSON = ?");
            }
        }
    }
    public List<ViolationPassRecordVo> queryViolationRecord(final ViolationRecordReq req,final int num)
    {
    	String sql=((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationRecord");
    	if(req.getAnalyseType()==1)
    	{
    		sql=((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationRecordGroup");
    	}
    	
        try
        {
        	
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
            		buildParmterFeeder(req,num), CONVERTER(req.getAnalyseType()));
        } catch (SQLException e)
        {
            throw new ServiceException(e, "queryViolationRecord error!");
        }
    }
    private Converter<ViolationPassRecordVo, SQLException> CONVERTER(int analyseType)
    {
    	//按重点车辆类型统计
    	if(analyseType==1)
    	{
    		return new JdbcTemplate.Converter<ViolationPassRecordVo, SQLException>()
                    {
                @Override
                public ViolationPassRecordVo convert(ResultSet rs) throws SQLException
                {
                	ViolationPassRecordVo recordVo = new ViolationPassRecordVo();
                	recordVo.setForbidType(rs.getString("FORBID_TYPE"));
                	recordVo.setCount(rs.getInt("COUNT"));
                    return recordVo;
                }
            }; 
    	}
    	//按号牌号码、号牌种类统计
    	return new JdbcTemplate.Converter<ViolationPassRecordVo, SQLException>()
                {
            @Override
            public ViolationPassRecordVo convert(ResultSet rs) throws SQLException
            {
            	ViolationPassRecordVo recordVo = new ViolationPassRecordVo();
            	recordVo.setLicensePlate(rs.getString("LICENSE_PLATE"));
            	recordVo.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
            	recordVo.setCount(rs.getInt("COUNT"));
                return recordVo;
            }
        }; 
    }
    public List<ViolationCameraVo> queryViolationRecordCamera(final ViolationTimeReq req)
    {
    	String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationRecordCameraID");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
                    new JdbcTemplate.ParameterFeeder()
                    {

                        @Override
                        public void setParameter(PreparedStatement pst) throws SQLException
                        {
                            pst.setInt(1,req.getDateKey());
                            pst.setInt(2,req.getMinTime());
                            pst.setInt(3,req.getMaxTime());
                            pst.setInt(4,6);
                        }
                    }, new JdbcTemplate.Converter<ViolationCameraVo, SQLException>()
                    {
                        @Override
                        public ViolationCameraVo convert(ResultSet rs) throws SQLException
                        {
                        	ViolationCameraVo recordVo = new ViolationCameraVo();
                        	recordVo.setLpCameraId(rs.getString("LP_CAMERA_ID"));
                        	recordVo.setLicensePlate(rs.getString("LICENSE_PLATE"));
                        	recordVo.setLicensePlateLink1(rs.getString("LICENSE_PLATE_LINK1"));
                        	recordVo.setTime(req.getTime());
                            return recordVo;
                        }
                    });
        } catch (SQLException e)
        {
            throw new ServiceException(e, "queryViolationRecordCamera error!");
        }
    }
    public List<ViolationCameraVo> queryViolationRecordCameraCount(final int dateKey)
    {
    	String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getViolationRecordCameraCount");
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getList(sql,
                    new JdbcTemplate.ParameterFeeder()
                    {

                        @Override
                        public void setParameter(PreparedStatement pst) throws SQLException
                        {
                            pst.setInt(1,dateKey);
                        }
                    }, new JdbcTemplate.Converter<ViolationCameraVo, SQLException>()
                    {
                        @Override
                        public ViolationCameraVo convert(ResultSet rs) throws SQLException
                        {
                        	ViolationCameraVo recordVo = new ViolationCameraVo();
                        	recordVo.setLpCameraId(rs.getString("LP_CAMERA_ID"));
                        	recordVo.setCount(rs.getInt("COUNT"));
                            return recordVo;
                        }
                    });
        } catch (SQLException e)
        {
            throw new ServiceException(e, "queryViolationRecordCameraCount error!");
        }
    }
    /** <一句话功能简述>
     * <功能详细描述>
     * @param pageCount
     * @return
     * @see [类、类#方法、类#成员]
     */
    private ParameterFeeder buildParmterFeeder(final ViolationRecordReq req,final int num)
    {
        return new JdbcTemplate.ParameterFeeder()
        {
            @Override
            public void setParameter(PreparedStatement pst)
                throws SQLException
            {
                int index = 1;
                pst.setInt(index++, req.getBeginDate());
                pst.setInt(index++, req.getEndDate());
                pst.setInt(index++, num);
            }
        };
    }
}
