/*
 * 文 件 名:  DimViolationPassRecordDaoImpl.java
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
import java.sql.SQLException;
import java.util.List;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationPassRecordDao;
import com.jszt.domain.DimViolationPassRecord;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;
import com.jszt.vo.ViolationTimeReq;

/**
 * 过车记录数据库操作记录
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationPassRecordDaoImpl extends IllegalRecordUtilsDaoImpl implements DimViolationPassRecordDao
{

    
    @Override
    int getIllegalRecordType()
    {
        return 1;
    }

    @Override
    public List<IllegalRecord> extractPassRecord(final IllegalRecordQueryConditionReq condition)
    {
        return super.extractRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.extractPassRecord"),
            condition);
    }

    @Override
    public void updateVerifyMark(final List<IllegalRecord> record, final int mark)
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).executeBatch(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.updateVerifyMark"),
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement pst)
                        throws SQLException
                    {
                        for (IllegalRecord illegalRecord : record)
                        {
                            int index = 1;
                            pst.setInt(index++, mark);
                            pst.setInt(index++, Integer.parseInt(illegalRecord.getDateKey()));
                            pst.setString(index++, illegalRecord.getLicensePlate());
                            
                            pst.setString(index++, illegalRecord.getCardType());
                            pst.setString(index++, illegalRecord.getPlateTypeId());
                            pst.setString(index++, illegalRecord.getForbidType());
                            pst.setString(index++, illegalRecord.getAreaId());
                            
                            pst.addBatch();
                        }
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateVerifyMark error!");
        }
    }

    @Override
    public void initVerifyMark()
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                    .getConfig("sql.initVerifyMark"));
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "initVerifyMark error!");
        }
    }

    @Override
    public PicImageResp getPassPic(int date, String licensePlate,String areaId)
    {
        return super.getPicImageList(date,
            licensePlate,
            areaId,
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getPassPic"));
    }

    /**
     * @param con
     * @param data
     */
    @Override
    public void deletePassRecord(Connection con, IllegalApproval data)
    {
        super.deleteRecodrs(con, data);
    }

    @Override
    public void addVioRecord(final DimViolationPassRecord record)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addVioRecord");
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        ps.setString(++index, record.getLpCameraId());
                        ps.setInt(++index, record.getDateKey());
                        ps.setInt(++index, record.getTimeKey());
                        ps.setString(++index, record.getLicensePlate());
                        ps.setString(++index, record.getPlateTypeId());
                        ps.setString(++index, record.getForbidType());
                        ps.setString(++index, record.getCardType());
                        ps.setString(++index, record.getDeviceType());
                        ps.setString(++index, record.getLicensePlateColor());
                        ps.setString(++index, record.getDirection());
                        ps.setString(++index, record.getLane());
                        ps.setInt(++index, record.getVehicleSpeed());
                        ps.setInt(++index, record.getVehicleLength());
                        ps.setString(++index, record.getAlarmType());
                        ps.setString(++index, record.getTstrans());
                        ps.setString(++index, record.getLicenseRegion());
                        ps.setString(++index, record.getLicensePlateLink1());
                        ps.setString(++index, record.getLicensePlateLink2());
                        ps.setString(++index, record.getLicensePlateLink3());
                        ps.setInt(++index, record.getVerifyMark()); 
                        ps.setInt(++index, record.getAreaId());
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addVioRecord error!");
        }
        
    }

    /**
     * @param dateKey
     */
    @Override
    public void deleteRecordOnlyOnePic(final int dateKey)
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class)
                .execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteRecordOnlyOnePic"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setInt(1, dateKey);
                            pst.setInt(2, dateKey);
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "deleteRecordOnlyOnePic error!");
        }
    }

    /**
     * @param dateKey
     */
    @Override
    public List<String> getNonlocalRecord(int dateKey)
    {
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class)
                .getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getNonlocalRecord"),
                    dateKey);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getNonlocalRecord error!");
        }
    }

    @Override
    public boolean isPassRecordFromWjwf(int startDate, int endDate, String licensePlate)
    {
        try
        {
            return new JdbcTemplate((ConnectionFactory)ServiceUtil.getService("wjwfFactory")).getInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.isPassRecordFromWjwf"),
                startDate,
                endDate,
                licensePlate) <= 0;
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "isPassRecordFromWjwf error!");
        }
    }

    @Override
    public void deleteRecord(final List<String> licensePlates, final int dateKey)
    {
        try
        {
            //ServiceUtil.getService(JdbcTemplate.class).executeBatch(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deletePassRecord"), new JdbcTemplate.ParameterFeeder()
        	ServiceUtil.getService(JdbcTemplate.class).executeBatch(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deleteNonLocalPassRecord"), new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    for (String string : licensePlates)
                    {
                        pst.setInt(1, dateKey);
                        pst.setString(2, string);
                        
                        pst.addBatch();
                    }
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "deleteRecord error!");
        }
    }
    
    /**
     * @param datekey
     */
    @Override
    public void delteRepeatingPicData(final int datekey)
    {
        try
        {
            ServiceUtil.getService(JdbcTemplate.class)
                .execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delteRepeatingPicData"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setInt(1, datekey);
                            pst.setInt(2, datekey);
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "delteRepeatingPicData error!");
        }
    }

    /**
     * @return
     */
    @Override
    public int getViolationPassRecordCout(IllegalRecordQueryConditionReq condition)
    {
        return super.getRecordCount(condition);
    }

    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryPassRecord(IllegalRecordQueryConditionReq condition)
    {
        return super.queryRecord(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryPassRecord"),
            condition);
    }

    /**
     * @param condition
     * @return
     */
    @Override
    public int queryPassRecordCount(IllegalRecordQueryConditionReq condition)
    {
        return super.getDataCount(condition, ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.queryPassRecord"));
    }

	@Override
	public List<ViolationPassRecordVo> queryViolationRecord(
			ViolationRecordReq req) {
		int num=10;
		return super.queryViolationRecord(req,num);
	}

	@Override
	public List<ViolationCameraVo> queryViolationRecordCamera() {
		String time=DateUtils.getCurrentTime();
		int dateKey=Integer.parseInt(time.substring(0,8));
    	int minTime=Integer.parseInt(time.substring(8)+"000");
    	int maxTime=Integer.parseInt(time.substring(8)+"999");
    	ViolationTimeReq req=new ViolationTimeReq(dateKey,minTime,maxTime,time);
		return super.queryViolationRecordCamera(req);
	}

	@Override
	public List<ViolationCameraVo> queryViolationRecordCameraCount() {
		String time=DateUtils.getCurrentTime();
		int dateKey=Integer.parseInt(time.substring(0,8));
		return super.queryViolationRecordCameraCount(dateKey);
	}

	@Override
	public void deltePassRecordByDate(final int datekey) {
		try
        {
            ServiceUtil.getService(JdbcTemplate.class)
                .execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.deltePassRecordByDate"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setInt(1, datekey);
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "deltePassRecordByDate error!");
        }
	}

	@Override
	public void delteHisPassRecordByDate(final int datekey) {
		try
        {
            ServiceUtil.getService(JdbcTemplate.class)
                .execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.delteHisPassRecordByDate"),
                    new JdbcTemplate.ParameterFeeder()
                    {
                        @Override
                        public void setParameter(PreparedStatement pst)
                            throws SQLException
                        {
                            pst.setInt(1, datekey);
                        }
                    });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "delteHisPassRecordByDate error!");
        }
	}

	@Override
	public void rebuildIndex(String buildSql) {

		try
        {
            ServiceUtil.getService(JdbcTemplate.class).execute(buildSql);
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "rebuildIndex error!"+buildSql);
        }
	}


    
    
    
}
