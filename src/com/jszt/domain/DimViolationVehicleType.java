/*
 * 文 件 名:  DimViolationVehicleType.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年9月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.domain;

/**
 * 车牌类型和车牌颜色表
 * <功能详细描述>
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年9月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationVehicleType
{
    /**
     * 车辆类型
     */
    private String plateTypeId;
    
    /**
     * 车辆类型名称
     */
    private String plateTypeName;
    
    /**
     * 车牌颜色
     */
    private String plateColor;
    
    /**
     * 描述
     */
    private String description;

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
     * @return 返回 plateTypeName
     */
    public String getPlateTypeName()
    {
        return plateTypeName;
    }

    /**
     * @param 对plateTypeName进行赋值
     */
    public void setPlateTypeName(String plateTypeName)
    {
        this.plateTypeName = plateTypeName;
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
