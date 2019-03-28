/*
 * 文 件 名:  IllegalRecordServiceImpl.java
 * 版    权:  Huawei Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  ChangJun
 * 修改时间:  2015年5月20日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.UUID;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.tempuri.ServiceStub;

import com.jsits.commons.db.ConnectionFactory;
import com.jsits.commons.domain.PicWrod;
import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.CloseUtils;
import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.ExcelUtil;
import com.jsits.commons.util.ImageUtils;
import com.jsits.commons.util.ServiceAssert;
import com.jsits.commons.util.ServiceException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.CommonDao;
import com.jszt.dao.DimSurveillanceCameraDao;
import com.jszt.dao.DimUnstandardVehicleDao;
import com.jszt.dao.DimViolationInvalidDao;
import com.jszt.dao.DimViolationPassRecordDao;
import com.jszt.dao.DimViolationStatisticDao;
import com.jszt.dao.DimViolationValidDao;
import com.jszt.domain.DimConstructionVehicle;
import com.jszt.domain.DimSurveillanceCamera;
import com.jszt.domain.DimSysAccount;
import com.jszt.domain.DimUnstandardVehicle;
import com.jszt.domain.DimViolationStatistic;
import com.jszt.domain.DimViolationVehicleType;
import com.jszt.service.CommonService;
import com.jszt.service.ConstructionVehicleService;
import com.jszt.service.IllegalRecordService;
import com.jszt.service.PassCardService;
import com.jszt.service.UnstandardVehicleService;
import com.jszt.service.common.ImageService;
import com.jszt.vo.ConstructionVehicleReq;
import com.jszt.vo.IllegalApproval;
import com.jszt.vo.IllegalRecord;
import com.jszt.vo.IllegalRecordQueryConditionReq;
import com.jszt.vo.LPCameraVo;
import com.jszt.vo.NoticeVo;
import com.jszt.vo.PassCard;
import com.jszt.vo.PassCardReq;
import com.jszt.vo.PicImageReq;
import com.jszt.vo.PicImageResp;
import com.jszt.vo.UnstandardVehicleReq;
import com.jszt.vo.ViolationCameraVo;
import com.jszt.vo.ViolationPassRecordVo;
import com.jszt.vo.ViolationRecordReq;

/**
 * 违法记录业务操作类
 * 
 * @author  ChangJun
 * @version  [版本号, 2015年5月20日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class IllegalRecordServiceImpl implements IllegalRecordService
{
    
    private static Logger logger = Logger.getLogger(IllegalRecordServiceImpl.class);
    
    // 锁标识
    private static final String LOCKFLAG = "LOCK";
    
    private static final Map<String,String> FORBID_TYPE  = new HashMap<String,String>(3);
    
    private static final Map<String,String> PLATE_TYPE_ID  = new HashMap<String,String>(2);
    
    private static final Map<String,String> CARD_TYPE  = new HashMap<String,String>(3);
    
    static 
    {
        FORBID_TYPE.put("1", "货车");
        FORBID_TYPE.put("2", "危险品车辆");
        FORBID_TYPE.put("3", "黄标车");
        
        PLATE_TYPE_ID.put("1", "大型车辆");
        PLATE_TYPE_ID.put("2", "小型车辆");
        
        CARD_TYPE.put("1", "短期通行证");
        CARD_TYPE.put("2", "长期通行证");
        CARD_TYPE.put("0", "无通行证");
    }
    
    
    /**
     * 审核数据提取
     */
    @Override
    public List<IllegalRecord> extractRecord(IllegalRecordQueryConditionReq condition)
    {
        List<IllegalRecord> records = getRecord(condition);
        
        // 封装违法次数
        buildIllegalData(records);
        return records;
    }

    /** 封装违法违法次数
     * <功能详细描述>
     * @param records
     * @see [类、类#方法、类#成员]
     */
    private void buildIllegalData(List<IllegalRecord> records)
    {
        int mouth =
            ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.IllegalCountMouth"),
                3);
        String time = DateUtils.formater(DateUtils.adjustMonth(DateUtils.getCurrentTime(), -mouth), "yyyyMMdd");
        int count = 0;
        if(null!=records&&records.size()>0)
        {
        	for (IllegalRecord illegalRecord : records)
            {
                // 封装违法次数
                count =
                    ServiceUtil.getService(DimViolationValidDao.class).getViolationCount(time,
                        illegalRecord.getLicensePlate());
                illegalRecord.setIllegalCount(count);
                
                // 黄标车需要查询品牌
                if (StringUtils.equals(illegalRecord.getForbidType(), "3"))
                {
                    UnstandardVehicleReq req = new UnstandardVehicleReq();
                    req.setLicensePlate(illegalRecord.getLicensePlate());
                    List<DimUnstandardVehicle> unstandardVehicle =
                        ServiceUtil.getService(DimUnstandardVehicleDao.class).getUnstandardVehicle(req);
                    if (CollectionUtils.isNotEmpty(unstandardVehicle))
                    {
                        illegalRecord.setVehicleBrand(unstandardVehicle.get(0).getVehicleBrand());
                        DimViolationVehicleType violationVehicleType = ServiceUtil.getService(CommonService.class)
                            .getViolationVehicleType(unstandardVehicle.get(0).getPlateTypeId());
                        if (null != violationVehicleType)
                        {
                            illegalRecord.setPlateColor(violationVehicleType.getPlateColor());
                        }
                        illegalRecord.setPlateTypeId(unstandardVehicle.get(0).getPlateTypeId());
                        illegalRecord.setOwnerName(unstandardVehicle.get(0).getOwnerName());
                    }
                }
                DimSurveillanceCamera scByNum = ServiceUtil.getService(DimSurveillanceCameraDao.class).getSCByNum(illegalRecord.getAreaId());
                
                if (scByNum != null)
                {
                    // 禁行区域名称
                    illegalRecord.setIllegalSites(scByNum.getInstallationPosition());
                }
               
            }
        }
        
        
    }

    /**
     * 闯禁审核数据提取
     * @param condition
     * @return
     */
    private List<IllegalRecord> getRecord(IllegalRecordQueryConditionReq condition)
    {
        List<IllegalRecord> ret = null;
        
        // 根据帐号获取禁行区域列表（获取的是列表的ID）
        List<Integer> forbidAreaByAccount = ServiceUtil.getService(CommonService.class).getForbidAreaByAccount(condition.getAccount());
        if (CollectionUtils.isEmpty(forbidAreaByAccount))
        {
            throw new ServiceException("forbidArea is empty , nothing can search for account " + condition.getAccount());
        }
        
        // 页面需要根据违法区域禁行查询
        if (condition.getAreaId() != 0)
        {
            // 权限中是否包含查询该区域查询权限
            if (forbidAreaByAccount.contains(condition.getAreaId()))
            {
                forbidAreaByAccount.clear();
                forbidAreaByAccount.add(condition.getAreaId());
                condition.setAreaIds(forbidAreaByAccount);
            }
            // 没有权限查询
            else 
            {
                return new ArrayList<IllegalRecord>();
            }
        }
        condition.setAreaIds(forbidAreaByAccount);
        
        
        // 待审核
        if (condition.getProcessState() == 1)
        {
            //给当前代码块加锁，同一时刻只有一个线程可以执行当前代码块
        	synchronized (LOCKFLAG)
            {
                // 同步并更新
                DimViolationPassRecordDao service = ServiceUtil.getService(DimViolationPassRecordDao.class);
                ret = service.extractPassRecord(condition);
                service.updateVerifyMark(ret, 1);
            }
        	
            if(null!=ret&&ret.size()>0)
            {
            	// 设置30分钟后更新标识
                Timer timer = new Timer();
                int time =
                    ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService"))
                        .getConfig("sys.UpdateVerifyMarkTime"),
                        1800000);
                timer.schedule(new UpdateVerifyMarkTask(ret), time);
            }
            
        }
       
        // 审核有效
        else if (condition.getProcessState() == 2)
        {
            ret = ServiceUtil.getService(DimViolationValidDao.class).extractDimViolationValid(condition);
            setVerifyPerson(ret);
        }
        // 审核无效
        else if (condition.getProcessState() == 3)
        {
            ret = ServiceUtil.getService(DimViolationInvalidDao.class).extractViolationInvalid(condition);
            setVerifyPerson(ret);
        }
