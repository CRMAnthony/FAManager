package com.jszt.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;


public class SocketUnit
{
	private Socket socket;
	private OutputStream out;
	private InputStream in;

	public SocketUnit(Socket socket)
	{
		this.setSocket(socket);
	}

	public boolean isActive()
	{
		if (socket.isClosed() || !socket.isConnected())
		{
			return false;
		}
		return true;
	}

	public OutputStream getWriter() throws IOException
	{
		if (out == null)
		{
			out = socket.getOutputStream();
		}
		return out;
	}

	public InputStream getReader() throws IOException
	{
		if (in == null)
		{
			in = socket.getInputStream();
		}
		return in;
	}

	public void setSocket(Socket socket)
	{
		this.socket = socket;
	}

	public Socket getSocket()
	{
		return socket;
	}

	public boolean isClosed()
	{
		if (socket == null)
		{
			return true;
		}
		return socket.isClosed();
	}

	public void close()
	{
		try
		{
			socket.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
