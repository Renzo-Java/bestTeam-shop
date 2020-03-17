package com.qf.beat.team.index.controller;

import com.qf.constant.CookieConstant;
import com.qf.constant.LoginRedis;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class CheckIsLogin {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("checkIsLogin")
    @ResponseBody
    public ResultBean checkIsLogin(@CookieValue(name= CookieConstant.USER_LOGIN,required = false)String uuid, HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if(CookieConstant.USER_LOGIN.equals(cookie.getName())){
                String id = cookie.getValue();

                TUser user = (TUser)redisTemplate.opsForValue().get(LoginRedis.USER_LOGIN_PRE + id);

                if(user!=null){
                    return ResultBean.success(user,"用户已登录");
                }
            }
        }

        return null;
    }
}
