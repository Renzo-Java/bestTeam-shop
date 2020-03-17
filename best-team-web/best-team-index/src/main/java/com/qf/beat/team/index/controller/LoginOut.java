package com.qf.beat.team.index.controller;

import com.qf.constant.CartRedis;
import com.qf.constant.CookieConstant;
import com.qf.constant.LoginRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginOut {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/loginOut")
    public String loginOut(@CookieValue(name = CookieConstant.USER_LOGIN,required = false)String uuid,
                           @CookieValue(name = CookieConstant.USER_CART,required = false)String uuid1,
                           HttpServletResponse response){
        if(uuid!=null && !".".equals(uuid)){
            redisTemplate.delete(LoginRedis.USER_LOGIN_PRE+uuid);
        }

        Cookie cookie = new Cookie(CookieConstant.USER_LOGIN, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        return "redirect:/getType";
    }

}
