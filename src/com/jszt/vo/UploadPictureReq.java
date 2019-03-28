package com.jszt.vo;

import com.jszt.boulevard.webservice.bean.UploadBase;

/**
 * 图片上传
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-7-9]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class UploadPictureReq extends UploadBase
{

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -1955933981078330319L;
    
    @Override
    public String getRelativePath()
    {
        return "data\\certificate";
    }
}
