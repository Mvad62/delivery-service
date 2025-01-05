package ru.liga.deliveryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.AddressResponseDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.model.entity.CourierPosition;
import ru.liga.deliveryservice.service.DeliveryService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// Этого класса не будет по идее, его сами пишут
@SpringBootTest
@AutoConfigureMockMvc
class DeliveryControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockitoBean
    DeliveryService deliveryService;

    @Test
    void create_shouldReturnValidResponse_withValidRequest() throws Exception {
        Double courierX = 10.0;
        Double courierY = 20.0;
        List<String> addresses = List.of("Address1", "Address2", "Address3");

        CourierPosition courierPosition = new CourierPosition(courierX, courierY);
        DeliveryRequestDto requestDto = new DeliveryRequestDto(courierPosition, addresses);

        List<AddressResponseDto> responseAddresses = List.of(
                new AddressResponseDto("Address1", 10.0, 20.0),
                new AddressResponseDto("Address2", 15.0, 25.0),
                new AddressResponseDto("Address3", 20.0, 30.0)
        );

        DeliveryResponseDto responseDto = new DeliveryResponseDto(responseAddresses);

        when(deliveryService.create(any(DeliveryRequestDto.class))).thenReturn(responseDto);

        mockMvc.perform(post("/delivery")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().json(objectMapper.writeValueAsString(responseDto)));

        verify(deliveryService).create(any(DeliveryRequestDto.class));
    }
}