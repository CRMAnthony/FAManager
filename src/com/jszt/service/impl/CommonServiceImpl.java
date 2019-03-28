package com.jszt.service.impl;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.holders.StringHolder;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.CommonDao;
import com.jszt.dao.DimForbidAreaDao;
import com.jszt.dao.DimForbidLineDao;
import com.jszt.dao.DimLongPassCardDao;
import com.jszt.dao.DimPassLineDao;
import com.jszt.dao.DimTempPassCardDao;
import com.jszt.dao.DimUnstandardVehicleDao;
import com.jszt.dao.SpecialVehicleDao;
import com.jszt.domain.DimElectricPoliceCamera;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimForbidLine;
import com.jszt.domain.DimIntersection;
import com.jszt.domain.DimLicenseCamera;
import com.jszt.domain.DimLongPassCard;
import com.jszt.domain.DimNoticeCount;
import com.jszt.domain.DimPassLine;
import com.jszt.domain.DimPlateType;
import com.jszt.domain.DimSysConfig;
import com.jszt.domain.DimTempPassCard;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.domain.DimViolationVehicleType;
import com.jszt.domain.Param;
import com.jszt.getcarphone.WebService;
import com.jszt.getcarphone.WebServiceLocator;
import com.jszt.service.CommonService;
import com.jszt.vo.CameraPolice;
import com.jszt.vo.NoticePassCard;
import com.jszt.vo.NoticeReq;
import com.jszt.vo.NoticeVo;
import com.jszt.vo.TimeSlot;
import com.jszt.vo.UnstandardVehicleReq;
import com.soap57ProviderPort.Soap57ProviderBindingStub;

