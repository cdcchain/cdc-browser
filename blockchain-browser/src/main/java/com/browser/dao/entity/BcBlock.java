package com.browser.dao.entity;

import com.browser.tools.common.DateUtil;

import java.util.Date;

public class BcBlock {
	
	private String blockId;
	private Long blockNum;
	private Long blockSize;
	private String signee;
	private Date signeeTime;
	private Integer trxNum;
	private String trxAmount;

	public String getBlockId() {
		return blockId;
	}

	public void setBlockId(String blockId) {
		this.blockId = blockId;
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

	public String getSignee() {
		return signee;
	}

	public void setSignee(String signee) {
		this.signee = signee;
	}

	public Date getSigneeTime() {
		return signeeTime;
	}
	
	public String getSigneeTimes() {
		return DateUtil.parseGmt(signeeTime);
	}

	public void setSigneeTime(Date signeeTime) {
		this.signeeTime = signeeTime;
	}

	public Integer getTrxNum() {
		return trxNum;
	}

	public void setTrxNum(Integer trxNum) {
		this.trxNum = trxNum;
	}

	public String getTrxAmount() {
		return trxAmount;
	}

	public void setTrxAmount(String trxAmount) {
		this.trxAmount = trxAmount;
	}

}
