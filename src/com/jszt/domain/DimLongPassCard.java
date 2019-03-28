/*
 * 文 件 名:  DimLongPassCard.java
 * 版    权:  江苏智通交通科技
 * 描    述:  <描述>
 * 修 改 人:  huaxiulin
 * 修改时间:  2015年5月18日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;


/**
 * 长期通行证数据 <功能详细描述>
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月18日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class DimLongPassCard extends DimPassCard
{
    
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 7678552755292663569L;
    
    /**
     * 录入人
     */
    private String accountId;
    
    /**
     * 录入人中队单位
     */
    private String agencyId;
    
    /**
     * 审核状态 0:待秩序科审核 1:待大队领导审核 2:审核通过 3:审核不通过
     * 
     */
    private int status;
    
    /**
     * 秩序科审核时间
     */
    private String zxkDate;
    
    /**
     * 大队领导审核时间
     */
    private String ddDate;
    
    /**
     * 秩序科审核意见
     */
    private String zxkOpinion;
    
    /**
     * 大队领导审核意见
     */
    private String ddOpinion;
    
    /**
     * 延长通行证父通行证编号
     */
    private String parentCardId;
    
    /**
	 * 操作权限账户(多个用逗号分隔)
	 */
	private String authId;
    
    public String getAuthId() {
		return authId;
	}

	public void setAuthId(String authId) {
		this.authId = authId;
	}

	public String getParentCardId() {
		return parentCardId;
	}

	public void setParentCardId(String parentCardId) {
		this.parentCardId = parentCardId;
	}

	public String getAccountId()
    {
        return accountId;
    }

    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }

    public String getAgencyId()
    {
        return agencyId;
    }

    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    /**
     * @return 返回 status
     */
    public int getStatus()
    {
        return status;
    }
    
    /**
     * @param 对status进行赋值
     */
    public void setStatus(int status)
    {
        this.status = status;
    }
    
    public String getZxkDate() {
		return zxkDate;
	}

	public void setZxkDate(String zxkDate) {
		this.zxkDate = zxkDate;
	}

	public String getDdDate() {
		return ddDate;
	}

	public void setDdDate(String ddDate) {
		this.ddDate = ddDate;
	}

	/**
     * @return 返回 zxkOpinion
     */
    public String getZxkOpinion()
    {
        return zxkOpinion;
    }
    
    /**
     * @param 对zxkOpinion进行赋值
     */
    public void setZxkOpinion(String zxkOpinion)
    {
        this.zxkOpinion = zxkOpinion;
    }
    
    /**
     * @return 返回 ddOpinion
     */
    public String getDdOpinion()
    {
        return ddOpinion;
    }
    
    /**
     * @param 对ddOpinion进行赋值
     */
    public void setDdOpinion(String ddOpinion)
    {
        this.ddOpinion = ddOpinion;
    }
    
}
