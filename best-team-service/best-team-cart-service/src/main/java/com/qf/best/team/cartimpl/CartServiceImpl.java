package com.qf.best.team.cartimpl;

import com.alibaba.dubbo.config.annotation.Service;
import com.qf.beat.team.cartapi.CartApi;
import com.qf.constant.CartRedis;
import com.qf.dto.ResultBean;
import com.qf.entity.TProduct;
import com.qf.mapper.TProductMapper;
import com.qf.vo.CartItem;
import com.qf.vo.ProductCartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import java.util.*;

@Component
@Service
public class CartServiceImpl implements CartApi {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TProductMapper tProductMapper;

    @Override
    public ResultBean addProduct(String id, Long productId, int count) {
        String redisKey = CartRedis.USER_CART_PRE + id;
        Object o = redisTemplate.opsForValue().get(redisKey);
        if(o==null){
            CartItem cartItem = new CartItem(productId, count, new Date());
            ArrayList<CartItem> cartItems = new ArrayList<>();
            cartItems.add(cartItem);

            redisTemplate.opsForValue().set(redisKey, cartItems);
            return ResultBean.success(cartItems, "购物车添加成功");
        }

        ArrayList<CartItem> cartItems =  (ArrayList<CartItem>)o;
        for (CartItem cartItem : cartItems) {
            if(cartItem.getProductId().longValue()==productId.longValue()){
                cartItem.setCount(cartItem.getCount()+count);
                cartItem.setUpdateTime(new Date());

                redisTemplate.opsForValue().set(redisKey, cartItems);
                return ResultBean.success(cartItems, "购物车添加成功");
            }
        }

        CartItem cartItem = new CartItem(productId, count, new Date());
        cartItems.add(cartItem);
        redisTemplate.opsForValue().set(redisKey, cartItems);

        return ResultBean.success(cartItems, "购物车添加成功");
    }

    @Override
    public ResultBean clean(String id) {
        redisTemplate.delete(CartRedis.USER_CART_PRE+id);
        return ResultBean.success("情况购物车成功");
    }

    @Override
    public ResultBean updateCart(String id, Long productId, int count) {

        Object o = redisTemplate.opsForValue().get(CartRedis.USER_CART_PRE + id);
        if(o!=null){
            List<CartItem> cartItems = (List<CartItem>) o;
            for (CartItem cartItem : cartItems) {
                if(cartItem.getProductId().longValue()==productId.longValue()){
                    cartItem.setCount(count);
                    cartItem.setUpdateTime(new Date());
                    redisTemplate.opsForValue().set(CartRedis.USER_CART_PRE+id, cartItems);
                    return ResultBean.success(cartItems, "更新购物车成功");
                }
            }
        }
        return ResultBean.success("当前没有购物车");
    }

    @Override
    public ResultBean showCart(String id) {

        Object o = redisTemplate.opsForValue().get(CartRedis.USER_CART_PRE + id);
        if(o!=null){
            List<CartItem> cartItems = (List<CartItem>)o;
            List<ProductCartVo> products = new ArrayList<>();
            for (CartItem cartItem : cartItems) {
                TProduct product =(TProduct) redisTemplate.opsForValue().get(CartRedis.PRODUCT_PRE+cartItem.getProductId().toString());
                if(product==null){
                    product = tProductMapper.selectByPrimaryKey(cartItem.getProductId());
                    redisTemplate.opsForValue().set(CartRedis.PRODUCT_PRE+cartItem.getProductId().toString(), product);
                }

                ProductCartVo productCartVo = new ProductCartVo();
                productCartVo.setProduct(product);
                productCartVo.setCount(cartItem.getCount());
                productCartVo.setUpdateTime(new Date());

                products.add(productCartVo);
            }
            Collections.sort(products, new Comparator<ProductCartVo>() {
                @Override
                public int compare(ProductCartVo o2, ProductCartVo o1) {
                    return (int)(o1.getUpdateTime().getTime()-o2.getUpdateTime().getTime());
                }
            });
            return ResultBean.success(products);
        }

        return ResultBean.error("当前无购物车");
    }

    @Override
    public ResultBean merge(String id, String uuid) {
        Object noLogin = redisTemplate.opsForValue().get(CartRedis.USER_CART_PRE + uuid);
        Object login = redisTemplate.opsForValue().get(CartRedis.USER_CART_PRE + id);
        if(noLogin==null){
            return ResultBean.error("未登录状态下没有购物车，不需要合并");
        }
        if(login==null){
            redisTemplate.opsForValue().set(CartRedis.USER_CART_PRE + id,noLogin);
            redisTemplate.delete(CartRedis.USER_CART_PRE + uuid);
            return ResultBean.success(noLogin,"登录状态下购物车无商品，合并成功");
        }

        List<CartItem> noLoginCarts = (List<CartItem>) noLogin;
        List<CartItem> loginCarts = (List<CartItem>) login;

        Map<Long,CartItem> map = new HashMap<>();

        for (CartItem noLoginCart : noLoginCarts) {
            map.put(noLoginCart.getProductId(),noLoginCart);
        }

        for (CartItem loginCart : loginCarts) {
            CartItem cartItem = map.get(loginCart.getProductId());
            if(cartItem!=null){
                cartItem.setCount(loginCart.getCount()+cartItem.getCount());
            }
            map.put(loginCart.getProductId(), loginCart);
        }

        redisTemplate.delete(CartRedis.USER_CART_PRE+uuid);

        Collection<CartItem> values = map.values();
        List<CartItem> list = new ArrayList<>(values);
        redisTemplate.opsForValue().set(CartRedis.USER_CART_PRE+id,list );
        return ResultBean.success(list,"合并成功");
    }
}
























