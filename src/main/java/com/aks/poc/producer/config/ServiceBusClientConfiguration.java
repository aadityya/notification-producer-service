package com.aks.poc.producer.config;

import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.servicebus.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@Slf4j
public class ServiceBusClientConfiguration {

    private static final String SERVICE_BUS_FQDN = "xxx.servicebus.windows.net";
    private static final String QUEUE_NAME = "xxx-queue";

    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder() {
        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(SERVICE_BUS_FQDN)
                .credential(new ManagedIdentityCredentialBuilder().build());
    }

    @Bean
    ServiceBusSenderClient serviceBusSenderClient(ServiceBusClientBuilder builder) {
        return builder
                .sender()
                .queueName(QUEUE_NAME)
                .buildClient();
    }

    @Bean
    ServiceBusProcessorClient serviceBusProcessorClient(ServiceBusClientBuilder builder) {
        return builder.processor()
                .queueName(QUEUE_NAME)
                .processMessage(ServiceBusClientConfiguration::processMessage)
                .processError(ServiceBusClientConfiguration::processError)
                .buildProcessorClient();
    }

    private static void processMessage(ServiceBusReceivedMessageContext context) {
        ServiceBusReceivedMessage message = context.getMessage();
        log.info("Processing message. Id: %s, Sequence #: %s. Contents: %s%n",
                message.getMessageId(), message.getSequenceNumber(), message.getBody());
    }

    private static void processError(ServiceBusErrorContext context) {
        log.info("Error when receiving messages from namespace: '%s'. Entity: '%s'%n",
                context.getFullyQualifiedNamespace(), context.getEntityPath());
    }
}
