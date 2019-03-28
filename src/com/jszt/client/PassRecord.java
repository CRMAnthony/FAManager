package com.jszt.client;

import com.jsits.commons.domain.EhsObject;

/**
 * 过车记录
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-22]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassRecord extends EhsObject
{
    /**
     * 摄像头编号
     */
    private String deviceId;
    
    /**
     * 数据采集时间（精确到毫秒）
     */
    private String time;
    
    /**
     * 设备类型
     */
    private String deviceType;
    
    /**
     * 车牌号码
     */
    private String plate;
    
    /**
     * 车牌颜色
     */
    private String color;
    
    /**
     * 车辆类型
     */
    private String type;
    
    /**
     * 车辆品牌
     */
    private String brand;
    
    /**
     * 方向
     */
    private String direction;
    
    /**
     * 车道
     */
    private String lane;
    
    /**
     * 速度
     */
    private String speed;
    
    /**
     * 车辆长度
     */
    private String length;
    
    /**
     * 过车记录图片的链接
     */
    private String url1;
    
    /**
     * 车身特写图片的链接
     */
    private String url2;
    
    /**
     * 车牌图片的链接
     */
    private String url3;

    public String getDeviceId()
    {
        return deviceId;
    }

    public void setDeviceId(String deviceId)
    {
        this.deviceId = deviceId;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDeviceType()
    {
        return deviceType;
    }

    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }

    public String getPlate()
    {
        return plate;
    }

    public void setPlate(String plate)
    {
        this.plate = plate;
    }

    public String getColor()
    {
        return color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getDirection()
    {
        return direction;
    }

    public void setDirection(String direction)
    {
        this.direction = direction;
    }

    public String getLane()
    {
        return lane;
    }

    public void setLane(String lane)
    {
        this.lane = lane;
    }

    public String getSpeed()
    {
        return speed;
    }

    public void setSpeed(String speed)
    {
        this.speed = speed;
    }

    public String getLength()
    {
        return length;
    }

    public void setLength(String length)
    {
        this.length = length;
    }

    public String getUrl1()
    {
        return url1;
    }

    public void setUrl1(String url1)
    {
        this.url1 = url1;
    }

    public String getUrl2()
    {
        return url2;
    }

    public void setUrl2(String url2)
    {
        this.url2 = url2;
    }

    public String getUrl3()
    {
        return url3;
    }

    public void setUrl3(String url3)
    {
        this.url3 = url3;
    }

    @Override
    public String toString()
    {
        return "PassRecord [deviceId=" + deviceId + ", time=" + time + ", deviceType=" + deviceType + ", plate="
            + plate + ", color=" + color + ", type=" + type + ", brand=" + brand + ", direction=" + direction
            + ", lane=" + lane + ", speed=" + speed + ", length=" + length + ", url1=" + url1 + ", url2=" + url2
            + ", url3=" + url3 + "]";
    }
    
    
}
