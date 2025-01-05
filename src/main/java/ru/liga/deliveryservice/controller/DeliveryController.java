package ru.liga.deliveryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.liga.deliveryservice.model.dto.request.DeliveryRequestDto;
import ru.liga.deliveryservice.model.dto.response.DeliveryResponseDto;
import ru.liga.deliveryservice.service.DeliveryService;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/delivery")
    public ResponseEntity<DeliveryResponseDto> create(@RequestBody @Validated DeliveryRequestDto deliveryRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(deliveryService.create(deliveryRequestDto));
    }
}