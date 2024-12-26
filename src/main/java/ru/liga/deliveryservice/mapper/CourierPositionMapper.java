package ru.liga.deliveryservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.liga.deliveryservice.model.entity.CourierPosition;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CourierPositionMapper {

    CourierPosition mapFromPointsToEntity(Double x, Double y);
}
