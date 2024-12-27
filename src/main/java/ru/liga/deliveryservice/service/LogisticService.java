package ru.liga.deliveryservice.service;

import org.springframework.stereotype.Service;
import ru.liga.deliveryservice.model.entity.Address;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class LogisticService {

    public List<Address> createShortestRoute(CourierPosition currentPosition, List<Address> addresses) {
        List<Address> route = new ArrayList<>();

        List<Address> remainingAddresses = new ArrayList<>(addresses);

        while (!remainingAddresses.isEmpty()) {
            Address nearestAddress = findNearestAddress(currentPosition, remainingAddresses);

            route.add(nearestAddress);

            currentPosition = new CourierPosition(nearestAddress.getX(), nearestAddress.getY());

            //эт дропнем
            remainingAddresses.remove(nearestAddress);
        }

        return route;
    }

    private Address findNearestAddress(CourierPosition currentPosition, List<Address> addresses) {
        return addresses.stream()
                .min(Comparator.comparingDouble(address -> calculateDistance(
                        currentPosition.x(), currentPosition.y(),
                        address.getX(), address.getY())))
                .orElseThrow(() -> new IllegalArgumentException("Список адресов не может быть пустым"));
    }

    private double calculateDistance(Double x1, Double y1, Double x2, Double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}