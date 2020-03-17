package com.qf.beat.sso.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.best.team.userloginapi.UserLoginApi;
import com.qf.constant.CookieConstant;
import com.qf.constant.LoginRedis;
import com.qf.dto.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class LonginController {

    @Reference
    private UserLoginApi userLoginApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("toLogin")
    public String loginPage(){
        return "login";
    }

    @RequestMapping("checkLogin")
    public String checkLogin(String uname, String password, HttpServletResponse response){
        ResultBean resultBean = userLoginApi.checkLogin(uname, password);
        if(resultBean.getErrno()==0){
            String uuid = UUID.randomUUID().toString();
            Cookie cookie = new Cookie(CookieConstant.USER_LOGIN, uuid);

            redisTemplate.opsForValue().set(LoginRedis.USER_LOGIN_PRE+uuid, resultBean.getData(), 30, TimeUnit.DAYS);

            cookie.setMaxAge(30*24*60*60);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            response.addCookie(cookie);

            return "redirect:http://localhost:1010/getType";
        }
        return "loginError";
    }



}
