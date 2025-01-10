package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.service.formatter.AddressFormatter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageSendingService {

    private final AddressFormatter addressFormatter;

    public void send(List<AddressResponseDto> route) {
        String routeString = addressFormatter.toString(route);
        System.out.println("Доставка отправляется по маршруту: " + routeString);
    }
}