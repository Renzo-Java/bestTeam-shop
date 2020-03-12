package com.qf.mapper;

import com.qf.entity.TPay;

public interface TPayMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TPay record);

    int insertSelective(TPay record);

    TPay selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TPay record);

    int updateByPrimaryKey(TPay record);
}