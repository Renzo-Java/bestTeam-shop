package com.qf.beat.team.smsservice.smsserivce;

import com.qf.beat.team.smsservice.util.ApiDemo4Java;
import com.qf.beat.team.smsservice.util.RCSCloudAPITest;
import com.qf.constant.SMSRabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class SMSService {

    static String tipId = "02c6342fb5d64b038c740a40aec0fb70";


    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitListener(queues = SMSRabbitMQConstant.SMS_SEND_QUEUE)
    public void process(String phone){
        System.out.println(phone);
        int code = (int) ((Math.random()+1)*1000);
        String content = "@1@="+code;
        System.out.println(code);

        RCSCloudAPITest.sendTplSms(tipId,phone,content,"");

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(phone, code, 5, TimeUnit.MINUTES);

    }

}
