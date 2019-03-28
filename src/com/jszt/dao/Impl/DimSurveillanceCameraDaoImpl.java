/*
 * 文 件 名:  DimSurveillanceCameraDaoImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年11月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.Converter;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimSurveillanceCameraDao;
import com.jszt.domain.DimSurveillanceCamera;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年11月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimSurveillanceCameraDaoImpl implements DimSurveillanceCameraDao
{

    /**
     * @param cameraNumber
     * @return
     */
    @Override
    public DimSurveillanceCamera getSCByNum(final String cameraNumber)
    {
        String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getSCByNum");
        try
        {
        	ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("basicFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            return jdbcTemplate.getItem(sql,new JdbcTemplate.ParameterFeeder()
            {
                
                @Override
                public void setParameter(PreparedStatement pst)
                    throws SQLException
                {
                    pst.setString(1, cameraNumber);
                }
            },new Converter<DimSurveillanceCamera, SQLException>()
            {
                @Override
                public DimSurveillanceCamera convert(ResultSet rs)
                    throws SQLException
                {
                    DimSurveillanceCamera sc = new DimSurveillanceCamera();
                    sc.setSurCameraNumber(rs.getString("SUR_CAMERA_NUMBER"));
                    sc.setSurCameraId(rs.getString("SUR_CAMERA_ID"));
                    sc.setDeviceType(rs.getString("DEVICE_TYPE"));
                    sc.setSegIntId(rs.getString("SEG_INT_ID"));
                    sc.setRoadId(rs.getString("ROADID"));
                    sc.setMainlineId(rs.getString("MAINLINE_ID"));
                    sc.setNetworkId(rs.getString("NETWORKID"));
                    sc.setSurCameraType(rs.getString("SUR_CAMERA_TYPE"));
                    sc.setInstallationDate(rs.getInt("INSTALLATION_DATE"));
                    sc.setInstallationMode(rs.getString("INSTALLATION_MODE"));
                    sc.setInstallationHeight(rs.getInt("INSTALLATION_HEIGHT"));
                    sc.setPillarHeight(rs.getString("PILLAR_HEIGHT"));
                    sc.setCrossarmLength(rs.getString("CROSSARM_LENGTH"));
                    sc.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
                    sc.setPositionSpell(rs.getString("POSITION_SPELL"));
                    sc.setMileMarker(rs.getInt("MILE_MARKER"));
                    sc.setLatitude(rs.getDouble("LATITUDE"));
                    sc.setLongitude(rs.getDouble("LONGITUDE"));
                    sc.setSurCameraArea(rs.getString("SUR_CAMERA_AREA"));
                    sc.setPowerMode(rs.getString("POWER_MODE"));
                    sc.setAddress(rs.getString("ADDRESS"));
                    sc.setGetway(rs.getString("GETWAY"));
                    sc.setAddressTermination(rs.getString("ADDRESS_TERMINATION"));
                    sc.setOpenStatus(rs.getString("OPEN_STATUS"));
                    sc.setOpenDete(rs.getInt("OPEN_DATE"));
                    sc.setAgencyId(rs.getString("AGENCY_ID"));
                    sc.setDescription(rs.getString("DESCRIPTION"));
                    sc.setVideoNumber(rs.getString("VIDEO_NUMBER"));
                    sc.setChannel(rs.getInt("CHANNEL"));
                    sc.setFacilityAddress(rs.getString("Facility_Address"));
                    sc.setFacilityId(rs.getString("Facility_Id"));
                    
                    return sc;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException("getLCByNum error! ",e);
        }
    }

    
    
}
