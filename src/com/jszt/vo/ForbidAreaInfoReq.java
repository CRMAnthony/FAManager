/*
 * 文 件 名:  ForbidAreaInfoReq.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月22日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 禁行区域数据信息请求类 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月22日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidAreaInfoReq
{
	/**
	 * 禁行区域编号
	 */
	private int areaId;

	/**
     * 禁行区域类型0:货车禁区 1:黄标车禁区
     */
    private String areaType;
	
    /**
     * 当前页面
     */
    private int pageIndex;

    /**
     * 每页记录数
     */
    private int pageCount;
    
	/**
	 * @return 返回 areaId
	 */
	public int getAreaId()
	{
		return areaId;
	}

	/**
	 * @param 对areaId进行赋值
	 */
	public void setAreaId(int areaId)
	{
		this.areaId = areaId;
	}

	
    /**
     * @return 返回 areaType
     */
    public String getAreaType()
    {
        return areaType;
    }

    /**
     * @param 对areaType进行赋值
     */
    public void setAreaType(String areaType)
    {
        this.areaType = areaType;
    }

    /**
     * @return 返回 pageIndex
     */
    public int getPageIndex()
    {
        return pageIndex;
    }

    /**
     * @param 对pageIndex进行赋值
     */
    public void setPageIndex(int pageIndex)
    {
        this.pageIndex = pageIndex;
    }

    /**
     * @return 返回 pageCount
     */
    public int getPageCount()
    {
        return pageCount;
    }

    /**
     * @param 对pageCount进行赋值
     */
    public void setPageCount(int pageCount)
    {
        this.pageCount = pageCount;
    }

    public String toString()
	{
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
