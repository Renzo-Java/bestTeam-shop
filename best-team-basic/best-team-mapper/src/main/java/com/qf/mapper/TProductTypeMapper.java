package com.qf.mapper;

import com.qf.base.IBaseDao;
import com.qf.entity.TProductType;

public interface TProductTypeMapper extends IBaseDao<TProductType> {
    int deleteByPrimaryKey(Long cid);

    int insert(TProductType record);

    int insertSelective(TProductType record);

    TProductType selectByPrimaryKey(Long cid);

    int updateByPrimaryKeySelective(TProductType record);

    int updateByPrimaryKey(TProductType record);
}
