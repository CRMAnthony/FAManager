package com.jszt.dao.Impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.db.JdbcTemplate;
import com.jsits.commons.db.JdbcTemplate.Converter;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.CommonDao;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimIntersection;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimNoticeCount;
import com.jszt.domain.DimPlateType;
import com.jszt.domain.DimSysAccount;
import com.jszt.domain.DimSysConfig;
import com.jszt.domain.DimViolationVehicleType;
import com.jszt.domain.Param;
import com.jszt.vo.LPCameraVo;
import com.jszt.vo.NoticeVo;

public class CommonDaoImpl implements CommonDao
{
    //账户转换列表
    public final JdbcTemplate.Converter<DimSysAccount, SQLException> ACCOUNT_CONVERTER =
        new JdbcTemplate.Converter<DimSysAccount, SQLException>()
        {
            @Override
            public DimSysAccount convert(ResultSet rs)
                throws SQLException
            {
                DimSysAccount account = new DimSysAccount();
                
                account.setAccount(rs.getString("ACCOUNT"));
                account.setAccountName(rs.getString("ACCOUNT_NAME"));
                account.setPassword(rs.getString("PASSWORD"));
                account.setAgencyId(rs.getString("AGENCY_ID"));
                account.setRoleId(rs.getString("ROLE_ID"));
                account.setAccountType(rs.getInt("ACCOUNT_TYPE"));
                account.setSaltValue(rs.getString("SALTVALUE"));
                account.setIndexPage(rs.getString("INDEX_PAGE"));
                
                return account;
            }
        };
    
