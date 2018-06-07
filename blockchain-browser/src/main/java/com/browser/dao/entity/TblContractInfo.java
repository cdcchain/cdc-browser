package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TblContractInfo {

    private String contractId;

    private String name;

    private String hash;

    private String owner;

    private String ownerAddress;

    private String ownerName;

    private String description;

    private Date regTime;

    private String regTimeStr;

    private String regTrxId;

    private Long balance;

    private BigDecimal balanceBig;

    private String balanceStr;

    private Integer assetId;

    private String symbol;

    private Integer status;
    private String statusStr;

    private String bytecode;

    private List<TblContractAbi> abiList;

    private List<TblContractEvent> eventList;

    private List<TblContractStorage> storageList;

    //对应合约交易的块号，用来排序
    private Long blockNum;

    private Long precision;

    //条件查询关键字
    private String keyword;

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash == null ? null : hash.trim();
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    public String getOwnerAddress() {
        return ownerAddress;
    }

    public void setOwnerAddress(String ownerAddress) {
        this.ownerAddress = ownerAddress == null ? null : ownerAddress.trim();
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName == null ? null : ownerName.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public Date getRegTime() {
        return regTime;
    }

    public void setRegTime(Date regTime) {
        this.regTime = regTime;
    }

    public String getRegTrxId() {
        return regTrxId;
    }

    public void setRegTrxId(String regTrxId) {
        this.regTrxId = regTrxId == null ? null : regTrxId.trim();
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getBytecode() {
        return bytecode;
    }

    public void setBytecode(String bytecode) {
        this.bytecode = bytecode == null ? null : bytecode.trim();
    }

    public List<TblContractAbi> getAbiList() {
        return abiList;
    }

    public void setAbiList(List<TblContractAbi> abiList) {
        this.abiList = abiList;
    }

    public List<TblContractEvent> getEventList() {
        return eventList;
    }

    public void setEventList(List<TblContractEvent> eventList) {
        this.eventList = eventList;
    }

    public List<TblContractStorage> getStorageList() {
        return storageList;
    }

    public void setStorageList(List<TblContractStorage> storageList) {
        this.storageList = storageList;
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRegTimeStr() {
        return regTimeStr;
    }

    public void setRegTimeStr(String regTimeStr) {
        this.regTimeStr = regTimeStr;
    }

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TblContractInfo{");
        sb.append("contractId='").append(contractId).append('\'');
        sb.append(", name='").append(name).append('\'');
        sb.append(", hash='").append(hash).append('\'');
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", ownerAddress='").append(ownerAddress).append('\'');
        sb.append(", ownerName='").append(ownerName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", regTime=").append(regTime);
        sb.append(", regTrxId='").append(regTrxId).append('\'');
        sb.append(", balance=").append(balance);
        sb.append(", status=").append(status);
        sb.append(", bytecode='").append(bytecode).append('\'');
        sb.append(", abiList=").append(abiList);
        sb.append(", eventList=").append(eventList);
        sb.append(", storageList=").append(storageList);
        sb.append(", blockNum=").append(blockNum);
        sb.append('}');
        return sb.toString();
    }

    public String getBalanceStr() {
        return balanceStr;
    }

    public void setBalanceStr(String balanceStr) {
        this.balanceStr = balanceStr;
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

    public BigDecimal getBalanceBig() {
       // return new BigDecimal(balance).divide(new BigDecimal(Constant.PRECISION));
        return balanceBig;
    }

    public void setBalanceBig(BigDecimal balanceBig) {
        this.balanceBig = balanceBig;
    }

    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }
}