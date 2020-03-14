package com.qf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

@SpringBootTest
class BestTeamEmailServiceApplicationTests {

    @Autowired
    private JavaMailSender sender;

    @Test
    void contextLoads() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("亲爱的如意宝贝儿");
        mailMessage.setText("你好呀，很高兴认识你哦，不要生气啦好不好");
        mailMessage.setFrom("1378523714@qq.com");
        mailMessage.setTo("704118163@qq.com");
        sender.send(mailMessage);
    }

}
