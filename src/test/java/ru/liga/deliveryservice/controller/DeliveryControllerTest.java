package ru.liga.deliveryservice.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.deliveryservice.formatter.DeliveryFormatter;
import ru.liga.deliveryservice.mapper.CourierPositionMapper;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.model.entity.CourierPosition;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeliveryControllerTest {

    @Mock
    private CourierPositionMapper courierPositionMapper;

    @Mock
    private DeliveryMapper deliveryMapper;

    @Mock
    private DeliveryService deliveryService;

    @Mock
    private DeliveryFormatter deliveryFormatter;

    @InjectMocks
    private DeliveryController deliveryController;

    @Test
    void create_shouldReturnFormattedResponse() {
        Double courierX = 10.0;
        Double courierY = 20.0;
        String firstAddress = "Address1";
        String secondAddress = "Address2";
        String thirdAddress = "Address3";

        CourierPosition courierPosition = new CourierPosition(courierX, courierY);
        DeliveryRequestDto requestDto = new DeliveryRequestDto(courierPosition, List.of(firstAddress, secondAddress, thirdAddress));
        DeliveryResponseDto responseDto = new DeliveryResponseDto(List.of(
                new Address(1, "Address1", 10.0, 20.0),
                new Address(2, "Address2", 15.0, 25.0),
                new Address(3, "Address3", 20.0, 30.0)
        ));
        String formattedResponse = "Вам надо ехать по адресам: Address1; Address2; Address3";

        when(courierPositionMapper.mapFromPointsToEntity(courierX, courierY)).thenReturn(courierPosition);
        when(deliveryMapper.mapFromParamsToDto(courierPosition, List.of(firstAddress, secondAddress, thirdAddress)))
                .thenReturn(requestDto);
        when(deliveryService.create(requestDto)).thenReturn(responseDto);
        when(deliveryFormatter.formatToResponse(responseDto)).thenReturn(formattedResponse);

        String result = deliveryController.create(courierX, courierY, firstAddress, secondAddress, thirdAddress);

        assertThat(result).isEqualTo(formattedResponse);
        verify(courierPositionMapper).mapFromPointsToEntity(courierX, courierY);
        verify(deliveryMapper).mapFromParamsToDto(courierPosition, List.of(firstAddress, secondAddress, thirdAddress));
        verify(deliveryService).create(requestDto);
        verify(deliveryFormatter).formatToResponse(responseDto);
    }
}