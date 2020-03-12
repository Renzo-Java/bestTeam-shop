package com.qf.mapper;

import com.qf.entity.TOrderlog;

public interface TOrderlogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderlog record);

    int insertSelective(TOrderlog record);

    TOrderlog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderlog record);

    int updateByPrimaryKey(TOrderlog record);
}