package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.service.formatter.AddressFormatter;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final AddressService addressService;
    private final LogisticService logisticService;
    private final DeliveryMapper deliveryMapper;
    private final AddressFormatter addressFormatter;
    private final MessageSendingService messageSendingService;

    public DeliveryResponseDto create(DeliveryRequestDto deliveryRequestDto) {
        List<AddressResponseDto> addresses = addressService.findAll(deliveryRequestDto.addresses());
        List<AddressResponseDto> route = logisticService.createShortestRoute(deliveryRequestDto.courierPosition(),
                addresses);

        String routeString = addressFormatter.toString(route);
        messageSendingService.send(routeString);

        return deliveryMapper.mapFromEntityToDto(route);
    }
}