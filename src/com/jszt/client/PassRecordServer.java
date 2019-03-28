package com.jszt.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.LineDelimiter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.ConvertUtils;
import com.jsits.commons.util.ServiceUtil;

/**
 * 接收过车记录服务端
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  ling
 * @version  [版本号, 2015-7-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class PassRecordServer {
	private int bindPort;
	private static Logger logger = Logger.getLogger(PassRecordServer.class);
	
	/**
	 * 创建服务端
	 * <一句话功能简述>
	 * <功能详细描述>
	 * @see [类、类#方法、类#成员]
	 */
	public void createServer(){
	    ConfigureService cfgService = (ConfigureService)ServiceUtil.getService("faManagerConfigureService");
	    bindPort = Integer.parseInt(cfgService.getConfig("sys.serverPort"));
	    
        SocketAcceptor acceptor = new NioSocketAcceptor(); 
        acceptor.getFilterChain().addLast("codec",
                new ProtocolCodecFilter(new TextLineCodecFactory(Charset.forName("UTF-8"),
                 LineDelimiter.WINDOWS.getValue(),
                 LineDelimiter.WINDOWS.getValue())));
        acceptor.setHandler(new ServerHandler()); 
        
        try { 
            acceptor.bind(new InetSocketAddress(bindPort));
            System.out.println("Mina Server start! "+bindPort);
            logger.info("Mina Server start! "+bindPort);
        } catch (IOException e) { 
            System.out.println("Mina Server start for error!"+bindPort); 
            logger.error("Mina Server start for error!"+bindPort);
            e.printStackTrace(); 
        } 
        System.out.println("Mina Server run done! on port:"+bindPort); 
        logger.info("Mina Server run done! on port:"+bindPort);
	}
	
	public void startCreateServer()
	{
		// 设置15分钟后socket服务
        Timer timer = new Timer();
        int time =
                ConvertUtils.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.initServer"),
                		10*60*1000);
        timer.schedule(new TimerTask(){

			@Override
			public void run() {
				createServer();
			}
        	
        }, time);
	}
}