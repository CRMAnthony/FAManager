/*
 * 文 件 名:  DimForbidAreaDao.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimViolationAgency;

/**
 * 禁行区域数据库配置 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月21日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface DimForbidAreaDao
{
	/**
	 * 新增禁行区域 <功能详细描述>
	 * 
	 * @param areaName
	 * @param intList
	 * @param deviceList
	 * @param areaPoints
	 * @see [类、类#方法、类#成员]
	 */
	void addForbidArea(DimForbidArea area);

	/**
     * 获得禁行区域信息 <功能详细描述>
     * 
     * @param areaId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidArea> getForbidArea(int areaId);
    
    /**
     * 获得禁行区域信息 <功能详细描述>
     * 
     * @param areaId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidArea> getForbidArea(int areaId,String areaType);
    
    /**
     * 根据禁区ID获得禁行区域信息 <功能详细描述>
     * 
     * @param areaId
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidArea> getForbidAreaById(String areaId);

    /**
     * 删除禁行区域
     * <功能详细描述>
     * @param id
     * @see [类、类#方法、类#成员]
     */
    void delForbidArea(int [] id);
    
    /**
	 * 根据部门编号获取部门名称
	 * <功能详细描述>
	 * @param agencyId
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimViolationAgency getViolationAgency(String agencyId);
    
	/**
	 * 根据地点查找部门id(电警)
	 * <功能详细描述>
	 * @param position
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimElectricPoliceCamera getCamera (String position)throws JsztException;
	
	/**
	 * 根据地点查找部门id（卡口）
	 * <功能详细描述>
	 * @param position
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimLicenseCamera getLicenseCamera(String position) throws JsztException;
	
	
	/**
	 * 根据id找地点(电警)
	 * <功能详细描述>
	 * @param cameraId
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimElectricPoliceCamera> getCameraById () throws JsztException;
	
	/**
	 * 根据id找地点（卡口）
	 * <功能详细描述>
	 * @param camreaId
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimLicenseCamera> getlicenseCameraById () throws JsztException;
	
	/**
	 * 更新禁行区域信息
	 * <功能详细描述>
	 * @param area
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	void updateDimForbidArea (DimForbidArea area) throws JsztException;
	
	
}
