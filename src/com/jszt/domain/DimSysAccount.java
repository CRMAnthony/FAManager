/*
 * 文 件 名:  DimSysAccount.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  clarie
 * 修改时间:  2015年10月23日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 系统账户PO
 * 
 * 
 * @author  gaoxiaojuan
 * @version  [版本号, 2015年10月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimSysAccount extends EhsObject implements Serializable
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = 5818964931914602037L;
    
    /**
     * 账户编号
     */
    private String account;
    
    /**
     * 账户名称
     */
    private String accountName;
    
    /**
     * 账户密码
     */
    private String password;
    
    /**
     * 所属部门
     */
    private String agencyId;
    
    /**
     * 角色编号
     */
    private String roleId;
    
    /**
     * 账户类型 0:后台管理员1:普通账户
     */
    private int accountType;
    
    /**
     * 密码MD5盐值
     */
    private String saltValue;
    
    /**
     * 主页
     */
    private String indexPage;
    
    public String getAccount()
    {
        return account;
    }

    public void setAccount(String account)
    {
        this.account = account;
    }

    public String getAccountName()
    {
        return accountName;
    }

    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public int getAccountType()
    {
        return accountType;
    }

    public void setAccountType(int accountType)
    {
        this.accountType = accountType;
    }

    public String getSaltValue()
    {
        return saltValue;
    }

    public void setSaltValue(String saltValue)
    {
        this.saltValue = saltValue;
    }

    public String getIndexPage()
    {
        return indexPage;
    }

    public void setIndexPage(String indexPage)
    {
        this.indexPage = indexPage;
    }

}
