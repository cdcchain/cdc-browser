package com.browser.dao.entity;

public class TblAsset {
    private Integer assetId;

    private String symbol;

    private Long precision;

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
        this.symbol = symbol == null ? null : symbol.trim();
    }

    public Long getPrecision() {
        return precision;
    }

    public void setPrecision(Long precision) {
        this.precision = precision;
    }
}