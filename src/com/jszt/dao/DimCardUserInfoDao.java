/*
 * 文 件 名:  DimCardUserInfoDao.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2016年1月7日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimCardUserInfo;
import com.jszt.vo.CardUserInfoReq;
import com.jszt.vo.PassCardRequest;

/**
 * 通行证用户信息绑定数据库层
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2016年1月7日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DimCardUserInfoDao
{	
	/**
	 * 新增用户绑定信息
	 * <功能详细描述>
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	void addDimCardUserInfo(DimCardUserInfo info) throws JsztException;	
	 
	/**
	 * 删除用户绑定信息
	 * <功能详细描述>
	 * @param info
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	void deleteDimCardUserInfo (String  id) throws JsztException;
	
	/**
	 * 修改用户绑定信息
	 * <功能详细描述>
	 * @param info
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	void updateDimcardUserInfo (DimCardUserInfo info) throws JsztException;

	/**
	 * 根据条件获取用户绑定信息
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimCardUserInfo getDimCardUserInfo (DimCardUserInfo info) throws JsztException;
	
	/**
	 * 根据主键获取用户绑定信息
	 * <功能详细描述>
	 * @param id
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimCardUserInfo getDimCardUserInfoByKey (String id) throws JsztException;
	
	/**
	 * 根据条件获取重复车牌号车主
	 * <功能详细描述>
	 * @param info
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	DimCardUserInfo getSameCardUserInfo (DimCardUserInfo info) throws JsztException;
	
	/**
	 * 获取临时通行证绑定信息
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimCardUserInfo> getAllDimcardUserInfo(CardUserInfoReq info) throws JsztException;
	
	/**
	 * 获取记录总数
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	String getCardUserInfoCount(CardUserInfoReq info) throws JsztException;
	
	/**
	 * 校验是否已绑定
	 * <功能详细描述>
	 * @param cardRequest
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimCardUserInfo> checkBound(PassCardRequest cardRequest) throws JsztException;
	
}	
