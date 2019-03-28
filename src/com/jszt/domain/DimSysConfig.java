package com.jszt.domain;

import java.io.Serializable;

import com.jsits.commons.domain.EhsObject;

/**
 * 系统配置（包括长期通行证的时段配置）
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimSysConfig extends EhsObject implements Serializable
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 9174360916799417283L;

    private int id;
    
    private String configKey;
    
    private String configName;
    
    private String configValue;
    
    private int showFlag;
    
    public DimSysConfig()
    {
        
    }
    
    public DimSysConfig(DimSysConfig config)
    {
        this.id = config.getId();
        this.configKey = config.getConfigKey();
        this.configName = config.getConfigName();
        this.configValue = config.getConfigValue();
    }
    
    public void setDimSysConfig(DimSysConfig config)
    {
        this.id = config.getId();
        this.configKey = config.getConfigKey();
        this.configName = config.getConfigName();
        this.configValue = config.getConfigValue();
    }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getConfigKey()
    {
        return configKey;
    }

    public void setConfigKey(String configKey)
    {
        this.configKey = configKey;
    }

    public String getConfigName()
    {
        return configName;
    }

    public void setConfigName(String configName)
    {
        this.configName = configName;
    }

    public String getConfigValue()
    {
        return configValue;
    }

    public void setConfigValue(String configValue)
    {
        this.configValue = configValue;
    }

    public int getShowFlag()
    {
        return showFlag;
    }

    public void setShowFlag(int showFlag)
    {
        this.showFlag = showFlag;
    }
}
