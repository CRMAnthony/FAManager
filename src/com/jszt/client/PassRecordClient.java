package com.jszt.client;

import java.io.InputStream;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;

import com.jsits.commons.service.ConfigureService;
import com.jsits.commons.util.CloseUtils;
import com.jsits.commons.util.ServiceUtil;
import com.jszt.service.VioPassRecordService;
import com.jszt.util.XMLParser;

public class PassRecordClient extends HttpServlet
{
    /**
     * 注释内容
     */
    private static final long serialVersionUID = -815321017142878188L;
    
    private static Logger logger = Logger.getLogger(PassRecordClient.class);

    private static int port;
    private static String ip;
    
    private static Socket socket;
    
    public void init() throws ServletException
    {
        ip = ((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.serverIp");
        port = Integer.parseInt(((ConfigureService)ServiceUtil.getService("faManagerConfigureService")).getConfig("sys.serverPort"));
        new Thread("passrecord client")
        {
            public void run()
            {
                try
                {
                    Thread.sleep(10*1000);
                } catch (Exception e)
                {
                    e.printStackTrace();
                }
                
                while (true)
                {
                    if (socket == null || socket.isClosed())
                    {
                        try
                        {
                            socket = new Socket(ip, port);
                            
                            logger.info("连接"+ip+":"+port+"成功");
                            System.out.println("连接"+ip+":"+port+"成功");
                            
                            if (socket.isConnected())
                            {
                                Thread thread = new PassRecordClient.Handler(socket);
                                thread.start();
                            }else {
                                if (socket != null)
                                {
                                    socket.close();
                                    socket = null;
                                }
                            }
                            
                        } catch (Exception e)
                        {
                            logger.error("client connect error",e);
                        }
                    }
                    
                }
            };
        }.start();
    }
    
    public static class Handler extends Thread
    {
        private Socket socket;
        
        private InputStream in;
        
        private VioPassRecordService recordService = ServiceUtil.getService(VioPassRecordService.class);

        public Handler(Socket socket)
        {
            this.socket = socket;
        }

        public void run()
        {
            InputStream br = null;
            try
            {
                
                br = in == null ? socket.getInputStream() : in;
                
                byte[] b = new byte[1024];
                int length;
                while ((length = br.read(b)) != -1)
                {
                    StringBuilder sBuilder = new StringBuilder();
                    sBuilder.append(new String(b, 0, length, "UTF-8"));
                    while (length == 1024)
                    {
                        byte[] tmpByte = new byte[1024];
                        length = br.read(tmpByte);
                        sBuilder.append(new String(tmpByte, 0, length, "UTF-8"));
                    }
                    
                    String receiveInfo = sBuilder.toString();
                    
                    PassRecord record = XMLParser.xmlParser(receiveInfo);
                    recordService.addVioPassRecord(record);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            finally
            {
                CloseUtils.close(socket);
                CloseUtils.close(br);
            }
        }
    }
}
