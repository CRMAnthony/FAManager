package com.jszt.service;

import java.io.ByteArrayOutputStream;
import java.net.MalformedURLException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceException;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.vo.CardUserInfoReq;
import com.jszt.vo.DeleteCardUserInfo;
import com.jszt.vo.PassCard;
import com.jszt.vo.PassCardAnalyseReq;
import com.jszt.vo.PassCardAnalyseRes;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PassCardRequest;
import com.jszt.vo.TempPassCardCount;
import com.jszt.vo.TempPassCardCountReq;
import com.jszt.vo.UpdatePassCardReq;

/**
 * 通行证服务
 * @author  ling
 * @version  [版本号, 2015-5-20]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PassCardService
{
    /**
     * 查询通行证
     * @param passCardReq
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PassCard> getPassCard(PassCardReq passCardReq);
    
    /**
     * 录入通行证
     * @param passCardRequest
     * @see [类、类#方法、类#成员]
     */
    String addPassCard(PassCardRequest passCardRequest) throws JsztException;
    
    /**
     * 秩序科审批
     * <一句话功能简述>
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void updateZXKProve(UpdatePassCardReq req);
    
    /**
     * 大队领导审批
     * <一句话功能简述>
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void updateDDProve(UpdatePassCardReq req);
    
    /**
     * 通行证打印状态更新
     * <一句话功能简述>
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void updatePassCardPrint(UpdatePassCardReq req);
    
    /**
     * 通行证打印
     * <一句话功能简述>
     * <功能详细描述>
     * @param cardId
     * @param cardType
     * @see [类、类#方法、类#成员]
     */
    List<String> passCardPrint(String cardId ,int cardType);

    /**
     * 微信端通行证打印
     * <一句话功能简述>
     * <功能详细描述>
     * @param cardId
     * @param cardType
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> weiXinPrint(String cardId ,int cardType);

    /** 
     * 封装通行证excel
     * @param passCardList
     * @return
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream buildPassCardExcelBody(List<PassCard> passCardList);
    
    /**
     * 封装发短信内容
     * 
     * @param req
     * @return
     * @throws RemoteException
     * @throws ParseException
     * @throws MalformedURLException 
     * @see [类、类#方法、类#成员]
     */
    boolean smsNotice(PassCardRequest passCardRequest) throws RemoteException, MalformedURLException;
    
    /**
     * 临时通行时段显示
     * 
     * @param passPeriod
     * @return
     * @see [类、类#方法、类#成员]
     */
    String tempCardPeriodShow(String passPeriod);
    
    /**
     * 长期通行时段显示
     * 
     * @param passPeriod
     * @return
     * @see [类、类#方法、类#成员]
     */
    String longCardPeriodShow(String passPeriod);
    
    /**
     * 将申请通行证的信息新增到绑定表里
     * <功能详细描述>
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    String boundCard (PassCardRequest passCardRequest);
    
    /**
     * 修改绑定通行证的信息
     * <功能详细描述>
     * @param passCardRequest
     * @see [类、类#方法、类#成员]
     */
    void updateCardUserInfo(PassCardRequest passCardRequest);
    
    /**
     * 获取绑定通行证的信息
     * <功能详细描述>
     * @param passCardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    DimCardUserInfo getDimCardUserInfo(PassCardRequest passCardRequest);
    
    /**
     * 删除绑定通行证的信息
     * <功能详细描述>
     * @param passCardRequest
     * @see [类、类#方法、类#成员]
     */
    void delCardUserInfo(DeleteCardUserInfo info);
    
    
    /**
     * 绑定通行证审批
     * <功能详细描述>
     * @param req
     * @see [类、类#方法、类#成员]
     */
    void updateProveResult(UpdatePassCardReq req);
    
    /**
     * 根据条件获取所有绑定通行证信息
     * <功能详细描述>
     * @param info
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimCardUserInfo> getAllCardUserInfo(CardUserInfoReq info) throws JsztException;
    
    
    /**
     * 更新长期通行证时间
     * <功能详细描述>
     * @param req
     * @throws JsztException
     * @see [类、类#方法、类#成员]
     */
    void updateLongPassCard (PassCardReq req) throws JsztException;
    
    /**
     * 统计申请的临时通行的数目
     * <功能详细描述>
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<TempPassCardCount> getTempPassCardCount (TempPassCardCountReq req)  throws ServiceException ;
    
    
    /**
     * 查询长期通行证延长历史记录
     * @param cardId 当前通行证编号
     * @return
     */
    List<PassCard> queryLongPassCardExtendHis(String cardId);

    /**
     * 
     * <功能详细描述>
     * @param cardRequest
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimCardUserInfo> getSameCardUserInfo(PassCardRequest cardRequest);
    
    /**
     * 导出统计临时通行证申请数目
     * <功能详细描述>
     * @param map
     * @return
     * @see [类、类#方法、类#成员]
     */
    ByteArrayOutputStream buildTempPassCardCount(List<TempPassCardCount> map);
    
    /**
     * 查询通行证
     * @param req
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<PassCardAnalyseRes> getPassCard(PassCardAnalyseReq req);

    void deleteExpirePassCard();
}
