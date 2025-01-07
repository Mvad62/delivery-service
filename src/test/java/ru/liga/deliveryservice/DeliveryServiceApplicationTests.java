package ru.liga.deliveryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.deliveryservice.container.AbstractPostgresContainer;

@SpringBootTest("spring.profiles.active=test")
class DeliveryServiceApplicationTests extends AbstractPostgresContainer {

	@Test
	void contextLoads() {
	}
}