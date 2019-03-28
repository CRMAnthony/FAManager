/*
 * 文 件 名:  ForbidAreaServiceImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
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

import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.LogUtil;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.CommonDao;
import com.jszt.dao.DimForbidAreaDao;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimViolationAgency;
import com.jszt.service.CommonService;
import com.jszt.service.ForbidAreaService;
import com.jszt.vo.CameraPolice;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.ForbidAreaInfoReq;
import com.jszt.vo.ForbidAreaReq;
import com.jszt.vo.ForbidAreaVo;

/**
 * 禁行区域业务接口 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidAreaServiceImpl implements ForbidAreaService
{

	@Override
	public void addForbidArea(ForbidAreaReq req) throws JsztException
	{
		DimForbidArea area = null;
		if (req.getDevices() == "")
		{
			area = new DimForbidArea();
			area.setAreaName(req.getAreaName());
			area.setAreaType(req.getAreaType());
			area.setIntList("-1");
			area.setDeviceList("-1");
			area.setAreaPoints("-1");
			area.setDescription(req.getDescription());
			area.setAccountList(req.getAccountList());
			area.setDevices(req.getDevices());
			ServiceUtil.getService(DimForbidAreaDao.class).addForbidArea(area);
		} else
		{
			area = new DimForbidArea();
			List<String> eleList = ServiceUtil.getService(CommonServiceImpl.class).getElectricByposition(req.getDevices());
			if (CollectionUtils.isNotEmpty(eleList))
			{

				StringBuilder sb = new StringBuilder();
				for (String string : eleList)
				{
					sb.append(string).append(",");
				}
				area.setAreaName(req.getAreaName());
				area.setAreaType(req.getAreaType());
				area.setIntList("-1");
				area.setDeviceList((StringUtils.substring(sb.toString(), 0, sb.length() - 1)));
				area.setDescription(req.getDescription());
				area.setAccountList(req.getAccountList());
				area.setAreaPoints("-1");
				ServiceUtil.getService(DimForbidAreaDao.class).addForbidArea(area);
			}
		}
		// 更新禁行区域缓存数据
		ServiceUtil.getService(CommonService.class).updateForbidAreaInfo();

	}

	@Override
	public List<DimForbidArea> getForbidAreaId() throws JsztException
	{
		return ServiceUtil.getService(DimForbidAreaDao.class).getForbidArea(0);
	}

	@Override
	public List<ForbidAreaVo> getForbidArea(ForbidAreaInfoReq req) throws JsztException
	{

		List<DimForbidArea> forbidAreas = ServiceUtil.getService(DimForbidAreaDao.class).getForbidArea(req.getAreaId(), req.getAreaType());
		List<ForbidAreaVo> forbidAreaVos = toVoList(forbidAreas);
		Map<Integer, ForbidAreaVo> dimMap = new HashMap<Integer, ForbidAreaVo>(forbidAreas.size());
		for (ForbidAreaVo dimForbidArea : forbidAreaVos)
		{
			ForbidAreaVo dimForbidArea2 = dimMap.get(dimForbidArea.getAreaId());
			// 集合里没有
			if (dimForbidArea2 == null)
			{
				dimMap.put(dimForbidArea.getAreaId(), dimForbidArea);
			}
			// 集合里有
			else
			{
				dimForbidArea.setDeviceList(dimForbidArea2.getDeviceList() + "," + dimForbidArea.getDeviceList());
				dimMap.put(dimForbidArea.getAreaId(), dimForbidArea);
			}
		}

		return new ArrayList<ForbidAreaVo>(dimMap.values());
	}

	/**
	 * PO转VO <功能详细描述>
	 * 
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	private List<ForbidAreaVo> toVoList(List<DimForbidArea> dimForbidAreas) throws JsztException
	{
		List<ForbidAreaVo> forbidAreaVos = new ArrayList<ForbidAreaVo>();
		if (CollectionUtils.isNotEmpty(dimForbidAreas))
		{
			DimForbidAreaDao forbidAreaDao =  ServiceUtil.getService(DimForbidAreaDao.class);
			for (DimForbidArea dimForbidArea : dimForbidAreas)
			{

				ForbidAreaVo forbidAreaVo = new ForbidAreaVo();
				// 地点
				String[] devices = StringUtils.split(getPositions(dimForbidArea.getDeviceList()), ",");
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
				forbidAreaVo.setDeivceList(list);
				forbidAreaVo.setAreaId(dimForbidArea.getAreaId());
				forbidAreaVo.setAreaName(dimForbidArea.getAreaName());
				forbidAreaVo.setAreaType(dimForbidArea.getAreaType());
				forbidAreaVo.setIntList(dimForbidArea.getIntList());
				forbidAreaVo.setAccountList(dimForbidArea.getAccountList());
				forbidAreaVo.setAreaPoints(dimForbidArea.getAreaPoints());
				forbidAreaVo.setDescription(dimForbidArea.getDescription());
				forbidAreaVos.add(forbidAreaVo);
			}
		}
		return forbidAreaVos;
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

	/**
	 * @param req
	 */
	@Override
	public void delForbidArea(DelIdReq req)
	{
		// String [] lineId =req.getId().split(",");
		ServiceUtil.getService(DimForbidAreaDao.class).delForbidArea(req.getId());
		// 更新通行线路缓存
		ServiceUtil.getService(CommonService.class).updateForbidAreaInfo();
	}

	/**
	 * 更新禁行区域信息
	 * @param req
	 * @throws JsztException
	 */
	@Override
	public void updateForbidArea(ForbidAreaReq req) throws JsztException
	{
		DimForbidArea area = null;
		if (req.getDevices() == "")
		{
			area = new DimForbidArea();
			area.setAreaName(req.getAreaName());
			area.setAreaType(req.getAreaType());
			area.setIntList("-1");
			area.setDeviceList("-1");
			area.setAreaPoints("-1");
			area.setDescription(req.getDescription());
			area.setAccountList(req.getAccountList());
			area.setDevices(req.getDevices());
			area.setAreaId(req.getAreaId());
			ServiceUtil.getService(DimForbidAreaDao.class).updateDimForbidArea(area);
		} else
		{
			area = new DimForbidArea();
			List<String> eleList = ServiceUtil.getService(CommonServiceImpl.class).getElectricByposition(req.getDevices());
			if (CollectionUtils.isNotEmpty(eleList))
			{

				StringBuilder sb = new StringBuilder();
				for (String string : eleList)
				{
					sb.append(string).append(",");
				}
				area.setAreaId(req.getAreaId());
				area.setAreaName(req.getAreaName());
				area.setAreaType(req.getAreaType());
				area.setIntList("-1");
				area.setDeviceList((StringUtils.substring(sb.toString(), 0, sb.length() - 1)));
				area.setDescription(req.getDescription());
				area.setAccountList(req.getAccountList());
				area.setAreaPoints("-1");
				ServiceUtil.getService(DimForbidAreaDao.class).updateDimForbidArea(area);
			}
		}
		// 更新禁行区域缓存数据
		ServiceUtil.getService(CommonService.class).updateForbidAreaInfo();
	}

	/**
	 * 获取设备信息
	 * @return
	 * @throws JsztException
	 */
	@Override
	public Map<String, Set<String>> getMonitorDevices() throws JsztException
	{
		List<DimElectricPoliceCamera> elset = ServiceUtil.getService(CommonDao.class).getCameras();
		List<DimLicenseCamera> licen = ServiceUtil.getService(CommonDao.class).getLicenseCamera();

		Map<String, Set<String>> cameras = new HashMap<String, Set<String>>();

		Map<String, String> agencyMap =  ServiceUtil.getService(CommonDao.class).getAgency();
		// 封装电子警察
		for (DimElectricPoliceCamera temp : elset)
		{
			CameraPolice police = new CameraPolice();
			police.setPoliceId(temp.getElectricPoliceId());
			String agencyName = agencyMap.get(temp.getAgencyId());
			if (StringUtils.isEmpty(agencyName))
			{
				continue;
			}
			police.setAgencyName(agencyName);
			police.setType(1);
			police.setInstallationPosition(temp.getInstallationPosition());

			Set<String> agency = cameras.get(agencyName);
			if (CollectionUtils.isEmpty(agency))
			{
				agency = new HashSet<String>();
				cameras.put(agencyName, agency);
			}
			agency.add(temp.getInstallationPosition());
		}

		for (DimLicenseCamera dimLicenseCamera : licen)
		{
			CameraPolice police = new CameraPolice();
			police.setPoliceId(dimLicenseCamera.getLpCameraid());
			String agencyName = agencyMap.get(dimLicenseCamera.getAgencyId());
			if (StringUtils.isEmpty(agencyName))
			{
				continue;
			}
			police.setAgencyName(agencyName);
			police.setType(1);
			police.setInstallationPosition(dimLicenseCamera.getInstallationPosition());

			Set<String> agency = cameras.get(agencyName);
			if (CollectionUtils.isEmpty(agency))
			{
				agency = new HashSet<String>();
				cameras.put(agencyName, agency);
			}
			agency.add(dimLicenseCamera.getInstallationPosition());
		}

		return cameras;
	}

}
