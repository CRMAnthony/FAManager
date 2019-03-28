/*
 * 文 件 名:  DimPassLineDao.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.dao;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimPassLine;
import com.jszt.vo.PassLineInfoReq;

/**
 * 通行线路配置数据库操作
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface DimPassLineDao
{
	/**
	 * 根据交叉口列表和线路名称配置通行线路
	 * 
	 * @param intList
	 * @param lineName
	 * @see [类、类#方法、类#成员]
	 */
	void addPassLine(DimPassLine passLine);
	
	/**
	 * 编辑通行线路
	 * 
	 * @param intList
	 * @param lineName
	 * @see [类、类#方法、类#成员]
	 */
	void updatePassLine(DimPassLine passLine);
	
	/**
	 * 查询指定用户不可以申请的所有通行线路
	 * @param req
	 * @return
	 * @throws JsztException
	 */
	List<Integer> getNoAuthPassLineByAccountName(Integer lineId, String account);
	
	/**
	 * 获取passLine的nextval
	 * @return
	 */
	Integer getAddPassLineId();
	
	/**
	 * 获取通行线路 <功能详细描述>
	 * 
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DimPassLine> getPassLine();

	/**
	 * 查询指定用户可以申请的所有通行线路
	 * @param req
	 * @return
	 * @throws JsztException
	 */
	List<DimPassLine> getPassLineByAccountName(String account);
	
	/**
	 * 微信端查询所有通行线路
	 * @return
	 * @throws JsztException
	 */
	List<DimPassLine> getPassLineByWeChat();
	
	/**
	 * 根据路线名查询通行线路
	 * <功能详细描述>
	 * @param req
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<DimPassLine> getPassLineByLineName(PassLineInfoReq req);
	
	/**
	 * 删除通行线路
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	void delPassline (int [] req);
	
	/**
	 * 根据ids查询所有的通行线路AuthId操作权限用户
	 * @return
	 * @throws JsztException
	 */
	List<String> getPassLineAuthIdsByIds(String passLineIds) throws JsztException;
	
}
