package ru.liga.deliveryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DeliveryMapper {

    DeliveryRequestDto mapFromParamsToDto(CourierPosition courierPosition, List<String> addresses);

    default DeliveryResponseDto mapFromEntityToDto(List<Address> route) {
        return new DeliveryResponseDto(route);
    }
}