package com.jszt.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.jsits.commons.domain.PicWrod;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ExcelUtil;
import com.jsits.commons.util.ImageUtils;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.CommonDao;
import com.jszt.dao.DimCardUserInfoDao;
import com.jszt.dao.DimForbidAreaDao;
import com.jszt.dao.DimLongPassCardDao;
import com.jszt.dao.DimPassLineDao;
import com.jszt.dao.DimTempPassCardDao;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimForbidLine;
import com.jszt.domain.DimLongPassCard;
import com.jszt.domain.DimPassCard;
import com.jszt.domain.DimPassLlimit;
import com.jszt.domain.DimSysAccount;
import com.jszt.domain.DimSysConfig;
import com.jszt.domain.DimTempPassCard;
import com.jszt.domain.DimViolationAgency;
import com.jszt.service.CommonService;
import com.jszt.service.PassCardService;
import com.jszt.service.PassLimitService;
import com.jszt.service.VioPassRecordService;
import com.jszt.util.CommonUtil;
import com.jszt.vo.CardPrintInfo;
import com.jszt.vo.CardUserInfoReq;
import com.jszt.vo.DeleteCardUserInfo;
import com.jszt.vo.Intersection;
import com.jszt.vo.NoticeVo;
import com.jszt.vo.PassCard;
import com.jszt.vo.PassCardAnalyseReq;
import com.jszt.vo.PassCardAnalyseRes;
import com.jszt.vo.PassCardAnalyseVo;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.TempCountPo;
import com.jszt.vo.TempPassCardCount;
import com.jszt.vo.TempPassCardCountReq;
import com.jszt.vo.UpdatePassCardReq;

public class PassCardServiceImpl implements PassCardService
{
    // 临时通行证类型
    private static final int TEMP_CARD_TYPE = 1;
    
    // 长期通行证类型
    private static final int LONG_CARD_TYPE = 2;
    
    // 待大队领导审批
    private static final int DD_STATUS = 0;
    
    // 审批通过
    private static final int PASS_STATUS = 2;
    
    // 审批不通过
    private static final int UNPASS_STATUS = 3;
    
    //通行证图片右上角标识长度
    private static final int IDENTIFY_LENGTH = 7;
    
    // 通行证正面标志
    // private static final String CARD_FRONT = "front";
    
    // 通行证反面标志
    // private static final String CARD_BACK = "back";
    
    // 长期通行证标志
    private static final String CARD_LONG = "long";
    
    // 临时通行证标志
    private static final String CARD_TEMP = "temp";
    
    // 通行证正面字体
    private static final Font front_font = new Font("黑体", Font.PLAIN, 63);
    
    // 通行证反面字体
    // private static final Font back_font = new Font("黑体", Font.PLAIN, 65);
    
    // 通行证通行线路一行的字数
    private static final int ROW_WORDS = 50;
    
    // 通行证通行线路的行间距（加上字体的高度）
    private static final int ROW_DISTANCE = 150;
    
    /**
     * 货车禁行区域类型
     */
    private static final int TRUCK_FORBID_AREA = 0;
    
    // 吴江大道和江城大道货车禁行区ID
    private static final int[] AREA_ID_LIST = new int[]{109,89};
    
    //吴江区松陵货车
    private static final int WJ_AREA_ID = 44;
    
    private static Logger logger = Logger.getLogger(PassCardServiceImpl.class);
    
    @Override
    public List<PassCard> getPassCard(PassCardReq passCardReq)
    {
        List<PassCard> retList = new ArrayList<PassCard>();
        
        int cardType = passCardReq.getCardType();
        if (cardType == TEMP_CARD_TYPE)// 临时通行证
        {
            DimTempPassCardDao dimTempPassCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
            List<DimTempPassCard> tempCardList = dimTempPassCardDao.getTempPassCards(passCardReq);
            for (DimTempPassCard tempPassCard : tempCardList)
            {
                PassCard passCard = cardTrans(tempPassCard);
                passCard.setCardType(TEMP_CARD_TYPE + "");
                passCard.setPassPeriod(tempCardPeriodTrans(tempPassCard.getPassPeriod()));
                retList.add(passCard);
            }
            
        }
        else if (cardType == LONG_CARD_TYPE)
        {
            // 长期通行证
            DimLongPassCardDao dimLongPassCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
            List<DimLongPassCard> longCardList = dimLongPassCardDao.getLongPassCards(passCardReq);
            longPassCardBuild(retList, longCardList);
        }
        return retList;
    }
    
    private void longPassCardBuild(List<PassCard> retList, List<DimLongPassCard> longCardList)
    {
    	CommonService commonService = ServiceUtil.getService(CommonService.class);
        for (DimLongPassCard longPassCard : longCardList)
        {
            PassCard passCard = cardTrans(longPassCard);
            passCard.setCardType(LONG_CARD_TYPE + "");
            passCard.setPassPeriod(longCardPeriodTrans(longPassCard.getPassPeriod()));
            passCard.setPassPeriodIds(longPassCard.getPassPeriod());
            passCard.setStatus(longPassCard.getStatus());
            passCard.setParentCardId(longPassCard.getParentCardId());
            passCard.setAccountId(longPassCard.getAccountId());
            passCard.setPassLine(commonService.getPassLineName(longPassCard.getPassLine()));
            passCard.setPassLineId(longPassCard.getPassLine());
            if (longPassCard.getZxkDate() != null)
            {
                passCard.setZxkDate(Long.parseLong(longPassCard.getZxkDate()));
            }
            passCard.setZxkOpinion(longPassCard.getZxkOpinion());
            if (longPassCard.getDdDate() != null)
            {
                passCard.setDdDate(Long.parseLong(longPassCard.getDdDate()));
            }
            passCard.setDdOpinion(longPassCard.getDdOpinion());
            
            String agencyId = longPassCard.getAgencyId();
            passCard.setAgencyId(agencyId);
            DimForbidAreaDao dimForbidAreaDao = ServiceUtil.getService(DimForbidAreaDao.class);
            if (StringUtils.isNotBlank(agencyId))
            {
                DimViolationAgency dimViolationAgency = dimForbidAreaDao.getViolationAgency(agencyId);
                passCard.setAgencyName(null != dimViolationAgency ? dimViolationAgency.getAgencyName() : "未配置");
            }
            retList.add(passCard);
            
        }
    }
    
    /**
     * 通行证修改后作为前台展示 <一句话功能简述> <功能详细描述>
     * 
     * @param dimPassCard
     * @return
     * @see [类、类#方法、类#成员]
     */
    private PassCard cardTrans(DimPassCard dimPassCard)
    {
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        
        PassCard passCard = new PassCard();
        passCard.setCardId(dimPassCard.getCardId());
        passCard.setLicensePlate(dimPassCard.getLicensePlate());
        passCard.setPlateTypeId(dimPassCard.getPlateTypeId());
        passCard.setForbidType(dimPassCard.getForbidType());
        passCard.setBeginDate(dimPassCard.getBeginDate());
        passCard.setEndDate(dimPassCard.getEndDate());
        passCard.setPassLine(commonService.getPassLineName(dimPassCard.getPassLine()));
        passCard.setPassLineId(dimPassCard.getPassLine());
        List<DimForbidLine> forbidList = commonService.getForbidLinesByIds(dimPassCard.getForbidLine());
        passCard.setForbidLine(getForbidLineNames(forbidList));
        passCard.setForbidLineIds(dimPassCard.getForbidLine());
        passCard.setApplyType(dimPassCard.getApplyType() + "");
        passCard.setOwnerName(dimPassCard.getOwnerName());
        passCard.setContactPeople(dimPassCard.getContactPeople());
        passCard.setAddress(dimPassCard.getAddress());
        passCard.setPhoneNumber(dimPassCard.getPhoneNumber());
        passCard.setIdCard(dimPassCard.getIdCard());
        passCard.setPrint(dimPassCard.getPrint() + "");
        passCard.setApplyDate(Long.parseLong(dimPassCard.getApplyDate()));
        passCard.setXszUrl(dimPassCard.getXszUrl());
        passCard.setJszUrl(dimPassCard.getJszUrl());
        passCard.setBxUrl(dimPassCard.getBxUrl());
        passCard.setSfzUrl(dimPassCard.getSfzUrl());
        passCard.setYyzzUrl(dimPassCard.getYyzzUrl());
        passCard.setGhhtUrl(dimPassCard.getGhhtUrl());
        passCard.setYyzUrl(dimPassCard.getYyzUrl());
        
        return passCard;
        
    }
    
