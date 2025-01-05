package ru.liga.deliveryservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.deliveryservice.exception.AddressNotFoundException;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// Тут можно спринговые тесты, но баффа от них не будет, ток время теряем. spy() поставил на сервис логистики, ибо это логика, это важно
@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private AddressService addressService;

    @Spy
    private LogisticService logisticService;

    @Mock
    private DeliveryMapper deliveryMapper;

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    void create_shouldReturnDeliveryResponseDto_whenValidRequest() {
        Double courierX = 10.0;
        Double courierY = 20.0;
        List<String> addresses = List.of("Address1", "Address2", "Address3");

        CourierPosition courierPosition = new CourierPosition(courierX, courierY);
        DeliveryRequestDto requestDto = new DeliveryRequestDto(courierPosition, addresses);

        AddressResponseDto address1 = new AddressResponseDto("Address1", 10.0, 20.0);
        AddressResponseDto address2 = new AddressResponseDto("Address2", 15.0, 25.0);
        AddressResponseDto address3 = new AddressResponseDto("Address3", 20.0, 30.0);
        List<AddressResponseDto> addressResponses = List.of(address1, address2, address3);

        when(addressService.findAll(addresses)).thenReturn(addressResponses);

        doCallRealMethod().when(logisticService).createShortestRoute(courierPosition, addressResponses);

        List<AddressResponseDto> route = logisticService.createShortestRoute(courierPosition, addressResponses);

        assertThat(route.get(0)).isEqualTo(address1);
        assertThat(route.get(1)).isEqualTo(address2);
        assertThat(route.get(2)).isEqualTo(address3);

        DeliveryResponseDto expectedResponse = new DeliveryResponseDto(route);
        when(deliveryMapper.mapFromEntityToDto(route)).thenReturn(expectedResponse);

        DeliveryResponseDto actualResponse = deliveryService.create(requestDto);

        assertThat(actualResponse).isNotNull();
        assertThat(actualResponse).isEqualTo(expectedResponse);

        verify(addressService).findAll(addresses);
        verify(logisticService, times(2)).createShortestRoute(courierPosition, addressResponses);
        verify(deliveryMapper).mapFromEntityToDto(route);
    }

    @Test
    void create_shouldThrowException_whenAddressServiceFails() {
        Double courierX = 10.0;
        Double courierY = 20.0;
        List<String> addresses = List.of("Non Existence", "Address2", "Address3");

        CourierPosition courierPosition = new CourierPosition(courierX, courierY);
        DeliveryRequestDto requestDto = new DeliveryRequestDto(courierPosition, addresses);

        when(addressService.findAll(addresses)).thenThrow(new AddressNotFoundException(String.format("Адрес с названием %s не найден", "Non Existence")));

        assertThatThrownBy(() -> deliveryService.create(requestDto))
                .isInstanceOf(AddressNotFoundException.class);

        verify(addressService).findAll(addresses);
    }
}