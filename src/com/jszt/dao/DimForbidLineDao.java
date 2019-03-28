/*
 * 文 件 名:  DimForbidLineDao.java
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

import com.jszt.domain.DimForbidLine;
import com.jszt.vo.DimPassPeriod;
import com.jszt.vo.ForbidLineReq;

/**
 * 禁行线路配置数据库操作 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public interface DimForbidLineDao
{
	/**
	 * 根据线路名称和开始，结束时间配置禁行线路
	 * 
	 * @param lineName
	 * @param beginTime
	 * @param endTime
	 * @see [类、类#方法、类#成员]
	 */
	void addForbidLine(DimForbidLine forbidLine);
	
	/**
     * 获取一段时间内有效的禁行线路
     * <一句话功能简述>
     * <功能详细描述>
     * @param beginDate
     * @param endDate
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidLine> getForbidLinesByInterval(int beginDate,int endDate);
    
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
     * 根据线路名,开始时间,结束时间获取禁行线路
     * <功能详细描述>
     * @param lineName
     * @param beginTime
     * @param endTime
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimForbidLine> getForbidLine(ForbidLineReq req);
    
    /**
     * 长期通行证通行时间段
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassPeriod> getLongPassPeriod(int begin,int end);
    
    /**
     * 临时通行证通行时间段
     * <功能详细描述>
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DimPassPeriod> getTempPassPeriod(int begin,int end);
    
    /**
     * 删除禁行线路
     * <功能详细描述>
     * @param id
     * @see [类、类#方法、类#成员]
     */
    void delForbidLine (int [] id);
    
}
