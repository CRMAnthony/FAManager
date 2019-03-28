/*
 * 文 件 名:  DimPassCard.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  高晓娟
 * 修改时间:  2015年7月1日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  gxj
 * @version  [版本号, 2015年7月1日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimPassCard extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 6759725456284475385L;

    /**
     * 通行证编号
     */
    private String cardId;
    
    /**
     * 车牌号
     */
    private String licensePlate;
    
    /**
     * 车牌种类
     */
    private String plateTypeId;
    
    /**
     * 重点车辆类型 '1'表示货车,'2'表示危险品车辆,'3'表示黄标车
     */
    private String forbidType;
    
    /**
     * 开始日期
     */
    private int beginDate;
    
    /**
     * 结束日期
     */
    private int endDate;
    
    /**
     * 通行时段
     */
    private String passPeriod;
    
    /**
     * 通行路线
     */
    private String passLine;
    
    /**
     * 禁行路线 如有多个线路用","隔开
     */
    private String forbidLine;
    
    /**
     * 申请时间
     */
    private String applyDate;
    
    /**
     * 申请类型
     */
    private int applyType;
    
    /**
     * 车主姓名
     */
    private String ownerName;
    
    /**
     * 联系人姓名
     */
    private String contactPeople;
    
    /**
     * 地址
     */
    private String address;
    
    /**
     * 联系电话
     */
    private String phoneNumber;
    
    /**
     * 身份证号
     */
    private String idCard;
    
    /**
     * 打印状态
     */
    private int print;
    
    /**
     * 行驶证扫描件
     */
    private String xszUrl;
    
    /**
     * 驾驶证扫描件
     */
    private String jszUrl;
    
    /**
     * 保险保单扫描件
     */
    private String bxUrl;
    
    /**
     * 身份证扫描件
     */
    private String sfzUrl;
    
    /**
     * 营业执照扫描件
     */
    private String yyzzUrl;
    
    /**
     * 供货合同扫描件
     */
    private String ghhtUrl;
    
    /**
     * 押运员押运证扫描件
     */
    private String yyzUrl;
    
    /**
     * 状态描述
     */
    private String description;
    
    
    /** <默认构造函数>
     */
    public DimPassCard()
    {
        passLine = "-1";
    }

    /**
     * @return 返回 cardId
     */
    public String getCardId()
    {
        return cardId;
    }
    
    /**
     * @param 对cardId进行赋值
     */
    public void setCardId(String cardId)
    {
        this.cardId = cardId;
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
     * @return 返回 beginDate
     */
    public int getBeginDate()
    {
        return beginDate;
    }
    
    /**
     * @param 对beginDate进行赋值
     */
    public void setBeginDate(int beginDate)
    {
        this.beginDate = beginDate;
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
     * @return 返回 passPeriod
     */
    public String getPassPeriod()
    {
        return passPeriod;
    }
    
    /**
     * @param 对passPeriod进行赋值
     */
    public void setPassPeriod(String passPeriod)
    {
        this.passPeriod = passPeriod;
    }
    
    /**
     * @return 返回 passLine
     */
    public String getPassLine()
    {
        return passLine;
    }
    
    /**
     * @param 对passLine进行赋值
     */
    public void setPassLine(String passLine)
    {
        this.passLine = passLine;
    }
    
    /**
     * @return 返回 forbidLine
     */
    public String getForbidLine()
    {
        return forbidLine;
    }
    
    /**
     * @param 对forbidLine进行赋值
     */
    public void setForbidLine(String forbidLine)
    {
        this.forbidLine = forbidLine;
    }
    
    /**
     * @return 返回 applyType
     */
    public int getApplyType()
    {
        return applyType;
    }
    
    public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	/**
     * @param 对applyType进行赋值
     */
    public void setApplyType(int applyType)
    {
        this.applyType = applyType;
    }
    
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
     * @return 返回 contactPeople
     */
    public String getContactPeople()
    {
        return contactPeople;
    }
    
    /**
     * @param 对contactPeople进行赋值
     */
    public void setContactPeople(String contactPeople)
    {
        this.contactPeople = contactPeople;
    }
    
    /**
     * @return 返回 address
     */
    public String getAddress()
    {
        return address;
    }
    
    /**
     * @param 对address进行赋值
     */
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    /**
     * @return 返回 phoneNumber
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
    
    /**
     * @param 对phoneNumber进行赋值
     */
    public void setPhoneNumber(String phoneNumber)
    {
        this.phoneNumber = phoneNumber;
    }
    
    /**
     * @return 返回 idCard
     */
    public String getIdCard()
    {
        return idCard;
    }
    
    /**
     * @param 对idCard进行赋值
     */
    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }
    
    /**
     * @return 返回 print
     */
    public int getPrint()
    {
        return print;
    }
    
    /**
     * @param 对print进行赋值
     */
    public void setPrint(int print)
    {
        this.print = print;
    }
    
    /**
     * @return 返回 xszUrl
     */
    public String getXszUrl()
    {
        return xszUrl;
    }
    
    /**
     * @param 对xszUrl进行赋值
     */
    public void setXszUrl(String xszUrl)
    {
        this.xszUrl = xszUrl;
    }
    
    /**
     * @return 返回 jszUrl
     */
    public String getJszUrl()
    {
        return jszUrl;
    }
    
    /**
     * @param 对jszUrl进行赋值
     */
    public void setJszUrl(String jszUrl)
    {
        this.jszUrl = jszUrl;
    }
    
    /**
     * @return 返回 bxUrl
     */
    public String getBxUrl()
    {
        return bxUrl;
    }
    
    /**
     * @param 对bxUrl进行赋值
     */
    public void setBxUrl(String bxUrl)
    {
        this.bxUrl = bxUrl;
    }
    
    /**
     * @return 返回 sfzUrl
     */
    public String getSfzUrl()
    {
        return sfzUrl;
    }
    
    /**
     * @param 对sfzUrl进行赋值
     */
    public void setSfzUrl(String sfzUrl)
    {
        this.sfzUrl = sfzUrl;
    }
    
    /**
     * @return 返回 yyzzUrl
     */
    public String getYyzzUrl()
    {
        return yyzzUrl;
    }
    
    /**
     * @param 对yyzzUrl进行赋值
     */
    public void setYyzzUrl(String yyzzUrl)
    {
        this.yyzzUrl = yyzzUrl;
    }
    
    /**
     * @return 返回 ghhtUrl
     */
    public String getGhhtUrl()
    {
        return ghhtUrl;
    }
    
    /**
     * @param 对ghhtUrl进行赋值
     */
    public void setGhhtUrl(String ghhtUrl)
    {
        this.ghhtUrl = ghhtUrl;
    }
    
    /**
     * @return 返回 yyzUrl
     */
    public String getYyzUrl()
    {
        return yyzUrl;
    }
    
    /**
     * @param 对yyzUrl进行赋值
     */
    public void setYyzUrl(String yyzUrl)
    {
        this.yyzUrl = yyzUrl;
    }
    
    /**
     * @return 返回 description
     */
    public String getDescription()
    {
        return description;
    }
    
    /**
     * @param 对description进行赋值
     */
    public void setDescription(String description)
    {
        this.description = description;
    }
}
