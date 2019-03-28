/*
 * 文 件 名:  ForbidLineServiceImpl.java
 * 版    权:  江苏智通交通科技有限公司
 * 描    述:  <描述>
 * 修 改 人:  HuaXiuLin
 * 修改时间:  2015年5月19日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package com.jszt.service.impl;

import java.rmi.RemoteException;
import java.rmi.ServerException;
import java.text.ParseException;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.jsits.commons.util.DateUtils;
import com.jsits.commons.util.JsztException;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.dao.DimForbidLineDao;
import com.jszt.domain.DimForbidLine;
import com.jszt.service.CommonService;
import com.jszt.service.ForbidLineService;
import com.jszt.vo.DelIdReq;
import com.jszt.vo.DimPassPeriod;
import com.jszt.vo.ForbidLineReq;
import com.jszt.vo.NoticePassCard;
import com.jszt.vo.NoticeVo;

/**
 * 禁行线路相关业务接口
 * 
 * @author huaxiulin
 * @version [版本号, 2015年5月19日]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class ForbidLineServiceImpl implements ForbidLineService
{

	@Override
	public void addForbidLine(ForbidLineReq req)
	{
		DimForbidLine forbidLine = new DimForbidLine();
		forbidLine.setLineName(req.getLineName());
		forbidLine.setBeginTime(req.getBeginTime());
		forbidLine.setEndTime(req.getEndTime());
		forbidLine.setDescription(req.getDescription());
		ServiceUtil.getService(DimForbidLineDao.class).addForbidLine(forbidLine);
	}

	@Override
	public List<DimForbidLine> getForbidLine(ForbidLineReq req) throws JsztException
	{
		return ServiceUtil.getService(DimForbidLineDao.class).getForbidLine(req);
	}

	@Override
	public List<DimPassPeriod> getLongPassPeriod(int begin, int end)
	{
		return ServiceUtil.getService(DimForbidLineDao.class).getLongPassPeriod(begin, end);
	}

	@Override
	public List<DimPassPeriod> getTempPassPeriod(int begin, int end)
	{
		return ServiceUtil.getService(DimForbidLineDao.class).getTempPassPeriod(begin, end);
	}

	/**
	 * 根据禁行时间判断是否通知
	 * 
	 * @param req
	 * @throws RemoteException
	 * @throws ParseException
	 */
	@Override
	public boolean infoUserForBid(ForbidLineReq req) throws RemoteException, ParseException
	{

		List<NoticePassCard> noteCard = ServiceUtil.getService(CommonService.class).getNoticePassCards(req.getBeginTime(), req.getEndTime());
		if (CollectionUtils.isEmpty(noteCard))
		{
			return false;
		} else
		{
			for (NoticePassCard noticePassCard : noteCard)
			{
				String content = null;
				// 车牌号
				String cardNo = noticePassCard.getLicensePlate();
				// 手机号
				String phoneNumber = noticePassCard.getPhoneNumber();
				// 通知
				content = "【吴江交警大队】   车主：" + " " + cardNo + " " + req.getDescription();
				try
				{
					NoticeVo noticeVo=new NoticeVo();
		        	noticeVo.setNoticeContent(content);
		        	noticeVo.setNoticeTime(DateUtils.getCurrentTime());
		        	ServiceUtil.getService(CommonService.class).writeNotice(noticeVo);
					ServiceUtil.getService(CommonService.class).smsInfo(content, phoneNumber);
					
				} catch (Exception e)
				{
					throw new ServerException("短信通知失败", e);
				}
			}
		}
		return true;
	}

	/**
	 * @param req
	 */
	@Override
	public void delForbidLine(DelIdReq req)
	{
		//String[] id = req.getId().split(",");
		ServiceUtil.getService(DimForbidLineDao.class).delForbidLine(req.getId());
	}
}
