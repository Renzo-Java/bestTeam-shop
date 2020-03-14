package com.qf.best.team.producttype;

import com.qf.base.IBaseDao;
import com.qf.entity.TProductType;

import java.util.List;

public interface TProductTypeApi extends IBaseDao<TProductType> {
    List<TProductType> getAll();
}
