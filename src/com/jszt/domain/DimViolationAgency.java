/*
 * 文 件 名:  DimViolationAgency.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  wblv
 * 修改时间:  2014年3月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 交通部门PO
 * <功能详细描述>
 * 
 * @author  wblv
 * @version  [版本号, 2014年3月18日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationAgency extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7789601255685777205L;
    
    /**
     * 部门编号
     */
    private String agencyId;
    
    /**
     * 外部编号
     */
    private String agencyFn;
    
    /**
     * 部门名称
     */
    private String agencyName;
    
    /**
     * 上级部门
     */
    private String parentAgency;
    
    /**
     * 上级部门编号
     */
    private String parentAgencyId;
    
    /**
     * 行政级别
     */
    private String administLevel;
    
    /**
     * 机构类型
     */
    private String agencyTime;
    
    /**
     * 所在地址
     */
    private String address;
    
    /**
     * 业务描述
     */
    private String serviceDescription;
    
    /**
     * 机构职能
     */
    private String agencyResponsibility;
    
    /**
     * 编制人数
     */
    private int enrolledNumber;
    
    /**
     * 邮政编码
     */
    private String postcode;
    
    /**
     * 在编职工
     */
    private int enrolledStaff;
    
    /**
     * 在编警员
     */
    private int enrolledPoliceman;
    
    /**
     * 其他人数
     */
    private int otherNumber;
    
    /**
     * 部门领导
     */
    private String agencyLeader;
    
    /**
     * 领导电话
     */
    private String leaderTelephone;
    
    /**
     * 领导手机
     */
    private String leaderCellphone;
    
    /**
     * 值班电话
     */
    private String dutyTelephone1;
    
    /**
     * 值班电话2
     */
    private String dutyTelephone2;
    
    /**
     * 传真电话
     */
    private String faxTelephone;
    
    /**
     * 关联路网编号
     */
    private String relateNetwork;

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }

    public String getParentAgency()
    {
        return parentAgency;
    }

    public void setParentAgency(String parentAgency)
    {
        this.parentAgency = parentAgency;
    }

    public String getParentAgencyId()
    {
        return parentAgencyId;
    }

    public void setParentAgencyId(String parentAgencyId)
    {
        this.parentAgencyId = parentAgencyId;
    }

    public String getAdministLevel()
    {
        return administLevel;
    }

    public void setAdministLevel(String administLevel)
    {
        this.administLevel = administLevel;
    }

    public String getAgencyTime()
    {
        return agencyTime;
    }

    public void setAgencyTime(String agencyTime)
    {
        this.agencyTime = agencyTime;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getServiceDescription()
    {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription)
    {
        this.serviceDescription = serviceDescription;
    }

    public String getAgencyResponsibility()
    {
        return agencyResponsibility;
    }

    public void setAgencyResponsibility(String agencyResponsibility)
    {
        this.agencyResponsibility = agencyResponsibility;
    }

    public int getEnrolledNumber()
    {
        return enrolledNumber;
    }

    public void setEnrolledNumber(int enrolledNumber)
    {
        this.enrolledNumber = enrolledNumber;
    }

    public String getPostcode()
    {
        return postcode;
    }

    public void setPostcode(String postcode)
    {
        this.postcode = postcode;
    }

    public int getEnrolledStaff()
    {
        return enrolledStaff;
    }

    public void setEnrolledStaff(int enrolledStaff)
    {
        this.enrolledStaff = enrolledStaff;
    }

    public int getEnrolledPoliceman()
    {
        return enrolledPoliceman;
    }

    public void setEnrolledPoliceman(int enrolledPoliceman)
    {
        this.enrolledPoliceman = enrolledPoliceman;
    }

    public int getOtherNumber()
    {
        return otherNumber;
    }

    public void setOtherNumber(int otherNumber)
    {
        this.otherNumber = otherNumber;
    }

    public String getAgencyLeader()
    {
        return agencyLeader;
    }

    public void setAgencyLeader(String agencyLeader)
    {
        this.agencyLeader = agencyLeader;
    }

    public String getLeaderTelephone()
    {
        return leaderTelephone;
    }

    public void setLeaderTelephone(String leaderTelephone)
    {
        this.leaderTelephone = leaderTelephone;
    }

    public String getLeaderCellphone()
    {
        return leaderCellphone;
    }

    public void setLeaderCellphone(String leaderCellphone)
    {
        this.leaderCellphone = leaderCellphone;
    }

    public String getDutyTelephone1()
    {
        return dutyTelephone1;
    }

    public void setDutyTelephone1(String dutyTelephone1)
    {
        this.dutyTelephone1 = dutyTelephone1;
    }

    public String getDutyTelephone2()
    {
        return dutyTelephone2;
    }

    public void setDutyTelephone2(String dutyTelephone2)
    {
        this.dutyTelephone2 = dutyTelephone2;
    }

    public String getFaxTelephone()
    {
        return faxTelephone;
    }

    public void setFaxTelephone(String faxTelephone)
    {
        this.faxTelephone = faxTelephone;
    }

    public String getAgencyFn()
    {
        return agencyFn;
    }

    public void setAgencyFn(String agencyFn)
    {
        this.agencyFn = agencyFn;
    }

    public String getRelateNetwork()
    {
        return relateNetwork;
    }

    public void setRelateNetwork(String relateNetwork)
    {
        this.relateNetwork = relateNetwork;
    }
}
