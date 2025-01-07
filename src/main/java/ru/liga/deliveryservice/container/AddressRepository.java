package ru.liga.deliveryservice.container;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.liga.deliveryservice.model.entity.Address;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByName(String name);
}