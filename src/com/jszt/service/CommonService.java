package com.jszt.service;

import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jszt.domain.DimForbidArea;
import com.jszt.domain.DimForbidLine;
import com.jszt.domain.DimPassLine;
import com.jszt.domain.DimPlateType;
import com.jszt.domain.DimSysConfig;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.domain.DimViolationSpecialVehicle;
import com.jszt.domain.DimViolationVehicleType;
import com.jszt.vo.NoticePassCard;
import com.jszt.vo.NoticeReq;
import com.jszt.vo.NoticeVo;

/**
 * 公共接口（车辆类型、电子警察、长期通行证常用时段、通行线路、禁行线路）
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface CommonService
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
     * 根据编号获取车辆类型
     * <一句话功能简述>
     * <功能详细描述>
     * @param plateTypeId
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getPlateTypeName(String plateTypeId);
    
    /**
     * 获取通行线路
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassLine> getPassLine();
    
    /**
     * 根据编号获取线路名称
     * <一句话功能简述>
     * <功能详细描述>
     * @param lineIds 多个id，使用逗号分隔
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getPassLineName(String lineIds);
    
    /**
     * 根据编号获取线路
     * <一句话功能简述>
     * <功能详细描述>
     * @param lineIds 多个id，使用逗号分隔
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassLine> getPassLineById(String lineIds);
    
    /**
     * 长期通行证时段（全时段，避高峰）
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimSysConfig> getLongPeriod();
    
    /**
     * 根据线路（交叉口字符串），查询该线路上的设备编号
     * <一句话功能简述>
     * <功能详细描述>
     * @param points 格式为交叉口编号间用英文逗号隔开，即  intId,intId ...
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getElectricPoliceByLine(String points);
    
    /**
     * 根据设备安装地点，查询改安装地点上设备编号
     * <功能详细描述>
     * @param points
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getElectricByposition(String points);
    
    /**
     * 获取交叉口对应的设备
     * <一句话功能简述>
     * <功能详细描述>
     * @param intId 交叉口编号
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getElectricPoliceByIntId(String intId);
    
    /**
     * 获取交叉口名称（intList可以为多个交叉口编号，英文逗号隔开）
     * <一句话功能简述>
     * <功能详细描述>
     * @param intList
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getIntName(String intList);
    
    /**
     * 获取通行证对应的有效的禁行线路
     * <一句话功能简述>
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @param period
     * @param cardType
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidLine> getForbidLinesByCard(int beginDate,int endDate,String period,int cardType);
    
    /**
     * 获取因禁行线路需要通知的通行证
     * <一句话功能简述>
     * <功能详细描述>
     * @param beginTime
     * @param endTime
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<NoticePassCard> getNoticePassCards(long beginTime,long endTime);
    
    /**
     * 根据编号，获取禁行线路（若多个，用英文逗号隔开）
     * <一句话功能简述>
     * <功能详细描述>
     * @param lineIds
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidLine> getForbidLinesByIds(String lineIds);
    
    /**
     * 根据当前帐号获取禁行区域列表编号
     * @param acctount
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<Integer> getForbidAreaByAccount(String acctount);
    
    
    /**
     * 根据禁行区域id获取禁行区域编号
     * @param areaId 
     * @return
     * @see [类、类#方法、类#成员]
     */
    String getForbidAreaNameById(int areaId);
    
    /**
     * 更新静态的数据（设备、车辆类型、长期通行证的通行时段）
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void refreshData();
    
    /**
     * 更新通行线路
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void refreshPassLine();
    
    /**
     * 获取违法次数
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    DimSysConfig getVioTimes();
    
    /**
     * 短信通知
     * <功能详细描述>
     * @param content 短信内容
     * @param mobileString 手机号
     * @throws RemoteException 
     * @throws MalformedURLException 
     * @see [类、类#方法、类#成员]
     */
    boolean smsInfo(String content,String mobileString) throws RemoteException, MalformedURLException;
    
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
     * 获取禁行区域中的检测器
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> getForbidAreaDev();
    
   /**
    * 获取禁行区域信息
    * <一句话功能简述>
    * <功能详细描述>
    * @param type 禁行区域类型
    * @return
    * @see [类、类#方法、类#成员]
    */
    List<DimForbidArea> getForbidAreaList(int type);
    
    /**
     * 更新禁行区域缓存数据
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void updateForbidAreaInfo();
    
    /**
     * 获取黄标车
     * <一句话功能简述>
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimUnstandardVehicle> getUnstandardVehicles();
    
    /**
     * 更新黄标车缓存数据
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void updateUnstandardVehicle();
    
    /**
     * 根据车牌号获取特殊车辆
     * <一句话功能简述>
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimViolationSpecialVehicle> getSpecialVehicleById(String licensePlate);
    
    /**
     * 更新特殊车辆缓存数据
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void updateSpecialVehicle();
    
    /**
     * 根据车牌获取车主电话号码
     * @param licensePlate 车牌
     * @return 车主电话号码，如果找不到返回空
     * @see [类、类#方法、类#成员]
     */
    String getCarOwnerPhone(String licensePlate); 
    
    /**
     * 获取车牌类型
     * @param plateTypeId 车牌id
     * @return 
     * @see [类、类#方法、类#成员]
     */
    DimViolationVehicleType getViolationVehicleType(String plateTypeId);
    
    /**
     * 通知用户更新操作
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void infoUserUpdate();
    
    /**
     * 获取设备信息
     * @return
     * @see [类、类#方法、类#成员]
     */
    Map<String, Set<String>> getPolices();
    
    /**
     * 写入短信内容
     * <一句话功能简述>
     * <功能详细描述>
     * @see [类、类#方法、类#成员]
     */
    void writeNotice(NoticeVo noticeVo);
    
    /**
     * 根据开始时间、结束时间查询短信内容
     * <一句话功能简述>
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<NoticeVo> getNotice(NoticeReq req);
    
    /**
     * 判断是否发送短信 false:不发送 ,true:发送
     * @return
     */
    boolean checkSend();
}
