package com.browser.service;

import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblBcTransactionEx;
import com.browser.protocol.EUDataGridResult;

import java.util.List;
import java.util.Map;

public interface TransactionService {
	
	/**
	 * 
	* @Title: selectByPrimaryKey 
	* @Description:根据交易ID查询交易信息
	* @author David
	* @param 
	* @return TblBcTransaction 
	* @throws
	 */
	TblBcTransaction selectByPrimaryKey(String trxId);
	
	/**
	 * 
	* @Title: selectByTrxData 
	* @Description:查询最新的5条数据
	* @author David
	* @param 
	* @return String 
	* @throws
	 */
	String selectByTrxData();
	
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
    * @Title: queryNum 
    * @Description:计算总额
    * @author David
    * @param 
    * @return Map<String,Object> 
    * @throws
     */
	Map<String, Object> queryNum(String blockId);
	
	/**
	 * 
	* @Title: queryByTrxId 
	* @Description:根据交易ID查询交易信息
	* @author David
	* @param 
	* @return TblBcTransaction
	* @throws
	 */
	TblBcTransaction queryByTrxId(String trxId);
	
	/**
	 * 
	* @Title: selectBy 
	* @Description:更新统计信息
	* @author David
	* @param 
	* @return void 
	* @throws
	 */
	void updateSelect();
	
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

	EUDataGridResult getTransactionList(TblBcTransaction transaction, Integer page, Integer rows);

	void insertBatchTransactionEx(List<TblBcTransactionEx> list);

	void insertBatchTransaction(List<TblBcTransaction> list);

	List<TblBcTransaction> queryContractTrx(TblBcTransaction transaction);
}
