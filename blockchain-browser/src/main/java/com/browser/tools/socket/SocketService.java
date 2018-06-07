package com.browser.tools.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketService {
	
	public static void main(String[] args) throws IOException {
		ServerSocket server = new ServerSocket(5678);
		Socket client = server.accept();
		BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		PrintWriter out = new PrintWriter(client.getOutputStream());
		while (true) {
			String str = in.readLine();
			out.println("has receive.");
			out.flush();
			if ("end".equals(str)) {
				break;
			}
		}
		client.close();
	}
	
}
