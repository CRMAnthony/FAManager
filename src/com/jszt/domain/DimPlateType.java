package com.jszt.domain;

import com.jsits.commons.domain.EhsObject;

/**
 * 车辆类型
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-18]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimPlateType extends EhsObject
{
    /**
     * 编号
     */
    private String typeId;
    
    /**
     * 类型名称
     */
    private String name;

    public String getTypeId()
    {
        return typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }
    
    
}
