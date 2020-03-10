package com.qf.mapper;

import com.qf.base.IBaseDao;
import com.qf.entity.TProduct;

public interface TProductMapper extends IBaseDao<TProduct> {
    int deleteByPrimaryKey(Long pid);

    int insert(TProduct record);

    int insertSelective(TProduct record);

    TProduct selectByPrimaryKey(Long pid);

    int updateByPrimaryKeySelective(TProduct record);

    int updateByPrimaryKey(TProduct record);
}
