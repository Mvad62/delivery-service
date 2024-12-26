package ru.liga.deliveryservice.model.dto.response;

import ru.liga.deliveryservice.model.entity.Address;

import java.util.List;


public record DeliveryResponseDto(List<Address> addresses) {
}