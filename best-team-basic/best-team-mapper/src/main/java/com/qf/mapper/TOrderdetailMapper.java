package com.qf.mapper;

import com.qf.entity.TOrderdetail;

public interface TOrderdetailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TOrderdetail record);

    int insertSelective(TOrderdetail record);

    TOrderdetail selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TOrderdetail record);

    int updateByPrimaryKey(TOrderdetail record);
}