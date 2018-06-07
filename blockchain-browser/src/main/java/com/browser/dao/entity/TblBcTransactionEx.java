package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TblBcTransactionEx {
    private Long id;

    private String trxId;

    private String origTrxId;

    private String fromAcct;

    private String fromAddr;

    private String toAcct;

    private String toAddr;

    private Long amount;

    private Long fee;

    private BigDecimal amountBig;

    private BigDecimal feeBig;

    private String amountStr;

    private String feeStr;

    private Date trxTime;

    private Integer trxType;

    private String memo;

    private Integer assetId;

    private String symbol;

    private Long blockNum;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTrxId() {
        return trxId;
    }

    public void setTrxId(String trxId) {
        this.trxId = trxId == null ? null : trxId.trim();
    }

    public String getOrigTrxId() {
        return origTrxId;
    }

    public void setOrigTrxId(String origTrxId) {
        this.origTrxId = origTrxId == null ? null : origTrxId.trim();
    }

    public String getFromAcct() {
        return fromAcct;
    }

    public void setFromAcct(String fromAcct) {
        this.fromAcct = fromAcct == null ? null : fromAcct.trim();
    }

    public String getFromAddr() {
        return fromAddr;
    }

    public void setFromAddr(String fromAddr) {
        this.fromAddr = fromAddr == null ? null : fromAddr.trim();
    }

    public String getToAcct() {
        return toAcct;
    }

    public void setToAcct(String toAcct) {
        this.toAcct = toAcct == null ? null : toAcct.trim();
    }

    public String getToAddr() {
        return toAddr;
    }

    public void setToAddr(String toAddr) {
        this.toAddr = toAddr == null ? null : toAddr.trim();
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

    public Date getTrxTime() {
        return trxTime;
    }

    public void setTrxTime(Date trxTime) {
        this.trxTime = trxTime;
    }

    public Integer getTrxType() {
        return trxType;
    }

    public void setTrxType(Integer trxType) {
        this.trxType = trxType;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public BigDecimal getAmountBig() {
        return amountBig;
    }

    public void setAmountBig(BigDecimal amountBig) {
        this.amountBig = amountBig;
    }

    public BigDecimal getFeeBig() {
        return feeBig;
    }

    public void setFeeBig(BigDecimal feeBig) {
        this.feeBig = feeBig;
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

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
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
}