package ru.liga.deliveryservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.liga.deliveryservice.container.AbstractIntegrationTest;

@SpringBootTest("spring.profiles.active=test")
class DeliveryServiceApplicationTests extends AbstractIntegrationTest {

	@Test
	void contextLoads() {
	}
}