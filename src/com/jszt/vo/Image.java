/*
 * 文 件 名:  Image.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年6月2日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

/**
 * 图片
 * <功能详细描述>
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年6月2日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class Image
{
    /**
     * 图片地址
     */
    private String picUrl;
    
    /**
     * 图片时间
     */
    private String picTime;
    

    /** <默认构造函数>
     */
    public Image()
    {
        super();
    }

    /** <默认构造函数>
     */
    public Image(String picUrl)
    {
        super();
        this.picUrl = picUrl;
    }

    /**
     * @return 返回 picUrl
     */
    public String getPicUrl()
    {
        return picUrl;
    }

    /**
     * @param 对picUrl进行赋值
     */
    public void setPicUrl(String picUrl)
    {
        this.picUrl = picUrl;
    }

    /**
     * @return 返回 picTime
     */
    public String getPicTime()
    {
        return picTime;
    }

    /**
     * @param 对picTime进行赋值
     */
    public void setPicTime(String picTime)
    {
        this.picTime = picTime;
    }
    
    
}
