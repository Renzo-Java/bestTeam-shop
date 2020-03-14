package com.qf.bestteamemail.emailcontroller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.best.team.regist.api.RegistApi;
import com.qf.constant.EmailRabbitMQConstant;
import com.qf.constant.EmailRedisConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.vo.Email;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Controller
public class EMailController {

    @Reference
    private RegistApi registApi;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("regist/email")
    public String registByEmail(TUser user){
        Email email = new Email(user.getEmail(),user.getPassword());
        String uuid = UUID.randomUUID().toString();
        email.setUuid(uuid);

        rabbitTemplate.convertAndSend(EmailRabbitMQConstant.EMAIL_TOPIC_EXCHANGE, "email.send", email);

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(EmailRedisConstant.REGIST_EMAIL_URL_KEY_PRE+uuid, user.getEmail(), 15, TimeUnit.MINUTES);


        registApi.insertSelective(user);

        return "success";
    }


}
