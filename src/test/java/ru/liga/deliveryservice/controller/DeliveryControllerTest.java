package ru.liga.deliveryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.deliveryservice.container.AbstractIntegrationTest;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


// Сами пишут по идее
@SpringBootTest(properties = "spring.profiles.active=test")
@AutoConfigureMockMvc
class DeliveryControllerTest extends AbstractIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    void create_withValidRequest_shouldReturnValidResponse() throws Exception {
        Double courierX = 10.0;
        Double courierY = 20.0;
        List<String> addresses = List.of(
                "переулок Нечистой Силы",
                "проспект Непутевых Надежд",
                "улица Абсолютной Нелепости"
        );

        CourierPosition courierPosition = new CourierPosition(courierX, courierY);
        DeliveryRequestDto requestDto = new DeliveryRequestDto(courierPosition, addresses);

        List<AddressResponseDto> expectedResponseAddresses = List.of(
                new AddressResponseDto("улица Абсолютной Нелепости", 98.6543, 100.4321),
                new AddressResponseDto("переулок Нечистой Силы", 100.0142, 100.8712),
                new AddressResponseDto("проспект Непутевых Надежд", 115.8765, 119.5432)
        );

        DeliveryResponseDto expectedResponseDto = new DeliveryResponseDto(expectedResponseAddresses);

        mockMvc.perform(post("/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResponseDto)));
    }
}