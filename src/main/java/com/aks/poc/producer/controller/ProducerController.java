package com.aks.poc.producer.controller;

import com.aks.poc.producer.dto.Notification;
import com.aks.poc.producer.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@Slf4j
public class ProducerController {
    @Autowired
    ProducerService service;

    @PostMapping("/notification")
    public Notification sendMessage(@RequestBody Notification notification) throws Exception {
        return service.sendMessage(notification);
    }
}
