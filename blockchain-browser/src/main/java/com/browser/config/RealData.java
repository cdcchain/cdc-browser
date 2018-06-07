package com.browser.config;

import com.alibaba.fastjson.JSONObject;
import com.browser.dao.entity.TblAsset;
import com.browser.dao.entity.TblContractInfo;
import com.browser.dao.mapper.TblAssetMapper;
import com.browser.service.SocketService;
import com.browser.tools.Constant;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 页面轮询请求的实施数据
 */
@Component
public class RealData implements InitializingBean {

    @Autowired
    private SocketService socketService;

    @Autowired
    private TblAssetMapper tblAssetMapper;

    @Value("${asset.show}")
    private Boolean assetShow;

    //最新统计数据
    private String statisInfo;
    //最新区块数据
    private String blockInfo;
    //最新交易数据
    private String transactionInfo;


    //注册合约
    private Set<TblContractInfo> registerContractInfo;

    //更新的合约
    private Set<TblContractInfo> updateContractInfo;

    private Map<Integer,TblAsset> tblAssetMap;

    /**
     * 启动加载
     */
    @Override
    public void afterPropertiesSet(){
        registerContractInfo = new TreeSet<TblContractInfo>(contractInfoASC);
        updateContractInfo = new TreeSet<TblContractInfo>(contractInfoASC);
        refreshAssetInfo();
    }

    //合约信息排序,块号升序
    Comparator<TblContractInfo> contractInfoASC = new Comparator<TblContractInfo>() {
        public int compare(TblContractInfo o1, TblContractInfo o2) {
            boolean flag = o1.getBlockNum() == o2.getBlockNum() && o1.getBlockNum() != 0;
            if (flag) {
                return 0;
            }
            int ret = o1.getBlockNum().compareTo(o2.getBlockNum());
            if (ret == 0) {
                return o1.getBlockNum().compareTo(o2.getBlockNum());
            } else {
                return ret;
            }
        }
    };

    /**
     * 更新合约信息
     * @param tblContractInfo
     */
    public void setUpdateContractInfo(TblContractInfo tblContractInfo) {
        synchronized (updateContractInfo) {
            try {
                this.updateContractInfo.add(tblContractInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  Set<TblContractInfo> getUpdateContractInfo(){
        return updateContractInfo;
    }

    /**
     * 新增注册合约信息
     * @param tblContractInfo
     */
    public void setRegisterContractInfo(TblContractInfo tblContractInfo) {
        synchronized (registerContractInfo) {
            try {
                this.registerContractInfo.add(tblContractInfo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public  Set<TblContractInfo> getRegisterContractInfo(){
        return registerContractInfo;
    }

    public void refreshAssetInfo(){
        tblAssetMap = new HashMap<>();
        List<TblAsset> assetList = tblAssetMapper.selectAll();
        if(assetList!=null && assetList.size()>0){
            for(TblAsset asset:assetList){
                tblAssetMap.put(asset.getAssetId(),asset);
            }
        }
    }

    /**
     * 获取资产信息
     * @param assetId
     * @return
     */
    public TblAsset getSymbolByAssetId(Integer assetId){
        if(!assetShow || assetId==null){
            TblAsset tblAsset = new TblAsset();
            tblAsset.setAssetId(assetId);
            tblAsset.setPrecision(Constant.PRECISION);
            tblAsset.setSymbol(Constant.SYMBOL);
            return tblAsset;
        }else{
            TblAsset tblAsset = tblAssetMap.get(assetId);
            //为空从新查询接口
            if(tblAsset == null){
                String result = socketService.getAssetInfo(assetId);
                JSONObject json = JSONObject.parseObject(result);
                if(json!=null){
                    String symbol = json.getString("symbol");
                    Long precision = json.getLong("precision");
                    if(!StringUtils.isEmpty(symbol) && precision!=null){
                        tblAsset = new TblAsset();
                        tblAsset.setAssetId(assetId);
                        tblAsset.setPrecision(precision);
                        tblAsset.setSymbol(symbol);

                        tblAssetMapper.insert(tblAsset);

                        //刷新内存数据
                        refreshAssetInfo();
                    }
                }
            }
            if(tblAsset==null){
                tblAsset = new TblAsset();
                tblAsset.setAssetId(assetId);
                tblAsset.setPrecision(Constant.PRECISION);
                tblAsset.setSymbol(Constant.SYMBOL);
            }
            return tblAsset;
        }
    }

    /**
     * 清空存储数据
     */
    public void clear() {
        registerContractInfo = new TreeSet<TblContractInfo>(contractInfoASC);
        updateContractInfo = new TreeSet<TblContractInfo>(contractInfoASC);
    }

    public String getStatisInfo() {
        return statisInfo;
    }

    public void setStatisInfo(String statisInfo) {
        this.statisInfo = statisInfo;
    }

    public String getBlockInfo() {
        return blockInfo;
    }

    public void setBlockInfo(String blockInfo) {
        this.blockInfo = blockInfo;
    }

    public String getTransactionInfo() {
        return transactionInfo;
    }

    public void setTransactionInfo(String transactionInfo) {
        this.transactionInfo = transactionInfo;
    }
}
