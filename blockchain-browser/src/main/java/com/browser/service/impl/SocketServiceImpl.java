package com.browser.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.service.SocketService;
import com.browser.tools.BlockchainTool;
import com.browser.tools.Constant;
import com.browser.tools.common.RpcLink;
import com.browser.tools.exception.BrowserException;
import com.browser.tools.socket.SocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

@Service
public class SocketServiceImpl implements SocketService {

	private static Logger logger = LoggerFactory.getLogger(SocketServiceImpl.class);

	@Autowired
	private SocketFactory socketFactory;

	@Value("${wallet.socket.username}")
	private String username;

	@Value("${wallet.socket.password}")
	private String password;

	@Override
	public String send(String method, List<Object> params) {
		String result = null;

		long idSend = BlockchainTool.getId();

		JSONObject sendObject = new JSONObject();
		sendObject.put("jsonrpc", "2.0");
		sendObject.put("id", idSend);
		sendObject.put("method", method);
		
		// list 转换位 json
		JSONArray paramsObject = new JSONArray();
		paramsObject.addAll(params);

		sendObject.put("params", paramsObject);

		String sendMessage = sendObject.toJSONString();
		logger.info("【发送字符串】：{}",sendMessage);

		PrintWriter os = null;

		BufferedReader is = null;

		Socket socket = null;
		try {
			// 获取链接
			socket = socketFactory.getInstance();
			os = new PrintWriter(socket.getOutputStream());
			is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// 登录
			login(os, is);

			// 发送报文
			os.println(sendMessage);
			os.flush();

			// 获取报文
			String returnMessage = is.readLine();
			logger.info("【返回报文信息】：{}",returnMessage);
			JSONObject returnObject = JSONObject.parseObject(returnMessage);
			long idReturn = returnObject.getLongValue("id");

			if (idSend != idReturn) {
				throw new BrowserException("发送id和返回id不一致 " + returnMessage);
			}

			result = returnObject.getString("result");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			try {
				os.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}

			try {
				socket.close();
			} catch (Throwable e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public String send2(String method, List<Object> params,Socket socket,PrintWriter os,BufferedReader is) {
		String result = null;
		try {
		long idSend = BlockchainTool.getId();

		JSONObject sendObject = new JSONObject();
		sendObject.put("jsonrpc", "2.0");
		sendObject.put("id", idSend);
		sendObject.put("method", method);

		// list 转换位 json
		JSONArray paramsObject = new JSONArray();
		paramsObject.addAll(params);

		sendObject.put("params", paramsObject);

		String sendMessage = sendObject.toJSONString();

			// 发送报文
			os.println(sendMessage);
			os.flush();

			// 获取报文
			String returnMessage = is.readLine();
			JSONObject returnObject = JSONObject.parseObject(returnMessage);
			result = returnObject.getString("result");
			if(StringUtils.isEmpty(result)){
				logger.info("【发送字符串】：{}",sendMessage);
				logger.info("【返回报文信息】：{}",returnMessage);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private void login(PrintWriter os, BufferedReader is) throws IOException {

		long id = BlockchainTool.getId();

		JSONObject sendObject = new JSONObject();
		sendObject.put("jsonrpc", "2.0");
		sendObject.put("id", id);
		sendObject.put("method", "login");

		JSONArray paramsObject = new JSONArray();
		paramsObject.add(username);
		paramsObject.add(password);

		sendObject.put("params", paramsObject);

		String sendMessage = sendObject.toJSONString();

		os.println(sendMessage);
		os.flush();

		String returnMessage = is.readLine();

		JSONObject returnObject = JSONObject.parseObject(returnMessage);

		String result = returnObject.getString("result");

		if (!"true".equals(result)) {
			throw new BrowserException("登录失败 " + returnMessage);
		}
	}

	@Override
	public String getBlockNum(Long num,Socket socket,PrintWriter os,BufferedReader is) {
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		return send2(RpcLink.BLOCK_NUM,params,socket,os,is);
	}

	@Override
	public String getBlockSignee(Long num,Socket socket,PrintWriter os,BufferedReader is) {
		List<Object> params = new ArrayList<Object>();
		params.add(num);
		return send2(RpcLink.BLOCK_SIGNEE,params,socket,os,is);
	}

	@Override
	public String getBlockTrx(String trxId,Socket socket,PrintWriter os,BufferedReader is) {
		List<Object> params = new ArrayList<Object>();
		params.add(trxId);
		return send2(RpcLink.BLOCK_TRX,params,socket,os,is);
	}

	@Override
	public Long getBlockCount(){
		List<Object> params = new ArrayList<Object>();
		return Long.valueOf(send(RpcLink.BLOCK_COUNT,params)) ;
	}

	@Override
	public String getBlockchainBalance(String balanceId, Socket socket, PrintWriter os, BufferedReader is){
		List<Object> params = new ArrayList<Object>();
		params.add(balanceId);
		return send2(RpcLink.BLOCK_BALANCE,params,socket,os,is);
	}

	@Override
	public String getContractTrx(String trxId, Socket socket, PrintWriter os, BufferedReader is){
		List<Object> params = new ArrayList<Object>();
		params.add(trxId);
		return send2(RpcLink.CONTRACT_TRX,params,socket,os,is);
	}

	@Override
	public String getContractInfo(String contractId, Socket socket, PrintWriter os, BufferedReader is){
		List<Object> params = new ArrayList<Object>();
		params.add(contractId);
		return send2(RpcLink.CONTRACT_INFO,params,socket,os,is);
	}

	@Override
	public String getContractBalance(String contractId, Socket socket, PrintWriter os, BufferedReader is){
		List<Object> params = new ArrayList<Object>();
		params.add(contractId);
		return send2(RpcLink.CONTRACT_BALANCE,params,socket,os,is);
	}

	@Override
	public String getAssetInfo(Integer assetId){
		List<Object> params = new ArrayList<Object>();
		params.add(assetId);
		return send(RpcLink.BLOCK_ASSET,params);
	}

	/**
	 * 查询多签账户
	 * @param required
	 * @param addresses
	 * @return
	 */
	@Override
	public String getMultisigAccount(Integer required, JSONArray addresses){
		List<Object> params = new ArrayList<Object>();
		params.add(Constant.SYMBOL);
		params.add(required);
		params.add(addresses);
		return send(RpcLink.MULTISIG_ACCOUNT,params);
	}

	@Override
	public String getBalance(String address){
		List<Object> params = new ArrayList<Object>();
		params.add(address);
		return send(RpcLink.ADDRESS_BALANCE,params);
	}

}
