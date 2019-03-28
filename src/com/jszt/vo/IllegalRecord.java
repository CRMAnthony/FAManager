/*
 * �ļ���IllegalRecord.java  1.0.0  2015��5��14��
 * ������&����˵��
 * ���ߣ�����
 * ��Ȩ��������ͨ��ͨ�Ƽ����޹�˾
 */

package com.jszt.vo;

import org.apache.commons.lang3.StringUtils;

 /**
 *  违法信息
 * 〈功能详细描述〉
 * @author      常骏
 * @see        [相关类/方法]
 * @since      [产品/模块版本] 
 */
public class IllegalRecord
{
	/**
	 * 日期
	 */
	private String dateKey;
	
	/**
	 * 车牌号码
	 */
	private String licensePlate;
	
	/**
	 * 车辆类型
	 */
	private String forbidType;
	
	
	/**
	 * 通行证类型
	 */
	private String cardType;
	
	/**
	 * 处理状态
	 */
	private String processState;
	
	/**
	 * 上传状态
	 */
	private String uploadState;
	
	/**
	 * 违法次数
	 */
	private int illegalCount;
	
	/**
	 * 通知状态
	 */
	private String noticeState;
	
	/**
	 * 车牌类型
	 */
	private String plateTypeId;
	
	/**
	 * 审核人员
	 */
	private String verifyPerson;
	
	/**
	 * 审核时间
	 */
	private String verifyTime;
	
	/**
	 * 处理状态编号
	 */
	private int processStateId;
	
	/**
	 * 黄标车品牌
	 */
	private String vehicleBrand;
	
	/**
	 * 黄标车车牌颜色
	 */
	private String plateColor;
	
	/**
	 * 禁行区域编号
	 */
	private String areaId;
	
    
    /**
     * 违法地点
     */
    private String illegalSites;
    
    /**
     * 黄标车拥有车
     */
    private String ownerName;
    
    /**
	 * 审核人员名称
	 */
	private String verifyPersonName;
    
    /**
     * @return 返回 ownerName
     */
    public String getOwnerName()
    {
        return ownerName;
    }

    /**
     * @param 对ownerName进行赋值
     */
    public void setOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
    }

    /**
     * @return 返回 areaId
     */
    public String getAreaId()
    {
        return areaId;
    }

    /**
     * @param 对areaId进行赋值
     */
    public void setAreaId(String areaId)
    {
        this.areaId = areaId;
    }

    /**
     * @return 返回 plateColor
     */
    public String getPlateColor()
    {
        return plateColor;
    }

    /**
     * @param 对plateColor进行赋值
     */
    public void setPlateColor(String plateColor)
    {
        this.plateColor = plateColor;
    }

    /**
     * @return 返回 vehicleBrand
     */
    public String getVehicleBrand()
    {
        return vehicleBrand;
    }

    /**
     * @param 对vehicleBrand进行赋值
     */
    public void setVehicleBrand(String vehicleBrand)
    {
        this.vehicleBrand = vehicleBrand;
    }

    /**
     * @return 返回 verifyPerson
     */
    public String getVerifyPerson()
    {
        return verifyPerson;
    }

    /**
     * @param 对verifyPerson进行赋值
     */
    public void setVerifyPerson(String verifyPerson)
    {
        this.verifyPerson = verifyPerson;
    }

    /**
     * @return 返回 verifyTime
     */
    public String getVerifyTime()
    {
        return verifyTime;
    }

    /**
     * @param 对verifyTime进行赋值
     */
    public void setVerifyTime(String verifyTime)
    {
        this.verifyTime = verifyTime;
    }

    public IllegalRecord()
	{
	    
	}
	
	/**
	 * 记录类型1.过车记录，2违法有效记录，3.违法无效记录，4外地车首次违法记录
	 */
	public IllegalRecord(int recodType)
	{
        if (recodType == 1)
        {
            // 过车记录数据默认为未处理
            this.processState = "未处理";
            this.uploadState = "未上传";
            this.noticeState = "未通知";
        }
        else if (recodType == 3)
        {
            // 无效记录
            this.processState = "已处理";
            this.uploadState = "未上传";
            this.noticeState = "未通知";
        }
        else if (recodType == 4 || recodType == 2)
        {
            // 外地车首次违法记录 或者 违法有效记录
            this.processState = "已处理";
        }
        
        this.processStateId = recodType;
	}
	
	/**
     * @return 返回 processStateId
     */
    public int getProcessStateId()
    {
        return processStateId;
    }

    /**
     * @return 返回 processState
     */
    public String getProcessState()
    {
        return processState;
    }

    /**
     * @param 对processState进行赋值
     */
    public void setProcessState(String processState)
    {
        this.processState = processState;
    }


    /**
     * @return 返回 uploadState
     */
    public String getUploadState()
    {
        return uploadState;
    }

    /**
     * @param 对uploadState进行赋值
     */
    public void setUploadState(String uploadState)
    {
        this.uploadState = uploadState;
    }

    /**
     * @return 返回 noticeState
     */
    public String getNoticeState()
    {
        return noticeState;
    }

    /**
     * @param 对noticeState进行赋值
     */
    public void setNoticeState(String noticeState)
    {
        this.noticeState = noticeState;
    }

    public String getIllegalSites()
	{
//        try
//        {
//            if (StringUtils.equals(forbidType, "3"))
//            {
//                return new String(
//                    ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.yellowIllegalSitesName")
//                        .getBytes(), "gbk");
//            }
//            return new String(
//                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.illegalSitesName")
//                    .getBytes(), "gbk");
//        }
//        catch (UnsupportedEncodingException e)
//        {
//            throw new ServiceException(e, "getIllegalSites convert properties error!");
//        }
        return illegalSites;
	}
	
	/**
     * @param 对illegalSites进行赋值
     */
    public void setIllegalSites(String illegalSites)
    {
        this.illegalSites = illegalSites;
    }

    public String getTerritorial()
	{
		if (StringUtils.isNotEmpty(licensePlate))
		{
			return licensePlate.indexOf("苏E")>=0 ? "本地" : "外地";
		}
		else 
		{
			return "无车牌";
		}
	}

	public String getDateKey()
	{
		return dateKey;
	}

	public void setDateKey(String dateKey)
	{
		this.dateKey = dateKey;
	}

	public String getLicensePlate()
	{
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate)
	{
		this.licensePlate = licensePlate;
	}

    public String getForbidType()
    {
        return forbidType;
    }

    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }

    public String getCardType()
	{
		return cardType;
	}

	public void setCardType(String cardType)
	{
		this.cardType = cardType;
	}


	public int getIllegalCount()
	{
		return illegalCount;
	}

	public void setIllegalCount(int illegalCount)
	{
		this.illegalCount = illegalCount;
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

	public String getVerifyPersonName() {
		return verifyPersonName;
	}

	public void setVerifyPersonName(String verifyPersonName) {
		this.verifyPersonName = verifyPersonName;
	}

	
}
