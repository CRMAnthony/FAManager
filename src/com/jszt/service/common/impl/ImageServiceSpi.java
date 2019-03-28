/*
 * 文 件 名:  ImageServiceSpi.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月25日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.common.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.CloseUtils;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.service.common.ImageService;

/**
 * 图片上传
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月25日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ImageServiceSpi implements ImageService
{
	private static Logger logger = Logger.getLogger(ImageServiceSpi.class);
    private static String[] imageService;
    
    private static int[] imageServicePort;
    
    // 负载均衡随机数
    private static final Random RANDOM = new Random();

   // private static final 
    /**
     * @param stream
     * @return
     */
    @Override
    public String imageUpload(byte[] pic)
    {
        // 第一次加载获取图片服务器
        getImageService();
        
        // 随机负载均衡
        int index = RANDOM.nextInt(imageService.length);
        String ip = imageService[index];
        int port = imageServicePort[index];
        logger.info("上传ip:"+ip+"上传port:"+port);
        return sendPostImage(pic, ip, port);
        
    }
    
    
    private String sendPostImage(byte[] pic, String ip, int port)
    {
        
        String releasePath = null;
        
        Socket socket = null;
        BufferedOutputStream bufferedOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try
        {
            
            socket = new Socket(ip, port);
            bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());
            
            
            // 写消息头 type = 0 过车记录图片  1 违法图片
            writeString(bufferedOutputStream, "POST /imageServer/image?name="+UUID.randomUUID().toString() +".jpg&type=1 HTTP/1.1\r\n");
            writeString(bufferedOutputStream, "Host: " + ip + "\r\n");
            writeString(bufferedOutputStream, "Connection:keep-alive\r\n");
            writeString(bufferedOutputStream, "Content-Length: " + pic.length + "\r\n");
            writeString(bufferedOutputStream, "\r\n");
            
            // 写图片
            bufferedOutputStream.write(pic);
            bufferedOutputStream.flush();
            
            // 读取发布地址
            byteArrayOutputStream = new ByteArrayOutputStream();
            bufferedInputStream = new BufferedInputStream(socket.getInputStream());
            int contentLength = -1;
            int charValue = bufferedInputStream.read();
            boolean meetData = false;
            
            while (charValue != -1)
            {
                byteArrayOutputStream.write(charValue);
                
                if (charValue == '\n')
                {
                    if (contentLength >= 0 && byteArrayOutputStream.size() == 2)
                    {
                        meetData = true;
                    }
                    else
                    {
                        String str = new String(byteArrayOutputStream.toByteArray()).trim();
                        
                        if (str.startsWith("Content-Length:"))
                        {
                            contentLength = Integer.parseInt(str.substring(16));
                        }
                    }
                    
                    byteArrayOutputStream = new ByteArrayOutputStream();
                }
                else if (meetData)
                {
                    if (byteArrayOutputStream.size() == contentLength)
                    {
                        break;
                    }
                }
                
                charValue = bufferedInputStream.read();
            }
            
            releasePath =
                "http://" + ip + ":" + port + "/imageServer/image?name="
                    + new String(byteArrayOutputStream.toByteArray()).trim();
        }
        catch (IOException e)
        {
            throw new ServiceException(e,"sendPostImage error!");
        }
        finally
        {
            CloseUtils.close(bufferedInputStream);
            CloseUtils.close(byteArrayOutputStream);
            CloseUtils.close(bufferedOutputStream);
            CloseUtils.close(socket);
        }
        
        return releasePath;
    }
    
    private void writeString(BufferedOutputStream bos, String str)
        throws IOException
    {
        bos.write(str.getBytes());
    }
    
    
    /**
     * <一句话功能简述> <功能详细描述>
     * 
     * @see [类、类#方法、类#成员]
     */
    private void getImageService()
    {
        if (ArrayUtils.isEmpty(imageService) || ArrayUtils.isEmpty(imageServicePort))
        {
            imageService = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.imageService").split(",");
            String[] port = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.imageServicePort").split(",");
            imageServicePort = new int[port.length];
            for (int i = 0; i < port.length; i++)
            {
                imageServicePort[i] = Integer.parseInt(port[i]);
            }
        }
        
    }
    
}
