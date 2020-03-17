package com.qf.best.team.cart;

import com.qf.constant.CartRedis;
import com.qf.constant.CookieConstant;
import com.qf.constant.LoginRedis;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import org.springframework.aop.RawTargetAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;

@Controller
public class CheckController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("shopcart")
    public String toCart(){
        return "shopcart";
    }

    @RequestMapping("checkIsLogin")
    @ResponseBody
    public ResultBean checkIsLogin(@CookieValue(value = CookieConstant.USER_LOGIN,required = false)String uuid, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(CookieConstant.USER_LOGIN.equals(cookie.getName())){
                String uid = cookie.getValue();
                TUser user = (TUser)redisTemplate.opsForValue().get(LoginRedis.USER_LOGIN_PRE+uid);
                if(user != null){
                    return ResultBean.success(user, "用户已登录");
                }
            }
        }

        return ResultBean.error("用户未登录");
    }


    @RequestMapping("loginOut")
    public String loginOut(@CookieValue(value = CookieConstant.USER_LOGIN,required = false)String uuid,
                           @CookieValue(value = CookieConstant.USER_CART,required = false)String uuid1,
                           HttpServletResponse response){
        if(uuid!=null && !".".equals(uuid)){
            redisTemplate.delete(LoginRedis.USER_LOGIN_PRE+uuid);

        }


        Cookie cookie = new Cookie(CookieConstant.USER_LOGIN, "");
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:http://localhost:1010/getType";
    }
}
