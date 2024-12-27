package ru.liga.deliveryservice.formatter;

import org.springframework.stereotype.Component;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;

import java.util.stream.Collectors;

@Component
public class DeliveryFormatter {

    public String formatToResponse(DeliveryResponseDto deliveryResponseDto) {
        return "Вам надо ехать по адресам: " +
                deliveryResponseDto.addresses().stream()
                        .map(Address::getName)
                        .collect(Collectors.joining("; "));
    }
}