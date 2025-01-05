package ru.liga.deliveryservice.model.dto.request;

import org.springframework.lang.NonNull;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

public record DeliveryRequestDto(@NonNull CourierPosition courierPosition,
                                 @NonNull List<String> addresses
) {
}