/**
 * 公共服务 <一句话功能简述> <功能详细描述>
 * 
 * @author ling
 * @version [版本号, 2015-5-18]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class CommonServiceImpl implements CommonService
{
    private static Logger logger = Logger.getLogger(CommonServiceImpl.class);
    /**
     * 短信id
     */
    private static int i=1;
    
    /**
     * 车辆类型信息缓存
     */
    private final static List<DimPlateType> plateTypeList = new ArrayList<DimPlateType>();
    
    /**
     * 车辆类型信息缓存锁
     */
    private final static ReadWriteLock plateTypeLock = new ReentrantReadWriteLock();
    
    /**
     * 线路信息缓存
     */
    private final static List<DimPassLine> passLineList = new ArrayList<DimPassLine>();
    
    /**
     * 线路信息缓存锁
     */
    private final static ReadWriteLock passLineLock = new ReentrantReadWriteLock();
    
    /**
     * 设备信息缓存
     */
    private final static Map<String, List<String>> deviceMap = new HashMap<String, List<String>>();
    
    /**
     * 设备信息缓存锁
     */
    private final static ReadWriteLock deviceLock = new ReentrantReadWriteLock();
    
    /**
     * 电警信息缓存
     */
    private final static Map<String, List<String>> electricPoliceMap = new HashMap<String, List<String>>();
    
    /**
     * 电警信息缓存锁
     */
    private final static ReadWriteLock electricPoliceLock = new ReentrantReadWriteLock();
    
    /**
     * 长期通行证的通行时段缓存
     */
    private final static List<DimSysConfig> longPeriodList = new ArrayList<DimSysConfig>();
    
    /**
     * 长期通行证的通行时段缓存锁
     */
    private final static ReadWriteLock longPeriodLock = new ReentrantReadWriteLock();
    
    /**
     * 交叉口信息
     */
    private final static Map<String, String> intersectionMap = new HashMap<String, String>();
    
    private final static ReadWriteLock intersectionLock = new ReentrantReadWriteLock();
    
    /**
     * 违法次数缓存
     */
    private final static DimSysConfig vioTimes = new DimSysConfig();
    
    private final static ReadWriteLock vioTimesLock = new ReentrantReadWriteLock();
    
    /**
     * 禁行区域信息
     */
    private static List<DimForbidArea> forbidAreaList = new ArrayList<DimForbidArea>();
    
    private final static ReadWriteLock forbidAreaLock = new ReentrantReadWriteLock();
    
    /**
     * 车牌信息
     */
    private static List<DimViolationVehicleType> violationVehicleTypeList = new ArrayList<DimViolationVehicleType>();
    
    private final static ReadWriteLock violationVehicleTypeLock = new ReentrantReadWriteLock();
    
    /**
     * 车辆黑白名单
     */
    private static List<DimViolationSpecialVehicle> specialVehList = new ArrayList<DimViolationSpecialVehicle>();
    
    private final static ReadWriteLock specialVehLock = new ReentrantReadWriteLock();
    
    /**
     * 黄标车信息
     */
    private static List<DimUnstandardVehicle> unstandardVehList = new ArrayList<DimUnstandardVehicle>();
    
    private final static ReadWriteLock unstandardVehLock = new ReentrantReadWriteLock();
    
    /**
     * 短信内容缓存
     */
    private final static List<NoticeVo> noticeList = new ArrayList<NoticeVo>();
    
    /**
     * 短信缓存锁
     */
    private final static ReadWriteLock noticeLock = new ReentrantReadWriteLock();
    
    private static final int TEMP_CARD_TYPE = 1;
    
    private static final int LONG_CARD_TYPE = 2;
    
    private static final int noticeSize = 30;
    
    
    /**
     * 同步车辆类型信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimPlateType> syncPlateType()
        throws Exception
    {
        List<DimPlateType> retList = null;
        
        if (CollectionUtils.isEmpty(plateTypeList))
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            plateTypeLock.writeLock().lock();
            List<DimPlateType> dbList = commonDao.getPlateType();
            plateTypeList.clear();
            plateTypeList.addAll(dbList);
            retList = new ArrayList<DimPlateType>(plateTypeList);
            plateTypeLock.writeLock().unlock();
        }
        else
        {
            plateTypeLock.readLock().lock();
            retList = new ArrayList<DimPlateType>(plateTypeList);
            plateTypeLock.readLock().unlock();
        }
        
        return retList;
    }
    
    /**
     * 同步车牌信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimViolationVehicleType> syncViolationVehicleType()
        throws Exception
    {
        List<DimViolationVehicleType> retList = null;
        
        if (CollectionUtils.isEmpty(violationVehicleTypeList))
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            violationVehicleTypeLock.writeLock().lock();
            List<DimViolationVehicleType> dbList = commonDao.getViolationVehicleType();
            violationVehicleTypeList.clear();
            violationVehicleTypeList.addAll(dbList);
            retList = new ArrayList<DimViolationVehicleType>(violationVehicleTypeList);
            violationVehicleTypeLock.writeLock().unlock();
        }
        else
        {
            violationVehicleTypeLock.readLock().lock();
            retList = new ArrayList<DimViolationVehicleType>(violationVehicleTypeList);
            violationVehicleTypeLock.readLock().unlock();
        }
        
        return retList;
    }
    
    /**
     * 同步通行线路信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimPassLine> syncPassLine()
        throws Exception
    {
        List<DimPassLine> retList = null;
        
        if (CollectionUtils.isEmpty(passLineList))
        {
            DimPassLineDao passLineDao = ServiceUtil.getService(DimPassLineDao.class);
            passLineLock.writeLock().lock();
            List<DimPassLine> dbList = passLineDao.getPassLine();
            passLineList.clear();
            passLineList.addAll(dbList);
            retList = new ArrayList<DimPassLine>(passLineList);
            passLineLock.writeLock().unlock();
        }
        else
        {
            passLineLock.readLock().lock();
            retList = new ArrayList<DimPassLine>(passLineList);
            passLineLock.readLock().unlock();
        }
        
        return retList;
    }
    
    /**
     * 同步设备信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private Map<String, List<String>> syncDevice()
        throws Exception
    {
        Map<String, List<String>> retMap = null;
        
        if (deviceMap.isEmpty())
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            deviceLock.writeLock().lock();
            List<DimElectricPoliceCamera> dbList = commonDao.getCameras();
            List<DimLicenseCamera> licenseCamera = commonDao.getLicenseCamera();
            deviceMap.clear();
            for (DimElectricPoliceCamera camera : dbList)
            {
                String devId = camera.getElectricPoliceNumber();
                String position = camera.getInstallationPosition();
                List<String> devList = deviceMap.get(position);
                if (devList == null)
                {
                    devList = new ArrayList<String>();
                }
                devList.add(devId);
                deviceMap.put(position, devList);
            }
            for (DimLicenseCamera licenseCameras : licenseCamera)
            {
                String devId = licenseCameras.getLpCameraNumber();
                String position = licenseCameras.getInstallationPosition();
                List<String> devList = deviceMap.get(position);
                if (devList == null)
                {
                    devList = new ArrayList<String>();
                }
                devList.add(devId);
                deviceMap.put(position, devList);
            }
            retMap = new HashMap<String, List<String>>(deviceMap);
            deviceLock.writeLock().unlock();
            
        }
        else
        {
            deviceLock.readLock().lock();
            retMap = new HashMap<String, List<String>>(deviceMap);
            deviceLock.readLock().unlock();
        }
        
        return retMap;
    }
    
    private Map<String, List<String>> syncElectricPolice()
    {
        Map<String, List<String>> retMap = null;
        if (electricPoliceMap.isEmpty())
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            electricPoliceLock.writeLock().lock();
            List<DimElectricPoliceCamera> dbList = commonDao.getCameras();
            electricPoliceMap.clear();
            for (DimElectricPoliceCamera camera : dbList)
            {
                String devId = camera.getElectricPoliceNumber();
                String intIds = camera.getIntersectionid();
                List<String> devList = electricPoliceMap.get(intIds);
                if (devList == null)
                {
                    devList = new ArrayList<String>();
                }
                devList.add(devId);
                electricPoliceMap.put(intIds, devList);
            }
            retMap = new HashMap<String, List<String>>(electricPoliceMap);
            electricPoliceLock.writeLock().unlock();
            
        }
        else
        {
            electricPoliceLock.readLock().lock();
            retMap = new HashMap<String, List<String>>(electricPoliceMap);
            electricPoliceLock.readLock().unlock();
        }
        return retMap;
    }
    
    /**
     * 同步交叉口信息 <一句话功能简述> <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private Map<String, String> syncIntersection()
        throws Exception
    {
        Map<String, String> retMap = null;
        
        if (intersectionMap.isEmpty())
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            intersectionLock.writeLock().lock();
            List<DimIntersection> intersectionList = commonDao.getIntersections();
            
            intersectionMap.clear();
            for (DimIntersection intersection : intersectionList)
            {
                String intId = intersection.getIntersectionId();
                String intName = intersection.getIntersectionName();
                intersectionMap.put(intId, intName);
            }
            retMap = new HashMap<String, String>(intersectionMap);
            
            intersectionLock.writeLock().unlock();
        }
        else
        {
            intersectionLock.readLock().lock();
            retMap = new HashMap<String, String>(intersectionMap);
            intersectionLock.readLock().unlock();
        }
        
        return retMap;
    }
    
    /**
     * 同步长期通行证的通行时段信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimSysConfig> syncLongPeriod()
        throws Exception
    {
        List<DimSysConfig> retList = null;
        
        if (CollectionUtils.isEmpty(longPeriodList))
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            longPeriodLock.writeLock().lock();
            List<DimSysConfig> dbList = commonDao.getLongPeriod();
            longPeriodList.clear();
            longPeriodList.addAll(dbList);
            retList = new ArrayList<DimSysConfig>(longPeriodList);
            longPeriodLock.writeLock().unlock();
        }
        else
        {
            longPeriodLock.readLock().lock();
            retList = new ArrayList<DimSysConfig>(longPeriodList);
            longPeriodLock.readLock().unlock();
        }
        
        return retList;
    }
    
    /**
     * 同步违法次数 <一句话功能简述> <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private DimSysConfig sysVioTimes()
        throws Exception
    {
        DimSysConfig retVioTimes = null;
        
        if (StringUtils.isBlank(vioTimes.getConfigKey()))
        {
            CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
            DimSysConfig dbConfig = commonDao.getVioTimes();
            if (dbConfig != null)
            {
                vioTimesLock.writeLock().lock();
                vioTimes.setDimSysConfig(dbConfig);
                vioTimesLock.writeLock().unlock();
            }
            
        }
        else
        {
            vioTimesLock.readLock().lock();
            retVioTimes = new DimSysConfig(vioTimes);
            vioTimesLock.readLock().unlock();
        }
        
        return retVioTimes;
    }
    
    /**
     * 同步禁行区域信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimForbidArea> syncForbidArea()
        throws Exception
    {
        List<DimForbidArea> retList = null;
        
        if (CollectionUtils.isEmpty(forbidAreaList))
        {
            DimForbidAreaDao forbidAreaDao = ServiceUtil.getService(DimForbidAreaDao.class);
            forbidAreaLock.writeLock().lock();
            List<DimForbidArea> dbList = forbidAreaDao.getForbidArea(0, null);
            forbidAreaList.clear();
            forbidAreaList.addAll(dbList);
            retList = new ArrayList<DimForbidArea>(forbidAreaList);
            forbidAreaLock.writeLock().unlock();
        }
        else
        {
            forbidAreaLock.readLock().lock();
            retList = new ArrayList<DimForbidArea>(forbidAreaList);
            forbidAreaLock.readLock().unlock();
        }
        
        return retList;
    }
    
    /**
     * 同步车辆（货车）黑白名单信息 <功能详细描述>
     * 
     * @return
     * @throws Exception
     * @see [类、类#方法、类#成员]
     */
    private List<DimViolationSpecialVehicle> syncSpecialVeh()
        throws Exception
    {
        List<DimViolationSpecialVehicle> retList = null;
        
        if (CollectionUtils.isEmpty(specialVehList))
        {
            SpecialVehicleDao specialVehicleDao = ServiceUtil.getService(SpecialVehicleDao.class);
            specialVehLock.writeLock().lock();
            List<DimViolationSpecialVehicle> dbList = specialVehicleDao.getSpecialTruck();
            specialVehList.clear();
            specialVehList.addAll(dbList);
            retList = new ArrayList<DimViolationSpecialVehicle>(specialVehList);
            specialVehLock.writeLock().unlock();
        }
        else
        {
            specialVehLock.readLock().lock();
            retList = new ArrayList<DimViolationSpecialVehicle>(specialVehList);
            specialVehLock.readLock().unlock();
        }
        
        return retList;
    }
    
    private List<DimUnstandardVehicle> syncUnstandardVeh()
        throws Exception
    {
        List<DimUnstandardVehicle> retList = null;
        
        if (CollectionUtils.isEmpty(unstandardVehList))
        {
            DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
            unstandardVehLock.writeLock().lock();
            List<DimUnstandardVehicle> dbList = unstandardVehicleDao.getUnstandardVehicle(new UnstandardVehicleReq());
            unstandardVehList.clear();
            unstandardVehList.addAll(dbList);
            retList = new ArrayList<DimUnstandardVehicle>(unstandardVehList);
            unstandardVehLock.writeLock().unlock();
        }
        else
        {
            unstandardVehLock.readLock().lock();
            retList = new ArrayList<DimUnstandardVehicle>(unstandardVehList);
            unstandardVehLock.readLock().unlock();
        }
        
        return retList;
    }
    
    @Override
    public List<DimPlateType> getPlateType()
    {
        List<DimPlateType> retList = null;
        try
        {
            retList = syncPlateType();
        }
        catch (Exception e1)
        {
            logger.error("getPlateType error", e1);
        }
        return retList;
    }
    
    @Override
    public List<DimPassLine> getPassLine()
    {
        List<DimPassLine> retList = null;
        try
        {
            retList = syncPassLine();
        }
        catch (Exception e1)
        {
            logger.error("getPassLine error", e1);
        }
        return retList;
    }
    
    @Override
    public List<DimSysConfig> getLongPeriod()
    {
        List<DimSysConfig> retList = null;
        try
        {
            retList = syncLongPeriod();
        }
        catch (Exception e)
        {
            logger.error("getLongPeriod error", e);
        }
        return retList;
    }
    
    @Override
    public List<String> getElectricPoliceByLine(String points)
    {
        List<String> retList = new ArrayList<String>();
        
        Map<String, List<String>> devMap = null;
        try
        {
            devMap = syncElectricPolice();
        }
        catch (Exception e)
        {
            logger.error("getElectricPoliceByLine error", e);
        }
        if (devMap != null && !devMap.isEmpty())
        {
            String[] pointArray = StringUtils.split(points, ",");
            for (String point : pointArray)
            {
                List<String> devList = devMap.get(point);
                if (CollectionUtils.isNotEmpty(devList))
                {
                    for (String dev : devList)
                    {
                        retList.add(dev);
                    }
                }
                
            }
        }
        return retList;
    }
    
    @Override
    public List<String> getElectricPoliceByIntId(String intId)
    {
        Map<String, List<String>> devMap = null;
        try
        {
            devMap = syncDevice();
        }
        catch (Exception e)
        {
            
            logger.error("getElectricPoliceByIntId error", e);
        }
        if (devMap != null && !devMap.isEmpty())
        {
            return devMap.get(intId);
        }
        return null;
    }
    
    @Override
    public void refreshData()
    {
        CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
        
        // 车辆类型
//        plateTypeLock.writeLock().lock();
        List<DimPlateType> dbList = commonDao.getPlateType();
        plateTypeList.clear();
        plateTypeList.addAll(dbList);
//        plateTypeLock.writeLock().unlock();
        
        // 设备信息
        deviceLock.writeLock().lock();
        List<DimElectricPoliceCamera> devDbList = commonDao.getCameras();
        deviceMap.clear();
        for (DimElectricPoliceCamera camera : devDbList)
        {
            String devId = camera.getElectricPoliceNumber();
            String intId = camera.getIntersectionid();
            List<String> devList = deviceMap.get(intId);
            if (devList == null)
            {
                devList = new ArrayList<String>();
            }
            devList.add(devId);
            deviceMap.put(intId, devList);
        }
        deviceLock.writeLock().unlock();
        
        // 通行时段
        longPeriodLock.writeLock().lock();
        List<DimSysConfig> periodDbList = commonDao.getLongPeriod();
        longPeriodList.clear();
        longPeriodList.addAll(periodDbList);
        longPeriodLock.writeLock().unlock();
        
        // 通行线路
        DimPassLineDao passLineDao = ServiceUtil.getService(DimPassLineDao.class);
        passLineLock.writeLock().lock();
        List<DimPassLine> dbineList = passLineDao.getPassLine();
        passLineList.clear();
        passLineList.addAll(dbineList);
        passLineLock.writeLock().unlock();
        
        // 违法次数
        DimSysConfig vioTimes = commonDao.getVioTimes();
        if (vioTimes != null)
        {
            vioTimesLock.writeLock().lock();
            vioTimes.setDimSysConfig(vioTimes);
            vioTimesLock.writeLock().unlock();
        }
    }
    
    @Override
    public void refreshPassLine()
    {
        DimPassLineDao passLineDao = ServiceUtil.getService(DimPassLineDao.class);
        
        passLineLock.writeLock().lock();
        List<DimPassLine> dbList = passLineDao.getPassLine();
        passLineList.clear();
        passLineList.addAll(dbList);
        passLineLock.writeLock().unlock();
        
    }
    
    @Override
    public List<DimForbidLine> getForbidLinesByIds(String lineIds)
    {
        if (StringUtils.isNotBlank(lineIds))
        {
            DimForbidLineDao forbidDao = ServiceUtil.getService(DimForbidLineDao.class);
            return forbidDao.getForbidLinesByIds(lineIds);
        }
        return null;
    }
    
    /**
     * 根据登录人账号获取禁行区域的列表
     * @param acctount
     * @return
     */
    @Override
    public List<Integer> getForbidAreaByAccount(String acctount)
    {
        List<Integer> ret = new ArrayList<Integer>();
        
        if (CollectionUtils.isEmpty(forbidAreaList))
        {
            try
            {
                syncForbidArea();
            }
            catch (Exception e)
            {
                logger.error("syncForbidArea error!");
            }
        }
        
        if (StringUtils.isNotEmpty(acctount))
        {
            for (DimForbidArea area : forbidAreaList)
            {
                String[] accountList = StringUtils.split(area.getAccountList(), ',');
                if (ArrayUtils.contains(accountList, acctount))
                {
                    ret.add(area.getAreaId());
                }
            }
        }
        return ret;
    }
    
    /**
     * @param areaId
     * @return
     */
    @Override
    public String getForbidAreaNameById(int areaId)
    {
        
        if (CollectionUtils.isEmpty(forbidAreaList))
        {
            try
            {
                syncForbidArea();
            }
            catch (Exception e)
            {
                logger.error("syncForbidArea error!");
            }
        }
        for (DimForbidArea area : forbidAreaList)
        {
            if (area.getAreaId() == areaId)
            {
                return area.getAreaName();
            }
        }
        
        return null;
    }
    
    @Override
    public List<DimForbidLine> getForbidLinesByCard(int beginDate, int endDate, String period, int cardType)
    {
        DimForbidLineDao forbidDao = ServiceUtil.getService(DimForbidLineDao.class);
        
        List<DimForbidLine> retList = new ArrayList<DimForbidLine>();
        
        List<DimForbidLine> lineList = forbidDao.getForbidLinesByInterval(beginDate, endDate);
        if (CollectionUtils.isNotEmpty(lineList))
        {
            for (DimForbidLine forbidLine : lineList)
            {
                long forbidBeginTime = forbidLine.getBeginTime();
                long forbidEndTime = forbidLine.getEndTime();
                int forbidBeginDate = (int)(forbidBeginTime / 1000000);
                int forbidEndDate = (int)(forbidEndTime / 1000000);
                
                // 获取与通行证交叉的日期
                int sdate = forbidBeginDate >= beginDate ? forbidBeginDate : beginDate;
                int edate = forbidEndDate <= endDate ? forbidEndDate : endDate;
                int dateBetween = DateUtils.getDayBetween(String.valueOf(sdate), String.valueOf(edate));
                
                if (dateBetween > 1)
                {
                    retList.add(forbidLine);
                }
                else
                {
                    List<TimeSlot> prepareTimeList = createPrepareTime(sdate, edate, period, cardType);
                    for (TimeSlot prepareTime : prepareTimeList)
                    {
                        if (prepareTime.getStartTime() < forbidEndTime && prepareTime.getEndTime() > forbidBeginTime)
                        {
                            retList.add(forbidLine);
                            break;
                        }
                    }
                }
            }
        }
        
        return retList;
    }
    
    /**
     * 构造交叉时间内用于比较判断的时间点 <一句话功能简述> <功能详细描述>
     * 
     * @param sdate
     * @param edate
     * @param period
     * @param cardType
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<TimeSlot> createPrepareTime(int sdate, int edate, String period, int cardType)
    {
        List<TimeSlot> prepareTime = new ArrayList<TimeSlot>();
        if (cardType == TEMP_CARD_TYPE)// 临时通行证
        {
            Map<Integer, List<TimeSlot>> tempCardMap = tempCardPeriod(period);
            for (int i = sdate; i <= edate; i++)
            {
                List<TimeSlot> timeList = tempCardMap.get(i);
                if (timeList != null)
                {
                    for (TimeSlot timeSlot : timeList)
                    {
                        prepareTime.add(timeSlot);
                    }
                }
            }
        }
        else if (cardType == LONG_CARD_TYPE)// 长期通行证
        {
            String[] timeArray = StringUtils.split(period, ",");
            for (int i = sdate; i <= edate; i++)
            {
                for (int j = 0; j < timeArray.length; j++)
                {
                    long stime = (long)i * 1000000 + (Integer.parseInt(timeArray[j])) * 10000;
                    long etime = stime + 10000;
                    prepareTime.add(new TimeSlot(stime, etime));
                }
            }
        }
        
        return prepareTime;
    }
    
    /**
     * 解析临时通行证的时间段 <一句话功能简述> <功能详细描述>
     * 
     * @param period
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Map<Integer, List<TimeSlot>> tempCardPeriod(String period)
    {
        Map<Integer, List<TimeSlot>> tempCardMap = new HashMap<Integer, List<TimeSlot>>();
        
        String[] daysArray = StringUtils.split(period, ";");
        for (int i = 0; i < daysArray.length; i++)
        {
            String[] dayArray = StringUtils.split(daysArray[i], ":");
            if (dayArray.length != 2)
            {
                break;
            }
            else
            {
                int date = Integer.parseInt(dayArray[0]);
                String[] timeArray = StringUtils.split(dayArray[1], ",");
                List<TimeSlot> timeList = new ArrayList<TimeSlot>();
                for (int j = 0; j < timeArray.length; j++)
                {
                    long stime = (long)date * 1000000 + (Integer.parseInt(timeArray[j])) * 10000;
                    long etime = stime + 10000;
                    timeList.add(new TimeSlot(stime, etime));
                }
                tempCardMap.put(date, timeList);
            }
            
        }
        
        return tempCardMap;
    }
    
    @Override
    public String getPlateTypeName(String plateTypeId)
    {
        List<DimPlateType> retList = null;
        try
        {
            retList = syncPlateType();
        }
        catch (Exception e)
        {
            
            logger.error("getPlateTypeName error", e);
        }
        if (CollectionUtils.isNotEmpty(retList))
        {
            for (DimPlateType plateType : retList)
            {
                if (plateType.getTypeId().equals(plateTypeId))
                {
                    return plateType.getName();
                }
            }
        }
        
        return null;
    }
    
    @Override
    public DimViolationVehicleType getViolationVehicleType(String plateTypeId)
    {
        List<DimViolationVehicleType> retList = null;
        try
        {
            retList = syncViolationVehicleType();
        }
        catch (Exception e)
        {
            
            logger.error("getPlateTypeName error", e);
        }
        if (CollectionUtils.isNotEmpty(retList))
        {
            for (DimViolationVehicleType plateType : retList)
            {
                if (plateType.getPlateTypeId().equals(plateTypeId))
                {
                    return plateType;
                }
            }
        }
        return null;
    }
    
    @Override
    public String getPassLineName(String lineIds)
    {
    	String[] lineIdArray = lineIds.split(",");
    	StringBuffer passLineNameBF = new StringBuffer();
        List<DimPassLine> retList = null;
        try
        {
            retList = syncPassLine();
        }
        catch (Exception e)
        {
            logger.error("getPassLineName error", e);
        }
        
        if (CollectionUtils.isNotEmpty(retList))
        {
        	for(int i=0; i<lineIdArray.length; i++){
        		for (DimPassLine passLine : retList)
                {
                    if (lineIdArray[i].equals(passLine.getLineId()+""))
                    {
                    	passLineNameBF.append(passLine.getLineName()).append(",");
                    	break;
                    }
                }
        	}
        }
        if(StringUtils.isNotEmpty(passLineNameBF)){
        	passLineNameBF.deleteCharAt(passLineNameBF.length()-1);
        	return passLineNameBF.toString();
        } else{
        	return null;
        }
    }
    
    @Override
    public List<DimPassLine> getPassLineById(String lineIds)
    {
    	String[] lineIdArray = lineIds.split(",");
        List<DimPassLine> retList = null;
        List<DimPassLine> dimPassLineList = new ArrayList<>();
        try
        {
            retList = syncPassLine();
        }
        catch (Exception e)
        {
            logger.error("getPassLineById error", e);
        }
        if (CollectionUtils.isNotEmpty(retList))
        {
        	for(int i=0; i<lineIdArray.length; i++){
        		for (DimPassLine passLine : retList)
                {
                    if (lineIdArray[i].equals(passLine.getLineId()+""))
                    {
                    	dimPassLineList.add(passLine);
                    	break;
                    }
                }
        	}
        }
        return dimPassLineList;
    }
    
    public static List<DimPlateType> getPlatetypelist()
    {
        return plateTypeList;
    }
    
    public static ReadWriteLock getPlatetypelock()
    {
        return plateTypeLock;
    }
    
    /**
     * 短信通知
     * 
     * @param content
     * @param mobileString
     * @throws RemoteException
     * @throws MalformedURLException
     */
    @Override
    public boolean smsInfo(String content, String mobileString)
        throws RemoteException, MalformedURLException
    {
        
        Soap57ProviderBindingStub bindingStub = new Soap57ProviderBindingStub();
        String sp = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sms.spID");
        String pass = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sms.password");
        String access =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sms.accessCode");
        String resultCode = bindingStub.submit(sp, pass, access, content, mobileString);
        //将发送的短信写入短信缓存
        /*if(resultCode.indexOf("0") == 0)
        {*/
        	
        //}
        return resultCode.indexOf("0") == 0;
    	//return false;
        
    }
    
    /**
     * @param licensePlate
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    @Override
    public String getCarOwnerPhone(String licensePlate)
    {
        WebServiceLocator.WebServiceSoap_address =
            ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.getCarOwnerPhoneUrl");
        WebService service = new WebServiceLocator();
        
        StringHolder ret = new StringHolder();
        // 先根据车牌获取大型车辆手机号码
        try
        {
            service.getWebServiceSoap().getSJHMByHPHMAndType(licensePlate, 2, ret, new StringHolder());
            // 大型车辆找不到就去找小型车辆
            if (StringUtils.isEmpty(ret.value))
            {
                service.getWebServiceSoap().getSJHMByHPHMAndType(licensePlate, 1, ret, new StringHolder());
            }
        }
        catch (RemoteException e)
        {
            logger.error("getCarOwnerPhone error!", e);
        }
        catch (ServiceException e)
        {
            logger.error("getCarOwnerPhone error!", e);
        }
        
        return ret.value;
    }
    
    @Override
    public DimSysConfig getVioTimes()
    {
        DimSysConfig retVioTimes = null;
        try
        {
            retVioTimes = sysVioTimes();
        }
        catch (Exception e)
        {
            logger.error("getVioTimes error", e);
        }
        return retVioTimes;
    }
    
    @Override
    public List<NoticePassCard> getNoticePassCards(long beginTime, long endTime)
    {
        List<NoticePassCard> retList = new ArrayList<NoticePassCard>();
        
        int forbidBeginDate = (int)(beginTime / 1000000);
        int forbidEndDate = (int)(endTime / 1000000);
        
        DimLongPassCardDao longCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
        DimTempPassCardDao tempCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
        
        List<DimLongPassCard> longCardList = longCardDao.getLongCardByInterval(forbidBeginDate, forbidEndDate);
        List<DimTempPassCard> tempCardList = tempCardDao.getTempCardByInterval(forbidBeginDate, forbidEndDate);
        List<NoticePassCard> noticeList = new ArrayList<NoticePassCard>();
        if (CollectionUtils.isNotEmpty(longCardList))
        {
            for (DimLongPassCard longCard : longCardList)
            {
                noticeList.add(new NoticePassCard(longCard));
            }
        }
        if (CollectionUtils.isNotEmpty(tempCardList))
        {
            for (DimTempPassCard tempCard : tempCardList)
            {
                noticeList.add(new NoticePassCard(tempCard));
            }
        }
        
        for (NoticePassCard noticeCard : noticeList)
        {
            // 获取与通行证交叉的日期
            int beginDate = noticeCard.getBeginDate();
            int endDate = noticeCard.getEndDate();
            int sdate = forbidBeginDate >= beginDate ? forbidBeginDate : beginDate;
            int edate = forbidEndDate <= endDate ? forbidEndDate : endDate;
            
            int dateBetween = DateUtils.getDayBetween(String.valueOf(sdate), String.valueOf(edate));
            
            if (dateBetween > 1)
            {
                retList.add(noticeCard);
            }
            else
            {
                
                List<TimeSlot> prepareTimeList =
                    createPrepareTime(sdate, edate, noticeCard.getPassPeriod(), noticeCard.getCardType());
                for (TimeSlot prepareTime : prepareTimeList)
                {
                    // 通行证开始时间
                    long prepStart = prepareTime.getStartTime();
                    // 通行证结束时间
                    long prepEnd = prepareTime.getEndTime();
                    if ((prepStart < endTime && prepEnd > beginTime))
                    {
                        retList.add(noticeCard);
                        break;
                    }
                }
            }
        }
        
        return retList;
    }
    
    @Override
    public void updateAvoidPeak(String period)
    {
        CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
        commonDao.updateAvoidPeak(period);
        
        longPeriodLock.writeLock().lock();
        for (DimSysConfig longPeriod : longPeriodList)
        {
            if (longPeriod.getConfigKey().equals("avoidPeak"))
            {
                longPeriod.setConfigValue(period);
            }
        }
        longPeriodLock.writeLock().unlock();
    }
    
    @Override
    public void updateVioTimes(String times)
    {
        CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
        commonDao.updateAvoidPeak(times);
        
        vioTimesLock.writeLock().lock();
        vioTimes.setConfigValue(times);
        vioTimesLock.writeLock().unlock();
    }
    
    @Override
    public String getIntName(String intList)
    {
        StringBuilder intBuilder = new StringBuilder();
        try
        {
            Map<String, String> intMap = syncIntersection();
            String[] intArray = StringUtils.split(intList, ",");
            
            for (int i = 0; i < intArray.length; i++)
            {
                intBuilder.append(intMap.get(intArray[i]));
                if (i != intArray.length - 1)
                {
                    intBuilder.append(",");
                }
            }
        }
        catch (Exception e)
        {
            logger.error("getIntName error", e);
        }
        return intBuilder.toString();
    }
    
    @Override
    public List<String> getForbidAreaDev()
    {
        List<String> retList = new ArrayList<String>();
        
        List<DimForbidArea> areaList = null;
        try
        {
            areaList = syncForbidArea();
        }
        catch (Exception e1)
        {
            logger.error("getForbidAreaDev error", e1);
        }
        
        if (CollectionUtils.isNotEmpty(areaList))
        {
            for (DimForbidArea dimForbidArea : areaList)
            {
                String devices = dimForbidArea.getDeviceList();
                if (StringUtils.isNotBlank(devices) && !devices.equals("-1"))
                {
                    String[] deviceArray = StringUtils.split(devices, ",");
                    for (String device : deviceArray)
                    {
                        retList.add(device);
                    }
                }
            }
        }
        
        return retList;
    }
    
    @Override
    public List<DimForbidArea> getForbidAreaList(int type)
    {
        List<DimForbidArea> retList = new ArrayList<DimForbidArea>();
        List<DimForbidArea> areaList = null;
        try
        {
            areaList = syncForbidArea();
            if (CollectionUtils.isNotEmpty(areaList))
            {
                for (DimForbidArea forbidArea : areaList)
                {
                    if (forbidArea.getAreaType() == type)
                    {
                        retList.add(forbidArea);
                    }
                }
            }
        }
        catch (Exception e1)
        {
            logger.error("getForbidAreaList error", e1);
        }
        return retList;
    }
    
    @Override
    public void updateForbidAreaInfo()
    {
        DimForbidAreaDao forbidAreaDao = ServiceUtil.getService(DimForbidAreaDao.class);
        forbidAreaLock.writeLock().lock();
        List<DimForbidArea> dbList = forbidAreaDao.getForbidArea(0);
        forbidAreaList.clear();
        forbidAreaList.addAll(dbList);
        forbidAreaLock.writeLock().unlock();
    }
    
    @Override
    public List<DimViolationSpecialVehicle> getSpecialVehicleById(String licensePlate)
    {
        List<DimViolationSpecialVehicle> vehList = null;
        List<DimViolationSpecialVehicle> retList = new ArrayList<DimViolationSpecialVehicle>();
        try
        {
            vehList = syncSpecialVeh();
        }
        catch (Exception e)
        {
            logger.error("synvSpecialVeh error", e);
        }
        
        if (CollectionUtils.isNotEmpty(vehList))
        {
            for (DimViolationSpecialVehicle veh : vehList)
            {
                if (veh.getLicensePlate().equals(licensePlate))
                {
                    retList.add(veh);
                }
            }
        }
        
        return retList;
    }
    
    @Override
    public void updateSpecialVehicle()
    {
        SpecialVehicleDao specialVehicleDao = ServiceUtil.getService(SpecialVehicleDao.class);
        specialVehLock.writeLock().lock();
        List<DimViolationSpecialVehicle> dbList = specialVehicleDao.getSpecialVehicle();
        specialVehList.clear();
        specialVehList.addAll(dbList);
        specialVehLock.writeLock().unlock();
        
    }
    
    @Override
    public List<DimUnstandardVehicle> getUnstandardVehicles()
    {
        List<DimUnstandardVehicle> retList = null;
        try
        {
            retList = syncUnstandardVeh();
        }
        catch (Exception e)
        {
            logger.error("syncUnstandardVeh error", e);
        }
        return retList;
    }
    
    @Override
    public void updateUnstandardVehicle()
    {
        DimUnstandardVehicleDao unstandardVehicleDao = ServiceUtil.getService(DimUnstandardVehicleDao.class);
        unstandardVehLock.writeLock().lock();
        List<DimUnstandardVehicle> dbList = unstandardVehicleDao.getUnstandardVehicle(new UnstandardVehicleReq());
        unstandardVehList.clear();
        unstandardVehList.addAll(dbList);
        unstandardVehLock.writeLock().unlock();
    }
    
    @Override
    public void infoUserUpdate()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("Enter Func [infoUserUpdate.]");
        }
        String message = "【吴江交警大队】 您好：为提高吴江智慧交通平台系统运行效率,系统即将进行更新操作,所带来的不便请您谅解,谢谢!";
        try
        {
            ServiceUtil.getService(CommonService.class).smsInfo(message, "");
        }
        catch (RemoteException | MalformedURLException e)
        {
            e.printStackTrace();
        }
        if (logger.isDebugEnabled())
        {
            logger.debug("Exit Func [infoUserUpdate.]");
        }
    }

    
    
    
    @Override
    public Map<String, Set<String>> getPolices()
    {
        List<DimElectricPoliceCamera> policeCameras = ServiceUtil.getService(CommonDao.class).getCameras();
        List<DimLicenseCamera> licenseCamera = ServiceUtil.getService(CommonDao.class).getLicenseCamera();
        
        Map<String, Set<String>> cameras = new HashMap<String, Set<String>>();
        
       // 封装电子警察 
        for (DimElectricPoliceCamera temp : policeCameras)
        {
            CameraPolice police = new CameraPolice();
            police.setPoliceId(temp.getElectricPoliceId());
            String agencyName = ServiceUtil.getService(CommonDao.class).getAgency().get(temp.getAgencyId());
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
        
        for (DimLicenseCamera dimLicenseCamera : licenseCamera)
        {
            CameraPolice police = new CameraPolice();
            police.setPoliceId(dimLicenseCamera.getLpCameraid());
            String agencyName = ServiceUtil.getService(CommonDao.class).getAgency().get(dimLicenseCamera.getAgencyId());
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

	/**
	 * @param points
	 * @return
	 */
	@Override
	public List<String> getElectricByposition(String points)
	{
		 List<String> retList = new ArrayList<String>();
	        
	        Map<String, List<String>> devMap = null;
	        try
	        {
	            devMap = syncDevice();
	        }
	        catch (Exception e)
	        {
	            logger.error("getElectricPoliceByLine error", e);
	        }
	        if (devMap != null && !devMap.isEmpty())
	        {
	            String[] pointArray = StringUtils.split(points, ",");
	            for (String point : pointArray)
	            {
	                List<String> devList = devMap.get(point);
	                if (CollectionUtils.isNotEmpty(devList))
	                {
	                    for (String dev : devList)
	                    {
	                        retList.add(dev);
	                    }
	                }
	                
	            }
	        }
	        return retList;
	}



	@Override
	public synchronized void writeNotice(NoticeVo noticeVo) {
		CommonDao dao=ServiceUtil.getService(CommonDao.class);
		int noticeCount=dao.getNoticeCount();
		if(noticeCount>=noticeSize)
		{
			dao.updateID();
			//noticeVo.setId(noticeSize);
		}/*else
		{
			noticeVo.setId(noticeCount+1);
		}*/
		dao.addNotice(noticeVo);
		//设置今日短信数量
		DimNoticeCount notice= dao.getTodayNoticeCount(Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd")));
		if(notice==null)
		{
			notice=new DimNoticeCount();
			notice.setDateKey(Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd")));
			notice.setNoticeCount(1);
			dao.addNoticeCount(notice);
		}else
		{
			notice.setNoticeCount(notice.getNoticeCount()+1);
			dao.updateNoticeCount(notice);
		}
	}

	@Override
	public List<NoticeVo> getNotice(NoticeReq req) {
		List<NoticeVo> noticeVoList=new ArrayList<NoticeVo>();
		noticeLock.readLock().lock();
		for(NoticeVo noticeVo:noticeList)
		{
			if(req.getBeginTime().compareTo(noticeVo.getNoticeTime())<=0&&req.getEndTime().compareTo(noticeVo.getNoticeTime())>=0)
			{
				noticeVoList.add(noticeVo);
			}
		}
		noticeLock.readLock().unlock();
		return noticeVoList;
	}

	/**
     * 判断是否发送短信 false:不发送 ,true:发送
     * @return
     */
	@Override
	public boolean checkSend() {
		CommonDao commonDao = ServiceUtil.getService(CommonDao.class);
		Param param = commonDao.getParam("is_send_notice");
		if(null!=param && "1".equals(param.getValue()))
		{
			return true;
		}
		return false;
	}

    
    
    
}
