/*
 * 文 件 名:  ForbidLineService.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimForbidLine;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.DimPassPeriod;
import com.jszt.vo.ForbidLineReq;

/**
 * 禁行线路相关业务接口
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface ForbidLineService
{
    /**
     * 禁行线路配置
     * <功能详细描述>
     * @param lineName
     * @param beginTime
     * @param endTime
     * @see [类、类#方法、类#成员]
     */
    void addForbidLine (ForbidLineReq req) throws JsztException;
    
    /**
     * 禁行线路查询
     * <功能详细描述>
     * @param lineName
     * @param beginTime
     * @param endTime
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidLine> getForbidLine (ForbidLineReq req) throws JsztException;
    
    /**
     * 长期通行证时段
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassPeriod> getLongPassPeriod(int begin,int end);
    
    /**
     * 临时通行证时段
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassPeriod> getTempPassPeriod(int begin,int end);
    
    /**
     * 根据禁行时间判断是否通知
     * <功能详细描述>
     * @param req
     * @return 
     * @throws RemoteException 
     * @throws ParseException 
     * @see [类、类#方法、类#成员]
     */
    boolean infoUserForBid(ForbidLineReq req) throws RemoteException, ParseException;
    
    /**
     * 删除禁行线路
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void delForbidLine (DelIdReq req) ;
    
}
