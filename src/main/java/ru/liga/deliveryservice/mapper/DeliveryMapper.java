package ru.liga.deliveryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeliveryMapper {

    default DeliveryResponseDto mapFromEntityToDto(List<AddressResponseDto> route) {
        return new DeliveryResponseDto(route);
    }
}