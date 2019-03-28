/*
 * 文 件 名:  ImageService.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月25日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common;


/**
 * 图片上传服务
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ImageService
{
    
    /**
     * 图片上传
     * @param stream 文件流
     * @param byteLength 流的长度
     * @return 图片上传之后访问地址
     * @see [类、类#方法、类#成员]
     */
    String imageUpload(byte[] stream);
}
