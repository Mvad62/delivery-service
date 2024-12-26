package ru.liga.deliveryservice.model.dto.request;

import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

public record DeliveryRequestDto(CourierPosition courierPosition,
                                 List<String> addresses
) {
}
