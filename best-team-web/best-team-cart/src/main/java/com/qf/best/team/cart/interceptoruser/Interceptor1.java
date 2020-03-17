package com.qf.best.team.cart.interceptoruser;

import com.qf.constant.CookieConstant;
import com.qf.constant.LoginRedis;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class Interceptor1 implements HandlerInterceptor {

    @Autowired
    private RedisTemplate redisTemplate;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //1.获取cookie中uuid
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(CookieConstant.USER_LOGIN.equals(cookie.getName())){
                    String uuid = cookie.getValue();
                    Object o = redisTemplate.opsForValue().get(LoginRedis.USER_LOGIN_PRE +uuid);
                    if(o!=null){
                        //用户已登录
                        TUser tUser = (TUser) o;
                        //存入到request域中
                        request.setAttribute("user",tUser);
                        return true;

                    }

                }
            }
        }

        return true;
    }
}