    @Override
    public List<DimPlateType> getPlateType()
    {
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
            return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getPlateType"),
                null,
                new JdbcTemplate.Converter<DimPlateType, SQLException>()
                {
                    @Override
                    public DimPlateType convert(ResultSet rs)
                        throws SQLException
                    {
                        DimPlateType plateType = new DimPlateType();
                        plateType.setTypeId(rs.getString("TYPEID"));
                        plateType.setName(rs.getString("NAME"));
                        return plateType;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getPlateType error!");
        }
    }

    @Override
    public List<DimSysConfig> getLongPeriod()
    {
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
           return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getLongPeriod"),
                null,
                new JdbcTemplate.Converter<DimSysConfig, SQLException>()
                {
                    @Override
                    public DimSysConfig convert(ResultSet rs)
                        throws SQLException
                    {
                        DimSysConfig sysConfig = new DimSysConfig();
                        sysConfig.setId(rs.getInt("ID"));
                        sysConfig.setConfigKey(rs.getString("CONFIG_KEY"));
                        sysConfig.setConfigName(rs.getString("CONFIG_NAME"));
                        sysConfig.setConfigValue(rs.getString("CONFIG_VALUE"));
                        return sysConfig;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getLongPeriod error!");
        }
    }

    @Override
    public List<DimElectricPoliceCamera> getCameras()
    {
        
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("basicFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
           return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getElectric"),
                null,
                new JdbcTemplate.Converter<DimElectricPoliceCamera, SQLException>()
                {
                    @Override
                    public DimElectricPoliceCamera convert(ResultSet rs)
                        throws SQLException
                    {
                        DimElectricPoliceCamera camera = new DimElectricPoliceCamera();
                        camera.setElectricPoliceNumber(rs.getString("electric_police_number"));
                        camera.setInstallationPosition(rs.getString("installation_position"));
                        camera.setIntersectionid(rs.getString("INTERSECTIONID"));
                        camera.setElectricPoliceId(rs.getString("ELECTRIC_POLICE_ID"));
                        camera.setAgencyId(rs.getString("AGENCY_id"));
                        camera.setFacilityId(rs.getString("FACILITY_ID"));
						camera.setFacilityAddress(rs.getString("FACILITY_ADDRESS"));
                        
                        return camera;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getCameras error!");
        }
    }
    
    /**
     * 图片合成用
     */
    @Override
    public DimElectricPoliceCamera getCameras1(final String number)
    {
        
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("basicFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
           return jdbcTemplate.getItem(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getElectric1"),new JdbcTemplate.ParameterFeeder()
    			{
    				public void setParameter(PreparedStatement pst) throws SQLException
    				{
    					if (StringUtils.isNotEmpty(number))
    					{
    						pst.setString(1, number);
    					}
    				}
    			}, 
                new JdbcTemplate.Converter<DimElectricPoliceCamera, SQLException>()
                {
                    @Override
                    public DimElectricPoliceCamera convert(ResultSet rs)
                        throws SQLException
                    {
                        DimElectricPoliceCamera camera = new DimElectricPoliceCamera();
                        
                        camera.setInstallationPosition(rs.getString("installation_position"));
                        camera.setFacilityId(rs.getString("FACILITY_ID"));
						camera.setFacilityAddress(rs.getString("FACILITY_ADDRESS"));
						
                        return camera;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getCameras error!");
        }
    }

    @Override
    public DimSysConfig getVioTimes()
    {
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
           return jdbcTemplate.execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getVioTimes"),
                null,new JdbcTemplate.ResultSetCallback<DimSysConfig, SQLException>()
                {

                    @Override
                    public DimSysConfig doWithResultSet(ResultSet rs)
                        throws SQLException
                    {
                        DimSysConfig sysConfig = new DimSysConfig();
                        while (rs.next())
                        {
                            sysConfig.setId(rs.getInt("ID"));
                            sysConfig.setConfigKey(rs.getString("CONFIG_KEY"));
                            sysConfig.setConfigName(rs.getString("CONFIG_NAME"));
                            sysConfig.setConfigValue(rs.getString("CONFIG_VALUE"));
                            
                        }
                        return sysConfig;
                        
                    }
                }
                );
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getLongPeriod error!");
        }
    }

    @Override
    public void updateAvoidPeak(final String period)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateAvoidPeak");
        
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
            jdbcTemplate.execute(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setString(++index, period);
                } 
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateAvoidPeak error!");
        }
        
    }

    @Override
    public void updateVioTimes(final String times)
    {
        String sql = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.updateVioTimes");
        
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
            jdbcTemplate.execute(sql, new JdbcTemplate.ParameterFeeder(){

                @Override
                public void setParameter(PreparedStatement ps)
                    throws SQLException
                {
                    int index = 0;
                    ps.setString(++index, times);
                } 
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateVioTimes error!");
        }
        
    }

    @Override
    public List<DimIntersection> getIntersections()
    {
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
            return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getIntersections"),
                null,
                new JdbcTemplate.Converter<DimIntersection, SQLException>()
                {
                    @Override
                    public DimIntersection convert(ResultSet rs)
                        throws SQLException
                    {
                        DimIntersection intersection = new DimIntersection();
                        intersection.setIntersectionId(rs.getString("INTERSECTIONID"));
                        intersection.setIntersectionName(rs.getString("INTERSECTION_NAME"));
                        return intersection;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getIntersections error!");
        }
    }

    /**
     * @return
     */
    @Override
    public List<DimViolationVehicleType> getViolationVehicleType()
    {
        
        try
        {
            ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
            JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
            
            return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                .getConfig("sql.getViolationVehicleType"),
                null,
                new JdbcTemplate.Converter<DimViolationVehicleType, SQLException>()
                {
                    @Override
                    public DimViolationVehicleType convert(ResultSet rs)
                        throws SQLException
                    {
                        DimViolationVehicleType violationVehicleType = new DimViolationVehicleType();
                        violationVehicleType.setDescription(rs.getString("DESCRIPTION"));
                        violationVehicleType.setPlateColor(rs.getString("PLATE_COLOR"));
                        violationVehicleType.setPlateTypeId(rs.getString("PLATE_TYPE_ID"));
                        violationVehicleType.setPlateTypeName(rs.getString("PLATE_TYPE_NAME"));
                        return violationVehicleType;
                    }
                });
        }
        catch (SQLException e)
        {
           throw new ServiceException(e, "getViolationVehicleType error!");
        }
    }

	/**
	 * @return
	 */
	@Override
	public List<DimLicenseCamera> getLicenseCamera()
	{
		ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("basicFactory");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
        try
		{
			return jdbcTemplate.getList(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
			        .getConfig("sql.getLicenseCamera"),
			        null,
			        new JdbcTemplate.Converter<DimLicenseCamera, SQLException>()
			        {
			            @Override
			            public DimLicenseCamera convert(ResultSet rs)
			                throws SQLException
			            {
			            	DimLicenseCamera camera = new DimLicenseCamera();
			                camera.setLpCameraNumber(rs.getString("LP_CAMERA_NUMBER"));
							camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
							camera.setAgencyId(rs.getString("AGENCY_id"));
							camera.setFacilityId(rs.getString("FACILITY_ID"));
							camera.setFacilityAddress(rs.getString("FACILITY_ADDRESS"));
							
			                return camera;
			            }
			        });
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getLicenseCamera error!");
		}
	}
	
	/**
	 * 图片合成用
	 */
	@Override
	public DimLicenseCamera getLicenseCamera1(final String number)
	{
		ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("basicFactory");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
        try
		{
			return jdbcTemplate.getItem(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
			        .getConfig("sql.getLicenseCamera1"),
			        new JdbcTemplate.ParameterFeeder()
					{
						public void setParameter(PreparedStatement pst) throws SQLException
						{
							if (StringUtils.isNotEmpty(number))
							{
								pst.setString(1, number);
							}
						}
					}, 
			        new JdbcTemplate.Converter<DimLicenseCamera, SQLException>()
			        {
			            @Override
			            public DimLicenseCamera convert(ResultSet rs)
			                throws SQLException
			            {
			            	DimLicenseCamera camera = new DimLicenseCamera();
			                
							camera.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
							camera.setFacilityId(rs.getString("FACILITY_ID"));
							camera.setFacilityAddress(rs.getString("FACILITY_ADDRESS"));
							
			                return camera;
			            }
			        });
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getLicenseCamera error!");
		}
	}
    
	@Override
    public DimSysAccount getSysAccount(final String accountId)
    {
        ConnectionFactory connFactory = (ConnectionFactory) ServiceUtil.getService("wjzhjtFactory");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
        try
        {
            return jdbcTemplate
                .getItem(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getSysAccount"),
                    new JdbcTemplate.ParameterFeeder()       
            {
                @Override
                public void setParameter(PreparedStatement ps) throws SQLException
                {
                    ps.setString(1, accountId);
                }
            }, ACCOUNT_CONVERTER);
        } catch (SQLException e)
        {
            throw new ServiceException(e, "getSysAccount error!");
        }
    }

	
    @Override
    public Map<String,String> getAgency()
    {
        ConnectionFactory connFactory = (ConnectionFactory)ServiceUtil.getService("wjzhjtFactory");
        JdbcTemplate jdbcTemplate = new JdbcTemplate(connFactory);
        try
        {
            return jdbcTemplate.execute(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getAgency"),
                null,
                new JdbcTemplate.ResultSetCallback<Map<String, String>, SQLException>()
                {
                    @Override
                    public Map<String, String> doWithResultSet(ResultSet rs)
                        throws SQLException
                    {
                        Map<String, String> datas = new HashMap<String, String>();
                        while (rs.next())
                        {
                            datas.put(rs.getString("agency_id"), rs.getString("agency_name"));
                        }
                        return datas;
                    }
                });
            
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getSysAccount error!");
        }
    }
	
    @Override
	public LPCameraVo getCameraByID(final String cameraID,final String prefix) {
    	if(!"D".equals(prefix)&&!"K".equals(prefix))
    	{
    		return null;
    	}
		String sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getElectric2");
		if("K".equals(prefix))
		{
			sql = ((ConfigureService) ServiceUtil.getService("faManagerConfigureService")).getConfig("sql.getLicenseCamera2");
		}
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
                    pst.setString(1, cameraID);
                }
            },new Converter<LPCameraVo, SQLException>()
            {
                @Override
                public LPCameraVo convert(ResultSet rs)
                    throws SQLException
                {
                	LPCameraVo vo = new LPCameraVo();
                	vo.setInstallationPosition(rs.getString("INSTALLATION_POSITION"));
                	vo.setLatitude(rs.getDouble("LATITUDE"));
                	vo.setLongitude(rs.getDouble("LONGITUDE"));
                	vo.setFacilityAddress(rs.getString("FACILITY_ADDRESS"));
                	vo.setFacilityId(rs.getString("FACILITY_ID"));
                    
                    return vo;
                }
            });
        }
        catch (SQLException e)
        {
            throw new ServiceException("getCameraByID error! ",e);
        }
	}

