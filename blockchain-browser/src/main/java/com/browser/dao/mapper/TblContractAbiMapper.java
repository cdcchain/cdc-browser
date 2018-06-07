package com.browser.dao.mapper;

import com.browser.dao.entity.TblContractAbi;

import java.util.List;

public interface TblContractAbiMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TblContractAbi record);

    int insertSelective(TblContractAbi record);

    TblContractAbi selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TblContractAbi record);

    int updateByPrimaryKey(TblContractAbi record);

    int insertBatch(List<TblContractAbi> record);

    List<TblContractAbi> selectListByContractId(String contractId);

    int deleteByBlockNum(Long blockNum);
}