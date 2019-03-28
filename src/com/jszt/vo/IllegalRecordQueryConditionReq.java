/*
 * 文 件 名:  IllegalRecordQueryConditionReq.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.util.List;

/**
 * 违法记录查询条件类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月19日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IllegalRecordQueryConditionReq 
{
    /**
     * 起始时间
     */
    private int startDate;
    
    /**
     * 结束时间
     */
    private int endDate;
    
    /**
     * 车牌种类
     */
    private String plateTypeId;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 通行证类型
     */
    private String cardType;
    
    /**
     * 重点车辆类型
     */
    private String forbidType;
    
    /**
     * 处理状态
     */
    private int processState;
    
    /**
     * 提取条数
     */
    private int count;
    
    /**
     * 当前页面
     */
    private int pageIndex;
    
    /**
     * 每页记录数
     */
    private int pageCount;
    
    /**
     * 违法次数
     */
    private int illegalCount;
    
    /**
     * 当前登录人员帐号
     */
    private String account;
    
    /**
     * 禁行区域编号
     */
    private List<Integer> areaIds;
    
    /**
     * 违法区域编号
     */
    private int areaId;
    
    /**
     * 审核人
     */
    private String approveUser;
    
    
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
     * @return 返回 areaIds
     */
    public List<Integer> getAreaIds()
    {
        return areaIds;
    }

    /**
     * @param 对areaIds进行赋值
     */
    public void setAreaIds(List<Integer> areaIds)
    {
        this.areaIds = areaIds;
    }

    /**
     * @return 返回 account
     */
    public String getAccount()
    {
        return account;
    }

    /**
     * @param 对account进行赋值
     */
    public void setAccount(String account)
    {
        this.account = account;
    }

    /**
     * @return 返回 illegalCount
     */
    public int getIllegalCount()
    {
        return illegalCount;
    }

    /**
     * @param 对illegalCount进行赋值
     */
    public void setIllegalCount(int illegalCount)
    {
        this.illegalCount = illegalCount;
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

    /**
     * @return 返回 startDate
     */
    public int getStartDate()
    {
        return startDate;
    }

    /**
     * @param 对startDate进行赋值
     */
    public void setStartDate(int startDate)
    {
        this.startDate = startDate;
    }

    /**
     * @return 返回 endDate
     */
    public int getEndDate()
    {
        return endDate;
    }

    /**
     * @param 对endDate进行赋值
     */
    public void setEndDate(int endDate)
    {
        this.endDate = endDate;
    }

    /**
     * @return 返回 plateTypeId
     */
    public String getPlateTypeId()
    {
        return plateTypeId;
    }

    /**
     * @param 对plateTypeId进行赋值
     */
    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }

    /**
     * @return 返回 licensePlate
     */
    public String getLicensePlate()
    {
        return licensePlate;
    }

    /**
     * @param 对licensePlate进行赋值
     */
    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }

    /**
     * @return 返回 cardType
     */
    public String getCardType()
    {
        return cardType;
    }

    /**
     * @param 对cardType进行赋值
     */
    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }

    /**
     * @return 返回 forbidType
     */
    public String getForbidType()
    {
        return forbidType;
    }

    /**
     * @param 对forbidType进行赋值
     */
    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }

    /**
     * @return 返回 processState
     */
    public int getProcessState()
    {
        return processState;
    }

    /**
     * @param 对processState进行赋值
     */
    public void setProcessState(int processState)
    {
        this.processState = processState;
    }

    /**
     * @return 返回 count
     */
    public int getCount()
    {
        return count;
    }

    /**
     * @param 对count进行赋值
     */
    public void setCount(int count)
    {
        this.count = count;
    }

	@Override
	public String toString() {
		return "IllegalRecordQueryConditionReq [startDate=" + startDate
				+ ", endDate=" + endDate + ", plateTypeId=" + plateTypeId
				+ ", licensePlate=" + licensePlate + ", cardType=" + cardType
				+ ", forbidType=" + forbidType + ", processState="
				+ processState + ", count=" + count + ", pageIndex="
				+ pageIndex + ", pageCount=" + pageCount + ", illegalCount="
				+ illegalCount + ", account=" + account + ", areaIds="
				+ areaIds + ", areaId=" + areaId + "]";
	}

	public String getApproveUser() {
		return approveUser;
	}

	public void setApproveUser(String approveUser) {
		this.approveUser = approveUser;
	}
    
    

}
