package com.jszt.client;


import org.apache.log4j.Logger;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.jsits.commons.util.ServiceUtil;
import com.jszt.service.VioPassRecordService;
import com.jszt.util.XMLParser;


public class ServerHandler extends IoHandlerAdapter {
	private Logger logger = Logger.getLogger(ServerHandler.class);
	//private int count = 0;
	private VioPassRecordService recordService = ServiceUtil.getService(VioPassRecordService.class);

	// 当一个新客户端连接后触发此方法.
	public void sessionCreated(IoSession session) {
		System.out.println("新客户端连接:"+session.getRemoteAddress());
	}

	// 当一个客端端连结进入时 @Override
	public void sessionOpened(IoSession session) throws Exception {
//		count++;
//		System.out.println("第 " + count + " 个 client 登陆！address： : "
//				+ session.getRemoteAddress());

	}

	// 当客户端发送的消息到达时:
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {inputPassCard.json?
	    
	    String receiveMsg = message.toString();
	    //logger.info("接收的xml："+receiveMsg);
	    //System.out.println("接收的xml："+receiveMsg);
	    PassRecord passRecord = XMLParser.xmlParser(receiveMsg);
	    logger.debug("过车记录："+passRecord.toString());
	    //System.out.println("过车记录："+passRecord.toString());
	    logger.info("过车记录："+passRecord.toString());
	    recordService.addVioPassRecord(passRecord);
	}

	// 当信息已经传送给客户端后触发此方法.
	@Override
	public void messageSent(IoSession session, Object message) {

	}

	// 当一个客户端关闭时
	@Override
	public void sessionClosed(IoSession session) {
		
	}

	// 当连接空闲时触发此方法.
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) {
		//System.out.println("连接空闲");
	}

	// 当接口中其他方法抛出异常未被捕获时触发此方法
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) {
		//System.out.println("其他方法抛出异常");
		logger.error(cause.getMessage(), cause);

	}
}
