package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TblBcTransaction {
	
    private String trxId;

    private String blockId;

    private Long blockNum;

    private String fromAccount;
    
    private String fromAccountName;
    
    private String toAccount;
    
    private String toAccountName;

    private Long amount;

    private String amountStr;

    private BigDecimal amountBig;

    private Long fee;

    private String feeStr;

    private BigDecimal feeBig;
    
    private Long amountNum;
    
    private String memo;

    private Date trxTime;
    private String trxTimeStr;
    
    private String disTime;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    private String balanceId;

    //以下合约交易新增字段
    //调用的合约函数，非合约交易该字段为空
    private String calledAbi;

    //调用合约函数时传入的参数，非合约交易该字段为空
    private String abiParams;

    //结果交易id仅针对合约交易
    private String extraTrxId;

    /**
     * 合约调用结果
     0 - 成功
     1- 失败
     */
    private Integer isCompleted;

    /**
     * 0 - 普通转账
     1 - 代理领工资
     2 - 注册账户
     3 - 注册代理
     10 - 注册合约
     11 - 合约充值
     12 - 合约升级
     */
    private Integer trxType;
    private String trxTypeStr;

    //合约id
    private String contractId;

    //对应块号的打包代理
    private String signee;

    private Integer assetId;

    private String symbol;

    //结果交易
    private List<TblBcTransactionEx> transactionExList;

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId == null ? null : trxId.trim();
    }

    public String getBlockId() {
        return blockId;
    }

    public void setBlockId(String blockId) {
        this.blockId = blockId == null ? null : blockId.trim();
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public String getFromAccount() {
        return fromAccount;
    }

    public void setFromAccount(String fromAccount) {
        this.fromAccount = fromAccount == null ? null : fromAccount.trim();
    }

    public String getToAccount() {
        return toAccount;
    }

    public void setToAccount(String toAccount) {
        this.toAccount = toAccount == null ? null : toAccount.trim();
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public Date getTrxTime() {
		return trxTime;
	}

	public void setTrxTime(Date trxTime) {
		this.trxTime = trxTime;
	}

	public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Date getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

	public String getFromAccountName() {
		return fromAccountName;
	}

	public void setFromAccountName(String fromAccountName) {
		this.fromAccountName = fromAccountName;
	}

	public String getToAccountName() {
		return toAccountName;
	}

	public void setToAccountName(String toAccountName) {
		this.toAccountName = toAccountName;
	}

	public String getDisTime() {
		return disTime;
	}

	public void setDisTime(String disTime) {
		this.disTime = disTime;
	}

	public Long getAmountNum() {
		return amountNum;
	}

	public void setAmountNum(Long amountNum) {
		this.amountNum = amountNum;
	}

    public BigDecimal getAmountBig() {
        return amountBig;
    }

    public void setAmountBig(BigDecimal amountBig) {
        this.amountBig = amountBig;
    }

    public String getBalanceId() {
        return balanceId;
    }

    public void setBalanceId(String balanceId) {
        this.balanceId = balanceId;
    }

    public String getCalledAbi() {
        return calledAbi;
    }

    public void setCalledAbi(String calledAbi) {
        this.calledAbi = calledAbi;
    }

    public String getAbiParams() {
        return abiParams;
    }

    public void setAbiParams(String abiParams) {
        this.abiParams = abiParams;
    }

    public String getExtraTrxId() {
        return extraTrxId;
    }

    public void setExtraTrxId(String extraTrxId) {
        this.extraTrxId = extraTrxId;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getTrxType() {
        return trxType;
    }

    public void setTrxType(Integer trxType) {
        this.trxType = trxType;
    }

    public List<TblBcTransactionEx> getTransactionExList() {
        return transactionExList;
    }

    public void setTransactionExList(List<TblBcTransactionEx> transactionExList) {
        this.transactionExList = transactionExList;
    }

    public String getTrxTypeStr() {
        return trxTypeStr;
    }

    public void setTrxTypeStr(String trxTypeStr) {
        this.trxTypeStr = trxTypeStr;
    }

    public String getTrxTimeStr() {
        return trxTimeStr;
    }

    public void setTrxTimeStr(String trxTimeStr) {
        this.trxTimeStr = trxTimeStr;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId;
    }

    public String getSignee() {
        return signee;
    }

    public void setSignee(String signee) {
        this.signee = signee;
    }

    public String getAmountStr() {
        return amountStr;
    }

    public void setAmountStr(String amountStr) {
        this.amountStr = amountStr;
    }

    public String getFeeStr() {
        return feeStr;
    }

    public void setFeeStr(String feeStr) {
        this.feeStr = feeStr;
    }

    public Integer getAssetId() {
        return assetId;
    }

    public void setAssetId(Integer assetId) {
        this.assetId = assetId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getFeeBig() {
        return feeBig;
    }

    public void setFeeBig(BigDecimal feeBig) {
        this.feeBig = feeBig;
    }
}