    /**
     * 获取禁行线路名称 <一句话功能简述> <功能详细描述>
     * 
     * @param forbidList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getForbidLineNames(List<DimForbidLine> forbidList)
    {
        StringBuilder forbidBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(forbidList))
        {
            for (DimForbidLine forbidLine : forbidList)
            {
                forbidBuilder.append(forbidLine.getLineName());
                forbidBuilder.append(",");
            }
            int length = forbidBuilder.length();
            forbidBuilder.deleteCharAt(length - 1);
        }
        
        return forbidBuilder.toString();
    }
    
    /**
     * 通行证录入
     * @param passCardRequest
     * @throws JsztException 
     */
    @Override
    public String addPassCard(PassCardRequest passCardRequest) throws JsztException
    {
        int cardType = passCardRequest.getCardType();
        
        if (cardType == TEMP_CARD_TYPE) // 1：临时通行证
        {
            DimTempPassCard dimTempPassCard = (DimTempPassCard)passCardCommon(passCardRequest, new DimTempPassCard());
            ServiceUtil.getService(DimTempPassCardDao.class).addTempPassCard(dimTempPassCard);
            
            ServiceUtil.getService(VioPassRecordService.class).updateTempValidCard(dimTempPassCard);
            
            return dimTempPassCard.getCardId();
        }
        // 2：长期通行证
        else
        {
            DimLongPassCard dimLongPassCard = (DimLongPassCard)passCardCommon(passCardRequest, new DimLongPassCard());
            // 新增时候
            if (StringUtils.isEmpty(passCardRequest.getParentCardId()))
            {
                dimLongPassCard.setParentCardId("-1");
                dimLongPassCard.setStatus(DD_STATUS); // 未审核
            }
            // 延长通行证有效期
            else
            {
                dimLongPassCard.setParentCardId(passCardRequest.getParentCardId());
                dimLongPassCard.setStatus(PASS_STATUS); // 审核通过
            }
            
            String accountId = passCardRequest.getAccountId();
            dimLongPassCard.setAccountId(accountId);
            
            // 获取登录账户所在的部门单位编号
            DimSysAccount dimSysAccount = ServiceUtil.getService(CommonDao.class).getSysAccount(accountId);
            dimLongPassCard.setAgencyId(null != dimSysAccount ? dimSysAccount.getAgencyId() : "未配置");
            
            //根据当前通行证选择的线路查询出所有的authId
            if(StringUtils.isNotEmpty(dimLongPassCard.getPassLine())){
            	String passLineIds = dimLongPassCard.getPassLine();
            	int count = passLineIds.split(",").length;
            	List<String> authIdList = ServiceUtil.getService(DimPassLineDao.class).getPassLineAuthIdsByIds(passLineIds);
            	if(CollectionUtils.isNotEmpty(authIdList)){
            		String [] authIds = authIdList.get(0).split(",");
            		Map<String, Integer> authIdMap = new HashMap<>();
            		for (int i=0; i<authIds.length; i++) {
            			String authId = authIds[i];
						Integer idCount = authIdMap.get(authId);
						if(idCount == null){
							authIdMap.put(authId, 1);
						} else{
							idCount++;
							authIdMap.put(authId, idCount);
						}
					}
            		StringBuilder idSB = new StringBuilder(",");
            		for(Map.Entry<String, Integer> entry : authIdMap.entrySet()){
            			if(entry.getValue() == count){
            				idSB.append(entry.getKey()).append(",");
            			}
            		}
            		/*if(StringUtils.isNotEmpty(idSB)){
            			idSB.deleteCharAt(idSB.length()-1);
            		}*/
            		dimLongPassCard.setAuthId(idSB.toString());
            	}
            }
            
            ServiceUtil.getService(DimLongPassCardDao.class).addLongPassCard(dimLongPassCard);
            return dimLongPassCard.getCardId();
        }
    }
    
    /**
     * <一句话功能简述> 长期、临时通行证公共属性封装
     * 
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    private DimPassCard passCardCommon(PassCardRequest passCardRequest, DimPassCard dimPassCard)
    {
        String diverLicenseUrl = passCardRequest.getDiverLicenseUrl();
        String drivingLicenseUrl = passCardRequest.getDrivingLicenseUrl();
        String insuranceUrl = passCardRequest.getInsuranceUrl();
        
        diverLicenseUrl = StringUtils.replace(diverLicenseUrl, "58.211.234.126:8082", "172.22.227.12:8080");
        drivingLicenseUrl = StringUtils.replace(drivingLicenseUrl, "58.211.234.126:8082", "172.22.227.12:8080");
        insuranceUrl = StringUtils.replace(insuranceUrl, "58.211.234.126:8082", "172.22.227.12:8080");
        
        DimPassCard passCard = dimPassCard;
        passCard.setCardId(UUID.randomUUID().toString());
        passCard.setLicensePlate(passCardRequest.getLicensePlate());
        passCard.setPlateTypeId(passCardRequest.getPlateTypeId());
        passCard.setForbidType(passCardRequest.getForbidType());
        passCard.setBeginDate(passCardRequest.getBeginDate());
        passCard.setEndDate(passCardRequest.getEndDate());
        passCard.setPassPeriod(passCardRequest.getPassPeriod());
        passCard.setPassLine(passCardRequest.getPassLine());
        passCard.setForbidLine(passCardRequest.getForbidLine());
        passCard.setApplyDate(DateUtils.getCurrentTime());
        passCard.setApplyType(passCardRequest.getApplyType());
        passCard.setOwnerName(passCardRequest.getOwnerName());
        passCard.setContactPeople(passCardRequest.getContactPeople());
        passCard.setAddress(passCardRequest.getAddress());
        passCard.setPhoneNumber(passCardRequest.getPhoneNumer());
        passCard.setIdCard(passCardRequest.getIdCard());
        passCard.setPrint(0);
        passCard.setXszUrl(drivingLicenseUrl);
        passCard.setJszUrl(diverLicenseUrl);
        passCard.setBxUrl(insuranceUrl);
        passCard.setSfzUrl(passCardRequest.getIdCardUrl());
        passCard.setYyzzUrl(passCardRequest.getBusinessLicenceUrl());
        passCard.setGhhtUrl(passCardRequest.getSupplyContractUrl());
        passCard.setYyzUrl(passCardRequest.getEscortCardUrl());
        passCard.setDescription(passCardRequest.getDescription());
        return passCard;
    }
    
    /**
     * 长期通行证时段转换 <一句话功能简述> <功能详细描述>
     * 
     * @param period 格式： 1,2,3,4,5,6,7 1表示1:00-2:00，其他类似
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String longCardPeriodTrans(String period)
    {
        String showPeriod = null;
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        List<DimSysConfig> longPeriodList = commonService.getLongPeriod();
        for (DimSysConfig longPeriod : longPeriodList)
        {
            // 与配置的通用时间段（全时段、避高峰）相同，则显示对于的名称（全时段、避高峰）
            if (longPeriod.getConfigValue().equals(period))
            {
                showPeriod = longPeriod.getConfigName();
            }
        }
        if (showPeriod == null)
        {
            showPeriod = timePeriodShow(period);
        }
        
        return showPeriod;
        
    }
    
    /**
     * 临时通行证时段转换 <一句话功能简述> <功能详细描述>
     * 
     * @param period （格式 20150629:3,4,5,7;20150630:1,2）
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String tempCardPeriodTrans(String period)
    {
        StringBuilder periodBuilder = new StringBuilder();
        String[] daysArray = StringUtils.split(period, ";");
        if (daysArray.length == 1)
        {
            String[] dayArray = StringUtils.split(daysArray[0], ":");
            if (dayArray.length == 2)
            {
                String dateString = CommonUtil.intDateFormater1(Integer.parseInt(dayArray[0]));
                String time = dayArray[1];
                periodBuilder.append(dateString);
                periodBuilder.append(":");
                periodBuilder.append(timePeriodShow(time));
            }
        }
        else
        {
            // 若临时通行证有两天，则加上日期一起显示
            for (int i = 0; i < daysArray.length; i++)
            {
                String[] dayArray = StringUtils.split(daysArray[i], ":");
                if (dayArray.length != 2)
                {
                    continue;
                }
                else
                {
                    String dateString = CommonUtil.intDateFormater1(Integer.parseInt(dayArray[0]));
                    String time = dayArray[1];
                    periodBuilder.append(dateString);
                    periodBuilder.append(":");
                    periodBuilder.append(timePeriodShow(time));
                }
                if (i != daysArray.length - 1)
                {
                    periodBuilder.append(";  ");
                }
            }
        }
        
        return periodBuilder.toString();
        
    }
    
    /**
     * 通行时段（时间）显示（连续的两个时段需要合并显示） <一句话功能简述> <功能详细描述>
     * 
     * @param period 2,3,4,7,8,9 转换为 2:00-5:00,7:00-10:00
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String timePeriodShow(String period)
    {
        List<String> periodList = new ArrayList<String>();
        
        String[] periodArray = StringUtils.split(period, ",");
        List<Integer> periodIntList = new ArrayList<Integer>();
        for (int i = 0; i < periodArray.length; i++)
        {
            periodIntList.add(Integer.parseInt(periodArray[i]));
        }
        Collections.sort(periodIntList);
        
        List<Integer> tempList = new ArrayList<Integer>();
        for (int i = 0; i < periodIntList.size(); i++)
        {
            tempList.add(periodIntList.get(i));
            int size = tempList.size();
            if (size < 2)
            {
                continue;
            }
            else
            {
                int last = tempList.get(size - 1);
                int secLast = tempList.get(size - 2);
                // tempList中最后两个时间是否连续，若连续则继续添加，若不连续，生成这段时间的结果
                if (last - secLast == 1)
                {
                    continue;
                }
                else
                {
                    int first = tempList.get(0);
                    periodList.add(timeFormat(first, secLast));
                    
                    tempList.clear();
                    tempList.add(last);
                }
            }
        }
        // 最后一个时间段处理
        periodList.add(timeFormat(tempList.get(0), tempList.get(tempList.size() - 1)));
        
        StringBuilder periodBuilder = new StringBuilder();
        for (int i = 0; i < periodList.size(); i++)
        {
            periodBuilder.append(periodList.get(i));
            if (i != periodList.size() - 1)
            {
                periodBuilder.append(",");
            }
            
        }
        return periodBuilder.toString();
    }
    
    /**
     * 把定义的数字转换为时间段显示（1,1转为01:00-02:00；1,2 转为01:00-03:00） <一句话功能简述> <功能详细描述>
     * 
     * @param start
     * @param end
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String timeFormat(int start, int end)
    {
        DecimalFormat format = new DecimalFormat("00");
        return format.format(start) + ":00-" + format.format(end + 1) + ":00";
        
    }
    
    @Override
    public void updateZXKProve(UpdatePassCardReq req)
    {
        DimLongPassCardDao passCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
        
        String[] cardIdArray = StringUtils.split(req.getCardId(), ",");
        for (String cardId : cardIdArray)
        {
            DimLongPassCard longPassCard = new DimLongPassCard();
            longPassCard.setCardId(cardId);
            longPassCard.setZxkOpinion(req.getOpinion());
            longPassCard.setZxkDate(DateUtils.getCurrentTime());
            
            int result = req.getResult();
            int status = -1;
            if (result == 0)
            {
                status = UNPASS_STATUS;
            }
            else
            {
                status = DD_STATUS;
            }
            longPassCard.setStatus(status);
            
            passCardDao.updateZXKProve(longPassCard);
        }
    }
    
    @Override
    public void updateDDProve(UpdatePassCardReq req)
    {
        
        DimLongPassCardDao passCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
        
        String[] cardIdArray = StringUtils.split(req.getCardId(), ",");
        for (String cardId : cardIdArray)
        {
            DimLongPassCard dbLongCard = passCardDao.getLongPassCardById(cardId);
            if (dbLongCard.getStatus() != DD_STATUS)
            {
                continue;
            }
            DimLongPassCard longPassCard = new DimLongPassCard();
            longPassCard.setCardId(cardId);
            longPassCard.setDdOpinion(req.getOpinion());
            longPassCard.setDdDate(DateUtils.getCurrentTime());
            
            int result = req.getResult();
            int status = -1;
            if (result == 0)
            {
                status = UNPASS_STATUS;
            }
            else
            {
                status = PASS_STATUS;
            }
            longPassCard.setStatus(status);
            
            passCardDao.updateDDProve(longPassCard);
        }
    }
    
    @Override
    public void updatePassCardPrint(UpdatePassCardReq req)
    {
        int cardType = req.getCardTypeId();
        if (cardType == TEMP_CARD_TYPE)// 临时通行证
        {
            DimTempPassCardDao tempCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
            
            DimTempPassCard tempCard = new DimTempPassCard();
            tempCard.setCardId(req.getCardId());
            tempCard.setPrint(req.getPrint());
            
            tempCardDao.updateTempPrint(tempCard);
        }
        else if (cardType == LONG_CARD_TYPE)
        {// 长期通行证
            DimLongPassCardDao longCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
            
            DimLongPassCard longCard = new DimLongPassCard();
            longCard.setCardId(req.getCardId());
            longCard.setPrint(req.getPrint());
            
            longCardDao.updateLongPrint(longCard);
        }
        
    }
    
    @Override
    public List<String> passCardPrint(String cardId, int cardType)
    {
        ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
        String preUrl = cfgService.getConfig("sys.preUrl");
        
        return cardPrint(cardId, cardType, preUrl);
    }
    
    @Override
    public List<String> weiXinPrint(String cardId, int cardType)
    {
        ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
        String preUrl = cfgService.getConfig("sys.weixinPreUrl");
        
        return cardPrint(cardId, cardType, preUrl);
    }
    
    public static void main(String[] args)
    {
        /*//String cardLongDir = "G:\\workspace\\pingxiang\\code\\build\\target\\apache-tomcat-7.0.57\\data\\long.jpg";
        //String cardTempDir = "G:\\workspace\\pingxiang\\code\\build\\target\\apache-tomcat-7.0.57\\data\\temp.jpg";
        String longPassCardSeq = "87";
        int longPassCardInt = Integer.parseInt(longPassCardSeq);
        //String tempPassCardSeq = "100";
        String cardTypeFlag = "long";
        String complementary = "0";
        //int tempPassCardInt = tempPassCardSeq.length();
        if(cardTypeFlag.equals(CARD_LONG) && longPassCardSeq.length() < IDENTIFY_LENGTH)
        {
            complementary =  String.format("%07d", longPassCardInt);
        }
        
