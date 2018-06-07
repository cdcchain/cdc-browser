package com.browser.dao.entity;

import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;

import java.math.BigDecimal;
import java.util.Date;

public class TblBcBlock {
	
    private String blockId;

    private Long blockNum;

    private Long blockSize;

    private String previous;

    private String trxDigest;

    private String prevSecret;

    private String nextSecretHash;

    private String randomSeed;

    private String signee;

    private Date blockTime;

    private String blockTimeStr;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    //交易总数
    private Integer trxNum;
    //交易总金额
    private Long amount;

    private Long fee;

    private BigDecimal amountBig;
    private BigDecimal feeBig;

    private String amountStr;
    private String feeStr;

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

    public Long getBlockSize() {
        return blockSize;
    }

    public void setBlockSize(Long blockSize) {
        this.blockSize = blockSize;
    }

    public String getPrevious() {
        return previous;
    }

    public void setPrevious(String previous) {
        this.previous = previous == null ? null : previous.trim();
    }

    public String getTrxDigest() {
        return trxDigest;
    }

    public void setTrxDigest(String trxDigest) {
        this.trxDigest = trxDigest == null ? null : trxDigest.trim();
    }

    public String getPrevSecret() {
        return prevSecret;
    }

    public void setPrevSecret(String prevSecret) {
        this.prevSecret = prevSecret == null ? null : prevSecret.trim();
    }

    public String getNextSecretHash() {
        return nextSecretHash;
    }

    public void setNextSecretHash(String nextSecretHash) {
        this.nextSecretHash = nextSecretHash == null ? null : nextSecretHash.trim();
    }

    public String getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(String randomSeed) {
        this.randomSeed = randomSeed == null ? null : randomSeed.trim();
    }

    public String getSignee() {
        return signee;
    }

    public void setSignee(String signee) {
        this.signee = signee == null ? null : signee.trim();
    }

    public Date getBlockTime() {
		return blockTime;
	}

	public void setBlockTime(Date blockTime) {
		this.blockTime = blockTime;
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

    public Integer getTrxNum() {
        return trxNum;
    }

    public void setTrxNum(Integer trxNum) {
        this.trxNum = trxNum;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getBlockTimeStr() {
        return DateUtil.parseGmt(blockTime);
    }

    public void setBlockTimeStr(String blockTimeStr) {
        this.blockTimeStr = blockTimeStr;
    }

    public Long getFee() {
        return fee;
    }

    public void setFee(Long fee) {
        this.fee = fee;
    }

    public BigDecimal getAmountBig() {
        return new BigDecimal(amount).divide(new BigDecimal(Constant.PRECISION));
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