	@Override
	public int getNoticeCount() {
        String sql="select count(*) from DIM_NOTICE";
        try
        {
            return ServiceUtil.getService(JdbcTemplate.class).getItem(sql,new JdbcTemplate.Converter<Integer, SQLException>()
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
            throw new ServiceException(e, "getNoticeCount error! ");
        }
	}

	@Override
	public void addNotice(final NoticeVo vo) {
		String sql = "insert into DIM_NOTICE(NOTICE_TIME,NOTICE_CONTENT) values(?,?)";
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        //ps.setInt(++index, vo.getId());
                        ps.setString(++index, vo.getNoticeTime());
                        ps.setString(++index, vo.getNoticeContent());
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addNotice error!");
        }		
	}

	@Override
	public void updateID() {
		 try
	        {
			 JdbcTemplate jdbcTemplate=ServiceUtil.getService(JdbcTemplate.class);
			 jdbcTemplate
             .execute("delete from DIM_NOTICE where notice_time=(select min(notice_time) from dim_notice)");
			 /*jdbcTemplate
	                .execute("update DIM_NOTICE set ID=ID-1");*/
	        }
	        catch (SQLException e)
	        {
	            throw new ServiceException(e, "updateID error!" + e.getMessage());
	        }
	}

	@Override
	public DimNoticeCount getTodayNoticeCount(final int dateKey) {
		String sql = "select DATE_KEY,NOTICE_COUNT from DIM_NOTICE_COUNT where DATE_KEY=?";
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getItem(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setInt(1, dateKey);
				}
			}, new JdbcTemplate.Converter<DimNoticeCount, SQLException>()
			{

				@Override
				public DimNoticeCount convert(ResultSet rs) throws SQLException
				{
					DimNoticeCount noticeCount = new DimNoticeCount();
					noticeCount.setDateKey(rs.getInt("DATE_KEY"));
					noticeCount.setNoticeCount(rs.getInt("NOTICE_COUNT"));
					return noticeCount;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getTodayNoticeCount fail!!!");
		}
	}

