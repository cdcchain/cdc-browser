package com.browser.dao.mapper;

import com.browser.dao.entity.TblAsset;

import java.util.List;

public interface TblAssetMapper {
    int deleteByPrimaryKey(Integer assetId);

    int insert(TblAsset record);

    int insertSelective(TblAsset record);

    TblAsset selectByPrimaryKey(Integer assetId);

    int updateByPrimaryKeySelective(TblAsset record);

    int updateByPrimaryKey(TblAsset record);

    List<TblAsset> selectAll();

}