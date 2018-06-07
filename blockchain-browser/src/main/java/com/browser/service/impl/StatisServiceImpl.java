package com.browser.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.browser.config.RealData;
import com.browser.dao.entity.*;
import com.browser.dao.mapper.*;
import com.browser.service.StatisService;
import com.browser.tools.Constant;
import com.browser.tools.common.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class StatisServiceImpl implements StatisService {
	
	@Autowired
	private TblBcStatisticsMapper tblBcStatisticsMapper;
	
	@Autowired
	private TblBcNewsMapper tblBcNewsMapper;

	@Autowired
	private TblBcBlockMapper tblBcBlockMapper;

	@Autowired
	private TblBcTransactionMapper tblBcTransactionMapper;

	@Autowired
	private TblContractInfoMapper tblContractInfoMapper;

	@Autowired
	private RealData realData;

	@Override
	public TblBcStatistics selectByPrimaryKey(Long id) {
		return tblBcStatisticsMapper.selectByPrimaryKey(id);
	}
	
	@Override
	public List<TblBcNews> selectByNew() {
		return tblBcNewsMapper.selectByNew();
	}

	@Override
	public int updateByPrimaryKeySelective(TblBcStatistics statistic) {
		return tblBcStatisticsMapper.updateByPrimaryKeySelective(statistic);
	}

	@Override
	public 	void newBlockStatic(){
		List<TblBcBlock> blockList = tblBcBlockMapper.selectByNewData();
		if(blockList!=null && blockList.size()>0){
			for(TblBcBlock block:blockList){
				block.setAmountStr((new BigDecimal(block.getAmount()).divide(new BigDecimal(Constant.PRECISION))).stripTrailingZeros().toPlainString()
				+" " + Constant.SYMBOL);
			}
		}
		realData.setBlockInfo(JSONObject.toJSONString(blockList));
	}

	@Override
	public void newTransactionStatic(){
		List<TblBcTransaction> trx = tblBcTransactionMapper.selectByTrxData();
		if (null != trx && trx.size() > 0) {
			for (TblBcTransaction tb : trx) {
				DateUtil.trxTimeToDay(tb);
				TblAsset tblAsset = realData.getSymbolByAssetId(tb.getAssetId());

				if("transfer".equals(tb.getCalledAbi())){
					String abiParams = tb.getAbiParams();
					BigDecimal amount = new BigDecimal(abiParams.split(",")[1]);
					//获取合约精度
					TblContractInfo contractInfo = tblContractInfoMapper.selectByPrimaryKey(tb.getToAccount());
					Long precision = contractInfo.getPrecision();
					if(null == precision){
						precision = Constant.PRECISION;
					}
					tb.setAmountStr(amount.divide(new BigDecimal(precision)).stripTrailingZeros().toPlainString()
							+" " + contractInfo.getName());
				}else{
					tb.setAmountStr(new BigDecimal(tb.getAmount()).divide(new BigDecimal(tblAsset.getPrecision())).stripTrailingZeros().toPlainString()
							+" " + tblAsset.getSymbol());
				}
				tb.setFeeStr((new BigDecimal(tb.getFee()).divide(new BigDecimal(Constant.PRECISION))).stripTrailingZeros().toPlainString()
						+" " + Constant.SYMBOL);
			}
		}
		realData.setTransactionInfo(JSONObject.toJSONString(trx));
	}
	
}
