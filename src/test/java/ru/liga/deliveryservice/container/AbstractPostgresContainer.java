package ru.liga.deliveryservice.container;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

// Сами пишут по идее
//@Testcontainers
//@SpringBootTest(properties = "spring.profiles.active=test")
//@ExtendWith(SpringExtension.class)
//public abstract class AbstractIntegrationTest {
//
//    @Container
//    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:17.1")
//            .withExposedPorts(5432)
//            .withDatabaseName("delivery_db_test")
//            .withUsername("admin_test")
//            .withPassword("hoff_test");
//
//    @DynamicPropertySource
//    static void dynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", () -> "jdbc:postgresql://"
//                + postgresContainer.getHost()
//                + ":" + postgresContainer.getMappedPort(5432)
//                + "/delivery_db_test");
//        registry.add("spring.datasource.username", postgresContainer::getUsername);
//        registry.add("spring.datasource.password", postgresContainer::getPassword);
//    }
//}


@Testcontainers
@SpringBootTest(properties = "spring.profiles.active=test")
@ExtendWith(SpringExtension.class)
public abstract class AbstractPostgresContainer {

    final static String POSTGRES_VERSION = "postgres:17.1";
    final static String DATABASE_NAME = "test_db";
    final static String DATABASE_USER = "test_admin";
    final static String DATABASE_PASSWORD = "test_password";

    @Container
    static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>(POSTGRES_VERSION)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USER)
            .withPassword(DATABASE_PASSWORD);

    @DynamicPropertySource
    static void registerPgProperties(DynamicPropertyRegistry registry) {
        String jdbcUrl = "jdbc:postgresql://"
                + postgresContainer.getHost()
                + ":" + postgresContainer.getMappedPort(5432)
                + "/" + postgresContainer.getDatabaseName();

        registry.add("spring.datasource.url", () -> jdbcUrl);
    }
}