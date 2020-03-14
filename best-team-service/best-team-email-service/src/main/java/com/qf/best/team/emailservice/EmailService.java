package com.qf.best.team.emailservice;

import com.alibaba.dubbo.config.annotation.Reference;
import com.qf.best.team.regist.api.RegistApi;
import com.qf.constant.EmailRabbitMQConstant;
import com.qf.constant.EmailRedisConstant;
import com.qf.dto.ResultBean;
import com.qf.entity.TUser;
import com.qf.vo.Email;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;

@Controller
public class EmailService {

    @Reference
    private RegistApi registApi;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private JavaMailSender sender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${activeServerUrl}")
    private String activeServerUrl;

    @RequestMapping("email/active/{uuid}")
    public String activeEmail(@PathVariable String uuid){

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        String email = (String)redisTemplate.opsForValue().get(EmailRedisConstant.REGIST_EMAIL_URL_KEY_PRE+uuid);

        System.out.println(email);

        int i = registApi.updateByEmail(email);

        if(i>0){
            return "success";
        }
        return "error";
    }

    @RabbitListener(queues = EmailRabbitMQConstant.EMAIL_SEND_QUEUE)
    public void process(Email email){

        MimeMessage message = sender.createMimeMessage();

        MimeMessageHelper mailMessage = null;
        try {
            mailMessage = new MimeMessageHelper(message,true);
            mailMessage.setSubject("请激活您在本中心的账号");

            //读取模板内容
            Context context = new Context();
            context.setVariable("username",email.getEmailname().substring(0,email.getEmailname().lastIndexOf('@')));
            context.setVariable("url",activeServerUrl+email.getUuid());
            System.out.println(activeServerUrl+email.getUuid());
            String info = templateEngine.process("emailTemplate", context);

            mailMessage.setText(info,true);

            mailMessage.setFrom("1378523714@qq.com");//系统账号
            mailMessage.setTo(email.getEmailname());

            sender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
