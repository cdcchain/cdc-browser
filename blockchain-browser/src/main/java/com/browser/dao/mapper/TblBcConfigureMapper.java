package com.browser.dao.mapper;

import com.browser.dao.entity.TblBcConfigure;

import java.util.List;

public interface TblBcConfigureMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TblBcConfigure record);

    int insertSelective(TblBcConfigure record);

    TblBcConfigure selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TblBcConfigure record);

    int updateByPrimaryKey(TblBcConfigure record);
    
    /**
     * 
    * @Title: selectByCount 
    * @Description:判断统计表是否有数据
    * @author David
    * @param 
    * @return int 
    * @throws
     */
    int selectByCount();

    int insertBatch(List<TblBcConfigure> record);
}