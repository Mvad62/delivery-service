package ru.liga.deliveryservice.service;

import org.springframework.stereotype.Service;

@Service
public class MessageSendingService {

    public void send(String routeString) {
        System.out.println("Доставка отправляется по маршруту: " + routeString);
    }
}