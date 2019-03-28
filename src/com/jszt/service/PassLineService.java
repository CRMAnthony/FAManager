/*
 * 文 件 名:  PassLineService.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.util.List;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimPassLine;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.PassLineInfoAccoutReq;
import com.jszt.vo.PassLineInfoReq;
import com.jszt.vo.PassLineReq;
import com.jszt.vo.PassLineVo;

/**
 * 通行线路相关业务接口
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2015年5月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface PassLineService
{
	/**
	 * 根据交叉口列表和线路名称配置通行线路
	 * @param intList
	 * @param lineName
	 * @see [类、类#方法、类#成员]
	 */
	void addPassLine(PassLineReq req) throws JsztException;
	
	/**
	 * 编辑通行线路
	 * @param intList
	 * @param lineName
	 * @see [类、类#方法、类#成员]
	 */
	void updatePassLine(PassLineReq req) throws JsztException;
	
	
	/**
	 * 获得通行线路数据
	 * <功能详细描述>
	 * @param lineName
	 * @return
	 * @see [类、类#方法、类#成员]
	 */
	List<PassLineVo> getPassLine(PassLineInfoReq req) throws JsztException;
	
	/**
	 * 微信端查询所有通行线路
	 * @return
	 * @throws JsztException
	 */
	List<DimPassLine> getPassLineByWeChat() throws JsztException;
	
	/**
	 * 查询指定用户可以申请的所有通行线路
	 * @param req
	 * @return
	 * @throws JsztException
	 */
	List<DimPassLine> getPassLineByAccountName(PassLineInfoAccoutReq req) throws JsztException;
	
	/**
	 * 通行线路Id
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimPassLine> getPassLineId() throws JsztException;
	
	/**
	 * 删除通行线路
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	void delPassLine (DelIdReq req);
	
}
