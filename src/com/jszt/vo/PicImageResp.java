/*
 * 文 件 名:  PicImage.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月21日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.vo;

import java.util.List;

/**
 * 页面查询图片返回类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月21日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PicImageResp
{
    /**
     * 合成图片
     */
    private String composeImage;
    
    /**
     * 原始图片地址
     */
    private List<Image> sourceUrl;
    
    /**
     * 过车记录图片集合
     */
    private List<Image> imgList;
    
    /**
     * @return 返回 imgList
     */
    public List<Image> getImgList()
    {
        return imgList;
    }

    /**
     * @param 对imgList进行赋值
     */
    public void setImgList(List<Image> imgList)
    {
        this.imgList = imgList;
    }

    /**
     * @return 返回 composeImage
     */
    public String getComposeImage()
    {
        return composeImage;
    }

    /**
     * @param 对composeImage进行赋值
     */
    public void setComposeImage(String composeImage)
    {
        this.composeImage = composeImage;
    }

    /**
     * @return 返回 sourceUrl
     */
    public List<Image> getSourceUrl()
    {
        return sourceUrl;
    }

    /**
     * @param 对sourceUrl进行赋值
     */
    public void setSourceUrl(List<Image> sourceUrl)
    {
        this.sourceUrl = sourceUrl;
    }

    
    
    
}
