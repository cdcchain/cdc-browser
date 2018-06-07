package com.browser.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.*;
import com.browser.service.BlockService;
import com.browser.service.SocketService;
import com.browser.service.impl.CommonService;
import com.browser.tools.Constant;
import com.browser.tools.common.StringUtil;
import com.browser.tools.socket.SocketFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class SyncTaskBatch {

	private static Logger logger = LoggerFactory.getLogger(SyncTaskBatch.class);

	@Autowired
	private BlockService blockService;

	@Autowired
	private SocketService socketService;

	@Autowired
	private SocketFactory socketFactory;

	@Autowired
	private CommonService commonService;

	@Autowired
	private RealData realData;


	//@Scheduled(cron="0/2 * * * * ? ")
	public void syncData(){
		logger.info("同步数据开始");
		Long start = System.currentTimeMillis();
		//查询本地数据库最大块号
		Long blockNum = blockService.queryBlockNum();
		if(null == blockNum){
			blockNum = 0L;
		}
		Long total = socketService.getBlockCount();

		//分配任务数
		Long count = total - blockNum;
		if(count<=0){
			return;
		}
		if(count>1000){
			count = 1000L;
		}
		BlockingQueue<Long> queue = new LinkedBlockingQueue<Long>();
		for(int i=1;i<=count;i++){
			try {
				//存入块号
				queue.put(blockNum+i);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		//线程数
		Long threadNum = count/100+1;

		//线程池，最大100
		ExecutorService exe = Executors.newFixedThreadPool(11);
		for (int i = 1; i <= threadNum; i++) {
			exe.execute(new SyncThread(queue));
		}
		exe.shutdown();
		while (true) {
			//阻塞本次任务并判断是否结束
			if (exe.isTerminated()) {
				//线程池结束后更新合约信息
				commonService.insertBatchContractData();

				logger.info("同步数据结束");
				break;
			}
		}

		Long end = System.currentTimeMillis();
		logger.info("总计耗时：{}秒",(end-start)/1000);
	}


	/**
	 * 内部类，解决bean注入问题
	 */
	private class SyncThread implements Runnable {

		BlockingQueue<Long> queue;

		public SyncThread(BlockingQueue<Long> queue) {
			this.queue = queue;
		}

		public void run() {
			//获取队列数据，请求
			Socket socket = null;
			PrintWriter os = null;
			BufferedReader is = null;
			List<TblBcBlock> blockList = new ArrayList<>();
			List<TblBcTransaction> transactionList = new ArrayList<>();
			List<TblBcTransactionEx> transactionExList = new ArrayList<>();

			Long blockNum = null;
			try {
				socket = socketFactory.getInstance();
				os = new PrintWriter(socket.getOutputStream());
				is = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				while(true){
                    blockNum = queue.poll();
                    if(null != blockNum){
						String blockMsg = socketService.getBlockNum(blockNum,socket,os,is); // 获取快信息
						if(StringUtils.isEmpty(blockMsg)){
							continue;
						}
						String signessMsg = socketService.getBlockSignee(blockNum,socket,os,is); // 获取打包代理快

						//存入链表，后续批量操作
						TblBcBlock bc = StringUtil.getBlockInfo(blockMsg,signessMsg);

						//批量交易处理
						JSONObject blockJson = JSONObject.parseObject(blockMsg);
						JSONArray trxId = blockJson.getJSONArray("user_transaction_ids");
						String transMsg = null;
						Long amount = 0L;
						if (trxId != null && trxId.size() > 0) {
							for (int i = 0; i < trxId.size(); i++) {
								//处理单个交易信息
								String trxStr = (String) trxId.get(i);
								transMsg = socketService.getBlockTrx(trxStr,socket,os,is); // 获取快交易信息
								if(!StringUtils.isEmpty(transMsg)){
									//交易信息处理

									JSONArray jsa = JSONArray.parseArray(transMsg);
									//判断交易类型
									String firstOpType = jsa.getJSONObject(1).getJSONObject("trx").getJSONArray("operations").getJSONObject(0).getString("type");
									//合约交易
									if("transaction_op_type".equals(firstOpType)){
										TblBcTransaction trx = contractTransaction(jsa,trxStr,socket,os,is,bc.getBlockTime());
										trx.setBlockId(bc.getBlockId());
										trx.setTrxTime(bc.getBlockTime());
										amount = amount + trx.getAmount();
										transactionList.add(trx);
										if(trx.getTransactionExList()!=null && trx.getTransactionExList().size()>0){
											transactionExList.addAll(trx.getTransactionExList());
										}
									}
									else{//普通 交易
										TblBcTransaction trx =  commonService.analyzeBlockTrx(jsa);
										trx.setBlockId(bc.getBlockId());
										trx.setBlockNum(bc.getBlockNum());
										trx.setTrxTime(bc.getBlockTime());
										//普通交易类型
										trx.setTrxType(0);
										//获取交易的发送者地址
										if(!StringUtils.isEmpty(trx.getBalanceId())){
											String fromMsg = socketService.getBlockchainBalance(trx.getBalanceId(),socket,os,is);
											String fromAddress = StringUtil.getBlockchainBalance(fromMsg);
											trx.setFromAccount(fromAddress);
										}
										amount = amount + trx.getAmount();
										transactionList.add(trx);
									}

								}
							}
						}

						bc.setAmount(amount);
						bc.setTrxNum(trxId.size());
						blockList.add(bc);
                    }else{
                        //结束
                        break;
                    }
                }
			} catch (Exception e) {
				logger.error("{}块高处理数据出错",blockNum,e);
			}finally{

				//保存批量数据
				commonService.insertBatchBlockData(blockList,transactionList,transactionExList);
				//关闭流
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
		}
	}


	/**
	 * 查询合约交易信息
	 * @param jsa
	 */
	private TblBcTransaction contractTransaction(JSONArray jsa, String trxId, Socket socket, PrintWriter os, BufferedReader is, Date date){
		JSONObject trx = jsa.getJSONObject(1).getJSONObject("trx");
		String resultTrxId = trx.getString("result_trx_id");
		//调用接口查询合约信息
		String extraTrxId = StringUtils.isEmpty(resultTrxId)?trxId:resultTrxId;
		String fromMsg = socketService.getContractTrx(extraTrxId,socket,os,is);
		JSONObject contractTrxJson = JSONObject.parseObject(fromMsg);

		TblBcTransaction contractTrx = new TblBcTransaction();
		contractTrx.setTrxId(contractTrxJson.getString("orig_trx_id"));
		contractTrx.setExtraTrxId(trxId);
		contractTrx.setBlockNum(contractTrxJson.getLong("block_num"));
		contractTrx.setTrxType(contractTrxJson.getInteger("trx_type"));
		contractTrx.setIsCompleted(contractTrxJson.getBoolean("is_completed")?0:1);

		JSONObject entry = contractTrxJson.getJSONObject("to_contract_ledger_entry");
		contractTrx.setFromAccount(entry.getString("from_account"));
		contractTrx.setFromAccountName(entry.getString("from_account_name"));
		contractTrx.setToAccount(entry.getString("to_account"));
		contractTrx.setContractId(entry.getString("to_account"));
		contractTrx.setToAccountName(entry.getString("to_account_name"));
		contractTrx.setAmount(entry.getJSONObject("amount").getLong("amount"));
		contractTrx.setFee(entry.getJSONObject("fee").getLong("amount"));
		contractTrx.setMemo(entry.getString("memo"));

		if(contractTrx.getTrxType() == Constant.TRX_TYPE_CALL_CONTRACT){
			JSONArray reserved = contractTrxJson.getJSONArray("reserved");
			if(reserved!=null && reserved.size()>=2){
				contractTrx.setCalledAbi(reserved.getString(0));
				contractTrx.setAbiParams(reserved.getString(1));
			}
		}

		//合约处理
		switch (contractTrx.getTrxType()){
			case Constant.TRX_TYPE_REGISTER_CONTRACT:
				//注册合约处理

				String contractMsg = socketService.getContractInfo(contractTrx.getToAccount(),socket,os,is);
				JSONObject contractJson = JSONObject.parseObject(contractMsg);
				TblContractInfo  contractInfo = new TblContractInfo();
				//合约id
				contractInfo.setContractId(entry.getString("to_account"));
				contractInfo.setRegTrxId(contractTrxJson.getString("orig_trx_id"));
				contractInfo.setRegTime(date);
				contractInfo.setName(contractJson.getString("contract_name"));
				contractInfo.setOwner(contractJson.getString("owner"));
				contractInfo.setOwnerAddress(contractJson.getString("owner_address"));
				contractInfo.setOwnerName(contractJson.getString("owner_name"));
				contractInfo.setDescription(contractJson.getString("description"));
				contractInfo.setBlockNum(contractTrx.getBlockNum());
				JSONObject codePrintable = contractJson.getJSONObject("code_printable");
				contractInfo.setBytecode(codePrintable.getString("printable_code"));
				contractInfo.setHash(codePrintable.getString("code_hash"));
				contractInfo.setStatus(Constant.TEMP_STATE);
				//注册合约balance为0
				contractInfo.setBalance(0L);
				JSONArray abiArray = codePrintable.getJSONArray("abi");
				if(abiArray!=null && abiArray.size()>0){
					List<TblContractAbi> abiList = new ArrayList<TblContractAbi>();
					for(int i=0;i<abiArray.size();i++){
						TblContractAbi abi = new TblContractAbi();
						abi.setContractId(contractInfo.getContractId());
						abi.setAbiName(abiArray.getString(i));
						abi.setBlockNum(contractTrx.getBlockNum());
						abiList.add(abi);
					}
					contractInfo.setAbiList(abiList);
				}

				JSONArray eventArray = codePrintable.getJSONArray("events");
				if(eventArray!=null && eventArray.size()>0){
					List<TblContractEvent> eventList = new ArrayList<TblContractEvent>();
					for(int i=0;i<eventArray.size();i++){
						TblContractEvent event = new TblContractEvent();
						event.setContractId(contractInfo.getContractId());
						event.setEvent(abiArray.getString(i));
						event.setBlockNum(contractTrx.getBlockNum());
						eventList.add(event);
					}
					contractInfo.setEventList(eventList);
				}

				JSONArray storageArray = codePrintable.getJSONArray("printable_storage_properties");
				if(storageArray!=null && storageArray.size()>0){
					List<TblContractStorage> storageList = new ArrayList<TblContractStorage>();
					for(int i=0;i<storageArray.size();i++){
						JSONArray storageArr = storageArray.getJSONArray(i);
						TblContractStorage storage = new TblContractStorage();
						storage.setContractId(contractInfo.getContractId());
						storage.setName(storageArr.getString(0));
						storage.setType(storageArr.getString(1));
						storage.setBlockNum(contractTrx.getBlockNum());
						storageList.add(storage);
					}
					contractInfo.setStorageList(storageList);
				}
				//添加到内存
				realData.setRegisterContractInfo(contractInfo);

				break;

			case Constant.TRX_TYPE_UPGRADE_CONTRACT:
				//升级合约处理
				String contractMsgUpdate = socketService.getContractInfo(contractTrx.getToAccount(),socket,os,is);
				JSONObject contractJsonUpdate = JSONObject.parseObject(contractMsgUpdate);
				TblContractInfo  contractInfoUpdate = new TblContractInfo();
				//合约id
				contractInfoUpdate.setContractId(entry.getString("to_account"));
				contractInfoUpdate.setName(contractJsonUpdate.getString("contract_name"));
				contractInfoUpdate.setDescription(contractJsonUpdate.getString("description"));
				contractInfoUpdate.setBlockNum(contractTrx.getBlockNum());
				contractInfoUpdate.setStatus(Constant.FOREVER_STATE);
				contractInfoUpdate.setBlockNum(contractTrx.getBlockNum());

				realData.setUpdateContractInfo(contractInfoUpdate);
				break;
			case Constant.TRX_TYPE_DESTROY_CONTRACT:
				TblContractInfo  contractInfoDestory = new TblContractInfo();
				contractInfoDestory.setContractId(contractTrx.getToAccount());
				contractInfoDestory.setBlockNum(contractTrx.getBlockNum());
				contractInfoDestory.setStatus(Constant.DESTROY_STATE);
				contractInfoDestory.setBlockNum(contractTrx.getBlockNum());
				realData.setUpdateContractInfo(contractInfoDestory);
				break;

		}

		//非注册合约需要更新balance
		if(contractTrx.getTrxType() != Constant.TRX_TYPE_REGISTER_CONTRACT){
			String balanceMsgUpdate = socketService.getContractBalance(contractTrx.getToAccount(),socket,os,is);
			Long balance = JSONArray.parseArray(balanceMsgUpdate).getJSONObject(0).getLong("balance");
			TblContractInfo  contractInfo = new TblContractInfo();
			contractInfo.setContractId(contractTrx.getToAccount());
			contractInfo.setBlockNum(contractTrx.getBlockNum());
			contractInfo.setBalance(balance);
			contractInfo.setBlockNum(contractTrx.getBlockNum());

			realData.setUpdateContractInfo(contractInfo);
		}

		//统计结果交易
		List<TblBcTransactionEx> transactionExList =
				summaryTransactionEx(contractTrxJson.getJSONArray("from_contract_ledger_entries"),
						             contractTrx.getTrxId(),
						             contractTrx.getTrxTime(),
						             extraTrxId,
						contractTrx.getBlockNum());
		contractTrx.setTransactionExList(transactionExList);
		return contractTrx;
	}

	/**
	 * 解析结果交易
	 * @param jsa
	 * @param trxId
	 * @param trxTime
	 * @param extraTrxId
	 * @return
	 */
	private List<TblBcTransactionEx>  summaryTransactionEx(JSONArray jsa, String trxId, Date trxTime, String extraTrxId, Long blockNum){
		if(jsa!=null && jsa.size()>0){
			List<TblBcTransactionEx> transactionExList = new ArrayList<TblBcTransactionEx>();
			for(int i=0;i<jsa.size();i++){
				JSONObject jso = jsa.getJSONObject(i);
				TblBcTransactionEx ex = new TblBcTransactionEx();
				ex.setTrxId(extraTrxId);
				ex.setOrigTrxId(trxId);
				ex.setTrxTime(trxTime);
				ex.setFromAddr(jso.getString("from_account"));
				ex.setFromAcct(jso.getString("from_account_name"));
				ex.setToAddr(jso.getString("to_account"));
				ex.setToAcct(jso.getString("to_account_name"));
				ex.setAmount(jso.getJSONObject("amount").getLong("amount"));
				ex.setFee(jso.getJSONObject("fee").getLong("amount"));
				ex.setMemo(jso.getString("memo"));
				ex.setTrxType(15);
				ex.setBlockNum(blockNum);

				transactionExList.add(ex);
			}
			return transactionExList;
		}else{
			return new ArrayList<TblBcTransactionEx>();
		}
	}

}