	@Override
	public void addNoticeCount(final DimNoticeCount noticeCount) {
		String sql = "insert into DIM_NOTICE_COUNT(DATE_KEY,NOTICE_COUNT) values(?,?)";
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        ps.setInt(++index, noticeCount.getDateKey());
                        ps.setInt(++index, noticeCount.getNoticeCount());
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "addNoticeCount error!");
        }
	}

	@Override
	public void updateNoticeCount(final DimNoticeCount noticeCount) {
		String sql = "update DIM_NOTICE_COUNT set NOTICE_COUNT=? where DATE_KEY=?";
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        ps.setInt(++index, noticeCount.getNoticeCount());
                        ps.setInt(++index, noticeCount.getDateKey());
                        
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "updateNoticeCount error!");
        }
	}

	@Override
	public void deleteNoticeCount(final int dateKey) {
		String sql = "delete DIM_NOTICE_COUNT  where DATE_KEY < ? ";
        try{
            ServiceUtil.getService(JdbcTemplate.class).execute(sql,
                new JdbcTemplate.ParameterFeeder()
                {
                    
                    @Override
                    public void setParameter(PreparedStatement ps)
                        throws SQLException
                    {
                        int index = 0;
                        ps.setInt(++index, dateKey);
                    }
                });
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "deleteNoticeCount error!");
        }
	}

	@Override
	public Param getParam(final String key) {
		String sql = "select t.KEY,t.VALUE from PARAM t where t.KEY = ?";
		try
		{
			return ServiceUtil.getService(JdbcTemplate.class).getItem(sql, new JdbcTemplate.ParameterFeeder()
			{

				@Override
				public void setParameter(PreparedStatement pst) throws SQLException
				{
					pst.setString(1, key);
				}
			}, new JdbcTemplate.Converter<Param, SQLException>()
			{

				@Override
				public Param convert(ResultSet rs) throws SQLException
				{
					Param param = new Param();
					param.setKey(rs.getString("KEY"));
					param.setValue(rs.getString("VALUE"));
					return param;
				}
			});
		} catch (SQLException e)
		{
			throw new ServiceException(e, "getParam fail!!!");
		}
	}
    
}
