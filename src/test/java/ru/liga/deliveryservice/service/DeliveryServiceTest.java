package ru.liga.deliveryservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.deliveryservice.exception.AddressNotFoundException;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.model.entity.CourierPosition;
import ru.liga.deliveryservice.repository.AddressRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DeliveryServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private LogisticService logisticService;

    @Mock
    private DeliveryMapper deliveryMapper;

    @InjectMocks
    private DeliveryService deliveryService;

    @Test
    public void shouldCreateDeliveryResponseDto() {
        CourierPosition courierPosition = new CourierPosition(10.0, 20.0);
        List<String> addresses = List.of("Address1", "Address2", "Address3");
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto(courierPosition, addresses);

        Address address1 = new Address(1, "Address1", 10.0, 20.0);
        Address address2 = new Address(2, "Address2", 20.0, 30.0);
        Address address3 = new Address(3, "Address3", 30.0, 40.0);

        when(addressRepository.findByName("Address1")).thenReturn(Optional.of(address1));
        when(addressRepository.findByName("Address2")).thenReturn(Optional.of(address2));
        when(addressRepository.findByName("Address3")).thenReturn(Optional.of(address3));

        List<Address> route = List.of(address1, address2, address3);
        when(logisticService.createShortestRoute(courierPosition, List.of(address1, address2, address3)))
                .thenReturn(route);

        DeliveryResponseDto deliveryResponseDto = new DeliveryResponseDto(route);
        when(deliveryMapper.mapFromEntityToDto(route)).thenReturn(deliveryResponseDto);

        DeliveryResponseDto result = deliveryService.create(deliveryRequestDto);

        assertThat(result).isNotNull();
        assertThat(result.addresses()).containsExactlyElementsOf(route);

        verify(addressRepository).findByName("Address1");
        verify(addressRepository).findByName("Address2");
        verify(addressRepository).findByName("Address3");

        verify(logisticService).createShortestRoute(courierPosition, List.of(address1, address2, address3));

        verify(deliveryMapper).mapFromEntityToDto(route);
    }

    @Test
    public void shouldThrowExceptionWhenAddressNotFound() {
        CourierPosition courierPosition = new CourierPosition(10.0, 20.0);
        List<String> addresses = List.of("Address1");
        DeliveryRequestDto deliveryRequestDto = new DeliveryRequestDto(courierPosition, addresses);

        when(addressRepository.findByName("Address1")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> deliveryService.create(deliveryRequestDto))
                .isInstanceOf(AddressNotFoundException.class)
                .hasMessage("Адрес с названием Address1 не найден");

        verify(addressRepository).findByName("Address1");
    }
}