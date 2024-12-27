package ru.liga.deliveryservice.formatter;

import org.junit.jupiter.api.Test;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DeliveryFormatterTest {

    private final DeliveryFormatter deliveryFormatter = new DeliveryFormatter();

    @Test
    void formatToResponse_shouldFormatAddressesCorrectly() {
        List<Address> addresses = List.of(
                new Address(1, "Address1", 10.0, 20.0),
                new Address(2, "Address2", 15.0, 25.0),
                new Address(3, "Address3", 20.0, 30.0)
        );
        DeliveryResponseDto responseDto = new DeliveryResponseDto(addresses);

        String result = deliveryFormatter.formatToResponse(responseDto);

        assertThat(result).isEqualTo("Вам надо ехать по адресам: Address1; Address2; Address3");
    }

    @Test
    void formatToResponse_shouldHandleEmptyAddresses() {
        DeliveryResponseDto responseDto = new DeliveryResponseDto(List.of());

        String result = deliveryFormatter.formatToResponse(responseDto);

        assertThat(result).isEqualTo("Вам надо ехать по адресам: ");
    }

    @Test
    void formatToResponse_shouldHandleSingleAddress() {
        List<Address> addresses = List.of(new Address(1, "SingleAddress", 10.0, 20.0));
        DeliveryResponseDto responseDto = new DeliveryResponseDto(addresses);

        String result = deliveryFormatter.formatToResponse(responseDto);

        assertThat(result).isEqualTo("Вам надо ехать по адресам: SingleAddress");
    }

}