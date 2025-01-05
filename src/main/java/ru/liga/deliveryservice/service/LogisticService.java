package ru.liga.deliveryservice.service;

import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LogisticService {

    public List<AddressResponseDto> createShortestRoute(CourierPosition courierPosition, List<AddressResponseDto> addresses) {
        List<AddressResponseDto> route = new ArrayList<>();

        List<AddressResponseDto> remainingAddresses = new ArrayList<>(addresses);

        while (!remainingAddresses.isEmpty()) {
            AddressResponseDto nearestAddress = findNearestAddress(courierPosition, remainingAddresses);

            route.add(nearestAddress);

            courierPosition = new CourierPosition(nearestAddress.x(), nearestAddress.y());

            // удалить для лабы
            remainingAddresses.remove(nearestAddress);
        }

        return route;
    }

    private AddressResponseDto findNearestAddress(CourierPosition currentPosition, List<AddressResponseDto> addresses) {
        return addresses.stream()
                .min(Comparator.comparingDouble(address -> calculateDistance(
                        currentPosition.x(), currentPosition.y(),
                        address.x(), address.y())))
                .orElseThrow(() -> new IllegalArgumentException("Список адресов не может быть пустым"));
    }

    private double calculateDistance(Double x1, Double y1, Double x2, Double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}