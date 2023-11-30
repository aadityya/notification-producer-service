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


    @Bean
    ServiceBusClientBuilder serviceBusClientBuilder() {
        return new ServiceBusClientBuilder()
                .fullyQualifiedNamespace(SERVICE_BUS_FQDN)
                .credential(new ManagedIdentityCredentialBuilder().build());
    }
}
