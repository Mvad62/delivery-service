package ru.liga.deliveryservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.exception.AddressNotFoundException;
import ru.liga.deliveryservice.mapper.AddressMapper;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.repository.AddressRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    public List<AddressResponseDto> findAll(List<String> addressNames) {
        return addressNames.stream()
                .map(this::findAddress)
                .map(addressMapper::mapFromEntityToDto)
                .collect(Collectors.toList());
    }

    private Address findAddress(String name) {
        return addressRepository.findByName(name)
                .orElseThrow(() -> new AddressNotFoundException(String.format("Адрес с названием %s не найден", name)));
    }
}