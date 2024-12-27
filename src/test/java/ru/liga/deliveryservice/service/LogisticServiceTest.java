package ru.liga.deliveryservice.service;

import org.junit.jupiter.api.Test;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LogisticServiceTest {

    @Test
    public void shouldCreateShortestRoute() {
        LogisticService logisticService = new LogisticService();
        CourierPosition courierPosition = new CourierPosition(0.0, 0.0);
        Address address1 = new Address(1, "Address1", 1.0, 1.0);
        Address address2 = new Address(2, "Address2", 2.0, 2.0);
        Address address3 = new Address(3, "Address3", 3.0, 3.0);

        List<Address> addresses = List.of(address1, address2, address3);

        List<Address> result = logisticService.createShortestRoute(courierPosition, addresses);

        assertThat(result).isEqualTo(List.of(address1, address2, address3));
        assertThat(result).hasSize(3);
        assertThat(result.get(0)).isEqualTo(address1);
        assertThat(result.get(1)).isEqualTo(address2);
        assertThat(result.get(2)).isEqualTo(address3);
    }
}