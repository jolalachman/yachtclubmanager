package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.ReservationsListResponse;
import com.polsl.yachtclubmanager.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationsController {
    private final ReservationService service;
    @GetMapping
    public ResponseEntity<ReservationsListResponse> getReservations() {
        return ResponseEntity.ok(service.getReservations());
    }

    @PostMapping
    public ResponseEntity<Boolean> addReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(service.addReservation(reservationRequest));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addYachtReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(service.addYachtReservation(reservationRequest));
    }
}
