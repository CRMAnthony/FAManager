package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimViolationPassRecordHisDao;
import com.jszt.domain.DimViolationPassRecordHis;

public class DimViolationPassRecordHisDaoImpl implements DimViolationPassRecordHisDao
{

    @Override
    public void addVioRecordHis(final DimViolationPassRecordHis recordHis)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.addVioRecordHis");
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        ps.setString(++index, recordHis.getLpCameraId());
                        ps.setInt(++index, recordHis.getDateKey());
                        ps.setInt(++index, recordHis.getTimeKey());
                        ps.setString(++index, recordHis.getLicensePlate());
                        ps.setString(++index, recordHis.getPlateTypeId());
                        ps.setString(++index, recordHis.getForbidType());
                        ps.setString(++index, recordHis.getCardType());
                        ps.setString(++index, recordHis.getDeviceType());
                        ps.setString(++index, recordHis.getLicensePlateColor());
                        ps.setString(++index, recordHis.getDirection());
                        ps.setString(++index, recordHis.getLane());
                        ps.setInt(++index, recordHis.getVehicleSpeed());
                        ps.setInt(++index, recordHis.getVehicleLength());
                        ps.setString(++index, recordHis.getAlarmType());
                        ps.setString(++index, recordHis.getTstrans());
                        ps.setString(++index, recordHis.getLicenseRegion());
                        ps.setString(++index, recordHis.getLicensePlateLink1());
                        ps.setString(++index, recordHis.getLicensePlateLink2());
                        ps.setString(++index, recordHis.getLicensePlateLink3());
                        ps.setInt(++index, recordHis.getVerifyMark()); 
                        ps.setInt(++index, recordHis.getAreaId());
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addVioRecordHis error!");
        }
        
    }
    
}
