package com.browser.service;

import com.alibaba.fastjson.JSONArray;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public interface SocketService {
	
	/**
	 * 
	* @Title: send 
	* @Description: 获取接口数据
	* @author David
	* @param 
	* @return String 
	* @throws
	 */
	String send(String method, List<Object> params);

	String send2(String method, List<Object> params, Socket socket, PrintWriter os, BufferedReader is);

	/**
	 *
	* @Title: getBlockNum
	* @Description:调用获取快号数据
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockNum(Long num, Socket socket, PrintWriter os, BufferedReader is);

	/**
	 *
	* @Title: getBlockSignee
	* @Description:获取打包代理数据
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockSignee(Long num, Socket socket, PrintWriter os, BufferedReader is);

	/**
	 *
	* @Title: getBlockTrx
	* @Description:获取快交易信息
	* @author David
	* @param
	* @return String 
	* @throws
	 */
	String getBlockTrx(String trxId, Socket socket, PrintWriter os, BufferedReader is);

	/**
	 * 获取最大块号
	 * @return
	 */
	Long getBlockCount();

	String getBlockchainBalance(String balanceId, Socket socket, PrintWriter os, BufferedReader is);

	/**
	 * 获取合约交易信息
	 * @param trxId
	 * @param socket
	 * @param os
	 * @param is
	 * @return
	 */
	String getContractTrx(String trxId, Socket socket, PrintWriter os, BufferedReader is);

	String getContractInfo(String contractId, Socket socket, PrintWriter os, BufferedReader is);

	String getContractBalance(String contractId, Socket socket, PrintWriter os, BufferedReader is);

	String getAssetInfo(Integer assetId);

	String getMultisigAccount(Integer required, JSONArray addresses);

	String getBalance(String address);
}
