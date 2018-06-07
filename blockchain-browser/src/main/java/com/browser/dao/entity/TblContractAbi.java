package com.browser.dao.entity;

public class TblContractAbi {
    private Integer id;

    private String contractId;

    private String abiName;

    private Long blockNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContractId() {
        return contractId;
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getAbiName() {
        return abiName;
    }

    public void setAbiName(String abiName) {
        this.abiName = abiName == null ? null : abiName.trim();
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }
}