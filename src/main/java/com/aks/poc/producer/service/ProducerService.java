package com.aks.poc.producer.service;

import com.aks.poc.producer.dto.Notification;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.UUID;

@Service
@Slf4j
public class ProducerService {

    private static final Sinks.Many<Message<String>> many = Sinks.many().unicast().onBackpressureBuffer();
    public Notification sendMessage(Notification notification) throws Exception {
        log.info("Sending message");
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setSdkUsed(new Notification().getSdkUsed());
        many.emitNext(MessageBuilder.withPayload(new Gson().toJson(notification)).build(), Sinks.EmitFailureHandler.FAIL_FAST);
        log.info("Sent message");
        return notification;
    }
}
