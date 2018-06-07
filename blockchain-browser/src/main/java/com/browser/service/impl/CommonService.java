package com.browser.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.TblBcBlock;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblBcTransactionEx;
import com.browser.dao.entity.TblContractInfo;
import com.browser.dao.mapper.TblContractAbiMapper;
import com.browser.dao.mapper.TblContractEventMapper;
import com.browser.dao.mapper.TblContractInfoMapper;
import com.browser.dao.mapper.TblContractStorageMapper;
import com.browser.service.BlockService;
import com.browser.service.SocketService;
import com.browser.service.TransactionService;
import com.browser.tools.exception.BrowserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Created by mayakui on 2018/1/20 0020.
 */
@Service
public class CommonService {

    private static Logger logger = LoggerFactory.getLogger(CommonService.class);

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SocketService socketService;

    @Autowired
    private RealData realData;

    @Autowired
    private TblContractInfoMapper tblContractInfoMapper;

    @Autowired
    private TblContractAbiMapper tblContractAbiMapper;

    @Autowired
    private TblContractEventMapper tblContractEventMapper;

    @Autowired
    private TblContractStorageMapper tblContractStorageMapper;

    /**
     * 同步完成后批量插入合约相关信息
     */
    @Transactional
    public void insertBatchContractData() {
        try {
            Set<TblContractInfo> contractInfoSet = realData.getRegisterContractInfo();
            if (contractInfoSet != null) {
                for (TblContractInfo register : contractInfoSet) {
                    tblContractInfoMapper.insert(register);
                    if (register.getAbiList() != null && register.getAbiList().size() > 0) {
                        tblContractAbiMapper.insertBatch(register.getAbiList());
                    }
                    if (register.getEventList() != null && register.getEventList().size() > 0) {
                        tblContractEventMapper.insertBatch(register.getEventList());
                    }
                    if (register.getStorageList() != null && register.getStorageList().size() > 0) {
                        tblContractStorageMapper.insertBatch(register.getStorageList());
                    }
                }
            }

            Set<TblContractInfo> contractInfoUpdateSet = realData.getUpdateContractInfo();
            if (contractInfoUpdateSet != null) {
                for (TblContractInfo update : contractInfoUpdateSet) {
                    tblContractInfoMapper.updateByPrimaryKeySelective(update);
                }
            }

            //清空数据
            realData.clear();

        } catch (Exception e) {
            logger.error("批量插入合约数据失败", e);
            throw new BrowserException("批量插入合约数据失败");
        }
    }

    /**
     * 批量插入线程同步完的区块相关数据
     *
     * @param blockList
     * @param transactionList
     * @param transactionExList
     */
    @Transactional
    public void insertBatchBlockData(List<TblBcBlock> blockList, List<TblBcTransaction> transactionList, List<TblBcTransactionEx> transactionExList) {
        try {
            if (blockList != null && blockList.size() > 0) {
                blockService.insertBatchBlock(blockList);
            }
            if (transactionList != null && transactionList.size() > 0) {
                transactionService.insertBatchTransaction(transactionList);
            }
            if (transactionExList != null && transactionExList.size() > 0) {
                transactionService.insertBatchTransactionEx(transactionExList);
            }
        } catch (Exception e) {
            logger.error("批量插入区块数据失败", e);
            throw new BrowserException("批量插入区块数据失败");
        }
    }

    /**
     * 解析钱包返回的交易
     *
     * @param jsa
     * @return
     */
    public TblBcTransaction analyzeBlockTrx(JSONArray jsa) {
        TblBcTransaction trans = new TblBcTransaction();
        if (jsa != null && jsa.size() > 1) {
            //交易id
            trans.setTrxId(jsa.getString(0));
            JSONObject trx = jsa.getJSONObject(1).getJSONObject("trx");
            JSONArray operations = trx.getJSONArray("operations");

            if (operations != null && operations.size() > 0) {
                String balanceId = "";
                Long withdrawAmount = 0L;
                Long depositAmount = 0L;
                String toAddress = "";
                String memo = "";
                //遍历operations获取参数
                for (int i = 0; i < operations.size(); i++) {
                    JSONObject op = operations.getJSONObject(i);
                    if ("withdraw_op_type".equals(op.getString("type"))) {
                        balanceId = op.getJSONObject("data").getString("balance_id");
                        trans.setBalanceId(balanceId);
                        withdrawAmount = withdrawAmount + op.getJSONObject("data").getLong("amount");
                    }
                    if ("deposit_op_type".equals(op.getString("type"))) {
                        depositAmount = depositAmount + op.getJSONObject("data").getLong("amount");
                        JSONObject conditionJson = op.getJSONObject("data").getJSONObject("condition");
                        if ("withdraw_multisig_type".equals(conditionJson.getString("type"))) {
                            toAddress = socketService.getMultisigAccount(conditionJson.getJSONObject("data").getInteger("required"),
                                    conditionJson.getJSONObject("data").getJSONArray("owners"));
                        } else {
                            toAddress = conditionJson.getJSONObject("data").getString("owner");
                        }
                        trans.setAssetId(conditionJson.getInteger("asset_id"));
                    }
                    if ("imessage_memo_op_type".equals(op.getString("type"))) {
                        memo = op.getJSONObject("data").getString("imessage");
                    }
                }
                //转入地址
                trans.setToAccount(toAddress);
                //到账金额
                trans.setAmount(depositAmount);
                //手续费
                trans.setFee(withdrawAmount - depositAmount);
                //memo参数
                trans.setMemo(memo);
            }
        }
        return trans;
    }
}
