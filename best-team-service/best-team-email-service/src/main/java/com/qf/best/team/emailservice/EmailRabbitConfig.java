package com.qf.best.team.emailservice;

import com.qf.constant.EmailRabbitMQConstant;
import com.qf.constant.SMSRabbitMQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EmailRabbitConfig {

    @Bean
    public Queue getQueue(){
        return new Queue(EmailRabbitMQConstant.EMAIL_SEND_QUEUE);
    }

    @Bean
    public TopicExchange getTopicExchange(){
        return new TopicExchange(EmailRabbitMQConstant.EMAIL_TOPIC_EXCHANGE);
    }

    @Bean
    public Binding getBinding(){
        return BindingBuilder.bind(getQueue()).to(getTopicExchange()).with("email.send");
    }


}
