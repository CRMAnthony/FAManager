package com.jszt.dao;

import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;

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

/**
 * 公共接口部分的dao
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CommonDao
{
    /**
     * 获取车辆类型
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPlateType> getPlateType();
    
    /**
     * 长期通行证常用通行时段（全时段、避高峰）
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimSysConfig> getLongPeriod();
    
    /**
     * 获取违法次数
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    DimSysConfig getVioTimes();
    
    /**
     * 获取所有设备
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimElectricPoliceCamera> getCameras();
    
    /**
     * 图片合成用
     * @return
     */
    DimElectricPoliceCamera getCameras1(String number);
    
    /**
     * 获取卡口设备
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimLicenseCamera> getLicenseCamera();
    
    /**
     * 图片合成用
     * @return
     */
    DimLicenseCamera getLicenseCamera1(String number);
    
    
    /**
     * 获取所有交叉口信息
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimIntersection> getIntersections();
    
    /**
     * 更新避高峰的时段配置
     * <一句话功能简述>
     * <功能详细描述>
     * @param period
     * @see [类、类#方法、类#成员]
     */
    void updateAvoidPeak(String period);
    
    /**
     * 更新违法次数的配置
     * <一句话功能简述>
     * <功能详细描述>
     * @param times
     * @see [类、类#方法、类#成员]
     */
    void updateVioTimes(String times);
    
    /**
     * 获取违法车辆类型
     * @return 车辆类型
     * @see [类、类#方法、类#成员]
     */
    List<DimViolationVehicleType> getViolationVehicleType();
    
    /**
     * 根据账户编号获取该账户的信息
     * 
     * @param accountId
     * @return
     * @throws ServiceException 
     * @see [类、类#方法、类#成员]
     */
    DimSysAccount getSysAccount(String accountId);
    
    /**
     * 根据采集机关ID查询名称
     * <功能详细描述>
     * @param agencyId
     * @return
     * @see [类、类#方法、类#成员]
     */
    Map<String,String> getAgency();
    
    /**
     * 根据编号和类型获取设备纬度、经度
     * <功能详细描述>
     * @param cameraID
     * @param prefix
     * @return
     * @see [类、类#方法、类#成员]
     */
    LPCameraVo getCameraByID(final String cameraID,final String prefix);


    /**
     * 查询短信数量
     * @return
     * @see [类、类#方法、类#成员]
     */

    int  getNoticeCount();
    
    /**
     * 新增短信通知
     * <一句话功能简述>
     * <功能详细描述>
     * @param vo
     * @see [类、类#方法、类#成员]
     */
    void addNotice(NoticeVo vo);
    
    /**
     * 更新短信id
     * @see [类、类#方法、类#成员]
     */
    void updateID();
    
    /**
     * 查询当日申请短信数量
     * @return
     * @see [类、类#方法、类#成员]
     */

    DimNoticeCount  getTodayNoticeCount(int dateKey);
    
    /**
     * 插入当日申请短信数量
     * @return
     * @see [类、类#方法、类#成员]
     */

    void  addNoticeCount(DimNoticeCount noticeCount);
    
    /**
     * 更新当日申请短信数量
     * @return
     * @see [类、类#方法、类#成员]
     */

    void  updateNoticeCount(DimNoticeCount noticeCount);
    
    /**
     * 更新当日申请短信数量
     * @return
     * @see [类、类#方法、类#成员]
     */

    void  deleteNoticeCount(int dateKey);
    
    Param  getParam(String key);
}
