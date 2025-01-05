package ru.liga.deliveryservice.model.dto.response;

import java.util.List;


public record DeliveryResponseDto(List<AddressResponseDto> addresses) {
}