//        // 外地车首次违法
//        else if (condition.getProcessState() == 4)
//        {
//            ret = ServiceUtil.getService(DimViolationFirstDao.class).extractViolationFirst(condition);
//        }
        return ret;
    }
    private void setVerifyPerson(List<IllegalRecord> ret)
    {
    	CommonDao dao = ServiceUtil.getService(CommonDao.class);
    	if(CollectionUtils.isNotEmpty(ret))
    	{
    		for(IllegalRecord record:ret)
    		{
    			DimSysAccount dimSysAccount = dao.getSysAccount(record.getVerifyPerson());
    			record.setVerifyPersonName(null != dimSysAccount ? dimSysAccount.getAccountName() : "未配置");
    		}
    	}
    }
    @Override
    public PicImageResp getPic(PicImageReq req)
    {
        PicImageResp resp = null;
        if (req.getProcessState() == 1)
        {
            resp = ServiceUtil.getService(DimViolationPassRecordDao.class).getPassPic(req.getDate(), req.getLicensePlate(), req.getAreaId());
        }
        else if (req.getProcessState() == 2)
        {
            resp = ServiceUtil.getService(DimViolationValidDao.class).getViolationPic(req.getDate(), req.getLicensePlate(),req.getAreaId());
        }
        else if (req.getProcessState() == 3)
        {
            resp = ServiceUtil.getService(DimViolationInvalidDao.class).getInvalidPic(req.getDate(), req.getLicensePlate(),req.getAreaId());
        }
//        else if (req.getProcessState() == 4)
//        {
//            resp = ServiceUtil.getService(DimViolationFirstDao.class).getFirstPic(req.getDate(), req.getLicensePlate(),req.getAreaId());
//        }
        
        return resp;
    }

    /**
     * 违法审核数据提交
     * @param data
     */
    @Override
    public void illegalApproval(IllegalApproval data)
    {
        // 封装数据
        buildApprovalData(data);
        ConnectionFactory factory = ServiceUtil.getService("connectionFactory");
        Connection connection = null;
        try
        {
            connection = factory.getConnection();
            connection.setAutoCommit(false);
            String tableName = "";
            if (data.getSourceApprovalType() == 1)
            {
            	tableName = "违法表";
            }
            // 有效
            else if (data.getSourceApprovalType() == 2)
            {
            	tableName = "有效表";
            }
            // 无效
            else if (data.getSourceApprovalType() == 3)
            {
            	tableName = "无效表";
            }
            long t1=System.currentTimeMillis();
        	logger.info("【重点车辆】开始删除【"+tableName+"】原始数据,当前:"+t1+"ms");
            // 删除原始数据
            deleteSource(data,connection);
            long t2=System.currentTimeMillis();
            logger.info("【重点车辆】结束删除【"+tableName+"】原始数据,当前:"+t2+"ms"+",耗时:"+((t2-t1)/1000)+"s"+((t2-t1)%1000)+"ms");
            // 插入审核记录
            insertRecords(data, connection);
            logger.info("【重点车辆】结束插入审核记录,当前:"+System.currentTimeMillis()+"ms");
            connection.commit();
        }
        catch (Exception e)
        {
            if (connection != null)
            {
                try
                {
                    connection.rollback();
                }
                catch (SQLException e1)
                {
                    throw new ServiceException(e, "Connection rollbackError!");
                }
            }
            
            throw new ServiceException(e, "illegalApproval error!"+e.getMessage());
        }
        finally
        {
            CloseUtils.close(connection);
        }
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @param connection
     * @see [类、类#方法、类#成员]
     */
    private void deleteSource(IllegalApproval data, Connection connection)
    {
        // 待审核  删除过车记录表
        if (data.getSourceApprovalType() == 1)
        {
            ServiceUtil.getService(DimViolationPassRecordDao.class).deletePassRecord(connection, data);
        }
        // 有效
        else if (data.getSourceApprovalType() == 2)
        {
            ServiceUtil.getService(DimViolationValidDao.class).deleteViolationValid(connection, data);
        }
        // 无效
        else if (data.getSourceApprovalType() == 3)
        {
            ServiceUtil.getService(DimViolationInvalidDao.class).deleteViolationInvalid(connection, data);
        }
//        // 外地车首次违法 
//        else if(data.getSourceApprovalType() == 4)
//        {
//            ServiceUtil.getService(DimViolationFirstDao.class).deleteViolationFirst(connection, data);
//        }
        
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @param connection
     * @throws javax.xml.rpc.ServiceException 
     * @throws RemoteException 
     * @see [类、类#方法、类#成员]
     */
    private void insertRecords(IllegalApproval data, Connection connection)
    {
        // 插入到指定表中
        // 无效
        if (data.getApprovalType() == 3)
        {
        	long t1 = System.currentTimeMillis();
        	logger.info("【重点车辆】插入无效表开始,当前时间:"+t1+"ms");
            ServiceUtil.getService(DimViolationInvalidDao.class).insertViolationInvalid(connection, data);
            long t2 = System.currentTimeMillis();
            logger.info("【重点车辆】插入无效表结束,当前时间:"+t2+"ms"+",耗时:"+((t2-t1)/1000)+"s"+((t2-t1)%1000)+"ms");
        }
        // 有效
        else if (data.getApprovalType() == 2)
        {
        	long t1 = System.currentTimeMillis();
        	logger.info("【重点车辆】插入有效表开始,当前时间:"+t1+"ms");
            // 判断如果该记录已经在审核记录表中了
            if (ServiceUtil.getService(DimViolationInvalidDao.class)
                .queryViolationInvalid(StringUtils.substring(data.getDateKey(), 0, 8),
                    data.getNewLicensePlate(),
                    data.getAreaId()) == null)
            {
            	//20170626 暂时把发送短信去掉
            	/*logger.info("发送短信开始,当前时间:"+System.currentTimeMillis()+"ms");
                // 发送短信 
                sendMessage(data);
                logger.info("发送短信结束,当前时间:"+System.currentTimeMillis()+"ms");*/
                
                // 插入有效表
                ServiceUtil.getService(DimViolationValidDao.class).insertViolationValid(connection, data);
                long t2 = System.currentTimeMillis();
                logger.info("【重点车辆】插入有效表结束--,当前时间:"+t2+"ms"+",耗时:"+((t2-t1)/1000)+"s"+((t2-t1)%1000)+"ms");
            }
            else 
            {
            	logger.info("【重点车辆】插入无效表开始--,当前时间:"+t1+"ms");
                // 插入无效表
                ServiceUtil.getService(DimViolationInvalidDao.class).insertViolationInvalid(connection, data);
                long t2 = System.currentTimeMillis();
                logger.info("【重点车辆】插入无效表结束--,当前时间:"+t2+"ms"+",耗时:"+((t2-t1)/1000)+"s"+((t2-t1)%1000)+"ms");
            }
            long t3 = System.currentTimeMillis();
            logger.info("【重点车辆】插入有效表结束,当前时间:"+t3+"ms");
        }
//        // 首次违法
//        else if (data.getApprovalType() == 4)
//        {
//            sendMessage(data);
//            ServiceUtil.getService(DimViolationFirstDao.class).insertViolationFirst(connection, data);
//        }
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @throws javax.xml.rpc.ServiceException 
     * @throws RemoteException 
     * @see [类、类#方法、类#成员]
     */
    private void sendMessage(IllegalApproval data)
    {
        String phone = getPhoneNum(data.getNewLicensePlate());
        try
        {
            DimSurveillanceCamera scByNum = ServiceUtil.getService(DimSurveillanceCameraDao.class).getSCByNum(String.valueOf(data.getAreaId()));
            
            if (scByNum == null)
            {
                if (logger.isInfoEnabled())
                {
                    logger.info("scByNum is null,num is" + data.getAreaId());
                }
                return;
            }
            
            String meg = getMessage(data,scByNum);
            
            if (logger.isInfoEnabled())
            {
                logger.info("短信内容：" + meg);
            }
            
			// 取得的手机号为空，则调用通过车牌号查询手机号码接口
			if (StringUtils.isEmpty(phone))
			{
				ServiceStub stub = new ServiceStub();
				org.tempuri.ServiceStub.GetSJHMByHPHMAndType request = new org.tempuri.ServiceStub.GetSJHMByHPHMAndType();
				request.setHPHM(data.getNewLicensePlate());
				request.setType(StringUtils.equals(data.getPlateTypeId(), "1") ? 2 : 1);
				org.tempuri.ServiceStub.GetSJHMByHPHMAndTypeResponse response = stub.GetSJHMByHPHMAndType(request);
				// 手机号
				phone = response.getGetSJHMByHPHMAndTypeResult();
				// 获取手机号成功验证
				String message = response.getMsg();

				if (StringUtils.isNotEmpty(phone))
				{
					logger.info(phone);
					logger.info(message);
				} else
				{
					logger.info("Get phone number failed, license plate: " + data.getNewLicensePlate());
				}
			}
			NoticeVo noticeVo=new NoticeVo();
        	noticeVo.setNoticeContent(meg);
        	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
        	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
            
            if (StringUtils.isNotEmpty(phone)&&ServiceUtil.getService(CommonService.class).smsInfo(meg, phone))
            {
                data.setNoticeState("已通知");
            }
            
        }
        catch (UnsupportedEncodingException e1)
        {
            logger.error("getIllegalSites convert properties error!", e1);
        }
        catch (Exception e1)
        {
            logger.error("send phoneMessage error! Phone Num is" + phone, e1);
        }

    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @see [类、类#方法、类#成员]
     */
    private String getMessage(IllegalApproval data,DimSurveillanceCamera surveillanceCamera)
        throws UnsupportedEncodingException
    {
    	StringBuilder sb =null;
        if (!StringUtils.equals(data.getForbidType(), "3"))
        {
        	sb = new StringBuilder();
        	sb.append("【吴江交警大队】");
        	sb.append(data.getNewLicensePlate());
        	sb.append("车主您好,您于");
        	sb.append(DateUtils.formater(data.getDateKey(), "yyyy-MM-dd HH:mm"));
        	sb.append("在吴江城区货车禁行区内实施了违反禁令标志指示的交通违法行为,该行为已被电子警察记录,请通过“吴江交警”微信公众号申请通行证。");
        }
        
        return sb.toString();
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @throws javax.xml.rpc.ServiceException 
     * @throws RemoteException 
     * @see [类、类#方法、类#成员]
     */
    private String getPhoneNum(String licensePlate)
    {
        PassCardReq passcard = new PassCardReq();
        passcard.setLicensePlate(licensePlate);
        passcard.setCardType(2);
        passcard.setBeginDate(19870101);
        passcard.setEndDate(20500101);
        // 查询长期通行证
        List<PassCard> card = ServiceUtil.getService(PassCardService.class).getPassCard(passcard);
        
        if (CollectionUtils.isEmpty(card))
        {
            
            // 查询短期通行证
            passcard.setCardType(1);
            card = ServiceUtil.getService(PassCardService.class).getPassCard(passcard);
            if (CollectionUtils.isEmpty(card))
            {
                // 查询黄标车
                UnstandardVehicleReq unstandardVehicle = new UnstandardVehicleReq();
                unstandardVehicle.setLicensePlate(licensePlate);
                List<DimUnstandardVehicle> uCard =
                    ServiceUtil.getService(UnstandardVehicleService.class).getUnstandardVehicle(unstandardVehicle);
                if (CollectionUtils.isEmpty(uCard))
                {
                	//查询渣土车
                	ConstructionVehicleReq constructionVehicleReq =new ConstructionVehicleReq(); 
                	constructionVehicleReq.setLicensePlate(licensePlate);
                	List<DimConstructionVehicle> dCard = ServiceUtil.getService(ConstructionVehicleService.class).getConstructionVehicle(constructionVehicleReq);
                	return CollectionUtils.isEmpty(dCard) ? "" : dCard.get(0).getPhoneNumber();
                }
                
                return CollectionUtils.isEmpty(uCard) ? "" : uCard.get(0).getPhoneNumber();
            }
            return card.get(0).getPhoneNumber();
        }
        return card.get(0).getPhoneNumber();
        
        
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @see [类、类#方法、类#成员]
     */
    private void buildApprovalData(IllegalApproval data)
    {
        data.setViolationId(UUID.randomUUID().toString());
        ConfigureService configService = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService"));
        data.setCollectionDeviceId(configService.getConfig("sys.dbIllegalSitesDeviceId"));
        data.setViolationType(configService.getConfig("sys.violationType"));
        data.setAgencyId("320584000000");
        // 黄标车
        if (StringUtils.equals(data.getForbidType(), "3"))
        {
            data.setCollectionDeviceId(configService.getConfig("sys.dbYellowIllegalSitesDeviceId"));
            data.setViolationType(configService.getConfig("sys.yellowViolationType"));
            data.setAgencyId("320584000000");
        }
        
        data.setVerifyTime(DateUtils.getCurrentTime());
        // 09其他设备编号
        data.setDeviceType("09");
        data.setIsWhiteList("0");
        data.setUploadState("未上传");
        data.setNoticeState("未通知");
    }

    /**
     * @param pic
     */
    @Override
    public String imageFormed(String[] pic, String type, String time,String areaId)
    {
        List<byte[]> picBytes = new ArrayList<byte[]>(2);
        try
        {
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(IOUtils.toByteArray(new URL(pic[0]))));
            int width = (int)(image.getWidth() / 1.5);
            int height = (int)(image.getHeight() / 1.5);
            
            // 将网络上的图片转换成byte
            for (int i = 0; i < pic.length; i++)
            {
                // 将网络图片转换成指定大小
                picBytes.add(ImageUtils.resizeToByte(new URL(pic[i]), width, height, false));
            }
            
            // 生成黑底图片
            BufferedImage bi = new BufferedImage(width * 2, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2 = (Graphics2D)bi.getGraphics();   
            g2.setBackground(Color.BLACK);
            byte[] blackPic =  ImageUtils.bufferedImageToBytes(bi);
            logger.info("开始合成图片");
            // 前两张图片合成
            byte[] illegalPic = ImageUtils.picMerge(picBytes, 2);
            logger.info("图片合成完成");
            // 在第三张图片上添加文字
            byte[] wordPic = drawPicWords(type, blackPic, time, areaId);
            logger.info("在第三张图片上添加文字完成");
            // 违法图片和黑底图片进行合成
            picBytes.clear();
            picBytes.add(illegalPic);
            picBytes.add(wordPic);
            logger.info("开始进行违法图片和黑底图片的合成");
            byte[] picMerge = ImageUtils.picMerge(picBytes, 1);
            logger.info("违法图片和黑底图片的合成完成");
            // 将防伪码加在图片的结尾
            byte[] securityCode = getSecurityCode(picMerge);
            byte[] retPicByte = ArrayUtils.addAll(picMerge,securityCode);
            logger.info("开始进行图片的上传");
            // 上传
            return ServiceUtil.getService(ImageService.class).imageUpload(retPicByte);
            
        }
        catch (Exception e)
        {
            throw new ServiceException(e, "pic Merge error!"+"famanger imageFormed exception"+e.getMessage());
        }
    }
    
  
    // 增加防伪码
    private byte[] getSecurityCode(byte[] picMerge) throws NoSuchAlgorithmException, IOException
    {
        
        int[] retCode = new int[4];
        retCode[0] =  picMerge[0];
        retCode[1] =  picMerge[1];
        retCode[2] =  picMerge[2];
        retCode[3] =  picMerge[3];
       
       int i = 3;
       while(i < picMerge.length)
       {
           retCode[0] = (retCode[0]^picMerge[i+1]);
           retCode[1] = (retCode[1]^picMerge[i+2]);
           retCode[2] = (retCode[2]^picMerge[i+3]);
           retCode[3] = (retCode[3]^picMerge[i+4]);
           i=i+4;
           if (i+4 >= picMerge.length)
           {
               break;
           }
       }
       
       StringBuilder sb = new StringBuilder(8);
       String temp = null;
       for (int b : retCode)
       {
           temp = Integer.toHexString(b);
           if (temp.length() == 1)
           {
              sb.append("0");
              sb.append(temp);
           }
           else 
           {
               sb.append(StringUtils.substring(temp, temp.length() - 2,temp.length()));
           } 
       }
       return sb.toString().getBytes();
    }
    
    private String md5(String plainText) throws NoSuchAlgorithmException
    {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(plainText.getBytes());
        byte b[] = md.digest();
        
        int i;
        
        StringBuffer buf = new StringBuffer("");
        for (int offset = 0; offset < b.length; offset++)
        {
            i = b[offset];
            if (i < 0)
                i += 256;
            if (i < 16)
                buf.append("0");
            buf.append(Integer.toHexString(i));
        }
        
        return buf.toString();// 32位的加密
        
        // return buf.toString().substring(8, 24));// 16位的加密
        
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param type
     * @param time
     * @throws IOException 
     * @throws NoSuchAlgorithmException 
     * @see [类、类#方法、类#成员]
     */
    private byte[] drawPicWords(String type, byte[] pic, String time, String areaId)
        throws IOException, NoSuchAlgorithmException
    {
        DimSurveillanceCamera sc = ServiceUtil.getService(DimSurveillanceCameraDao.class).getSCByNum(areaId);
        
        if (sc == null)
        {
            throw new ServiceException("SurveillanceCamera is not find! areaId is = " + areaId);
        }
        
        String fontStyle = "宋体";
        List<PicWrod> words = new ArrayList<PicWrod>(4);
        int fontSize = 15;
        int xPadding = 5;
        
        // 图片上写字
        PicWrod pic1 =
            new PicWrod(xPadding, 25, "违法时间：" + DateUtils.formater(time, "yyyy-MM-dd HH:mm:ss"), new Font(fontStyle,
                Font.PLAIN, fontSize), Color.YELLOW);
        
        PicWrod pic2 = null;
        PicWrod pic3 = null;
        PicWrod pic4 = null;
        pic2 =
            new PicWrod(xPadding, 45, "违法地点：" + sc.getInstallationPosition(),
                new Font(fontStyle, Font.PLAIN, fontSize), Color.YELLOW);
        
        pic3 =
            new PicWrod(xPadding, 65, "违法地点代码：" + sc.getFacilityAddress(), new Font(fontStyle, Font.PLAIN, fontSize),
                Color.YELLOW);
        
        pic4 =
            new PicWrod(xPadding, 85, "设备编号：" + sc.getFacilityId(), new Font(fontStyle, Font.PLAIN, fontSize),
                Color.YELLOW);
        
        PicWrod pic5 =
            new PicWrod(xPadding, 105, "违法行为：13441  机动车违反禁令标志指示的", new Font(fontStyle, Font.PLAIN, fontSize),
                Color.YELLOW);
        
        PicWrod pic6 =
            new PicWrod(xPadding, 125, "防伪码：" + md5(DateUtils.getCurrentTime()), new Font(fontStyle, Font.PLAIN,
                fontSize), Color.YELLOW);
        
        words.add(pic1);
        words.add(pic2);
        words.add(pic3);
        words.add(pic4);
        words.add(pic5);
        words.add(pic6);
        
        return ImageUtils.picWriteWords(pic, words);
    }

    
    /**
     * 查询闯禁数据_1
     * @param condition
     * @return
     */
    private List<IllegalRecord> getRecordForPage(IllegalRecordQueryConditionReq condition)
    {
        List<IllegalRecord> ret = null;
        
        // 根据违法地点查询
        if (condition.getAreaId() != 0)
        {
            List<Integer> areaIds = new ArrayList<Integer>(1);
            areaIds.add(condition.getAreaId());
            condition.setAreaIds(areaIds);
        }
        // 待处理
        if (condition.getProcessState() == 1)
        {
            if(StringUtils.isNotEmpty(condition.getApproveUser()))
            {
            	ret = new ArrayList<IllegalRecord>();
            }else
            {
            	ret = ServiceUtil.getService(DimViolationPassRecordDao.class).queryPassRecord(condition);
            }
        	
        }
        
        // 审核有效
        else if (condition.getProcessState() == 2)
        {
        	logger.info("货车违闯禁查询有效");
            ret = ServiceUtil.getService(DimViolationValidDao.class).queryDimViolationValid(condition);
            setVerifyPerson(ret);
        }
        // 审核无效
        else if (condition.getProcessState() == 3)
        {
            ret = ServiceUtil.getService(DimViolationInvalidDao.class).queryViolationInvalid(condition);
            setVerifyPerson(ret);
        }
//        // 外地车首次违法
//        else if (condition.getProcessState() == 4)
//        {
//            ret = ServiceUtil.getService(DimViolationFirstDao.class).queryViolationFirst(condition);
//        }
        return ret;
    }
    
    /**
     * 定时器任务统计违法次数，发送短信
     */
    @Override
    public void statisticsIllegalCount()
    {
        if (logger.isDebugEnabled())
        {
            logger.debug("statisticsIllegalCount timer begin");
        }
        //20170626 暂时把发送短信去掉
        /*try
        {
            // 先处理统计表中已有的数据
            calcUpdateStatistic();
            
            // 根据违法记录表数据进行统计 (查询插入)
            calcInsertStatistic();
        }
        catch (Exception e)
        {
            logger.error("statisticsIllegalCount timmer error!", e);
        }*/
        
    }
    

    /**
     * 过滤不需要的数据 定时器任务
     */
    @Override
    public void deleteUnqualifiedIllegalRecord()
    {
        
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteUnqualifiedIllegalRecord timer begin");
        }
        try
        {
        	
            // 昨天
            int yesterday =
                ConvertUtils.parseInt(StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), -1), 0, 8),
                    0);
            // 前天
            int dayBeforeYesterday =
                ConvertUtils.parseInt(StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), -2), 0, 8),
                    0);
            // 3个月以前
            int threeMouthLater =
                ConvertUtils.parseInt(StringUtils.substring(DateUtils.adjustMonth(DateUtils.getCurrentTime(), -3), 0, 8),
                    0);
            
            // 删除重复数据
            try
            {
            	ServiceUtil.getService(DimViolationPassRecordDao.class).delteRepeatingPicData(yesterday);
            }catch(Exception e)
            {
            	logger.error("删除重复数据失败!", e);
            }
            
            
            // 删除当天记录为中只有一张图片的记录
            try
            {
            	ServiceUtil.getService(DimViolationPassRecordDao.class).deleteRecordOnlyOnePic(yesterday);
            }catch(Exception e)
            {
            	logger.error("删除当天记录中只有一张图片的记录失败!", e);
            }
            
            
            // 查过违法车记录表里的外地车（前一天的记录），如果外地车在不再（三个月以内 至 前2天））过车记录表里则删除违法车记录表里记录
            try
            {
            	List<String> delRecords = new ArrayList<String>();
                List<String> nonlocalRecords =
                    ServiceUtil.getService(DimViolationPassRecordDao.class).getNonlocalRecord(yesterday);
                for (String licensePlate : nonlocalRecords)
                {
                    if (ServiceUtil.getService(DimViolationPassRecordDao.class).isPassRecordFromWjwf(threeMouthLater,
                        dayBeforeYesterday,
                        licensePlate))
                    {
                        delRecords.add(licensePlate);
                    }
                }
                if(delRecords.size()>0)
                {
                	ServiceUtil.getService(DimViolationPassRecordDao.class).deleteRecord(delRecords, yesterday);
                }
            }
            catch(Exception e)
            {
            	logger.error("删除外地车在不再（三个月以内 至 前2天））过车记录表里则删除违法车记录表里记录失败!", e);
            }
            
            //放在后面等都执行了删除操作再重建索引
            logger.info("开始清理违法表和历史表，清除10天以前和60天以前的数据");
        	deletePassAndHisRecords();
        	logger.info("结束清理违法表和历史表，清除10天以前和60天以前的数据");
        	
        }
        catch (Exception e)
        {
            logger.error("deleteUnqualifiedIllegalRecord error!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteUnqualifiedIllegalRecord timer end");
        }
    }
    
    private void deletePassAndHisRecords()
    {
    	try
    	{
    		ConfigureService service = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
    		int tenBefore = 0 - Integer.parseInt(service.getConfig("sys.delPassDate"));
    		int sixtyBefore = 0 - Integer.parseInt(service.getConfig("sys.delHisDate"));
    		int tenDayBefore = Integer.parseInt(StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), tenBefore), 0, 8));
        	int twoMonthBefore = Integer.parseInt(StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), sixtyBefore), 0, 8));
        	DimViolationPassRecordDao recordDao = ServiceUtil.getService(DimViolationPassRecordDao.class);
        	logger.info("开始清理重点车辆违法表");
        	recordDao.deltePassRecordByDate(tenDayBefore);
        	logger.info("结束清理重点车辆违法表");
        	
        	String passIndex = service.getConfig("sys.passIndex");
    		String hisIndex = service.getConfig("sys.hisIndex");
    		String mainIndex = service.getConfig("sql.rebuildIndex");
    		try
    		{
    			if(StringUtils.isNotEmpty(passIndex))
        		{
        			logger.info("开始重建重点车辆违法表索引");
        			String []passIndexs = StringUtils.split(passIndex,",");
        			for(String index:passIndexs)
        			{
        				String newSql = StringUtils.replace(mainIndex, "#index", index);
        				logger.info("索引名:"+newSql);
        				recordDao.rebuildIndex(newSql);
        			}
        			logger.info("结束重建重点车辆违法表索引");
        		}
    		}catch(Exception e)
    		{
    			logger.info("重建重点车辆违法表索引失败:"+e.getMessage());
    		}
    		
    		
        	logger.info("开始清理重点车辆违法历史表");
        	recordDao.delteHisPassRecordByDate(twoMonthBefore);
        	logger.info("结束清理重点车辆违法历史表");
        	
        	if(StringUtils.isNotEmpty(hisIndex))
    		{
    			logger.info("开始重建重点车辆违法历史表索引");
    			String []passIndexs = StringUtils.split(hisIndex,",");
    			for(String index:passIndexs)
    			{
    				String newSql = StringUtils.replace(mainIndex, "#index", index);
    				logger.info("索引名:"+newSql);
    				recordDao.rebuildIndex(newSql);
    			}
    			logger.info("结束重建重点车辆违法历史表索引");
    		}
    	}
    	catch (Exception e)
        {
            logger.error("重点车辆deletePassAndHisRecords error!"+e.getMessage(), e);
        }
    }
    
    /**
     * 删除短信统计前30天无效信息 定时器任务
     */
    @Override
    public void deleteNoticeCount()
    {
        
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteNoticeCount timer begin");
        }
        try
        {
            // 前30天
            int yesterday =
                ConvertUtils.parseInt(StringUtils.substring(DateUtils.adjustDay(DateUtils.getCurrentTime(), -30), 0, 8),
                    0);
            
            // 删除短信统计前30天无效信息 定时器任务
            ServiceUtil.getService(CommonDao.class).deleteNoticeCount(yesterday);
            
        }
        catch (Exception e)
        {
            logger.error("deleteNoticeCount error!", e);
        }
        
        if (logger.isDebugEnabled())
        {
            logger.debug("deleteNoticeCount timer end");
        }
    }
    
    /**
     * 查询闯禁数据
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryRecord(IllegalRecordQueryConditionReq condition)
    {
        List<IllegalRecord> records = getRecordForPage(condition);
        // 封装违法次数
        buildIllegalData(records);
        return records;
    }
    
    


    /**
     * @param condition
     * @return
     */
    @Override
    public int getRecordCount(IllegalRecordQueryConditionReq condition)
    {
        // 待审核
        if (condition.getProcessState() == 1)
        {
            return ServiceUtil.getService(DimViolationPassRecordDao.class).getViolationPassRecordCout(condition);
        }
        
        // 审核有效
        else if (condition.getProcessState() == 2)
        {
            return ServiceUtil.getService(DimViolationValidDao.class).getViolationValidCount(condition);
        }
        // 审核无效
        else if (condition.getProcessState() == 3)
        {
            return ServiceUtil.getService(DimViolationInvalidDao.class).getViolationInvalidCount(condition);
        }
       
//        // 外地车首次违法
//        else if (condition.getProcessState() == 4)
//        {
//            ret = ServiceUtil.getService(DimViolationFirstDao.class).queryViolationFirst(condition);
//        }
        return 0;
    }

    /**
     * @param datas
     * @param processState
     */
    @Override
    public void deleteRecord(List<IllegalApproval> datas, int processState)
    {
        ConnectionFactory factory = ServiceUtil.getService("connectionFactory");
        Connection connection = null;
        try
        {
            connection = factory.getConnection();
        }
        catch (SQLException e)
        {
            throw new ServiceException(e, "getConnection error!");
        }
        
        for (IllegalApproval illegalApproval : datas)
        {
            // 待审核
            if (processState == 1)
            {
                ServiceUtil.getService(DimViolationPassRecordDao.class).deletePassRecord(connection, illegalApproval);
            }
            
            // 审核有效
            else if (processState == 2)
            {
                ServiceUtil.getService(DimViolationValidDao.class).deleteViolationValid(connection, illegalApproval);
            }
            // 审核无效
            else if (processState == 3)
            {
                ServiceUtil.getService(DimViolationInvalidDao.class)
                    .deleteViolationInvalid(connection, illegalApproval);
            }
        }
    }
    

    /**
     * @param condition
     * @return
     */
    @Override
    public List<IllegalRecord> queryViolationCount(IllegalRecordQueryConditionReq condition)
    {
        return ServiceUtil.getService(DimViolationValidDao.class).queryViolationCount(condition);
    }
    
    /**
     * @param condition
     * @return
     */
    @Override
    public ByteArrayOutputStream exportViolationCount(IllegalRecordQueryConditionReq condition)
    {
        List<IllegalRecord> countList =
            ServiceUtil.getService(DimViolationValidDao.class).queryViolationCount(condition);
        
        // 创建EXCEL
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        // 构造sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workBook, "违法次数统计");
        
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行证类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "重点车辆类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "违法次数");
        
        if (CollectionUtils.isNotEmpty(countList))
        {
            
            rowIndex = 1;
            
            for (IllegalRecord res : countList)
            {
                columnIndex = 0;
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getLicensePlate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, CARD_TYPE.get(res.getCardType()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, PLATE_TYPE_ID.get(res.getPlateTypeId()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, FORBID_TYPE.get(res.getForbidType()));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getIllegalCount());
                
                rowIndex++;
            }
        }
        return ExcelUtil.getByteArrayOutputStream(workBook);
    }
    
    
    

    /**
     * @param condition
     * @return
     */
    @Override
    public ByteArrayOutputStream exportRecord(IllegalRecordQueryConditionReq condition)
    {
        
        // 根据违法地点查询
        if (condition.getAreaId() != 0)
        {
            List<Integer> areaIds = new ArrayList<Integer>(1);
            areaIds.add(condition.getAreaId());
            condition.setAreaIds(areaIds);
        }
        // 表示不需要分页
        condition.setPageCount(0);
        List<IllegalRecord> datas = getPassRecord(condition);
        buildIllegalData(datas);
        
        // 创建EXCEL
        HSSFWorkbook workBook = new HSSFWorkbook();
        
        // 构造sheet页
        HSSFSheet sheet = ExcelUtil.getSheet(workBook, "违法次数统计");
        
        int rowIndex = 0;
        int columnIndex = 0;
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车牌号");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通行证类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "车辆类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "重点车辆类型");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "拥有者");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "违法日期");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "违法地点");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "违法次数");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "处理状态");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "上传状态");
        ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "通知状态");
        
        if (CollectionUtils.isNotEmpty(datas))
        {
            
            rowIndex = 1;
            
            for (IllegalRecord res : datas)
            {
                columnIndex = 0;
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getLicensePlate());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "1".equals(res.getCardType())  ? "短期通行证" : "2".equals(res.getCardType())  ? "长期通行证" : "无通行证");
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "1".equals(res.getPlateTypeId())  ? "大型车辆" : "小型车辆");
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, "1".equals(res.getForbidType())  ? "货车" : "2".equals(res.getForbidType()) ? "危险品车辆" : "黄标车");
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getOwnerName());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getDateKey());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, ServiceUtil.getService(CommonService.class).getForbidAreaNameById(ConvertUtils.parseInt(res.getAreaId(), 0)));
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getIllegalCount());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getProcessState());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getUploadState());
                ExcelUtil.setSheetCellValue(sheet, rowIndex, columnIndex++, res.getNoticeState());
                rowIndex++;
            }
        }
        
        return ExcelUtil.getByteArrayOutputStream(workBook);
         
    }
    
    

    /**
     * @param condition
     */
    @Override
    public void checkExportRecord(IllegalRecordQueryConditionReq condition)
    {
        // 根据违法地点查询
        if (condition.getAreaId() != 0)
        {
            List<Integer> areaIds = new ArrayList<Integer>(1);
            areaIds.add(condition.getAreaId());
            condition.setAreaIds(areaIds);
        }
        // 表示不需要分页
        condition.setPageCount(0);
        int count = 0;
        int maxRecord = ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.exportMaxRecord"), 0);
        // 待审核
        if (condition.getProcessState() == 1)
        {
            count = ServiceUtil.getService(DimViolationPassRecordDao.class).queryPassRecordCount(condition);
        }
        // 审核有效
        else if (condition.getProcessState() == 2)
        {
            count = ServiceUtil.getService(DimViolationValidDao.class).queryValidPassRecordCount(condition);
        }
        // 审核无效
        else if (condition.getProcessState() == 3)
        {
            count = ServiceUtil.getService(DimViolationInvalidDao.class).queryInvalidPassRecordCount(condition);
        }
        
        ServiceAssert.isTrue(count <= maxRecord, -1, "query count > " + maxRecord);
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param condition
     * @see [类、类#方法、类#成员]
     */
    private List<IllegalRecord> getPassRecord(IllegalRecordQueryConditionReq condition)
    {
        
        List<IllegalRecord> ret = null;
        // 待审核
        if (condition.getProcessState() == 1)
        {
            ret = ServiceUtil.getService(DimViolationPassRecordDao.class).queryPassRecord(condition);
        }
        // 审核有效
        else if (condition.getProcessState() == 2)
        {
            ret = ServiceUtil.getService(DimViolationValidDao.class).queryDimViolationValid(condition);
        }
        // 审核无效
        else if (condition.getProcessState() == 3)
        {
            ret = ServiceUtil.getService(DimViolationInvalidDao.class).queryViolationInvalid(condition);
        }
        return ret;
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @throws javax.xml.rpc.ServiceException 
     * @throws RemoteException 
     * @see [类、类#方法、类#成员]
     */
    private void calcInsertStatistic()
    {
        String time = DateUtils.getCurrentTime();
        int count =
            ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.IllegalCountMesg"), 3);
        DimViolationStatisticDao service = ServiceUtil.getService(DimViolationStatisticDao.class);
        List<DimViolationStatistic> datas = service.queryUnStatistic();
        List<DimViolationStatistic> inserDatas =  new ArrayList<DimViolationStatistic>(datas.size());
        for (DimViolationStatistic dimViolationStatistic : datas)
        {
            // 符合通知条件重置时间和次数，不符合的不插入
            if (dimViolationStatistic.getCount() >= count)
            {
                // 发送成功则统计
                if (sendMessage(dimViolationStatistic, time))
                {
                	inserDatas.add(dimViolationStatistic);
                }
            }
        }
        service.insertStatistic(inserDatas);
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @throws javax.xml.rpc.ServiceException 
     * @throws RemoteException 
     * @see [类、类#方法、类#成员]
     */
    private void calcUpdateStatistic()
    {
        String time = DateUtils.getCurrentTime();
        int count =
            Integer.parseInt(ServiceUtil.getService(CommonService.class).getVioTimes().getConfigValue());
        // 根据现有违法记录次数表进行统计(查询，更新)
        DimViolationStatisticDao service = ServiceUtil.getService(DimViolationStatisticDao.class);
        List<DimViolationStatistic> datas = service.queryStatistic();
        List<DimViolationStatistic> updateList = new ArrayList<DimViolationStatistic>(datas.size());
        for (DimViolationStatistic dimViolationStatistic : datas)
        {
            // 符合通知条件重置时间和次数，不符合的不更新
            if (dimViolationStatistic.getCount() >= count)
            {
                // 发送成功则更新
                if (sendMessage(dimViolationStatistic, time))
                {
                    updateList.add(dimViolationStatistic);
                }
            }
        }
        service.updateStatistic(updateList);
        
    }
    
    private boolean sendMessage(DimViolationStatistic data, String time) 
    {
        // 获取电话号码
        String phoneNum = getPhoneNum(data.getLicensePlate());
        if (StringUtils.isNotEmpty(phoneNum))
        {
            try
            {
                String sb = getMessage(data);
                NoticeVo noticeVo=new NoticeVo();
            	noticeVo.setNoticeContent(sb);
            	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
            	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
                if (ServiceUtil.getService(CommonService.class).smsInfo(sb, phoneNum))
                {
                    //打闯禁次数的日志
                	logger.info(sb);
                	data.setCount(0);
                    data.setLastNotify(time);
                }
            }
            catch (Exception e)
            {
                logger.error("sendphoneMessage error! phoneNum is " + phoneNum, e);
                return false;
            }
            return true;
        }
        return false;
    }

    /** <一句话功能简述>
     * <功能详细描述>
     * @param data
     * @return
     * @throws UnsupportedEncodingException
     * @see [类、类#方法、类#成员]
     */
    private String getMessage(DimViolationStatistic data)
        throws UnsupportedEncodingException
    {
        StringBuilder sb = new StringBuilder();
        sb.append("【吴江交警大队】");
        sb.append(data.getLicensePlate());
        sb.append("车主于");
        sb.append(data.getLastNotify());
        sb.append("至");
        sb.append(DateUtils.formater(DateUtils.getCurrentTime(), "yyyy/MM/dd"));
        sb.append("在");
        // 黄标车
        if (StringUtils.equals(data.getForbidType(), "3"))
        {
            sb.append(new String(
                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.yellowIllegalSitesName")
                    .getBytes(), "gbk"));
            sb.append("存在");
            sb.append(new String(
                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.yellowIllegalAction")
                    .getBytes(), "gbk"));
        }
        else 
        {
            sb.append(new String(
                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.illegalSitesName")
                    .getBytes(), "gbk"));
            sb.append("存在");
            sb.append(new String(
                ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.illegalAction")
                    .getBytes(), "gbk"));
        }
        sb.append("行为");
        sb.append("已达");
        sb.append(data.getCount());
        sb.append("次，请谨慎驾驶。");
        return sb.toString();
    }

	@Override
	public List<ViolationPassRecordVo> queryViolationRecord(
			ViolationRecordReq req) {
		DimViolationPassRecordDao service = ServiceUtil.getService(DimViolationPassRecordDao.class);
		List<ViolationPassRecordVo> voLists=service.queryViolationRecord(req);
		return voLists;
	}

	@Override
	public List<ViolationCameraVo> queryViolationRecordCamera(int type) {
		DimViolationPassRecordDao service = ServiceUtil.getService(DimViolationPassRecordDao.class);
		List<ViolationCameraVo> voLists=service.queryViolationRecordCamera();
		//按设备地点统计违法次数
		if(1==type)
		{
			voLists=service.queryViolationRecordCameraCount();
		}
		CommonDao dao = ServiceUtil.getService(CommonDao.class);
        if(CollectionUtils.isNotEmpty(voLists))
        {
        	for(ViolationCameraVo vo:voLists)
        	{
        		LPCameraVo lpCameraVo=dao.getCameraByID(vo.getLpCameraId(), StringUtils.isNotEmpty(vo.getLpCameraId())?vo.getLpCameraId().substring(0, 1):"");
        		if(lpCameraVo!=null)
        		{
        			vo.setInstallationPosition(lpCameraVo.getInstallationPosition());
        			vo.setLatitude(lpCameraVo.getLatitude());
        			vo.setLongitude(lpCameraVo.getLongitude());
        			vo.setFacilityId(lpCameraVo.getFacilityId());
        			vo.setFacilityAddress(lpCameraVo.getFacilityAddress());
        		}
        	}
        }
      //按设备地点统计违法次数
      if(1==type)
      {
    	  buildCount(voLists);
      }
		return voLists;
	}
	//由按摄像编号统计转化为按经纬度统计次数
	private void buildCount(List<ViolationCameraVo> voLists)
	{
		Map<String,Integer> map=new HashMap<String,Integer>();
  	  if(CollectionUtils.isNotEmpty(voLists))
  	  {
  		  for(ViolationCameraVo vo:voLists)
  			{
  				String key=vo.getLatitude()+","+vo.getLongitude();
  				Integer count=map.get(key);
  				map.put(key, vo.getCount()+(null!=count?count:0));
  			}

  	     List<Entry<String,Integer>> list =
  	    new ArrayList<Entry<String,Integer>>(map.entrySet());
  	     Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
  	    	    public int compare(Map.Entry<String, Integer> o1,
  	    	            Map.Entry<String, Integer> o2) {
  	    	        return (o2.getValue() - o1.getValue());
  	    	    }
  	    	});
  	     int i=0;
  	     if(CollectionUtils.isNotEmpty(list))
  	     {
  	    	 voLists.clear();
  	    	 for(Entry<String,Integer> entry:list)
  	    	 {
  	    		 if(i>=6)
  	        	 {
  	        		 break;
  	        	 }
  	    		 ViolationCameraVo vo=new ViolationCameraVo();
  	    		 vo.setLatitude(Double.valueOf(StringUtils.split(entry.getKey(),",")[0]));
  	    		 vo.setLongitude(Double.valueOf(StringUtils.split(entry.getKey(),",")[1]));
  	    		 vo.setCount(entry.getValue());
  	    		 voLists.add(vo);
  	    		 i++;
  	    	 }
  	     }
  	  }
		
	}

    
}
