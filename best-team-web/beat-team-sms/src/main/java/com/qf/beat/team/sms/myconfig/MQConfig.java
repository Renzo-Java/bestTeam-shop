package com.qf.beat.team.sms.myconfig;

import com.qf.constant.SMSRabbitMQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQConfig {
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(SMSRabbitMQConstant.SMS_TOPIC_EXCHANGE);
    }
}
