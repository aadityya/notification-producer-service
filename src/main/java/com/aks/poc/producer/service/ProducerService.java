package com.aks.poc.producer.service;

import com.aks.poc.producer.dto.Notification;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class ProducerService {

    @Autowired

    private ServiceBusSenderClient senderClient;

    public Notification sendMessage(Notification notification) throws Exception {
        log.info("Sending message");
        notification.setNotificationId(UUID.randomUUID().toString());
        senderClient.sendMessage(new ServiceBusMessage(new Gson().toJson(notification)));
        senderClient.close();
        log.info("Sent message");
        return notification;
    }
}
