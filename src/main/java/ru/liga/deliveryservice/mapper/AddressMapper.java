package ru.liga.deliveryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.entity.Address;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AddressMapper {

    AddressResponseDto mapFromEntityToDto(Address address);
}