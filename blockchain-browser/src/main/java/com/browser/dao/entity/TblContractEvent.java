package com.browser.dao.entity;

public class TblContractEvent {
    private Integer id;

    private String contractId;

    private String event;

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

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event == null ? null : event.trim();
    }

    public Long getBlockNum() {
        return blockNum;
    }

    public void setBlockNum(Long blockNum) {
        this.blockNum = blockNum;
    }
}