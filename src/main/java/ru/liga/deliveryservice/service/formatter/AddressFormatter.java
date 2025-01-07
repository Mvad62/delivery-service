package ru.liga.deliveryservice.service.formatter;

import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressFormatter {

    public String toString(List<AddressResponseDto> route) {
        return route.stream()
                .map(AddressResponseDto::name)
                .collect(Collectors.joining(" -> "));
    }
}