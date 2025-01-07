package ru.liga.deliveryservice.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.liga.deliveryservice.exception.AddressNotFoundException;
import ru.liga.deliveryservice.mapper.AddressMapper;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.container.AddressRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

// Будто когда есть интеграционный тест на контроллере - толку от интеграционного в сервисе, который вызовет контроллер = 0
@ExtendWith(MockitoExtension.class)
class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private AddressMapper addressMapper;

    @InjectMocks
    private AddressService addressService;

    @Test
    void findByName_withValidInput_shouldReturnAllAddresses() {
        String addressName1 = "Address1";
        String addressName2 = "Address2";

        Address address1 = new Address(1, addressName1, 1.0, 1.0);
        Address address2 = new Address(2, addressName2, 2.0, 2.0);

        AddressResponseDto addressDto1 = new AddressResponseDto(addressName1, 1.0, 1.0);
        AddressResponseDto addressDto2 = new AddressResponseDto(addressName2, 2.0, 2.0);

        when(addressRepository.findByName(addressName1)).thenReturn(Optional.of(address1));
        when(addressRepository.findByName(addressName2)).thenReturn(Optional.of(address2));
        when(addressMapper.mapFromEntityToDto(address1)).thenReturn(addressDto1);
        when(addressMapper.mapFromEntityToDto(address2)).thenReturn(addressDto2);

        List<String> addressNames = List.of(addressName1, addressName2);

        List<AddressResponseDto> result = addressService.findAll(addressNames);

        assertThat(result).containsExactly(addressDto1, addressDto2);

        verify(addressRepository).findByName(addressName1);
        verify(addressRepository).findByName(addressName2);
        verify(addressMapper).mapFromEntityToDto(address1);
        verify(addressMapper).mapFromEntityToDto(address2);
    }

    @Test
    void findByName_withNonExistenceAddress_shouldThrowAddressNotFoundException() {
        String missingAddressName = "MissingAddress";
        when(addressRepository.findByName(missingAddressName)).thenReturn(Optional.empty());

        List<String> addressNames = List.of(missingAddressName);

        assertThatThrownBy(() -> addressService.findAll(addressNames))
                .isInstanceOf(AddressNotFoundException.class);

        verify(addressRepository, times(1)).findByName(missingAddressName);
        verifyNoInteractions(addressMapper);
    }
}