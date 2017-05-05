package com.chanpay.ppd.mps.common.activemq;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

import javax.jms.Queue;

@EnableJms
@Configuration
public class ActiveMQConfig {

    @Value("${spring.activemq.user}")
    private String activemqUser;

    @Value("${spring.activemq.password}")
    private String activemqPassword;

    @Value("${spring.activemq.broker-url}")
    private String activemqBrokerUrl;

    @Bean//配置一个消息队列
    public Queue queue() {
        return new ActiveMQQueue("ins_queue");
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory (){
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(activemqUser, activemqPassword, activemqBrokerUrl);
        return activeMQConnectionFactory;
    }

}