        // 编号
        PicWrod codeWrod = new PicWrod(3500, 580, complementary);
        codeWrod.setFont(front_font);
        codeWrod.setWordColor(Color.RED);
        
        String baseCardDir = "G:\\workspace\\pingxiang\\code\\build\\target\\apache-tomcat-7.0.57\\data";
        List<PicWrod> frontList = new ArrayList<PicWrod>();
        frontList.add(codeWrod);
        
        try
        {
            ImageUtils.picWriteWords(baseCardDir, DateUtils.getCurrentTime() + "-" + "long.jpg", frontList);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }*/
    	System.out.println(new PassCardServiceImpl().tempCardPeriodTrans("20151202:9,10,11"));
    }
    
    /**
     * 通行证打印 <一句话功能简述> <功能详细描述>
     * 
     * @param cardId
     * @param cardType
     * @param preUrl
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<String> cardPrint(String cardId, int cardType, String preUrl)
    {
        List<String> retList = new ArrayList<String>();
        
        ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
        String targetCardDir = cfgService.getConfig("sys.targetCardDir");
        
        CardPrintInfo cardPrintInfo = null;
        DimPassCard dimPassCard = null;
        String period = null;
        String cardTypeFlag = null;
        
        DimLongPassCardDao longPassCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
        DimTempPassCardDao tempPassCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
        if (cardType == TEMP_CARD_TYPE)
        {
            dimPassCard = tempPassCardDao.getTempPassCardById(cardId);
            
            if (dimPassCard != null && StringUtils.isNotBlank(dimPassCard.getCardId()))
            {
                period = tempCardPeriodTrans(dimPassCard.getPassPeriod());
            }
            
            cardTypeFlag = CARD_TEMP;
        }
        else if (cardType == LONG_CARD_TYPE)
        {
            dimPassCard = longPassCardDao.getLongPassCardById(cardId);
            
            if (dimPassCard != null && StringUtils.isNotBlank(dimPassCard.getCardId()))
            {
                period = longCardPeriodTrans(dimPassCard.getPassPeriod());
            }
            
            cardTypeFlag = CARD_LONG;
        }
        
        if (dimPassCard == null || StringUtils.isBlank(dimPassCard.getCardId()))
        {
            return retList;
        }
        
        // 图片是否已经存在
        int beginDate = dimPassCard.getBeginDate();
        String cardDir = createFileDir(targetCardDir, beginDate, cardId, cardTypeFlag);
        // String cardBackDir = createFileDir(targetCardDir, beginDate, cardId, CARD_BACK);
        if (FileUtils.getFile(cardDir).exists())
        {
            retList.add(createPicUrl(preUrl, beginDate, cardId, cardTypeFlag));
            // retList.add(createPicUrl(preUrl, beginDate, cardId, CARD_BACK));
            return retList;
        }
        else
        {
            cardPrintInfo = getPrintInfo(dimPassCard,preUrl);
            cardPrintInfo.setPeriod(period);
            
            if (cardPrintInfo != null)
            {
                retList.addAll(createCard(cfgService, cardPrintInfo, preUrl, cardTypeFlag));
            }
            return retList;
        }
    }
    
    /**
     * 构造通行证图片位置 <一句话功能简述> <功能详细描述>
     * 
     * @param targetDir
     * @param beginDate
     * @param cardId
     * @param frontBack
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String createFileDir(String targetDir, int beginDate, String cardId, String frontBack)
    {
        StringBuilder dirBuilder = new StringBuilder();
        dirBuilder.append(targetDir);
        dirBuilder.append("\\");
        dirBuilder.append(beginDate / 100);
        dirBuilder.append("\\");
        dirBuilder.append(cardId);
        dirBuilder.append("_");
        dirBuilder.append(frontBack);
        dirBuilder.append(".jpg");
        
        return dirBuilder.toString();
    }
    
    /**
     * 构造通行证图片url <一句话功能简述> <功能详细描述>
     * 
     * @param beginDate
     * @param cardId
     * @param cardTypeFlag
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String createPicUrl(String preUrl, int beginDate, String cardId, String cardTypeFlag)
    {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder.append(preUrl);
        urlBuilder.append("/");
        urlBuilder.append(beginDate / 100);
        urlBuilder.append("/");
        urlBuilder.append(cardId);
        urlBuilder.append("_");
        urlBuilder.append(cardTypeFlag);
        urlBuilder.append(".jpg");
        
        return urlBuilder.toString();
    }
    
    /**
     * 获取通行证打印内容 <一句话功能简述> <功能详细描述>
     * 
     * @param passCard
     * @return
     * @see [类、类#方法、类#成员]
     */
    private CardPrintInfo getPrintInfo(DimPassCard passCard,String preUrl)
    {
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
        String weixinPreUrl = cfgService.getConfig("sys.weixinPreUrl");
        CardPrintInfo printInfo = new CardPrintInfo();
        printInfo.setCardId(passCard.getCardId());
        printInfo.setOwner(passCard.getOwnerName());
        printInfo.setLicensePlate(passCard.getLicensePlate());
        
        String plateTypeId = passCard.getPlateTypeId();
        String plateTypeName = commonService.getPlateTypeName(plateTypeId);
        printInfo.setPlateType(plateTypeName);
        
        List<DimForbidArea> areaList = commonService.getForbidAreaList(TRUCK_FORBID_AREA);
        StringBuilder forbidArea = new StringBuilder();
        //微信端申请的通行证禁行区域过滤掉吴江大道
        /*if(StringUtils.equals(weixinPreUrl,preUrl)&&CollectionUtils.isNotEmpty(areaList))
        {
        	for(Iterator<DimForbidArea> it=areaList.iterator();it.hasNext();)
    		{
    			DimForbidArea area = (DimForbidArea) it.next();
    			if(area.getAreaId()==AREA_ID_LIST[0]||area.getAreaId()==AREA_ID_LIST[1])
    			{
    				it.remove();
    			}
    		}
        }
        if (CollectionUtils.isNotEmpty(areaList))
        {
        	for (int i = 0; i < areaList.size(); i++)
            {
            	forbidArea.append(areaList.get(i).getAreaName());
                if (i != areaList.size() - 1)
                {
                    forbidArea.append(";");
                }
            }
        }*/
        //微信端申请的通行证禁行区域只显示松陵禁货区
//        if(StringUtils.equals(weixinPreUrl,preUrl)&&CollectionUtils.isNotEmpty(areaList))
//        {
//        	for(DimForbidArea area:areaList)
//    		{
//    			if(area.getAreaId()==WJ_AREA_ID)
//    			{
//    				forbidArea.append(area.getAreaName());
//    				break;
//    			}
//    		}
//        }
//        else 微信端暂时和长期通行证一样显示所有的货车禁行区域
        if (CollectionUtils.isNotEmpty(areaList))
        {
        	for (int i = 0; i < areaList.size(); i++)
            {
            	forbidArea.append(areaList.get(i).getAreaName());
                if (i != areaList.size() - 1)
                {
                    forbidArea.append(";");
                }
            }
        }
        printInfo.setForbidAreaName(forbidArea.toString());
        
        int beginDate = passCard.getBeginDate();
        int endDate = passCard.getEndDate();
        printInfo.setBeginDate(beginDate);
        printInfo.setStartDate(CommonUtil.intDateFormater1(beginDate));
        if (beginDate != endDate)
        {
            printInfo.setEndDate(CommonUtil.intDateFormater1(endDate));
        }
        printInfo.setForbidInfo(getForbidLineInfo(passCard.getForbidLine()));
//        List<DimPassLine> passLineList = commonService.getPassLineById(passCard.getPassLine());
        String passLineName =
                ServiceUtil.getService(CommonService.class).getPassLineName(passCard.getPassLine());
        if (StringUtils.isNotEmpty(passLineName))
        {
        	printInfo.setLineInfo(passLineName);
        }
        
        return printInfo;
    }
    
    /**
     * 生成通行证图片 <一句话功能简述> <功能详细描述>
     * 
     * @param cfgService
     * @param printInfo
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<String> createCard(ConfigureService cfgService, CardPrintInfo printInfo, String preUrl,
        String cardTypeFlag)
    {
        List<String> retList = new ArrayList<String>();
        
        String baseCardDir = null;
        //补0标识字段
        String complementary = "0";
        //长期编号标识序列
        String longPassCardSEQ = ServiceUtil.getService(DimLongPassCardDao.class).getLongPassCardSEQ();
        int longPassCardInt = Integer.parseInt(longPassCardSEQ);
        //临时编号标识序列
        String tempPassCardSEQ = ServiceUtil.getService(DimTempPassCardDao.class).getTempPassCardSEQ();
        int tempPassCardInt = Integer.parseInt(tempPassCardSEQ);
        
        if (cardTypeFlag.equals(CARD_LONG))
        {
            baseCardDir = cfgService.getConfig("sys.baseCardLongDir");
            if(longPassCardSEQ.length() < IDENTIFY_LENGTH)
            {
               complementary =  String.format("%07d", longPassCardInt);
            }
            else
            {
                complementary =  longPassCardSEQ;
            }
        }
        else if (cardTypeFlag.equals(CARD_TEMP))
        {
            baseCardDir = cfgService.getConfig("sys.baseCardTempDir");
            if(tempPassCardSEQ.length() < IDENTIFY_LENGTH)
            {
               complementary =  String.format("%07d", tempPassCardInt);
            }
            else
            {
                complementary = tempPassCardSEQ;
            }
        }
        
        String targetCardDir = cfgService.getConfig("sys.targetCardDir");
        
        int[] codePos = configToXYArray(cfgService.getConfig("sys.codePos"));
        int[] ownerPos = configToXYArray(cfgService.getConfig("sys.ownerPos"));
        int[] carNoPos = configToXYArray(cfgService.getConfig("sys.carNoPos"));
        int[] carTypePos = configToXYArray(cfgService.getConfig("sys.carTypePos"));
        
        int[] forbidAreaPos = configToXYArray(cfgService.getConfig("sys.forbidAreaPos"));
        int[] lineInfoPos = configToXYArray(cfgService.getConfig("sys.lineInfoPos"));
        int[] validDatePos = configToXYArray(cfgService.getConfig("sys.validDatePos"));
        int[] forbidLinePos = configToXYArray(cfgService.getConfig("sys.forbidLinePos"));
        
        List<PicWrod> frontList = new ArrayList<PicWrod>();
        
        //编号
        PicWrod codeWrod = new PicWrod(codePos[0], codePos[1], complementary);
        codeWrod.setFont(new Font("黑体", Font.PLAIN, 85));
        codeWrod.setWordColor(Color.RED);
        
        // 驾驶人
        PicWrod ownerWrod = new PicWrod(ownerPos[0], ownerPos[1], "驾  驶   人：" + printInfo.getOwner());
        ownerWrod.setFont(front_font);
        ownerWrod.setWordColor(Color.BLACK);
        // 车牌号
        PicWrod carnoWrod = new PicWrod(carNoPos[0], carNoPos[1], "车  牌   号：" + printInfo.getLicensePlate());
        carnoWrod.setFont(front_font);
        carnoWrod.setWordColor(Color.BLACK);
        // 车牌类型
        PicWrod carTypeWrod = new PicWrod(carTypePos[0], carTypePos[1], "车 牌 类 型：" + printInfo.getPlateType());
        carTypeWrod.setFont(front_font);
        carTypeWrod.setWordColor(Color.BLACK);
        // 禁区名称 去掉不显示
        /*PicWrod forbidAreaWrod = new PicWrod(forbidAreaPos[0], forbidAreaPos[1], "禁 区 名 称：" + printInfo.getForbidAreaName());
        forbidAreaWrod.setFont(front_font);
        forbidAreaWrod.setWordColor(Color.BLACK);*/
        
        // 线路
        List<PicWrod> passLineWrodList =
            modifyLineInfo("通 行 线 路：" + printInfo.getLineInfo(), lineInfoPos[0], lineInfoPos[1], Color.BLACK);
        
        // 通行时间
        StringBuilder validDate = new StringBuilder();
        if (cardTypeFlag.equals(CARD_LONG))
        {
            validDate.append("本证有效 期：" + printInfo.getStartDate());
            if (StringUtils.isNotBlank(printInfo.getEndDate()))
            {
                validDate.append("-" + printInfo.getEndDate());
            }
            validDate.append("；" + printInfo.getPeriod());
        }
        else if (cardTypeFlag.equals(CARD_TEMP))
        {
            validDate.append("本证有效 期：");
            validDate.append(printInfo.getPeriod());
        }
        
        int validDateY = lineInfoPos[1] + passLineWrodList.size() * ROW_DISTANCE;
        List<PicWrod> validDateList = modifyLineInfo(validDate.toString(), validDatePos[0], validDateY, Color.BLACK);
        // PicWrod startDateWrod = new PicWrod(validDatePos[0], validDateY, validDate.toString());
        // startDateWrod.setFont(front_font);
        // startDateWrod.setWordColor(Color.BLACK);
        
        int forbidLineY = validDateY + validDateList.size() * ROW_DISTANCE;
        List<PicWrod> forbidLineWrodList =
            modifyLineInfo("备       注：" + printInfo.getForbidInfo(), forbidLinePos[0], forbidLineY, Color.RED);
        
        frontList.add(codeWrod);
        frontList.add(ownerWrod);
        frontList.add(carnoWrod);
        frontList.add(carTypeWrod);
        
        //frontList.add(forbidAreaWrod);
        frontList.addAll(passLineWrodList);
        frontList.addAll(validDateList);
        frontList.addAll(forbidLineWrodList);
        
        String frontDir = createFileDir(targetCardDir, printInfo.getBeginDate(), printInfo.getCardId(), cardTypeFlag);
        
        try
        {
            String dir = targetCardDir + "\\" + printInfo.getBeginDate() / 100;
            FileUtils.getFile(dir).mkdir();
            ImageUtils.picWriteWords(baseCardDir, frontDir, frontList);
        }
        catch (IOException e)
        {
            logger.error("picWriteWords error", e);
            e.printStackTrace();
        }
        retList.add(createPicUrl(preUrl, printInfo.getBeginDate(), printInfo.getCardId(), cardTypeFlag));
        return retList;
        
    }
    
    /**
     * 通行线路或禁行线路分行（用于打印） <一句话功能简述> <功能详细描述>
     * 
     * @param lineInfo
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<PicWrod> modifyLineInfo(String lineInfo, int x, int y, Color color)
    {
        List<PicWrod> lineWrodList = new ArrayList<PicWrod>();
        
        int length = lineInfo.length();
        int rows = length / ROW_WORDS + (length % ROW_WORDS == 0 ? 0 : 1);
        
        for (int i = 0; i < rows - 1; i++)
        {
            String line = lineInfo.substring(i * ROW_WORDS, (i + 1) * ROW_WORDS);
            PicWrod lineWrod = new PicWrod(x, y + i * ROW_DISTANCE, line);
            lineWrod.setFont(front_font);
            lineWrod.setWordColor(color);
            lineWrodList.add(lineWrod);
            
        }
        String lastLine = lineInfo.substring((rows - 1) * ROW_WORDS);
        PicWrod lastWrod = new PicWrod(x, y + (rows - 1) * ROW_DISTANCE, lastLine);
        lastWrod.setFont(front_font);
        lastWrod.setWordColor(color);
        lineWrodList.add(lastWrod);
        
        return lineWrodList;
    }
    
    /**
     * 配置文件的xy坐标转换 <一句话功能简述> <功能详细描述>
     * 
     * @param config
     * @return
     * @see [类、类#方法、类#成员]
     */
    private int[] configToXYArray(String config)
    {
        String[] configArray = StringUtils.split(config, ",");
        int[] xyArray = new int[2];
        if (configArray != null && configArray.length == 2)
        {
            xyArray[0] = Integer.parseInt(configArray[0]);
            xyArray[1] = Integer.parseInt(configArray[1]);
        }
        return xyArray;
    }
    
    /**
     * 获取禁行线路的详细内容 <一句话功能简述> <功能详细描述>
     * 
     * @param forbidLines
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getForbidLineInfo(String forbidLines)
    {
        CommonService commonService = ServiceUtil.getService(CommonService.class);
        List<DimForbidLine> forbidLineList = commonService.getForbidLinesByIds(forbidLines);
        StringBuilder forbidInfoBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(forbidLineList))
        {
            for (int i = 0; i < forbidLineList.size(); i++)
            {
                DimForbidLine forbidLine = forbidLineList.get(i);
                forbidInfoBuilder.append(forbidLine.getLineName());
                forbidInfoBuilder.append("—");
                forbidInfoBuilder.append(forbidLine.getDescription());
                if (i != forbidLineList.size() - 1)
                {
                    forbidInfoBuilder.append("；");
                }
            }
        }
        return forbidInfoBuilder.toString();
        
    }
    
    /**
     * 线路信息转换为标准输出 举例：4/A,3/A,2/A,2/B,2/C,C/3,C/4 转换为 A(4 到 2),2(A 到 C),C(2 到 4) <一句话功能简述> <功能详细描述>
     * 
     * @param intStringList 交叉口名称（中间英文逗号隔开）
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String getLineInfo(String intStringList)
    {
        List<List<Intersection>> retList = new ArrayList<>();
        String[] intArray = StringUtils.split(intStringList, ",");
        List<Intersection> intersectList = transInt(intArray);
        
        // 如果交叉口不连续，输出交叉口名称
        if (!isContinuous(intersectList))
        {
            return intStringList;
        }
        
        Intersection startInt = intersectList.get(0);
        String sameRoad = null;
        for (int i = 1; i < intersectList.size();)
        {
            Intersection intersect = intersectList.get(i);
            sameRoad = startInt.getSameRoad(intersect);
            
            // 连续的线路每个路段上至少有两个交叉口
            List<Intersection> sectionList = new ArrayList<>();
            sectionList.add(replaceBySameRoad(startInt, sameRoad));
            sectionList.add(replaceBySameRoad(intersect, sameRoad));
            for (int j = i + 1; j < intersectList.size(); j++)
            {
                Intersection intersectj = intersectList.get(j);
                String sameRoadj = startInt.getSameRoad(intersectj);
                
                // 与起始交叉口在同一路段
                if (sameRoadj != null && sameRoadj.equals(sameRoad))
                {
                    sectionList.add(replaceBySameRoad(intersectj, sameRoad));
                    // 遍历到最后一个交叉口，特殊处理
                    if (j == intersectList.size() - 1)
                    {
                        retList.add(sectionList);
                        i = j + 1;
                    }
                    
                }
                else
                {
                    // 路段改变
                    startInt = intersectList.get(j - 1);
                    i = j;
                    retList.add(sectionList);
                    break;
                }
            }
        }
        
        StringBuilder lineBuilder = new StringBuilder();
        for (int i = 0; i < retList.size(); i++)
        {
            lineBuilder.append(transSection(retList.get(i)));
            if (i != retList.size() - 1)
            {
                lineBuilder.append("，");
            }
        }
        
        return lineBuilder.toString();
        
    }
    
    /**
     * 交叉口list组成的线路是否连续 <一句话功能简述> <功能详细描述>
     * 
     * @param intersectList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private boolean isContinuous(List<Intersection> intersectList)
    {
        if (intersectList.size() < 2)
        {
            return false;
        }
        Intersection formerInt = intersectList.get(0);
        for (int i = 1; i < intersectList.size(); i++)
        {
            Intersection intersection = intersectList.get(i);
            // 若线路连续，则相邻的两个交叉口必须在同一道路上
            if (formerInt.getSameRoad(intersection) == null)
            {
                return false;
            }
            else
            {
                formerInt = intersection;
            }
        }
        return true;
        
    }
    
    /**
     * 路段名称设为road1（方便输出） <一句话功能简述> <功能详细描述>
     * 
     * @param intersection
     * @param sameRoad
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Intersection replaceBySameRoad(Intersection intersection, String sameRoad)
    {
        Intersection retInt = new Intersection();
        if (intersection.getRoad1().equals(sameRoad))
        {
            retInt.setRoad1(intersection.getRoad1());
            retInt.setRoad2(intersection.getRoad2());
        }
        else
        {
            retInt.setRoad1(intersection.getRoad2());
            retInt.setRoad2(intersection.getRoad1());
        }
        return retInt;
    }
    
    /**
     * 线路具体描述 <一句话功能简述> <功能详细描述>
     * 
     * @param sectionList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String transSection(List<Intersection> sectionList)
    {
        Intersection startInt = sectionList.get(0);
        Intersection endInt = sectionList.get(sectionList.size() - 1);
        
        String sameRoad = startInt.getRoad1();
        StringBuilder sectionBuilder = new StringBuilder(sameRoad);
        sectionBuilder.append("（").append(startInt.getRoad2());
        sectionBuilder.append(" → ");
        sectionBuilder.append(endInt.getRoad2()).append("）");
        
        return sectionBuilder.toString();
        
    }
    
    /**
     * 交叉口转换为intersection对象 <一句话功能简述> <功能详细描述>
     * 
     * @param intList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private List<Intersection> transInt(String[] intList)
    {
        List<Intersection> retList = new ArrayList<>();
        for (String intString : intList)
        {
            String[] intArray = StringUtils.split(intString, "/");
            if (intArray.length < 2)
            {
                break;
            }
            else
            {
                Intersection intersection = new Intersection(intArray[0], intArray[1]);
                retList.add(intersection);
            }
        }
        return retList;
    }
    
    /**
     * @param passCardList
     * @return
     */
    @Override
    public ByteArrayOutputStream buildPassCardExcelBody(List<PassCard> passCardList)
    {
        // 创建EXCEL
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 构造sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workbook, "通行证基本信息");
        
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行证编号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆种类");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "重点车辆类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "开始日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "结束日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行时段");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行路线");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "禁行路线");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "申请日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "申请类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车主姓名");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "联系人姓名");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "地址");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "联系电话");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "身份证号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "打印状态");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "秩序科审核时间");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "大队领导审核时间");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "秩序科审核意见");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "大队领导审核意见");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "行驶证扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "驾驶证扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "保险保单扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "身份证扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "营业执照扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "供货合同扫描件");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "押运员押运证扫描件");
        
        if (CollectionUtils.isNotEmpty(passCardList))
        {
            rowIndex = 1;
            
            for (PassCard res : passCardList)
            {
                columnIndex = 0;
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getCardId());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getLicensePlate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPlateTypeId());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getForbidType());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getBeginDate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getEndDate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPassPeriod());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPassLine());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getForbidLine());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getApplyDate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getApplyType());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getOwnerName());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getContactPeople());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getAddress());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPhoneNumber());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getIdCard());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getPrint());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getZxkDate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getDdDate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getZxkOpinion());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getDdOpinion());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getXszUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getJszUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getBxUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getSfzUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getYyzzUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getGhhtUrl());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getYyzUrl());
                
                rowIndex++;
            }
        }
        return ExcelUtil.getByteArrayOutputStream(workbook);
    }
    
    /**
     * 临时通行时段显示
     * 
     * @param passPeriod
     * @return
     */
    @Override
    public String tempCardPeriodShow(String passPeriod)
    {
        return ServiceUtil.getService(PassCardServiceImpl.class).tempCardPeriodTrans(passPeriod);
    }
    
    /**
     * 长期通行时段显示
     * 
     * @param passPeriod
     * @return
     */
    @Override
    public String longCardPeriodShow(String passPeriod)
    {
        return ServiceUtil.getService(PassCardServiceImpl.class).longCardPeriodTrans(passPeriod);
    }
    
    /**
     * 封装发短信内容
     * 
     * @param passCardRequest
     * @return
     * @throws RemoteException
     * @throws ParseException
     * @throws MalformedURLException
     */
    @Override
    public boolean smsNotice(PassCardRequest passCardRequest)
        throws RemoteException, MalformedURLException
    {
        // 封装短信通知公共内容
        String content = buildContent(passCardRequest);
        StringBuilder strBuilder = new StringBuilder(content);
        
        String passPeriod = passCardRequest.getPassPeriod();
        String phoneNumber = passCardRequest.getPhoneNumer();
        int cardType = passCardRequest.getCardType();
        // 临时
        if (cardType == 1)
        {
            String passPeriodShow = ServiceUtil.getService(PassCardService.class).tempCardPeriodShow(passPeriod);
            strBuilder.append(",通行证有效期:").append(passPeriodShow);
        }// 长期
        else if (cardType == 2)
        {
            String passPeriodShow = ServiceUtil.getService(PassCardService.class).longCardPeriodShow(passPeriod);
            // 日期格式：2015年12月09日
            String beginDate = String.valueOf(passCardRequest.getBeginDate());
            String endDate = String.valueOf(passCardRequest.getEndDate());
            String startDate1 =
                beginDate.substring(0, 4) + "年" + beginDate.substring(4, 6) + "月" + beginDate.substring(6, 8) + "日";
            
            String endDate1 =
                endDate.substring(0, 4) + "年" + endDate.substring(4, 6) + "月" + endDate.substring(6, 8) + "日";
            strBuilder.append(",通行证有效期:")
                .append(startDate1)
                .append("-")
                .append(endDate1)
                .append(";")
                .append(passPeriodShow);
        }
        // System.out.println(strBuilder.toString());
        logger.info(strBuilder.toString()+" "+phoneNumber);
        NoticeVo noticeVo=new NoticeVo();
    	noticeVo.setNoticeContent(strBuilder.toString());
    	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
    	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
    	if(ServiceUtil.getService(CommonService.class).checkSend())
        {
    		return ServiceUtil.getService(CommonService.class).smsInfo(strBuilder.toString(), phoneNumber);
        }
        return true;
    }
    
    // 封装短信通知公共内容
    private String buildContent(PassCardRequest passCardRequest)
    {
        String licensePlate = passCardRequest.getLicensePlate();
        String plateTypeName =
            ServiceUtil.getService(CommonService.class).getPlateTypeName(passCardRequest.getPlateTypeId());
        List<DimForbidArea> areaList = ServiceUtil.getService(CommonService.class).getForbidAreaList(0);
        StringBuilder strBuilder = new StringBuilder();
        if (CollectionUtils.isNotEmpty(areaList))
        {
            for (int i = 0; i < areaList.size(); i++)
            {
                strBuilder.append(areaList.get(i).getAreaName());
                if (i != areaList.size() - 1)
                {
                    strBuilder.append(";");
                }
            }
        }
        String passLineName =
            ServiceUtil.getService(CommonService.class).getPassLineName(passCardRequest.getPassLine());
        String content =
            "【吴江交警大队】 您好！您申请的车牌为" + licensePlate + "的车辆通行证，通行线路为:" + passLineName + "," + "车牌类型为:" + plateTypeName
                //+ "," + "禁区名称为:" + strBuilder.toString()
                ;
        return content;
    }
    
    /**
     * 将通行证录入信息绑定实现
     * 
     * @param passCardRequest
     */
    @Override
    public String boundCard(PassCardRequest passCardRequest)
    {
        DimCardUserInfo cardUserInfo = converTer(passCardRequest);
        DimCardUserInfoDao cardUserInfoDao = ServiceUtil.getService(DimCardUserInfoDao.class);
        try
        {
            DimCardUserInfo dbObj = cardUserInfoDao.getSameCardUserInfo(cardUserInfo);
            if (null != dbObj)
            {
                cardUserInfo.setStatus(dbObj.getStatus());
                cardUserInfoDao.deleteDimCardUserInfo(dbObj.getId());
            }
            if(StringUtils.isNotEmpty(cardUserInfo.getJszUrl()))
            {
            	cardUserInfo.setJszUrl(StringUtils.replace(cardUserInfo.getJszUrl(), "wjwx.szusoft.com:8082", "172.22.227.12:8080"));
            }
            if(StringUtils.isNotEmpty(cardUserInfo.getXszUrl()))
            {
            	cardUserInfo.setXszUrl(StringUtils.replace(cardUserInfo.getXszUrl(), "wjwx.szusoft.com:8082", "172.22.227.12:8080"));
            }
            cardUserInfoDao.addDimCardUserInfo(cardUserInfo);
            try
            {
            	String content = message(cardUserInfo);
            	CommonService service = ServiceUtil.getService(CommonService.class);
            	NoticeVo noticeVo=new NoticeVo();
            	noticeVo.setNoticeContent(content);
            	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
            	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
            	//查询参数表param是否配置发送短信
            	if(service.checkSend())
            	{
            		service.smsInfo(content,
                            cardUserInfo.getPhoneNumber());
            	}
            }
            catch (RemoteException | MalformedURLException e)
            {
                logger.error("send message fail!!", e);
            }
        }
        catch (JsztException e)
        {
            logger.debug("boundCard error!!", e);
        }
        return cardUserInfo.getId();
    }
    
    /**
     * 短信内容 <功能详细描述>
     * 
     * @param cardUserInfo
     * @return
     * @see [类、类#方法、类#成员]
     */
    private String message(DimCardUserInfo cardUserInfo)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("【吴江交警大队】");
        sb.append(cardUserInfo.getLicensePlate()).append("车主您好,您在");
        sb.append(DateUtils.formater(DateUtils.getCurrentTime(), "yyyy-MM-dd HH:mm"));
        sb.append("时刻,成功通过微信绑定通行证信息,我大队将会对通行证审核。");
        return sb.toString();
    }
    
    /**
     * 封装绑定信息 <功能详细描述>
     * 
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    private DimCardUserInfo converTer(PassCardRequest passCardRequest)
    {
        DimCardUserInfo cardUserInfo = new DimCardUserInfo();
        if (null != passCardRequest)
        {
            String diverLicenseUrl = passCardRequest.getDiverLicenseUrl();
            String drivingLicenseUrl = passCardRequest.getDrivingLicenseUrl();
            
            diverLicenseUrl = StringUtils.replace(diverLicenseUrl, "58.211.234.126:8082", "172.22.227.12:8080");
            drivingLicenseUrl = StringUtils.replace(drivingLicenseUrl, "58.211.234.126:8082", "172.22.227.12:8080");
            cardUserInfo.setId(UUID.randomUUID().toString());
            cardUserInfo.setLicensePlate(passCardRequest.getLicensePlate());
            cardUserInfo.setPlateTypeId(passCardRequest.getPlateTypeId());
            cardUserInfo.setOwnerName(passCardRequest.getOwnerName());
            cardUserInfo.setPhoneNumber(passCardRequest.getPhoneNumer());
            cardUserInfo.setIdCard(passCardRequest.getIdCard());
            cardUserInfo.setXszUrl(drivingLicenseUrl);
            cardUserInfo.setJszUrl(diverLicenseUrl);
            cardUserInfo.setStatus(0);
        }
        return cardUserInfo;
    }
    
    /**
     * 更新绑定通行证的信息
     * 
     * @param passCardRequest
     */
    @Override
    public void updateCardUserInfo(PassCardRequest passCardRequest)
    {
        DimCardUserInfo cardUserInfo = converTer(passCardRequest);
        if (null != cardUserInfo)
        {
            try
            {
                ServiceUtil.getService(DimCardUserInfoDao.class).updateDimcardUserInfo(cardUserInfo);
            }
            catch (JsztException e)
            {
                logger.error("updateCardUserInfo fail!!", e);
            }
        }
    }
    
    /**
     * 获取绑定通行证的信息
     * 
     * @param passCardRequest
     * @return
     */
    @Override
    public DimCardUserInfo getDimCardUserInfo(PassCardRequest passCardRequest)
    {
        DimCardUserInfo cardUserInfo = converTer(passCardRequest);
        try
        {
            return ServiceUtil.getService(DimCardUserInfoDao.class).getDimCardUserInfo(cardUserInfo);
        }
        catch (JsztException e)
        {
            logger.error("getDimCardUserInfo fail!!", e);
        }
        return null;
    }
    
    /**
     * 删除绑定通行证实现
     * 
     * @param passCardRequest
     */
    @Override
    public void delCardUserInfo(DeleteCardUserInfo info)
    {
        try
        {
        	String[] idArray = StringUtils.split(info.getId(), ",");
            for (String id : idArray)
            {
            	ServiceUtil.getService(DimCardUserInfoDao.class).deleteDimCardUserInfo(id);
            }
        }
        catch (JsztException e)
        {
            logger.error("delCardUserInfo fail!!", e);
        }
    }
    
    /**
     * 审批实现
     * 
     * @param req
     */
    @Override
    public void updateProveResult(UpdatePassCardReq req)
    {
        try
        {
            DimCardUserInfoDao cardUserInfoDao = ServiceUtil.getService(DimCardUserInfoDao.class);
            
            String[] cardIdArray = StringUtils.split(req.getCardId(), ",");
            for (String cardId : cardIdArray)
            {
                DimCardUserInfo userInfo = new DimCardUserInfo();
                userInfo.setId(cardId);
                
                int result = req.getResult();
                int status = -1;
                if (result == 0)
                {
                    status = UNPASS_STATUS;
                }
                else
                {
                    status = PASS_STATUS;
                }
                userInfo.setStatus(status);
                cardUserInfoDao.updateDimcardUserInfo(userInfo);
                DimCardUserInfo dbObj = cardUserInfoDao.getDimCardUserInfoByKey(cardId);
                //查询参数表param是否配置发送短信
                if(ServiceUtil.getService(CommonService.class).checkSend())
                {
                	String content = null;
                	try
                    {
                		
                        if (result == 0)
                        {
                        	content = "【吴江交警大队】" + dbObj.getLicensePlate()
                                    + "车主您好,很抱歉,您所绑定的通行证信息经我大队审批未能审批通过，感谢您对吴江交警的支持,谢谢。";
                            ServiceUtil.getService(CommonService.class).smsInfo("【吴江交警大队】" + dbObj.getLicensePlate()
                                + "车主您好,很抱歉,您所绑定的通行证信息经我大队审批未能审批通过，感谢您对吴江交警的支持,谢谢。",
                                dbObj.getPhoneNumber());
                        }
                        else
                        {
                        	content = "【吴江交警大队】" + dbObj.getLicensePlate()
                                    + "车主您好,您所绑定的通行证信息已通过我大队审批,感谢您对吴江交警的支持,谢谢。";
                            ServiceUtil.getService(CommonService.class).smsInfo("【吴江交警大队】" + dbObj.getLicensePlate()
                                + "车主您好,您所绑定的通行证信息已通过我大队审批,感谢您对吴江交警的支持,谢谢。",
                                dbObj.getPhoneNumber());
                        }
                    }
                    catch (RemoteException | MalformedURLException e)
                    {
                        e.printStackTrace();
                    }
                	NoticeVo noticeVo=new NoticeVo();
                	noticeVo.setNoticeContent(content);
                	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
                	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
                }
                
            }
        }
        catch (JsztException e)
        {
            
            logger.error("updateProveResult fail!!", e);
        }
    }
    
    /**
     * @param info
     * @return
     * @throws JsztException
     */
    @Override
    public List<DimCardUserInfo> getAllCardUserInfo(CardUserInfoReq info)
        throws JsztException
    {
        return ServiceUtil.getService(DimCardUserInfoDao.class).getAllDimcardUserInfo(info);
    }
    
    /**
     * 更新长期通行证时间
     * 
     * @param req
     * @throws JsztException
     */
    @Override
    public void updateLongPassCard(PassCardReq req)
        throws JsztException
    {
        ServiceUtil.getService(DimLongPassCardDao.class).updateLongPassCard(req);
    }
    
    @Override
    public List<PassCard> queryLongPassCardExtendHis(String cardId)
    {
        List<DimLongPassCard> longHis =
            ServiceUtil.getService(DimLongPassCardDao.class).queryLongPassCardExtendHis(cardId);
        List<PassCard> retList = new ArrayList<PassCard>(longHis.size());
        longPassCardBuild(retList, longHis);
        
        for (PassCard passCard : retList)
        {
            DimSysAccount dimSysAccount =
                ServiceUtil.getService(CommonDao.class).getSysAccount(passCard.getAccountId());
            if (dimSysAccount != null)
            {
                passCard.setAccountName(dimSysAccount.getAccountName());
            }
        }
        return retList;
    }
    
    /**
     * @param req
     * @return
     */
    @Override
    public List<TempPassCardCount> getTempPassCardCount(TempPassCardCountReq req)
        throws ServiceException
    {
        List<TempPassCardCount> cardCounts =null;
        Map<String, TempPassCardCount> map = new HashMap<String, TempPassCardCount>();
        // 取出按天统计申请总数
        List<TempCountPo> sumCount = null;
        // 取出月时间段类统计申请黄牌车数目
        List<TempCountPo> yellowCountList = null;
        try
        {
            DimTempPassCardDao cardDao = ServiceUtil.getService(DimTempPassCardDao.class);
            switch (req.getCountType())
            {
            // 按照小时统计
                case 1:
                    // 取出小时时间段类统计总数
                    int countHour = DateUtils.getDayBetween(req.getBeginDate(), req.getEndDate());
                    List<TempCountPo> sumDayCount = new ArrayList<TempCountPo>();
                    List<TempCountPo> yellowDayCountList = new ArrayList<TempCountPo>();
                    for (int i = 0; i <= countHour; i++)
                    {
                        String beginDate = DateUtils.adjustDay(req.getBeginDate() + "000000", i);
                        sumCount = cardDao.getHourTempCount(Integer.valueOf(beginDate.substring(0, 8)));
                        yellowCountList =
                            cardDao.getHourYellowTempCount(Integer.valueOf(beginDate.substring(0, 8)), "02");
                        sumDayCount.addAll(sumCount);
                        yellowDayCountList.addAll(yellowCountList);
                        for (int j = 0; j < 24; j++)
                        {
                            if (String.valueOf(j).length() == 1)
                            {
                                map.put(beginDate.substring(0, 8) + "0" + String.valueOf(j), new TempPassCardCount());
                            }
                            else
                            {
                                map.put(beginDate.substring(0, 8) + String.valueOf(j), new TempPassCardCount());
                            }
                        }
                        
                    }
                    buildCount(map, sumDayCount, yellowDayCountList);
                    
                    break;
                // 按照天统计
                case 2:
                    // 取出天时间段类统计总数
                    sumCount =
                        cardDao.getDayTempCount(Integer.valueOf(req.getBeginDate()), Integer.valueOf(req.getEndDate()));
                    
                    // 黄牌总数
                    yellowCountList =
                        cardDao.getDayYellowTempCount(Integer.valueOf(req.getBeginDate()),
                            Integer.valueOf(req.getEndDate()),
                            "02");
                    
                    int countDay = DateUtils.getDayBetween(req.getBeginDate(), req.getEndDate());
                    // 初始化map
                    for (int i = 0; i <= countDay; i++)
                    {
                        String beginDate = DateUtils.adjustDay(req.getBeginDate() + "000000", i);
                        map.put(beginDate.substring(0, 8), new TempPassCardCount());
                    }
                    buildCount(map, sumCount, yellowCountList);
                    
                    break;
                // 按照月统计
                case 3:
                    // 取出月时间段类统计总数
                    sumCount =
                        cardDao.getMonthTempCount(Integer.valueOf(req.getBeginDate().substring(0, 6)),
                            Integer.valueOf(req.getEndDate().substring(0, 6)));
                    
                    // 黄牌总数
                    yellowCountList =
                        cardDao.getMonthYellowTempCount(Integer.valueOf(req.getBeginDate().substring(0, 6)),
                            Integer.valueOf(req.getEndDate().substring(0, 6)),
                            "02");
                    
                    buildCount(map, sumCount, yellowCountList);
                    break;
                default:
                    logger.error("countType must be in 1,2,3");
                    break;
            }
            cardCounts =new ArrayList<TempPassCardCount>();
            
            for(Map.Entry<String, TempPassCardCount> maps :map.entrySet())
            {
                TempPassCardCount cardCount = new TempPassCardCount();
                cardCount.setApplyDate(maps.getKey());
                cardCount.setTotal(maps.getValue().getTotal());
                cardCount.setYelloCount(maps.getValue().getYelloCount());
                cardCount.setBlueCount(maps.getValue().getBlueCount());
                cardCounts.add(cardCount);
            }
            
            
            Collections.sort(cardCounts,new Comparator<TempPassCardCount>()
                {
                public int compare(TempPassCardCount o1, TempPassCardCount o2)
                {
                    return ConvertUtils.parseInt(o1.getApplyDate(), 0) - ConvertUtils.parseInt(o2.getApplyDate(), 0);
                }
                });
        }
        
        
        
        
        catch (Exception e)
        {
            throw new ServiceException(e);
        }
        
        return cardCounts;
    }
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @param map
     * @param dbList
     * @param yellowCountList
     * @see [类、类#方法、类#成员]
     */
    private void buildCount(Map<String, TempPassCardCount> map, List<TempCountPo> dbList,
        List<TempCountPo> yellowCountList)
    {
        TempPassCardCount count = null;
        
        Set<String> keys = new HashSet<String>();
        // 总数
        for (TempCountPo counts : dbList)
        {
            count = new TempPassCardCount();
            count.setTotal(counts.getCount());
            map.put(counts.getApplyDate(), count);
            keys.add(counts.getApplyDate());
        }
        
        // 黄牌封装
        for (String key : keys)
        {
            count = map.get(key);
            for (TempCountPo tempCountPo : yellowCountList)
            {
                if (tempCountPo.getApplyDate().equals(key))
                {
                    count.setYelloCount(tempCountPo.getCount());
                    count.setBlueCount(count.getTotal() - tempCountPo.getCount());
                    break;
                }
                else
                {
                    count.setBlueCount(count.getTotal());
                }
            }
        }
        
    }
    
    /**
     * @param cardRequest
     * @return
     * @throws JsztException
     */
    @Override
    public List<DimCardUserInfo> getSameCardUserInfo(PassCardRequest cardRequest)
    {
        try
        {
            return ServiceUtil.getService(DimCardUserInfoDao.class).checkBound(cardRequest);
        }
        catch (JsztException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * @param map
     * @return
     */
    @Override
    public ByteArrayOutputStream buildTempPassCardCount(List<TempPassCardCount> map)
    {
        // 创建excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        
        // 创建sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workbook, "统计临时通行证申请数目");
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "申请时间");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "黄牌申请数量");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "蓝牌申请数量");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "申请总数");
        
        if (CollectionUtils.isNotEmpty(map))
        {
            rowIndex = 1;
            for (TempPassCardCount entry : map)
            {
                columnIndex = 0;
                String date = null;
                if (entry.getApplyDate().length() == 10)
                {
                    date = DateUtils.formater(entry.getApplyDate() + "0000", "yyyy-MM-dd HH");
                }
                else if (entry.getApplyDate().length() == 8)
                {
                    date = DateUtils.formater(entry.getApplyDate() + "000000", "yyyy-MM-dd");
                }
                else if (entry.getApplyDate().length() == 6)
                {
                    date = entry.getApplyDate().substring(0, 4)+'-'+entry.getApplyDate().substring(4, 6);
                }
                ExcelUtil.setSheetCellValue(sheet,
                    rowIndex,
                    columnIndex++,
                    date);
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, entry.getYelloCount());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, entry.getBlueCount());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, entry.getTotal());
                
                rowIndex++;
            }
            
        }
        
        return ExcelUtil.getByteArrayOutputStream(workbook);
    }

	@Override
	public List<PassCardAnalyseRes> getPassCard(PassCardAnalyseReq req) {
        List<PassCardAnalyseRes> retList = new ArrayList<PassCardAnalyseRes>();
        
        int cardType = req.getCardTypeId();
        if (cardType == TEMP_CARD_TYPE)// 临时通行证
        {
            DimTempPassCardDao dimTempPassCardDao = ServiceUtil.getService(DimTempPassCardDao.class);
            List<PassCardAnalyseVo> tempCardList = dimTempPassCardDao.getTempPassCardAnalyse(req.getBeginDate(),req.getEndDate());
            passCardTran(retList, tempCardList,TEMP_CARD_TYPE);
            try {
				List<DimPassLlimit>  passLlimits=ServiceUtil.getService(PassLimitService.class).getPassLimitSum();
				//限额计算次数
				int count=DateUtils.getDayBetween(String.valueOf(req.getBeginDate()),String.valueOf(req.getEndDate()))+1;
				passCardLimit(retList,passLlimits,count);
			} catch (JsztException e) {
				e.printStackTrace();
			}
            
        }
        else if (cardType == LONG_CARD_TYPE)
        {
            // 长期通行证
            DimLongPassCardDao dimLongPassCardDao = ServiceUtil.getService(DimLongPassCardDao.class);
            List<PassCardAnalyseVo> longCardList = dimLongPassCardDao.getLongPassCardAnalyse(req.getBeginDate(),req.getEndDate());
            passCardTran(retList, longCardList,LONG_CARD_TYPE);
        }
        return retList;
	}

    @Override
    public void deleteExpirePassCard() {
        logger.info("Enter func [deleteExpirePassCard.]" + DateUtils.getCurrentTime());
        try
        {
            //过滤条件为昨天零点
            Date lastDay = DateUtils.adjustDay(new Date(), -1);
            String date  = DateUtils.dateToString(lastDay, "yyyyMMdd") + "000000";
            logger.info("删除过车临时表在"+date+"之前的记录");
            ServiceUtil.getService(DimTempPassCardDao.class).deleteExpirePassCard(date);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        logger.info("Exit func [deleteExpirePassCard.]" + DateUtils.getCurrentTime());
    }

    //按小时申请数量统计
	private void passCardTran(List<PassCardAnalyseRes> retList, List<PassCardAnalyseVo> longCardList,int cardTypeId)
    {
		Map<String,Integer> passMap=new HashMap<String,Integer>();
		for(PassCardAnalyseVo vo:longCardList)
		{
			
			String []periods=StringUtils.split(vo.getPassPeriod(),";");
			for(String period:periods)
			{
				String []temp=StringUtils.split(period,":");
				if(null!=temp&&0<temp.length)
				{
					String passPeriodVo=temp[temp.length-1];
					String []passPeriods=StringUtils.split(passPeriodVo,",");
					if(ArrayUtils.isNotEmpty(passPeriods))
					{
						for(String passPeriod:passPeriods)
						{
							Integer count=passMap.get(passPeriod);
							if(count==null)
							{
								passMap.put(passPeriod, vo.getCount());
							}else
							{
								passMap.put(passPeriod,passMap.get(passPeriod)+vo.getCount());
							}
						}
					}
				}
			}
			
			
		}
		for(int i=0;i<=23;i++)
		{
			if(passMap.get(String.valueOf(i))==null)
			{
				passMap.put(String.valueOf(i), 0);
			}
		}
		for(Entry<String,Integer> entry:passMap.entrySet())
		{
			PassCardAnalyseRes res=new PassCardAnalyseRes();
			res.setCardTypeId(cardTypeId);
			res.setHour(Integer.parseInt(entry.getKey()));
			res.setCount(entry.getValue());
			retList.add(res);
		}
		if(CollectionUtils.isNotEmpty(retList))
		{
			Collections.sort(retList, new Comparator<PassCardAnalyseRes>(){

				@Override
				public int compare(PassCardAnalyseRes res1,
						PassCardAnalyseRes res2) {
					return res1.getHour()-res2.getHour();
				}
				
			});
		}
            
        
    }
	private void passCardLimit(List<PassCardAnalyseRes> retList,List<DimPassLlimit>  passLlimits,int count )
    {
           for(PassCardAnalyseRes res:retList) 
           {
        	   if(CollectionUtils.isNotEmpty(passLlimits))
        	   {
        		   for(DimPassLlimit dimPassLlimit:passLlimits)
        		   {
        			   if(res.getHour()*100==dimPassLlimit.getTimeKey())
        			   {
        				   res.setLimit(dimPassLlimit.getLimit()*count);
        			   }
        		   }
        	   }
        	   
           }
        
    }
}
