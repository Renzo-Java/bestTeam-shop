package com.qf.mapper;

import com.qf.base.IBaseDao;
import com.qf.entity.TUser;

public interface TUserMapper extends IBaseDao<TUser> {
    int deleteByPrimaryKey(Long id);

    int insert(TUser record);

    int insertSelective(TUser record);

    TUser selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(TUser record);

    int updateByPrimaryKey(TUser record);
}
