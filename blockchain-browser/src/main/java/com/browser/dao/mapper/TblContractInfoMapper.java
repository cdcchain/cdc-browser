package com.browser.dao.mapper;

import com.browser.dao.entity.TblContractInfo;

import java.util.List;

public interface TblContractInfoMapper {
    int deleteByPrimaryKey(String contractId);

    int insert(TblContractInfo record);

    int insertSelective(TblContractInfo record);

    TblContractInfo selectByPrimaryKey(String contractId);

    int updateByPrimaryKeySelective(TblContractInfo record);

    int updateByPrimaryKeyWithBLOBs(TblContractInfo record);

    int updateByPrimaryKey(TblContractInfo record);

    List<TblContractInfo> selectContractList(TblContractInfo record);
    int deleteByBlockNum(Long blockNum);
}