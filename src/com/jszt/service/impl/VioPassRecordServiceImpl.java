package com.jszt.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.client.PassRecord;
import com.jszt.dao.DimConstructionSpeCardDao;
import com.jszt.dao.DimConstructionVehicleDao;
import com.jszt.dao.DimLongPassCardDao;
import com.jszt.dao.DimTempPassCardDao;
import com.jszt.dao.DimViolationPassRecordDao;
import com.jszt.dao.DimViolationPassRecordHisDao;
import com.jszt.dao.DimViolationValidDao;
import com.jszt.domain.DimConstructionSpeCard;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimLongPassCard;
import com.jszt.domain.DimPassLine;
import com.jszt.domain.DimTempPassCard;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.domain.DimViolationPassRecord;
import com.jszt.domain.DimViolationPassRecordHis;
import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.service.CommonService;
import com.jszt.service.VioPassRecordService;
import com.jszt.vo.TempPassCardVio;
import com.jszt.vo.ValidPassCard;

public class VioPassRecordServiceImpl implements VioPassRecordService
{
    private static final String NONE_CARD_TYPE = "0";
    
    private static final String TEMP_CARD_TYPE = "1";
    
    private static final String LONG_CARD_TYPE = "2";
    
    private static final int WHITE_LIST = 0;
    
    private static final int BLACK_LIST = 1;
    
    /**
     * 货车禁行区域类型
     */
    private static final int TRUCK_FORBID_AREA = 0;
    
    /**
     * 有效通行证
     */
    private static Map<String, List<ValidPassCard>> validCardMap = new HashMap<String, List<ValidPassCard>>();
    
    /**
     * 有效通行证缓存锁
     */
    private final static ReadWriteLock validCardLock = new ReentrantReadWriteLock();
    
    /**
     * 下一天的有效通行证
     */
    private static Map<String, List<ValidPassCard>> nextValidCardMap = new HashMap<String, List<ValidPassCard>>();
    
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(VioPassRecordServiceImpl.class);
    public static void main(){
    	String timeString="20161202112334";
    	long time = Long.parseLong(timeString);
        int date = (int)(time / 1000000000);
    	int hour = (int)((time % 1000000000) / 10000000);
        String hourString = String.valueOf(hour);
        
        List<String> periodList = new VioPassRecordServiceImpl().parseCardPeriod("20161202:9,10,11", TEMP_CARD_TYPE, date);
        if (!periodList.contains(hourString))
        {// 未在通行证规定的时间内通行
           // illegal = true;
        	System.out.println("h");
        }
        System.out.println("a");
    }
    
    @Test
    public void test()
    {
        String time = StringUtils.substring(DateUtils.getCurrentTime(), 8, 14);
        System.out.println(time);
        int curTime = ConvertUtils.parseInt(StringUtils.substring(DateUtils.getCurrentTime(), 8, 14),0);
        System.out.println(curTime);
    }
    
