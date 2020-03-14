package com.qf.beat.team.smsservice.smsserivce;

import com.qf.beat.team.smsservice.util.ApiDemo4Java;
import com.qf.constant.SMSRabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SMSService {

    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = SMSRabbitMQConstant.SMS_SEND_QUEUE)
    public void process(String phone){
        System.out.println(phone);
        String tplId = "JSM42639-0001";
        int code = (int) ((Math.random()+1)*1000);
        String content = "@1@="+code;
        System.out.println(code);
        ApiDemo4Java.sendTplSms(phone,tplId,content);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

    }

}
