package com.qf.mapper;

import com.qf.entity.TOrdership;

public interface TOrdershipMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrdership record);

    int insertSelective(TOrdership record);

    TOrdership selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrdership record);

    int updateByPrimaryKey(TOrdership record);
}