    /**
     * 向货车过车表里新增数据
     */
    @Override
    public void addVioPassRecord(PassRecord passRecord)
    {
        if (passRecord == null)
        {
            return;
        }
        if(StringUtils.isEmpty(passRecord.getUrl1()))
        {
        	return;
        }
        String plateLicense = passRecord.getPlate();
        
        if (StringUtils.isBlank(plateLicense) || plateLicense.equals("未知"))
        {
            return;
        }
        
        // 系统当前时间
        int curTime = ConvertUtils.parseInt(StringUtils.substring(DateUtils.getCurrentTime(), 8, 14),0);
        
        // 过车记录日期
        String passTime = StringUtils.substring(passRecord.getTime(), 0, 8);
        
        // 昨天
        String yesterday = StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), -1), 0, 8);
        
        //如果系统当前时间大于2:00,当前过车记录日期为昨天
        if (curTime > 20000 && StringUtils.equals(passTime, yesterday))
        {
        	return;
        }
               
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        
        // 判断是否为黄标车
        List<DimUnstandardVehicle> unstandardVehList = commonService.getUnstandardVehicles();
        for (DimUnstandardVehicle unstandardVehicle : unstandardVehList)
        {
            if (unstandardVehicle.getLicensePlate().equals(plateLicense))
            {
                return;
            }
        }
        
        String deviceId = passRecord.getDeviceId();
        String timeString = passRecord.getTime();
        long time = Long.parseLong(timeString);
        int date = (int)(time / 1000000000);
        
        // 判断是否在禁行区域
        int areaId = -1;
        List<DimForbidArea> forbidAreaList = commonService.getForbidAreaList(TRUCK_FORBID_AREA);
        for (DimForbidArea forbidArea : forbidAreaList)
        {
            String devices = forbidArea.getDeviceList();
            if (StringUtils.isNotBlank(devices) && devices.contains(deviceId))
            {
                areaId = forbidArea.getAreaId();
            }
        }
        if (areaId == -1)// 不在禁行区域内
        {
            return;
        }
        
        boolean illegal = false;
        String cardTypeId = null;
        
        List<DimViolationSpecialVehicle> specialVehList = commonService.getSpecialVehicleById(plateLicense);
        DimViolationSpecialVehicle validVeh = getValidVehicle(specialVehList);
        
        if (validVeh == null)
        {
            String tempTime = DateUtils.getCurrentTime().substring(0, 8);
           
            List<ValidPassCard> passCards = null;
            passCards = validCardMap.get(plateLicense);
            
            // 如果不是当天的记录, 防止公安三所记录延迟严重,重新查找，内存是当天的通行证
            if (!tempTime.equals(String.valueOf(date)))
            {
                Map<String, List<ValidPassCard>> tempValidPassCard = getValidPassCard(date);
                passCards = tempValidPassCard.get(plateLicense);
            }
            // if (passCard == null)
            // {
            // passCard = getTempValidPassCard(plateLicense);
            // }
            if (CollectionUtils.isEmpty(passCards))// 无通行证
            {
                illegal = true;
                cardTypeId = NONE_CARD_TYPE;
                logger.info("无通行证");
            }
            else
            {
                List<String> devLists = new ArrayList<String>();
                StringBuffer period = new StringBuffer();
                ValidPassCard passCard = passCards.get(0);
                String cardType = passCard.getCardType();
                String pre = ";";
                if(cardType.equals(LONG_CARD_TYPE))
                {
                	pre=",";
                }
                for(ValidPassCard card:passCards)
                {
                	if(CollectionUtils.isNotEmpty(card.getDevList()))
                	{
                		devLists.addAll(card.getDevList());
                	}
                	period.append(pre).append(card.getPeriod());
                }
                if(period.length()>0)
                {
                	period.deleteCharAt(0);
                }
                passCard.setPeriod(period.toString());
                if (CollectionUtils.isNotEmpty(devLists) && !devLists.contains(deviceId))
                {// 未在规定的线路上通行
                    illegal = true;
                    logger.info("车牌号为【" + passCard.getPlateLicense() + "】," + "设备编号为【" + deviceId + "】,通行证设备为【" + devLists.toString() + "】");
                }
                else
                {
                    int hour = (int)((time % 1000000000) / 10000000);
                    String hourString = String.valueOf(hour);
                    List<String> periodList = parseCardPeriod(passCard.getPeriod(), cardType, date);
                    if (!periodList.contains(hourString))
                    {// 未在通行证规定的时间内通行
                        illegal = true;
                        logger.info("车牌号为【" + passCard.getPlateLicense() + "】,过车精确时间为【" + time + "】,过车小时为【" + hour + "】,申请通行证时段为【" + passCard.getPeriod() + "】,比对之后通行时段为【" + periodList.toString() + "】");
                    }
                }
                cardTypeId = cardType;
            }
        }
        else if (validVeh.getType() == WHITE_LIST)
        {
            illegal = false;
        }
        else if (validVeh.getType() == BLACK_LIST)
        {
            illegal = true;
            cardTypeId = NONE_CARD_TYPE;
            logger.info("名单类型为黑名单,过车精确时间为【" + time + "】");
        }
        
        if (illegal)
        {
            DimViolationPassRecordDao recordDao = ServiceUtil.getService(DimViolationPassRecordDao.class);
            DimViolationPassRecordHisDao recordHisDao = ServiceUtil.getBean(DimViolationPassRecordHisDao.class);
            
            DimViolationPassRecord record = new DimViolationPassRecord(passRecord, cardTypeId, areaId);
            DimViolationPassRecordHis recordHis = new DimViolationPassRecordHis(passRecord, cardTypeId, areaId);
            
            recordDao.addVioRecord(record);
            recordHisDao.addVioRecordHis(recordHis);
        }
    }
    
    /**
     * 获取有效的黑白名单记录 <一句话功能简述> <功能详细描述>
     * 
     * @param specialVehList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private DimViolationSpecialVehicle getValidVehicle(List<DimViolationSpecialVehicle> specialVehList)
    {
        int today = Integer.parseInt(DateUtils.dateToString(new Date(), "yyyyMMdd"));
        for (DimViolationSpecialVehicle veh : specialVehList)
        {
            if (today >= veh.getStartDate() && today <= veh.getEndDate())
            {
                return veh;
            }
        }
        return null;
    }
    
    /**
     * 获取车辆当天有效的通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param plateLicense2
     * @return
     * @see [类、类#方法、类#成员]
     */
    // private ValidPassCard getTempValidPassCard(String plateLicense2)
    // {
    // Calendar calendar = Calendar.getInstance();
    // int date =
    // calendar.get(Calendar.YEAR)*10000+(calendar.get(Calendar.MONTH)+1)*100+calendar.get(Calendar.DAY_OF_MONTH);
    //
    // DimTempPassCardDao tempCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
    // CommonService commonService = ServiceUtil.getService(CommonService.class);
    //
    // List<DimTempPassCard> validTempCardList = tempCardDao.getTempCardByDate(plateLicense2, date);
    // if (CollectionUtils.isNotEmpty(validTempCardList))
    // {
    // ValidPassCard passCard = new ValidPassCard();
    // for (int i = 0; i < validTempCardList.size(); i++)
    // {
    // DimTempPassCard tempCard = validTempCardList.get(i);
    //
    // //临时通行证，一天里可能有多条记录，需要将通行时段拼接起来
    // if (i != 0)
    // {
    // String period = passCard.getPeriod();
    // String secPeriod = tempCard.getPassPeriod();
    // //临时通行证不会跨天
    // String[] dayArray = StringUtils.split(secPeriod, ":");
    // if (dayArray.length == 2)
    // {
    // period = period + "," + dayArray[1];
    // }
    // passCard.setPeriod(period);
    // continue;
    // }
    // String plateLicense = tempCard.getLicensePlate();
    // passCard.setPlateLicense(plateLicense);
    // passCard.setPlateTypeId(tempCard.getPlateTypeId());
    // passCard.setPeriod(tempCard.getPassPeriod());
    // passCard.setCardType(TEMP_CARD_TYPE);
    // int lineId = tempCard.getPassLine();
    // if (lineId != -1)
    // {
    // DimPassLine passLine = commonService.getPassLineById(lineId);
    // if (passLine != null)
    // {
    // String dev = passLine.getDeviceList();
    // if (StringUtils.isNotBlank(dev)&&!dev.equals("-1"))
    // {
    // String[] devArray = StringUtils.split(dev,",");
    // List<String> devList = new ArrayList<String>();
    // for (int j = 0; j < devArray.length; j++)
    // {
    // devList.add(devArray[i]);
    // }
    // passCard.setDevList(devList);
    // }
    // }
    // }
    // }
    //
    // return passCard;
    // }
    //
    // return null;
    // }
    
    /**
     * 解析通行证的通行时段 <一句话功能简述> <功能详细描述>
     * 
     * @param period
     * @param cardType
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<String> parseCardPeriod(String period, String cardType, int date)
    {
        List<String> retList = new ArrayList<String>();
        if (cardType.equals(TEMP_CARD_TYPE))// 临时通行证
        {
            String[] daysArray = StringUtils.split(period, ";");
            for (int i = 0; i < daysArray.length; i++)
            {
                String[] dayArray = StringUtils.split(daysArray[i], ":");
                if (dayArray.length < 2)
                {
                    break;
                }
                else
                {
                    int periodDate = Integer.parseInt(dayArray[0]);
                    if (periodDate == date)
                    {
                        String[] timeArray = StringUtils.split(dayArray[1], ",");
                        for (int j = 0; j < timeArray.length; j++)
                        {
                            retList.add(timeArray[j]);
                        }
//                        break; 这里加上break不就值会添加一次for循环里面的period吗？应该不能break的；
                    }
                }
                
            }
        }
        else if (cardType.equals(LONG_CARD_TYPE))
        {// 长期通行证
            String[] periodArray = StringUtils.split(period, ",");
            for (int i = 0; i < periodArray.length; i++)
            {
                retList.add(periodArray[i]);
            }
        }
        return retList;
        
    }
    
    @Override
    public void updateValidCard()
    {
        logger.info("执行查询通行证有效期在系统当前日期之内的通行证定时器=========开始");
        nextValidCardMap.clear();
        
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int date =
            calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100
                + calendar.get(Calendar.DAY_OF_MONTH);
        
        Map<String, List<ValidPassCard>> retMap = getValidPassCard(date);
        nextValidCardMap.putAll(retMap);
        
        for (List<ValidPassCard> validPassCards : retMap.values())
        {
            for(ValidPassCard validPassCard:validPassCards)
            {
            	logger.info("通行证有效期在系统当前日期之内的通行证定时器:"  + validPassCard.toString());
            }
        }
        logger.info("执行查询通行证有效期在系统当前日期之内的通行证定时器=========成功");
    }
    
    @Override
    public void syncValidCard()
    {
        logger.info("执行同步通行证定时器=========开始");
        validCardLock.writeLock().lock();
        validCardMap.clear();
        validCardMap.putAll(nextValidCardMap);
        validCardLock.writeLock().unlock();
        logger.info("执行同步通行证定时器=========成功");
    }
    
    /**
     * 获取某一天有效的通行证 <一句话功能简述> <功能详细描述>
     * 
     * @param date
     * @return
     * @see [类、类#方法、类#成员]
     */
    public Map<String, List<ValidPassCard>> getValidPassCard(int date)
    {
        Map<String, List<ValidPassCard>> retMap = new HashMap<String, List<ValidPassCard>>();
        
        DimTempPassCardDao tempCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
        DimLongPassCardDao longCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
        DimConstructionVehicleDao cvCardDao = ServiceUtil.getService(DimConstructionVehicleDao.class);
        DimConstructionSpeCardDao csCardDao = ServiceUtil.getService(DimConstructionSpeCardDao.class);
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        
        List<DimLongPassCard> validLongCardList = longCardDao.getLongValidPassCards(date);
        List<DimTempPassCard> validTempCardList = tempCardDao.getTempValidPassCards(date);
        List<DimConstructionVehicle> validCvCardList = cvCardDao.getValidConVehicles(date);
        List<DimConstructionSpeCard> validCsCardList = csCardDao.getValidConSpeCards(date);
        if (CollectionUtils.isNotEmpty(validLongCardList))
        {
            for (DimLongPassCard longCard : validLongCardList)
            {
                ValidPassCard passCard = new ValidPassCard();
                String plateLicense = longCard.getLicensePlate();
                passCard.setPlateLicense(plateLicense);
                passCard.setPlateTypeId(longCard.getPlateTypeId());
                passCard.setPeriod(longCard.getPassPeriod());
                passCard.setCardType(LONG_CARD_TYPE);
                String lineIds = longCard.getPassLine();
                if (!"-1".equals(lineIds))
                {
                    List<DimPassLine> passLineList = commonService.getPassLineById(lineIds);
                    List<String> devList = new ArrayList<String>();
                    for (DimPassLine passLine : passLineList) {
                    	if (passLine != null)
                        {
                            String dev = passLine.getDeviceList();
                            if (StringUtils.isNotBlank(dev) && !dev.equals("-1"))
                            {
                                String[] devArray = StringUtils.split(dev, ",");
                                for (int i = 0; i < devArray.length; i++)
                                {
                                    devList.add(devArray[i]);
                                }
                            }
                        }
					}
                    passCard.setDevList(devList);
                    
                }
                List<ValidPassCard> passCards = retMap.get(plateLicense);
                if(null==passCards)
                {
                	passCards = new ArrayList<ValidPassCard>();
                }
                passCards.add(passCard);
                retMap.put(plateLicense, passCards);
            }
        }
        if (CollectionUtils.isNotEmpty(validTempCardList))
        {
            for (DimTempPassCard tempCard : validTempCardList)
            {
                ValidPassCard passCard = new ValidPassCard();
                String plateLicense = tempCard.getLicensePlate();
                // 临时通行证，一天里可能有两条记录，需要将通行时段拼接起来
                //20170814 lcf
                /*if (retMap.get(plateLicense) != null)
                {
                    ValidPassCard validPassCard = retMap.get(plateLicense);
                    String period = validPassCard.getPeriod();
                    String secPeriod = tempCard.getPassPeriod();
                    // 临时通行证不会跨天
                    String[] dayArray = StringUtils.split(secPeriod, ":");
                    if (dayArray.length == 2)
                    {
                        period = period + "," + dayArray[1];
                    }
                    validPassCard.setPeriod(period);
                    continue;
                }*/
                passCard.setPlateLicense(plateLicense);
                passCard.setPlateTypeId(tempCard.getPlateTypeId());
                passCard.setPeriod(tempCard.getPassPeriod());
                passCard.setCardType(TEMP_CARD_TYPE);
                String lineIds = tempCard.getPassLine();
                if (!"-1".equals(lineIds))
                {
                    List<DimPassLine> passLineList = commonService.getPassLineById(lineIds);
                    List<String> devList = new ArrayList<String>();
                    for (DimPassLine passLine : passLineList) {
                    	 if (passLine != null)
                         {
                             String dev = passLine.getDeviceList();
                             if (StringUtils.isNotBlank(dev) && !dev.equals("-1"))
                             {
                                 String[] devArray = StringUtils.split(dev, ",");
                                 for (int i = 0; i < devArray.length; i++)
                                 {
                                     devList.add(devArray[i]);
                                 }
                             }
                         }
					}
                    passCard.setDevList(devList);
                }
                List<ValidPassCard> passCards = retMap.get(plateLicense);
                if(null==passCards)
                {
                	passCards = new ArrayList<ValidPassCard>();
                }
                passCards.add(passCard);
                retMap.put(plateLicense, passCards);
            }
        }
        // 渣土车特殊通行证
        if (CollectionUtils.isNotEmpty(validCsCardList))
        {
            for (DimConstructionSpeCard speCard : validCsCardList)
            {
                ValidPassCard passCard = new ValidPassCard();
                String plateLicense = speCard.getLicensePlate();
                passCard.setPlateLicense(plateLicense);
                passCard.setPlateTypeId("1"); // 1:大型车,2:小型车
                passCard.setPeriod(speCard.getPassPeriod());
                passCard.setCardType(LONG_CARD_TYPE);
                
                List<ValidPassCard> passCards = retMap.get(plateLicense);
                if(null==passCards)
                {
                	passCards = new ArrayList<ValidPassCard>();
                }
                passCards.add(passCard);
                retMap.put(plateLicense, passCards);
            }
        }
        // 渣土车
        if (CollectionUtils.isNotEmpty(validCvCardList))
        {
            for (DimConstructionVehicle cVehicle : validCvCardList)
            {
                ValidPassCard passCard = new ValidPassCard();
                String plateLicense = cVehicle.getLicensePlate();
                passCard.setPlateLicense(plateLicense);
                passCard.setPlateTypeId(cVehicle.getPlateTypeId());
                passCard.setPeriod(cVehicle.getPassPeriod());
                passCard.setCardType(LONG_CARD_TYPE);
                List<ValidPassCard> passCards = retMap.get(plateLicense);
                if(null==passCards)
                {
                	passCards = new ArrayList<ValidPassCard>();
                }
                passCards.add(passCard);
                retMap.put(plateLicense, passCards);
            }
        }
        return retMap;
    }
    
    @Override
    public void initValidCard()
    {
        Calendar calendar = Calendar.getInstance();
        int date =
            calendar.get(Calendar.YEAR) * 10000 + (calendar.get(Calendar.MONTH) + 1) * 100
                + calendar.get(Calendar.DAY_OF_MONTH);
        logger.info("开始启动服务查询通行证有效期在系统当前日期之内的通行证:");
        Map<String, List<ValidPassCard>> retMap = getValidPassCard(date);
        validCardLock.writeLock().lock();
        validCardMap.clear();
        validCardMap.putAll(retMap);
        validCardLock.writeLock().unlock();
        logger.info("结束启动服务查询通行证有效期在系统当前日期之内的通行证:");
        /*for (List<ValidPassCard> validPassCards : retMap.values())
        {
        	for(ValidPassCard validPassCard:validPassCards)
        	{
        		logger.info("启动服务查询通行证有效期在系统当前日期之内的通行证:"  + validPassCard.toString());
        	}
        }*/
    }
    
    public void startInitValidCard()
	{
		// 设置10分钟后开始初始化通行证缓存
        Timer timer = new Timer();
        int time =
                ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.initValidCardTime"),
                		10*60*1000);
        timer.schedule(new TimerTask(){

			@Override
			public void run() {
				initValidCard();
			}
        	
        }, time);
	}
    
    @Override
    public void updateTempValidCard(DimTempPassCard tempPassCard)
    {
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        
        String plateLicense = tempPassCard.getLicensePlate();
        
        validCardLock.writeLock().lock();
        
        // 临时通行证，一天里可能有两条记录，需要将通行时段拼接起来
        /*if (validCardMap.get(plateLicense) != null)
        {
            ValidPassCard validPassCard = validCardMap.get(plateLicense);
            String period = validPassCard.getPeriod();
            String secPeriod = tempPassCard.getPassPeriod();
            // 临时通行证不会跨天
            String[] dayArray = StringUtils.split(secPeriod, ":");
            if (dayArray.length == 2)
            {
                period = period + "," + dayArray[1];
            }
            validPassCard.setPeriod(period);
        }
        else
        {
        	ValidPassCard passCard = new ValidPassCard();
            passCard.setPlateLicense(plateLicense);
            passCard.setPlateTypeId(tempPassCard.getPlateTypeId());
            passCard.setPeriod(tempPassCard.getPassPeriod());
            passCard.setCardType(TEMP_CARD_TYPE);
            int lineId = tempPassCard.getPassLine();
            if (lineId != -1)
            {
                DimPassLine passLine = commonService.getPassLineById(lineId);
                if (passLine != null)
                {
                    String dev = passLine.getDeviceList();
                    if (StringUtils.isNotBlank(dev) && !dev.equals("-1"))
                    {
                        String[] devArray = StringUtils.split(dev, ",");
                        List<String> devList = new ArrayList<String>();
                        for (int i = 0; i < devArray.length; i++)
                        {
                            devList.add(devArray[i]);
                        }
                        passCard.setDevList(devList);
                    }
                }
            }
            validCardMap.put(plateLicense, passCard);
        }*/
        ValidPassCard passCard = new ValidPassCard();
        passCard.setPlateLicense(plateLicense);
        passCard.setPlateTypeId(tempPassCard.getPlateTypeId());
        passCard.setPeriod(tempPassCard.getPassPeriod());
        passCard.setCardType(TEMP_CARD_TYPE);
        String lineIds = tempPassCard.getPassLine();
        if (!"-1".equals(lineIds))
        {
            List<DimPassLine> passLineList = commonService.getPassLineById(lineIds);
            List<String> devList = new ArrayList<String>();
            for (DimPassLine passLine : passLineList) {
            	if (passLine != null)
                {
                    String dev = passLine.getDeviceList();
                    if (StringUtils.isNotBlank(dev) && !dev.equals("-1"))
                    {
                        String[] devArray = StringUtils.split(dev, ",");
                        for (int i = 0; i < devArray.length; i++)
                        {
                            devList.add(devArray[i]);
                        }
                    }
                }
			}
            passCard.setDevList(devList);
        }
        List<ValidPassCard> passCards = validCardMap.get(plateLicense);
        if(null==passCards)
        {
        	passCards = new ArrayList<ValidPassCard>();
        }
        passCards.add(passCard);
        validCardMap.put(plateLicense, passCards);
        validCardLock.writeLock().unlock();
    }
    
    /**
     * 
     */
    @Override
    public void delViolationTempPassCard()
    {
        logger.info("Enter func [delViolationTempPassCard.]" + DateUtils.getCurrentTime());
        try
        {
            
            String nowDate = DateUtils.getCurrentTime();
            String lastDate = DateUtils.adjustDay(nowDate, -1).substring(0, 8);
            logger.info("Begin query record list");
            List<TempPassCardVio> dbList =
                ServiceUtil.getService(DimViolationValidDao.class).queryTempPassCardViolation(lastDate);
            logger.info("record list num: " + dbList.size());
            // 筛选日期在通行证日期内的过车记录
            if (CollectionUtils.isNotEmpty(dbList))
            {
                for (TempPassCardVio tempPassCardVio : dbList)
                {
                    int hour = (int)(tempPassCardVio.getTimeKey() / 10000000);
                    String hourString = String.valueOf(hour);
                    
                    List<String> periodList =
                        parseCardPeriod(tempPassCardVio.getPassPeriod(), TEMP_CARD_TYPE, Integer.valueOf(lastDate));
                    if (periodList.contains(hourString))
                    {
                        ServiceUtil.getService(DimViolationValidDao.class).delTempPassCardViolation(tempPassCardVio.getRowId());
                        logger.info("删除的过车记录：" + tempPassCardVio.getRowId()+"通行证时段："+tempPassCardVio.getPassPeriod()+ "过车时间："+tempPassCardVio.getTimeKey());
                    }
                    logger.info("period List: " + periodList.toString() + ", time key: " + hourString);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            //NumberFormatException
            //logger.info("queryTempPassCardViolation fail!", e);
        }
        logger.info("Exit func [delViolationTempPassCard.]" + DateUtils.getCurrentTime());
    }
    
}
