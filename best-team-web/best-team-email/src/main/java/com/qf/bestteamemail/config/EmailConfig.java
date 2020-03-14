package com.qf.bestteamemail.config;

import com.qf.constant.EmailRabbitMQConstant;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailConfig {
    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(EmailRabbitMQConstant.EMAIL_TOPIC_EXCHANGE);
    }
}
