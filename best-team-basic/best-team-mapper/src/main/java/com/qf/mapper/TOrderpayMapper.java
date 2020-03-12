package com.qf.mapper;

import com.qf.entity.TOrderpay;

public interface TOrderpayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderpay record);

    int insertSelective(TOrderpay record);

    TOrderpay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderpay record);

    int updateByPrimaryKey(TOrderpay record);
}