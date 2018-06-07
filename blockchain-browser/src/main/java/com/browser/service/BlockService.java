package com.browser.service;

import com.browser.dao.entity.TblBcBlock;
import com.browser.dao.entity.TblBcTransaction;
import com.browser.dao.entity.TblBcTransactionEx;

import java.util.List;
import java.util.Map;

public interface BlockService {
	
	 /**
     * 
    * @Title: selectByBlockNum 
    * @Description:根据快号查询快信息
    * @author David
    * @param 
    * @return TblBcBlock 
    * @throws
     */
    TblBcBlock selectByBlockNum(Long blocknUm);
    
    /**
     * 
    * @Title: queryByCount 
    * @Description:判断块表是否有数据
    * @author David
    * @param 
    * @return int 
    * @throws
     */
    int queryByCount();
    
    /**
     * 
    * @Title: queryBlockNum 
    * @Description:获取最大的快号
    * @author David
    * @param 
    * @return int 
    * @throws
     */
    Long queryBlockNum();
    
    /**
     * 
    * @Title: insertSelective 
    * @Description:保存快信息
    * @author David
    * @param 
    * @return int 
    * @throws
     */
    int insertSelective(String block, String trxStr, String singee);
    
    /**
     * 
    * @Title: selectByNewData 
    * @Description:查询最新的5条记录
    * @author David
    * @param 
    * @return List<TblBcBlock> 
    * @throws
     */
    String selectByNewData();
    
    /**
     * 
    * @Title: selectByPrimaryKey 
    * @Description:根据blockId查询快信息
    * @author David
    * @param 
    * @return TblBcBlock 
    * @throws
     */
    TblBcBlock selectByPrimaryKey(String blockId);
    
    /**
     * 
    * @Title: search 
    * @Description:搜索
    * @author David
    * @param 
    * @return String 
    * @throws
     */
	String search(Map<String, Object> params);

	String blockNew();

    void insertBatchBlock(List<TblBcBlock> list);

    void insertTransactionBlock(List<TblBcTransaction> list);

    void insertTransactionEx(List<TblBcTransactionEx> list);

    String getBalance(String address);
}
