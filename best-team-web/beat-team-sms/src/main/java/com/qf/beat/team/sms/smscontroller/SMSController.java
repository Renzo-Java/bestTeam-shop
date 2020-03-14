package com.qf.beat.team.sms.smscontroller;

import com.qf.constant.SMSRabbitMQConstant;
import com.qf.dto.ResultBean;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class SMSController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("getCode")
    @ResponseBody
    public ResultBean getCode(@RequestParam String phone, HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin", "*");
        rabbitTemplate.convertAndSend(SMSRabbitMQConstant.SMS_TOPIC_EXCHANGE,"sms.send",phone);
        return ResultBean.success();
    }

}
