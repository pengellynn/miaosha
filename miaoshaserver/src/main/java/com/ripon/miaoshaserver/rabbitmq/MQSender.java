package com.ripon.miaoshaserver.rabbitmq;

import com.alibaba.fastjson.JSON;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQSender {
    @Autowired
    AmqpTemplate amqpTemplate;
    public void sendMessage(MiaoshaMessage message) {
        amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, JSON.toJSONString(message));
    }
}
