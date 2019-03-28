/*
 * 文 件 名:  ElectricPolice.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  常骏
 * 修改时间:  2014年7月3日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 电子警察类
 * 
 * @author  changjun
 * @version  [版本号, 2014年7月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CameraPolice
{
    /**
     * 电子警察编号
     */
    private String policeId;
    
    /**
     * 电子警察位置
     */
    private String installationPosition;
    
    /**
     * 电子警察类型1：电子警察2：卡口摄像头
     */
    private int type;
    
    /**
     * 所属机构
     */
    private String agencyName;

    public String getPoliceId()
    {
        return policeId;
    }

    public void setPoliceId(String policeId)
    {
        this.policeId = policeId;
    }

    public String getInstallationPosition()
    {
        return installationPosition;
    }

    public void setInstallationPosition(String installationPosition)
    {
        this.installationPosition = installationPosition;
    }

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    
    
    
}
