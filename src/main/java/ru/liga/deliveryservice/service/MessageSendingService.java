package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageSendingService {

    public void send(String... route) {
        throw new RuntimeException("Отправка сообщений недоступна, нет доступа к серверу");
    }
}