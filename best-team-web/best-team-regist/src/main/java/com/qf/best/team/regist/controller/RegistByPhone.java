package com.qf.best.team.regist.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.best.team.regist.api.RegistApi;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.midi.Soundbank;

@Controller
public class RegistByPhone {

    @Autowired
    private RedisTemplate redisTemplate;

    @Reference
    private RegistApi registApi;

    @RequestMapping("registByPhone")
    @ResponseBody
    private ResultBean registByPhone(TUser user,int code){

        String phone = user.getPhone();
        System.out.println(code);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        int o = (int)redisTemplate.opsForValue().get(phone);

        if(o==code){
            registApi.insertSelective(user);
            return ResultBean.success("注册成功!");
        }
        return ResultBean.error("验证码错误，注册失败!");
    }

}
