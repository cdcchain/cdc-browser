package com.browser.dao.mapper;

import com.browser.dao.entity.TblBcStatistics;

public interface TblBcStatisticsMapper {
    int deleteByPrimaryKey(Long id);

    int insert(TblBcStatistics record);

    int insertSelective(TblBcStatistics record);

    TblBcStatistics selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TblBcStatistics record);

    int updateByPrimaryKey(TblBcStatistics record);
    
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
    
    /**
     * 
    * @Title: updateByStatic 
    * @Description:更新统计信息
    * @author David
    * @param 
    * @return int 
    * @throws
     */
    int updateByStatic(TblBcStatistics record);
    
}