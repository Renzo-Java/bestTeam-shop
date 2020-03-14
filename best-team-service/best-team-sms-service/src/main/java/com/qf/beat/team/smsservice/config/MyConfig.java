package com.qf.beat.team.smsservice.config;

import com.qf.constant.SMSRabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyConfig {

    @Bean
    public Queue getQueue(){
        return new Queue(SMSRabbitMQConstant.SMS_SEND_QUEUE);
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(SMSRabbitMQConstant.SMS_TOPIC_EXCHANGE);
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getTopicExchange()).with("sms.send");
    }

}
