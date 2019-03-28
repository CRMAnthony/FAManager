/*
 * 文 件 名:  ForbidAreaService.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.jsits.commons.util.JsztException;
import com.jszt.domain.DimForbidArea;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.ForbidAreaInfoReq;
import com.jszt.vo.ForbidAreaReq;
import com.jszt.vo.ForbidAreaVo;

/**
 * 禁行区域业务接口
 * <功能详细描述>
 * 
 * @author  huaxiulin
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ForbidAreaService
{
	/**
	 * 新增禁行区域
	 * <功能详细描述>
	 * @param req
	 * @see [类、类#方法、类#成员]
	 */
	void  addForbidArea (ForbidAreaReq req) throws JsztException;
	
	/**
	 * 获得禁行区域信息
	 * <功能详细描述>
	 * @param req
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<ForbidAreaVo> getForbidArea(ForbidAreaInfoReq req) throws JsztException;
	
	/**
	 * 获得禁行区域编号
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	List<DimForbidArea> getForbidAreaId () throws JsztException;

	/**
	 * 删除禁行区域
	 * <功能详细描述>
	 * @param req
	 * @see [类、类#方法、类#成员]
	 */
	void delForbidArea(DelIdReq req) ;
	
	
	/**
	 * 更新禁行区域信息
	 * <功能详细描述>
	 * @param req
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	void updateForbidArea(ForbidAreaReq req) throws JsztException;
	
	
	/**
	 * 获取设备信息
	 * <功能详细描述>
	 * @return
	 * @throws JsztException
	 * @see [类、类#方法、类#成员]
	 */
	Map<String, Set<String>> getMonitorDevices() throws JsztException;
}
