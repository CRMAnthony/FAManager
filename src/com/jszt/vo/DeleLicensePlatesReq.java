/*
 * 文 件 名:  DeleLicensePlates.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  clarie
 * 修改时间:  2015年9月23日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  clarie
 * @version  [版本号, 2015年9月23日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DeleLicensePlatesReq
{
    /**
     * 车牌号
     */
    private String[] licensePlates;

    /**
     * @return 返回 licensePlates
     */
    public String[] getLicensePlates()
    {
        return licensePlates;
    }

    /**
     * @param 对licensePlates进行赋值
     */
    public void setLicensePlates(String[] licensePlates)
    {
        this.licensePlates = licensePlates;
    }
    
}
