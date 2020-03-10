package com.qf.mapper;

import com.qf.base.IBaseDao;
import com.qf.entity.TProductDesc;

public interface TProductDescMapper extends IBaseDao<TProductDesc> {
    int deleteByPrimaryKey(Long id);

    int insert(TProductDesc record);

    int insertSelective(TProductDesc record);

    TProductDesc selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TProductDesc record);

    int updateByPrimaryKeyWithBLOBs(TProductDesc record);

    int updateByPrimaryKey(TProductDesc record);
}
