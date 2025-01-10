package ru.liga.deliveryservice.service;

import org.junit.jupiter.api.Test;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// Надо убрать будто этот тест
class LogisticServiceTest {

    @Test
    void createShortestRoute_withValidInput_shouldReturnValidShortestRoute() {
        LogisticService logisticService = new LogisticService();
        CourierPosition courierPosition = new CourierPosition(0.0, 0.0);
        AddressResponseDto address1 = new AddressResponseDto("Address1", 2.0, 2.0);
        AddressResponseDto address2 = new AddressResponseDto("Address2", 1.0, 1.0);
        AddressResponseDto address3 = new AddressResponseDto("Address3", 3.0, 3.0);

        List<AddressResponseDto> addresses = List.of(address1, address2, address3);

        List<AddressResponseDto> result = logisticService.createShortestRoute(courierPosition, addresses);

        assertThat(result).containsExactly(address2, address1, address3);
    }
}