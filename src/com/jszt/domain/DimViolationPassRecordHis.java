package com.jszt.domain;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.jsits.commons.domain.EhsObject;
import com.jszt.client.PassRecord;

/**
 * 违法过车记录历史表
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-5-25]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DimViolationPassRecordHis extends EhsObject
{
    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(DimViolationPassRecordHis.class);
    
    /**
     * 摄像头编号
     */
    private String lpCameraId;                   
    
    /**
     * 日期
     */
    private int dateKey;                        
    
    /**
     * 时间
     */
    private int timeKey; 
    
    /**
     * 车牌号码
     */
    private String licensePlate;                 
    
    /**
     * 车牌种类
     */
    private String plateTypeId;                  
    
    /**
     * 重点车辆类型
     */
    private String forbidType;                   
    
    /**
     * 通行证类型
     */
    private String cardType;                     
    
    /**
     * 设备类型
     */
    private String deviceType;                   
    
    /**
     * 车牌颜色
     */
    private String licensePlateColor;            
    
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
    private int vehicleSpeed;                    
    
    /**
     * 车辆长度
     */
    private int vehicleLength;                   
   
    /**
     * 报警类型
     */
    private String alarmType;                    
    
    /**
     * 转发标志
     */
    private String tstrans;                      
    
    /**
     * 车牌所属地区
     */
    private String licenseRegion;                
    
    /**
     * 过车记录图片的链接
     */
    private String licensePlateLink1;            
    
    /**
     * 车身特写图片的链接
     */
    private String licensePlateLink2;            
    
    /**
     * 车牌图片的链接
     */
    private String licensePlateLink3;            
    
    /**
     * 提取标识
     */
    private int verifyMark;   
    
    /**
     * 禁行区域编号
     */
    private int areaId;
    
    public  DimViolationPassRecordHis()
    {
        
    }
    
    public  DimViolationPassRecordHis(PassRecord passRecord,String cardType,int areaId)
    {
        this.lpCameraId = passRecord.getDeviceId();
        long time = Long.parseLong(passRecord.getTime());
        this.dateKey = (int)(time/1000000000);
        this.timeKey = (int)(time%1000000000);
        this.licensePlate = passRecord.getPlate();
        this.plateTypeId = passRecord.getType();
        this.forbidType = "1";
        this.cardType = cardType;
        this.deviceType = passRecord.getDeviceType();
        String color = passRecord.getColor();
        if (StringUtils.isNotBlank(color) && !color.equals("未识别"))
        {
            this.licensePlateColor = color;
            logger.info(color);
        }
        this.licensePlateColor = passRecord.getColor();
        this.direction = passRecord.getDirection();
        this.lane = passRecord.getLane();
        this.vehicleSpeed = Integer.parseInt(passRecord.getSpeed());
        this.vehicleLength = Integer.parseInt(passRecord.getLength());
        this.licensePlateLink1 = passRecord.getUrl1();
        this.licensePlateLink2 = passRecord.getUrl2();
        this.licensePlateLink3 = passRecord.getUrl3();
        this.verifyMark = 0;
        this.areaId = areaId;
    }
    
    public String getLpCameraId()
    {
        return lpCameraId;
    }
    
    public void setLpCameraId(String lpCameraId)
    {
        this.lpCameraId = lpCameraId;
    }
    
    public int getDateKey()
    {
        return dateKey;
    }
    
    public void setDateKey(int dateKey)
    {
        this.dateKey = dateKey;
    }
    
    public int getTimeKey()
    {
        return timeKey;
    }
   
    public void setTimeKey(int timeKey)
    {
        this.timeKey = timeKey;
    }
    
    public String getLicensePlate()
    {
        return licensePlate;
    }
    
    public void setLicensePlate(String licensePlate)
    {
        this.licensePlate = licensePlate;
    }
    
    public String getPlateTypeId()
    {
        return plateTypeId;
    }
    
    public void setPlateTypeId(String plateTypeId)
    {
        this.plateTypeId = plateTypeId;
    }
    
    public String getForbidType()
    {
        return forbidType;
    }
    
    public void setForbidType(String forbidType)
    {
        this.forbidType = forbidType;
    }
    
    public String getCardType()
    {
        return cardType;
    }
   
    public void setCardType(String cardType)
    {
        this.cardType = cardType;
    }
   
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
   
    public String getLicensePlateColor()
    {
        return licensePlateColor;
    }
    
    public void setLicensePlateColor(String licensePlateColor)
    {
        this.licensePlateColor = licensePlateColor;
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
    
    public int getVehicleSpeed()
    {
        return vehicleSpeed;
    }
    
    public void setVehicleSpeed(int vehicleSpeed)
    {
        this.vehicleSpeed = vehicleSpeed;
    }
   
    public int getVehicleLength()
    {
        return vehicleLength;
    }
   
    public void setVehicleLength(int vehicleLength)
    {
        this.vehicleLength = vehicleLength;
    }
   
    public String getAlarmType()
    {
        return alarmType;
    }
    
    public void setAlarmType(String alarmType)
    {
        this.alarmType = alarmType;
    }
    
    public String getTstrans()
    {
        return tstrans;
    }
    
    public void setTstrans(String tstrans)
    {
        this.tstrans = tstrans;
    }
    
    public String getLicenseRegion()
    {
        return licenseRegion;
    }
    
    public void setLicenseRegion(String licenseRegion)
    {
        this.licenseRegion = licenseRegion;
    }
    
    public String getLicensePlateLink1()
    {
        return licensePlateLink1;
    }
    
    public void setLicensePlateLink1(String licensePlateLink1)
    {
        this.licensePlateLink1 = licensePlateLink1;
    }
    
    public String getLicensePlateLink2()
    {
        return licensePlateLink2;
    }
   
    public void setLicensePlateLink2(String licensePlateLink2)
    {
        this.licensePlateLink2 = licensePlateLink2;
    }
    
    public String getLicensePlateLink3()
    {
        return licensePlateLink3;
    }
    
    public void setLicensePlateLink3(String licensePlateLink3)
    {
        this.licensePlateLink3 = licensePlateLink3;
    }
   
    public int getVerifyMark()
    {
        return verifyMark;
    }
    
    public void setVerifyMark(int verifyMark)
    {
        this.verifyMark = verifyMark;
    }

    public int getAreaId()
    {
        return areaId;
    }

    public void setAreaId(int areaId)
    {
        this.areaId = areaId;
    }
    
    
}
