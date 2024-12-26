package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.liga.deliveryservice.mapper.CourierPositionMapper;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

@ShellComponent
@RequiredArgsConstructor
public class DeliveryController {

    private final CourierPositionMapper courierPositionMapper;
    private final DeliveryMapper deliveryMapper;
    private final DeliveryService deliveryService;

    @ShellMethod()
    public DeliveryResponseDto create(Double courierX, Double courierY, String firstAddress, String secondAddress, String thirdAddress) {
        CourierPosition currentPosition = courierPositionMapper.mapFromPointsToEntity(courierX, courierY);
        DeliveryRequestDto deliveryRequestDto = deliveryMapper.mapFromParamsToDto(currentPosition,
                List.of(firstAddress, secondAddress, thirdAddress));

        return deliveryService.create(deliveryRequestDto);
    }
}