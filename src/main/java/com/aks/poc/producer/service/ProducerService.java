package com.aks.poc.producer.service;

import com.aks.poc.producer.dto.Notification;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class ProducerService {

    @Autowired
    private Sinks.Many<Message<String>> many;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProducerService.class);

    public Notification sendMessage(Notification notification) throws Exception {
        log.info("Sending message3");
        notification.setNotificationId(UUID.randomUUID().toString());
        notification.setSdkUsed(new Notification().getSdkUsed());
        many.emitNext(MessageBuilder.withPayload(new Gson().toJson(notification)).build(), Sinks.EmitFailureHandler.FAIL_FAST);
        log.info("Sent message3");
        return notification;
    }

    @Bean
    public Supplier<Flux<Message<String>>> supply() {
        log.info("Entered2");
        return ()->many.asFlux()
                .doOnNext(m->LOGGER.info("Manually sending message {}", m))
                .doOnError(t->LOGGER.error("Error encountered", t));
    }
}
