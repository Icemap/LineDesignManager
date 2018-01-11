package com.studio.tensor.ldm.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.studio.tensor.ldm.pojo.OrderInfo;

public interface OrderInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderInfo record);

    int insertSelective(OrderInfo record);

    OrderInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderInfo record);

    int updateByPrimaryKey(OrderInfo record);

    OrderInfo selectByUserId(Integer userId);
    
    List<OrderInfo> selectAll(
    		@Param("start")Integer start, 
    		@Param("size")Integer size);
    
    Integer selectNum();
}