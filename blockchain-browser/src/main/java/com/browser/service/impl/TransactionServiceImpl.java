package com.browser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.*;
import com.browser.dao.mapper.TblBcStatisticsMapper;
import com.browser.dao.mapper.TblBcTransactionExMapper;
import com.browser.dao.mapper.TblBcTransactionMapper;
import com.browser.dao.mapper.TblContractInfoMapper;
import com.browser.protocol.EUDataGridResult;
import com.browser.service.TransactionService;
import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;
import com.browser.tools.common.StringUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private RealData realData;

    @Autowired
    private TblBcTransactionMapper tblBcTransactionMapper;

    @Autowired
    private TblBcStatisticsMapper tblBcStatisticsMapper;

    @Autowired
    private TblBcTransactionExMapper tblBcTransactionExMapper;

    @Autowired
    private TblContractInfoMapper tblContractInfoMapper;

    @Override
    public String selectByTrxData() {
        List<TblBcTransaction> trx = tblBcTransactionMapper.selectByTrxData();
        if (null != trx && trx.size() > 0) {
            for (TblBcTransaction tb : trx) {
                DateUtil.trxTimeToDay(tb);
                TblAsset tblAsset = realData.getSymbolByAssetId(tb.getAssetId());
                tb.setFeeStr(new BigDecimal(tb.getFee()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                        +" " + Constant.SYMBOL);
                tb.setAmountStr(new BigDecimal(tb.getAmount()).divide(new BigDecimal(tblAsset.getPrecision())).stripTrailingZeros().toPlainString()
                        +" " + tblAsset.getSymbol());
                tb.setSymbol(tblAsset.getSymbol());
            }
        }
        return JSONObject.toJSONString(trx);
    }

    @Override
    public List<TblBcTransaction> selectByBlockId(String blockId) {
        List<TblBcTransaction> list = tblBcTransactionMapper.selectByBlockId(blockId);
        if (list != null && list.size() > 0) {
            for (TblBcTransaction trx : list) {
                String trxTypeStr = getTrxTypeStr(trx.getTrxType());
                trx.setTrxTypeStr(trxTypeStr);
                TblAsset tblAsset = realData.getSymbolByAssetId(trx.getAssetId());
                trx.setAmountStr(new BigDecimal(trx.getAmount()).divide(new BigDecimal(tblAsset.getPrecision())).stripTrailingZeros().toPlainString()
                        +" " + tblAsset.getSymbol());
                trx.setFeeStr(new BigDecimal(trx.getFee()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                        +" " + Constant.SYMBOL);
            }
        }
        return list;
    }

    //TODO
    @Override
    public Map<String, Object> queryNum(String blockId) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<TblBcTransaction> trx = tblBcTransactionMapper.selectByBlockId(blockId);
        if (null != trx) {
            Long amountNum = 0L;
            Long feeNum = 0L;
            for (TblBcTransaction tran : trx) {
                amountNum += tran.getAmount();
                feeNum += tran.getFee();
            }

            result.put("amountNum", StringUtil.div(new BigDecimal(amountNum), new BigDecimal(Constant.PRECISION), Constant.PRECISION_LENGTH));
            result.put("feeNum", StringUtil.div(new BigDecimal(feeNum), new BigDecimal(Constant.PRECISION), Constant.PRECISION_LENGTH));
        }

        return result;
    }

    @Override
    public TblBcTransaction selectByPrimaryKey(String trxId) {
        return tblBcTransactionMapper.selectByPrimaryKey(trxId);
    }

    @Override
    public TblBcTransaction queryByTrxId(String trxId) {
        TblBcTransaction result = tblBcTransactionMapper.queryByTrxId(trxId);

        //查询扩展交易
        if (result.getTrxType() != Constant.TRX_TYPE_TRANSFER) {
            List<TblBcTransactionEx> transactionExList = tblBcTransactionExMapper.selectByOrigTrxId(result.getTrxId());
            if (transactionExList != null && transactionExList.size() > 0) {
                for (TblBcTransactionEx ex : transactionExList) {
                    TblAsset tblAsset = realData.getSymbolByAssetId(ex.getAssetId());
                    ex.setAmountStr(new BigDecimal(ex.getAmount()).divide(new BigDecimal(tblAsset.getPrecision())).stripTrailingZeros().toPlainString()
                            +" " + tblAsset.getSymbol());
                    ex.setFeeStr(new BigDecimal(ex.getFee()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                            +" " + Constant.SYMBOL);
                }
                result.setTransactionExList(transactionExList);
            }
        }
        //调用合约,整理abi参数
        if (result.getTrxType() == Constant.TRX_TYPE_CALL_CONTRACT) {
            if (!StringUtils.isEmpty(result.getAbiParams())) {
                String params = "";
                if (result.getAbiParams().contains("{")) {
                    String jsonStr = result.getAbiParams().replace("'", "\"");
                    Map<String, Object> map = JSONObject.parseObject(jsonStr, Map.class);
                    for (Map.Entry<String, Object> entry : map.entrySet()) {
                        params = params + entry.getValue() + "|";
                    }
                    params = params.substring(0, params.length() - 1);
                } else if (result.getAbiParams().contains(":")) {
                    params = result.getAbiParams().replace(":", "|");
                } else {
                    params = result.getAbiParams();
                }
                result.setAbiParams(params);
            }
        }
        String trxTypeStr = getTrxTypeStr(result.getTrxType());
        result.setTrxTypeStr(trxTypeStr);

        TblAsset tblAsset2 = realData.getSymbolByAssetId(result.getAssetId());

        if("transfer".equals(result.getCalledAbi())){
            String abiParams = result.getAbiParams();
            BigDecimal amount = new BigDecimal(abiParams.split(",")[1]);
            //获取合约精度
            TblContractInfo contractInfo = tblContractInfoMapper.selectByPrimaryKey(result.getToAccount());
            Long precision = contractInfo.getPrecision();
            if(null == precision){
                precision = Constant.PRECISION;
            }
            result.setAmountStr(amount.divide(new BigDecimal(precision)).stripTrailingZeros().toPlainString()
                    +" " + contractInfo.getName());
            result.setContractId(result.getToAccount());
            result.setToAccount(abiParams.split(",")[0]);
        }else{
            result.setAmountStr(new BigDecimal(result.getAmount()).divide(new BigDecimal(tblAsset2.getPrecision())).stripTrailingZeros().toPlainString()
                    +" " + tblAsset2.getSymbol());
        }

        //result.setAmountStr(new BigDecimal(result.getAmount()).divide(new BigDecimal(tblAsset2.getPrecision())).stripTrailingZeros().toPlainString()
        //        + " " + tblAsset2.getSymbol());
        result.setFeeStr(new BigDecimal(result.getFee()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                + " " + Constant.SYMBOL);
        return result;
    }

    @Override
    public void updateSelect() {
        Long amountNum = tblBcTransactionMapper.selectByAmountNum();
        if (amountNum == null) {
            return;
        }
        Integer num = tblBcTransactionMapper.selectByNum();
        Long amountFee = tblBcTransactionMapper.selectByFee();
        Integer hourNum = tblBcTransactionMapper.selectHour(DateUtil.HourStatis());
        Integer hourMax = tblBcTransactionMapper.selectAll();
        Double allTrx = Double.valueOf(amountNum) / Constant.PRECISION;
        Double trxFee = Double.valueOf(amountFee) / Constant.PRECISION;

        TblBcStatistics statics = new TblBcStatistics();
        statics.setAllTrxAmount(new BigDecimal(allTrx));
        statics.setAllTrxFee(new BigDecimal(trxFee));
        statics.setTrxCount(num);
        statics.setTrxLatestHour(hourNum);
        if (hourMax != null) {
            statics.setTrxMaxHour(hourMax);
        } else {
            statics.setTrxMaxHour(0);
        }

        tblBcStatisticsMapper.updateByPrimaryKeySelective(statics);

        //缓存在内存里
        realData.setStatisInfo(JSONObject.toJSONString(statics));
    }

    @Override
    public Integer selectAll() {
        return this.tblBcTransactionMapper.selectAll();
    }

    @Override
    public EUDataGridResult getTransactionList(TblBcTransaction transaction, Integer page, Integer rows) {
        // 分页处理
        PageHelper.startPage(page, rows);
        List<TblBcTransaction> list = tblBcTransactionMapper.getTransactionList(transaction);
        if (list != null && list.size() > 0) {
            for (TblBcTransaction trx : list) {
                String trxTypeStr = getTrxTypeStr(trx.getTrxType());
                trx.setTrxTypeStr(trxTypeStr);
                if("transfer".equals(trx.getCalledAbi())){
                    String abiParams = trx.getAbiParams();
                    BigDecimal amount = new BigDecimal(abiParams.split(",")[1]);
                            //获取合约精度
                    TblContractInfo contractInfo = tblContractInfoMapper.selectByPrimaryKey(trx.getToAccount());
                    Long precision = contractInfo.getPrecision();
                    if(null == precision){
                        precision = Constant.PRECISION;
                    }
                    trx.setAmountStr(amount.divide(new BigDecimal(precision)).stripTrailingZeros().toPlainString()
                            +" " + contractInfo.getName());
                }else{
                    trx.setAmountStr(new BigDecimal(trx.getAmount()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                            +" " + Constant.SYMBOL);
                }
                trx.setFeeStr(new BigDecimal(trx.getFee()).divide(new BigDecimal(Constant.PRECISION)).stripTrailingZeros().toPlainString()
                        +" " + Constant.SYMBOL);
            }
        }
        // 创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        // 取记录总条数
        PageInfo<TblBcTransaction> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        return result;
    }


    private String getTrxTypeStr(int trxType) {
        String trxTypeStr = null;
        switch (trxType) {
            case 1:
                trxTypeStr = "代理领工资";
                break;
            case 2:
                trxTypeStr = "注册账户";
                break;
            case 3:
                trxTypeStr = "注册代理";
                break;
            case 10:
                trxTypeStr = "注册合约";
                break;
            case 11:
                trxTypeStr = "合约充值";
                break;
            case 12:
                trxTypeStr = "合约升级";
                break;
            case 13:
                trxTypeStr = "合约销毁";
                break;
            case 14:
                trxTypeStr = "调用合约";
                break;
            default:
                trxTypeStr = "普通转账";
                break;
        }
        return trxTypeStr;
    }

    @Override
    public void insertBatchTransactionEx(List<TblBcTransactionEx> list) {
        for (int j = 0; j < list.size(); j = j + Constant.BATCH_LENGTH) {
            List<TblBcTransactionEx> sub = null;
            if (j + Constant.BATCH_LENGTH > list.size()) {
                sub = list.subList(j, list.size());
            } else {
                sub = list.subList(j, j + Constant.BATCH_LENGTH);
            }
            tblBcTransactionExMapper.insertBatch(sub);
        }
    }

    @Override
    public void insertBatchTransaction(List<TblBcTransaction> list) {
        for (int j = 0; j < list.size(); j = j + Constant.BATCH_LENGTH) {
            List<TblBcTransaction> sub = null;
            if (j + Constant.BATCH_LENGTH > list.size()) {
                sub = list.subList(j, list.size());
            } else {
                sub = list.subList(j, j + Constant.BATCH_LENGTH);
            }
            tblBcTransactionMapper.insertBatch(sub);
        }
    }

    @Override
    public List<TblBcTransaction> queryContractTrx(TblBcTransaction transaction) {
        return tblBcTransactionMapper.queryContractTrx(transaction);
    }
}
