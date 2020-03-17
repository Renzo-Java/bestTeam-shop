package com.qf.beat.team.cartapi;

import com.qf.dto.ResultBean;

public interface CartApi {

    ResultBean addProduct(String id,Long productId,int count);

    ResultBean clean(String id);

    ResultBean updateCart(String id,Long productId,int count);

    ResultBean showCart(String id);

    ResultBean merge(String id,String uuid);

}
