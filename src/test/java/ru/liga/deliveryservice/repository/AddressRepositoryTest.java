package ru.liga.deliveryservice.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.liga.deliveryservice.model.entity.Address;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

// Тож сами пишут по идее
@TestPropertySource("classpath:application-test.yml")
@SpringBootTest(properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
@Testcontainers
class AddressRepositoryTest {

    @Container
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.1")
            .withExposedPorts(5432)
            .withDatabaseName("delivery_db_test")
            .withUsername("admin_test")
            .withPassword("hoff_test");

    @Autowired
    private AddressRepository addressRepository;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:" + postgresContainer.getMappedPort(5432) + "/delivery_db_test");
        registry.add("spring.flyway.url", () -> "jdbc:postgresql://localhost:" + postgresContainer.getMappedPort(5432) + "/delivery_db_test");
    }

    @Test
    void testFindByName() {
        Optional<Address> address = addressRepository.findByName("переулок Нечистой Силы");

        assertThat(address.isPresent()).isTrue();
    }

    @Test
    void testSave() {
        Address address = new Address();
        Address savedAddress = addressRepository.save(address);
        assertThat(savedAddress.getId()).isNotNull();
    }
}