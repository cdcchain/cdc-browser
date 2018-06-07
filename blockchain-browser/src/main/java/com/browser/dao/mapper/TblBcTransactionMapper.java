package com.browser.dao.mapper;

import com.browser.dao.entity.TblBcTransaction;

import java.util.List;

public interface TblBcTransactionMapper {
    int deleteByPrimaryKey(String trxId);

    int insert(TblBcTransaction record);

    int insertBatch(List<TblBcTransaction> list);

    int insertSelective(TblBcTransaction record);

    TblBcTransaction selectByPrimaryKey(String trxId);

    int updateByPrimaryKeySelective(TblBcTransaction record);

    int updateByPrimaryKey(TblBcTransaction record);
    
    /**
     * 
    * @Title: selectByTrxData 
    * @Description:查询最新的5条交易信息
    * @author David
    * @param 
    * @return List<TblBcTransaction> 
    * @throws
     */
    List<TblBcTransaction> selectByTrxData();
    
    /**
     * 
    * @Title: selectByBlockId 
    * @Description:根据快Id查询快信息
    * @author David
    * @param 
    * @return List<TblBcTransaction> 
    * @throws
     */
    List<TblBcTransaction> selectByBlockId(String blockId);
    
    /**
     * 
    * @Title: selectByAmountNum 
    * @Description:统计交易额
    * @author David
    * @param 
    * @return Map<String,Object> 
    * @throws
     */
    Long selectByAmountNum();
    
    /**
     * 
    * @Title: selectByFee 
    * @Description:统计手续费
    * @author David
    * @param 
    * @return Map<String,Object> 
    * @throws
     */
    Long selectByFee();
    
    /**
     * 
    * @Title: selectByNum 
    * @Description:统计交易数量
    * @author David
    * @param 
    * @return Map<String,Object> 
    * @throws
     */
    Integer selectByNum();
    
    /**
     * 
    * @Title: queryByTrxId 
    * @Description:通过交易ID查询交易信息
    * @author David
    * @param 
    * @return Map<String,Object> 
    * @throws
     */
     TblBcTransaction queryByTrxId(String trxId);
    
    /**
     * 
    * @Title: selectHour 
    * @Description:统计上一个小时的交易量
    * @author David
    * @param 
    * @return Integer 
    * @throws
     */
    Integer selectHour(String time);
    
    /**
     * 
    * @Title: selectAll 
    * @Description:查询所有数据
    * @author David
    * @param 
    * @return List<TblBcTransaction> 
    * @throws
     */
    Integer selectAll();

    List<TblBcTransaction> getTransactionList(TblBcTransaction record);

    int deleteByBlockNum(Long blockNum);

    List<TblBcTransaction> queryContractTrx(TblBcTransaction record);
}