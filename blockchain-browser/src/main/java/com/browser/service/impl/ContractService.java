package com.browser.service.impl;

import com.browser.config.RealData;
import com.browser.dao.entity.TblAsset;
import com.browser.dao.entity.TblContractAbi;
import com.browser.dao.entity.TblContractInfo;
import com.browser.dao.mapper.TblContractAbiMapper;
import com.browser.dao.mapper.TblContractInfoMapper;
import com.browser.protocol.EUDataGridResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mayakui on 2017/12/8 0008.
 */
@Service
public class ContractService {

    @Autowired
    private RealData realData;

    @Autowired
    private TblContractInfoMapper tblContractInfoMapper;
    @Autowired
    private TblContractAbiMapper tblContractAbiMapper;

    private static Map<Integer,String> contractStatusMap = null;
    static{
        contractStatusMap = new HashMap<>();
        contractStatusMap.put(0,"销毁");
        contractStatusMap.put(1,"临时");
        contractStatusMap.put(2,"永久");
    }


    public EUDataGridResult getContractList(TblContractInfo tblContractInfo, Integer page, Integer rows) {
        // 分页处理
        PageHelper.startPage(page, rows);
        List<TblContractInfo> list = tblContractInfoMapper.selectContractList(tblContractInfo);
        if(list!=null && list.size()>0){
            for(TblContractInfo contractInfo:list){
                TblAsset tblAsset = realData.getSymbolByAssetId(contractInfo.getAssetId());
                contractInfo.setBalanceStr((new BigDecimal(contractInfo.getBalance()).divide(new BigDecimal(tblAsset.getPrecision()))).stripTrailingZeros().toPlainString()
                        +" " + tblAsset.getSymbol());
            }
        }
        // 创建一个返回值对象
        EUDataGridResult result = new EUDataGridResult();
        result.setRows(list);
        // 取记录总条数
        PageInfo<TblContractInfo> pageInfo = new PageInfo<>(list);
        result.setTotal(pageInfo.getTotal());
        result.setPages(pageInfo.getPages());
        return result;
    }

    /**
     * 查询合约详情
     * @param contractId
     * @return
     */
    public TblContractInfo getContractDetail(String contractId){
        TblContractInfo contractInfo = tblContractInfoMapper.selectByPrimaryKey(contractId);
        if(contractInfo != null){
            List<TblContractAbi> contractAbiList= tblContractAbiMapper.selectListByContractId(contractId);
            contractInfo.setAbiList(contractAbiList);
            TblAsset tblAsset = realData.getSymbolByAssetId(contractInfo.getAssetId());
            contractInfo.setBalanceStr((new BigDecimal(contractInfo.getBalance()).divide(new BigDecimal(tblAsset.getPrecision()))).stripTrailingZeros().toPlainString()
                    +" " + tblAsset.getSymbol());
            contractInfo.setStatusStr(contractStatusMap.get(contractInfo.getStatus()));
        }
        return contractInfo;
    }
}
