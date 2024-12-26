package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.exception.AddressNotFoundException;
import ru.liga.deliveryservice.mapper.DeliveryMapper;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.repository.AddressRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeliveryService {

    private final AddressRepository addressRepository;
    private final LogisticService logisticService;
    private final DeliveryMapper deliveryMapper;

    public DeliveryResponseDto create(DeliveryRequestDto deliveryRequestDto) {
        List<Address> addresses = findAddresses(deliveryRequestDto);
        List<Address> route = logisticService.createShortestRoute(deliveryRequestDto.courierPosition(),
                addresses);

        return deliveryMapper.mapFromEntityToDto(route);
    }

    private List<Address> findAddresses(DeliveryRequestDto deliveryRequestDto) {
        return deliveryRequestDto.addresses()
                .stream()
                .map(name -> addressRepository.findByName(name)
                        .orElseThrow(() -> new AddressNotFoundException(String.format("Адрес с названием %s не найден", name))))
                .toList();
    }
}