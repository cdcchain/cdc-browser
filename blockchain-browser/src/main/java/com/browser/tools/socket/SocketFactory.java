package com.browser.tools.socket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

@Component
@Scope("singleton")
public class SocketFactory {

	@Value("${wallet.socket.host}")
	private String host;

	@Value("${wallet.socket.port}")
	private int port;

	@Value("${wallet.socket.timeout}")
	private int timeout;
	
	public synchronized Socket getInstance() {
		
		Socket socket = null;
		try {
			socket = new Socket();

			SocketAddress remoteAddr = new InetSocketAddress(host, port);

			socket.connect(remoteAddr, timeout);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		return socket;
	}
}
