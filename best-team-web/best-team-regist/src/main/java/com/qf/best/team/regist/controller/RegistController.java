package com.qf.best.team.regist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RegistController {


    @RequestMapping("register")
    public String toRegist(){
        return "register";
    }

    @RequestMapping("registerByPhone")
    public String toRegisterByEmail(){
        return "registerByPhone";
    }
}
