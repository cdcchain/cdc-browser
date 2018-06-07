package com.browser.dao.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TblBcStatistics {
    private Long id;

    private Integer trxCount;

    private BigDecimal allTrxAmount;

    private BigDecimal allTrxFee;

    private Integer trxLatestHour;

    private Integer trxMaxHour;

    private Date createdAt;

    private Date updatedAt;

    private Date deletedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTrxCount() {
        return trxCount;
    }

    public void setTrxCount(Integer trxCount) {
        this.trxCount = trxCount;
    }

    public BigDecimal getAllTrxAmount() {
        return allTrxAmount;
    }

    public void setAllTrxAmount(BigDecimal allTrxAmount) {
        this.allTrxAmount = allTrxAmount;
    }

    public BigDecimal getAllTrxFee() {
        return allTrxFee;
    }

    public void setAllTrxFee(BigDecimal allTrxFee) {
        this.allTrxFee = allTrxFee;
    }

    public Integer getTrxLatestHour() {
        return trxLatestHour;
    }

    public void setTrxLatestHour(Integer trxLatestHour) {
        this.trxLatestHour = trxLatestHour;
    }

    public Integer getTrxMaxHour() {
        return trxMaxHour;
    }

    public void setTrxMaxHour(Integer trxMaxHour) {
        this.trxMaxHour = trxMaxHour;
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
}