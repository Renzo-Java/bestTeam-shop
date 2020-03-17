package com.qf.best.team.cart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.beat.team.cartapi.CartApi;
import com.qf.constant.CookieConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.www.protocol.http.HttpURLConnection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.util.UUID;

@Controller
public class CartController {

    @Reference
    private CartApi cartApi;

    @RequestMapping("add/{productId}/{count}")
    @ResponseBody
    public ResultBean addCart(HttpServletRequest request,
                              HttpServletResponse response,
                              @CookieValue(name = CookieConstant.USER_CART,required = false)String uuid,
                              @PathVariable Long productId,
                              @PathVariable int count){
        Object o = request.getAttribute("user");
        if(o!=null){
            TUser user = (TUser) o;
            return cartApi.addProduct(user.getId().toString(),productId,count);
        }

        if(uuid==null||"".equals(uuid)) {
            uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(CookieConstant.USER_CART, uuid);
            cookie.setPath("/");
            response.addCookie(cookie);
        }

        return cartApi.addProduct(uuid, productId, count);
    }

    @RequestMapping("clean")
    @ResponseBody
    public ResultBean clean(@CookieValue(name = CookieConstant.USER_CART,required = false)String uuid,
                            HttpServletRequest request,
                            HttpServletResponse response){
        Object o = request.getAttribute("user");
        if(o!=null){
            TUser user = (TUser) o;
            return cartApi.clean(user.getId().toString());
        }

        if(uuid!=null&&!"".equals(uuid)){
            Cookie cookie = new Cookie(CookieConstant.USER_CART, "");
            cookie.setPath("/");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
            return cartApi.clean(uuid);
        }

        return ResultBean.error("没有购物车");
    }


    @RequestMapping("updateCart/{productId}/{count}")
    @ResponseBody
    public ResultBean updateCart(@CookieValue(name = CookieConstant.USER_CART,required = false)String uuid,
                                 HttpServletRequest request,
                                 @PathVariable Long productId,
                                 @PathVariable int count){
        Object o = request.getAttribute("user");
        if(o!=null){
            TUser user = (TUser) o;
            return cartApi.updateCart(user.getId().toString(),productId,count);
        }

        return cartApi.updateCart(uuid , productId, count);
    }


    @RequestMapping("showCart")
    @ResponseBody
    public ResultBean showCart(@CookieValue(name = CookieConstant.USER_CART,required = false)String uuid,
                           HttpServletRequest request){
        Object o = request.getAttribute("user");
        if(o!=null){
            TUser user = (TUser) o;
            return cartApi.showCart(user.getId().toString());
        }

        return cartApi.showCart(uuid);
    }

    @RequestMapping("merge")
    @ResponseBody
    public ResultBean merge(@CookieValue(name = CookieConstant.USER_CART,required = false)String uuid,
                            HttpServletRequest request,HttpServletResponse response){

        TUser user = (TUser) request.getAttribute("user");

        Cookie cookie = new Cookie(CookieConstant.USER_CART,"");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return cartApi.merge(user.getId().toString(), uuid);
    }





















}
