/*
 * 文 件 名:  PassLineServiceImpl.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.Assert;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.LogUtil;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.adapter.util.ErrorCode;
import com.jszt.dao.DimForbidAreaDao;
import com.jszt.dao.DimLongPassCardDao;
import com.jszt.dao.DimPassLineDao;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimPassLine;
import com.jszt.domain.DimViolationAgency;
import com.jszt.service.PassLimitService;
import com.jszt.service.PassLineService;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.PassLineInfoAccoutReq;
import com.jszt.vo.PassLineInfoReq;
import com.jszt.vo.PassLineReq;
import com.jszt.vo.PassLineVo;

/**
 * 通行线路相关业务接口 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class PassLineServiceImpl implements PassLineService
{

	@Override
	public void addPassLine(PassLineReq req) throws JsztException
	{
		if (StringUtils.isNotEmpty(req.getLineName()))
		{
			DimPassLine passLine = null;
			Integer passLineId = ServiceUtil.getService(DimPassLineDao.class).getAddPassLineId();
			if (StringUtils.isEmpty(req.getDevices()) && req.getAreaId()==null)
			{
				passLine = new DimPassLine();
				passLine.setLineId(passLineId);
				passLine.setLineName(req.getLineName());
				passLine.setIntList("-1");
				passLine.setDeviceList("-1");
				passLine.setDescription(req.getDescription());
				ServiceUtil.getService(DimPassLineDao.class).addPassLine(passLine);
			} else
			{
				if(req.getAreaId() != null){
					//禁区ID不为null，则获取对应禁区的所有设备ID，覆盖选择的设备ID
					List<DimForbidArea> forbidAreas = ServiceUtil.getService(DimForbidAreaDao.class).getForbidAreaById(req.getAreaId());
					StringBuilder deviceSB = new StringBuilder();
					for (DimForbidArea dimForbidArea : forbidAreas) {
						deviceSB.append(dimForbidArea.getDeviceList()).append(",");
					}
					passLine = new DimPassLine();
					passLine.setLineId(passLineId);
					passLine.setLineName(req.getLineName());
					passLine.setIntList("-1");
					passLine.setDeviceList(StringUtils.substring(deviceSB.toString(), 0, deviceSB.length() - 1));
					passLine.setAreaId(req.getAreaId());
					passLine.setAuthId(req.getAuthId());
					passLine.setTempCardFlag(req.getTempCardFlag());
					passLine.setDescription(req.getDescription());
					ServiceUtil.getService(DimPassLineDao.class).addPassLine(passLine);
					
				} else{
					//禁区ID为null，则使用选择的设备ID
					List<String> eleList = ServiceUtil.getService(CommonServiceImpl.class).getElectricByposition(req.getDevices());
					if (CollectionUtils.isNotEmpty(eleList))
					{
						StringBuilder sb = new StringBuilder();
						for (String string : eleList)
						{
							sb.append(string).append(",");
						}
						passLine = new DimPassLine();
						passLine.setLineId(passLineId);
						passLine.setLineName(req.getLineName());
						passLine.setIntList("-1");
						passLine.setDeviceList(StringUtils.substring(sb.toString(), 0, sb.length() - 1));
						passLine.setAreaId(req.getAreaId());
						passLine.setAuthId(req.getAuthId());
						passLine.setTempCardFlag(req.getTempCardFlag());
						passLine.setDescription(req.getDescription());
						ServiceUtil.getService(DimPassLineDao.class).addPassLine(passLine);
					}
				}
//				List<String> eleList = ServiceUtil.getService(CommonServiceImpl.class).getElectricPoliceByLine(req.getIntList());
			}
			ServiceUtil.getService(CommonServiceImpl.class).refreshPassLine();
			
			//添加完passLine后给相应的线路添加默认的限额配置
			PassLimitService limitService = ServiceUtil.getService(PassLimitService.class);
			StringBuilder limitSB = new StringBuilder();
			ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
	        String defaultLimit = cfgService.getConfig("tempPassLimit.defaultLimit"); //通行线路的默认限额值(0点到23点的限额数据)
//	        for (int i = 0; i < 24; i++)
//	        {
//	        	limitSB.append(defaultLimit).append(",");
//	        }
	        limitSB.append(defaultLimit);
//	        if(StringUtils.isNotEmpty(limitSB)){
//	        	limitSB.deleteCharAt(limitSB.length()-1);
//	        }
	        String plateTypeList = cfgService.getConfig("tempPassLimit.plateTypeList");
	        if(StringUtils.isNotEmpty(plateTypeList)){
	        	String [] plateTypeArray = plateTypeList.split(",");
	        	for(int j=0; j<plateTypeArray.length; j++){
	        		limitService.addPassLimit(limitSB.toString(),plateTypeArray[j],null,passLineId);
	        	}
	        }
			
		} else
		{
//			Assert.notEmpty(req.getIntList(), ErrorCode.PARAM_ERROR, "param [intList] can not be null!");
			Assert.notEmpty(req.getLineName(), ErrorCode.PARAM_ERROR, "param [lineName] can not be null!");
		}

	}

	@Override
	public List<PassLineVo> getPassLine(PassLineInfoReq req) throws JsztException
	{
		List<DimPassLine> passLineList = ServiceUtil.getService(DimPassLineDao.class).getPassLineByLineName(req);
		List<PassLineVo> passLineVoList = toVoList(passLineList);
		Map<Integer, PassLineVo> dimMap = new HashMap<Integer, PassLineVo>(passLineList.size());
		for (PassLineVo passLineVo : passLineVoList) {
			PassLineVo passLine = dimMap.get(passLineVo.getLineId());
			// 集合里没有
			if (passLine == null)
			{
				dimMap.put(passLineVo.getLineId(), passLineVo);
			}
			// 集合里有
			else
			{
				passLineVo.setDeviceList(passLine.getDeviceList() + "," + passLineVo.getDeviceList());
				dimMap.put(passLineVo.getLineId(), passLineVo);
			}
		}
		return  new ArrayList<PassLineVo>(dimMap.values());
	}

	/**
	 * @return
	 * @throws JsztException
	 */
	@Override
	public List<DimPassLine> getPassLineId() throws JsztException
	{
		PassLineInfoReq req = new PassLineInfoReq();
		req.setLineId(0);
		return ServiceUtil.getService(DimPassLineDao.class).getPassLineByLineName(req);
	}

	/**
	 * @param req
	 */
	@Override
	public void delPassLine(DelIdReq req)
	{
		//String [] lineId = req.getId().split(",");
		ServiceUtil.getService(DimPassLineDao.class).delPassline(req.getId());
		//更新通行线路缓存
		ServiceUtil.getService(CommonServiceImpl.class).refreshPassLine();
	}

	@Override
	public List<DimPassLine> getPassLineByAccountName(PassLineInfoAccoutReq req) throws JsztException {
		if(req!=null && StringUtils.isNotEmpty(req.getAccount())){
			return ServiceUtil.getService(DimPassLineDao.class).getPassLineByAccountName(req.getAccount());
		} else{
			throw new JsztException("getPassLineByAccountName, req can not be null!");
		}
	}
	
	/**
	 * PO转VO <功能详细描述>
	 * 
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	private List<PassLineVo> toVoList(List<DimPassLine> DimPassLineList) throws JsztException
	{
		List<PassLineVo> passLineVoList = new ArrayList<PassLineVo>();
		if (CollectionUtils.isNotEmpty(DimPassLineList))
		{
			DimForbidAreaDao forbidAreaDao =  ServiceUtil.getService(DimForbidAreaDao.class);
			for (DimPassLine dimPassLine : DimPassLineList)
			{

				PassLineVo passLineVo = new PassLineVo();
				// 地点
				String[] devices = StringUtils.split(getPositions(dimPassLine.getDeviceList()), ",");
				List<Map<String, String>> list = new ArrayList<Map<String, String>>();
				for (String device : devices)
				{

					Map<String, String> map = new HashMap<String, String>();
					DimElectricPoliceCamera camera = forbidAreaDao.getCamera(device.trim());
					if (camera != null)
					{
						DimViolationAgency agency = forbidAreaDao.getViolationAgency(camera.getAgencyId());
						if (agency != null)
						{
							map.put("agencyName", agency.getAgencyName());
							map.put("installationPosition", device.trim());
						}
					} else
					{
						DimLicenseCamera licenseCamera = forbidAreaDao.getLicenseCamera(device.trim());
						if (licenseCamera != null)
						{
							DimViolationAgency agency = forbidAreaDao.getViolationAgency(licenseCamera.getAgencyId());
							if (agency != null)
							{
								map.put("agencyName", agency.getAgencyName());
								map.put("installationPosition", device.trim());
							}
						}

					}
					list.add(map);
				}
				passLineVo.setDeivceList(list);
				passLineVo.setLineId(dimPassLine.getLineId());
				passLineVo.setLineName(dimPassLine.getLineName());
				passLineVo.setIntList(dimPassLine.getIntList());
				passLineVo.setAreaId(dimPassLine.getAreaId());
				passLineVo.setAuthId(dimPassLine.getAuthId());
				passLineVo.setDescription(dimPassLine.getDescription());
				passLineVo.setTempCardFlag(dimPassLine.getTempCardFlag());
				passLineVoList.add(passLineVo);
			}
		}
		return passLineVoList;
	}
	
	/**
	 * 电警、卡口map
	 */
	private static Map<String, String> eleMap;
	
	
	static
	{
		try
		{
			// 电警
			List<DimElectricPoliceCamera> camera = ServiceUtil.getService(DimForbidAreaDao.class).getCameraById();
			eleMap = new HashMap<String, String>();
			for (DimElectricPoliceCamera dimElectricPoliceCamera : camera)
			{
				eleMap.put(dimElectricPoliceCamera.getElectricPoliceNumber(), dimElectricPoliceCamera.getInstallationPosition());
			}
			// 卡口
			List<DimLicenseCamera> licenseCamera = ServiceUtil.getService(DimForbidAreaDao.class).getlicenseCameraById();
			for (DimLicenseCamera dimLicenseCamera : licenseCamera)
			{
				eleMap.put(dimLicenseCamera.getLpCameraNumber(), dimLicenseCamera.getInstallationPosition());
			}
		} catch (JsztException e)
		{
			LogUtil.error(e, "getCameraById fail!");
		}
	}
	
	/**
	 * 获取地点位置 <功能详细描述>
	 * 
	 * @param deviceLists
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	private String getPositions(String deviceLists) throws JsztException
	{
		String[] devices = StringUtils.split(deviceLists, ",");
		Set<String> set = new HashSet<String>();

		for (String device : devices)
		{
			set.add(eleMap.get(device));
		}
		String points = set.toString();
		return StringUtils.substring(points, 1, points.length() - 1).trim();
	}

	@Override
	public List<DimPassLine> getPassLineByWeChat() throws JsztException {
		return ServiceUtil.getService(DimPassLineDao.class).getPassLineByWeChat();
	}

	@Override
	public void updatePassLine(PassLineReq req) throws JsztException {

		if (StringUtils.isNotEmpty(req.getLineName()))
		{
			//根据修改的线路ID获取原先的线路信息
			PassLineInfoReq passLineReq = new PassLineInfoReq();
			passLineReq.setLineId(req.getLineId());
			DimPassLineDao dimPassLineDao = ServiceUtil.getService(DimPassLineDao.class);
			List<DimPassLine> passLineList = dimPassLineDao.getPassLineByLineName(passLineReq);
			if(CollectionUtils.isNotEmpty(passLineList)){
				DimPassLine oldPassLine = passLineList.get(0);
				//找出修改后的通行路线中新增的auth_id
				List<String> newAuthIdList = new ArrayList<>();
				String oldAuthId = ","+oldPassLine.getAuthId()+",";
				String [] newAuthIdArray = req.getAuthId().split(",");
				for (int i=0; i<newAuthIdArray.length; i++) {
					String newAuthId = ","+newAuthIdArray[i]+",";
					if(!oldAuthId.contains(newAuthId)){
						//修改前的auth_id里面没有包括的newAuthId就是新增的id
						newAuthIdList.add(newAuthIdArray[i]);
					}
				}
				//找出修改后的通行路线中减少的auth_id
				List<String> reduceAuthIdList = new ArrayList<>();
				String newAuthIdStr = ","+req.getAuthId()+",";
				String [] oldAuthIdArray = oldPassLine.getAuthId().split(",");
				for(int i=0; i<oldAuthIdArray.length; i++){
					String oldId = ","+oldAuthIdArray[i]+",";
					if(!newAuthIdStr.contains(oldId)){
						//修改后的auth_id里面没有包括的oldId就是减少的id
						reduceAuthIdList.add(oldAuthIdArray[i]);
					}
				}
				DimLongPassCardDao dimLongPassCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
				//对于新增的auth_id，需要找到其无权限访问的线路ID：noAuthLineIds，然后更新通行证表中非noAuthLineIds、且AUTH_ID字段不包括当前新增的auth_id的记录中的AUTH_ID=AUTH_ID+新增的auth_id
				if(CollectionUtils.isNotEmpty(newAuthIdList)){
					for (String account : newAuthIdList) {
						List<Integer> noAuthLineIds = dimPassLineDao.getNoAuthPassLineByAccountName(req.getLineId(), account);
						dimLongPassCardDao.updateLongPassCardAuthId(noAuthLineIds, account);
					}
				}
				//对于减少的auth_id，需要更新通行证表中LINE_ID字段包含当前修改的线路ID的记录的AUTH_ID的值，AUTH_ID.replace(减少的auth_id, '')
				if(CollectionUtils.isNotEmpty(reduceAuthIdList)){
					for (String reduceAccount : reduceAuthIdList) {
						dimLongPassCardDao.updateLongPassCardByReduceAuthId(req.getLineId(), reduceAccount);
					}
				}
				DimPassLine passLine = null;
				if(req.getAreaId() != null){
					//禁区ID不为null，则获取对应禁区的所有设备ID，覆盖选择的设备ID
					List<DimForbidArea> forbidAreas = ServiceUtil.getService(DimForbidAreaDao.class).getForbidAreaById(req.getAreaId());
					StringBuilder deviceSB = new StringBuilder();
					for (DimForbidArea dimForbidArea : forbidAreas) {
						deviceSB.append(dimForbidArea.getDeviceList()).append(",");
					}
					passLine = new DimPassLine();
					passLine.setLineId(req.getLineId());
					passLine.setLineName(req.getLineName());
					passLine.setIntList("-1");
					passLine.setDeviceList(StringUtils.substring(deviceSB.toString(), 0, deviceSB.length() - 1));
					passLine.setAreaId(req.getAreaId());
					passLine.setAuthId(req.getAuthId());
					passLine.setTempCardFlag(req.getTempCardFlag());
					passLine.setDescription(req.getDescription());
					dimPassLineDao.updatePassLine(passLine);
					
				} else{
					//禁区ID为null，则使用选择的设备ID
					List<String> eleList = ServiceUtil.getService(CommonServiceImpl.class).getElectricByposition(req.getDevices());
					if (CollectionUtils.isNotEmpty(eleList))
					{
						StringBuilder sb = new StringBuilder();
						for (String string : eleList)
						{
							sb.append(string).append(",");
						}
						passLine = new DimPassLine();
						passLine.setLineId(req.getLineId());
						passLine.setLineName(req.getLineName());
						passLine.setIntList("-1");
						passLine.setDeviceList(StringUtils.substring(sb.toString(), 0, sb.length() - 1));
						passLine.setAreaId(req.getAreaId());
						passLine.setAuthId(req.getAuthId());
						passLine.setTempCardFlag(req.getTempCardFlag());
						passLine.setDescription(req.getDescription());
						dimPassLineDao.updatePassLine(passLine);
					}
				}
				ServiceUtil.getService(CommonServiceImpl.class).refreshPassLine();
			}
			
		} else
		{
			Assert.notEmpty(req.getLineName(), ErrorCode.PARAM_ERROR, "param [lineName] can not be null!");
		}
		
	}

}
