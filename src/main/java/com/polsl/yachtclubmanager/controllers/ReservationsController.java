package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ChangeStatusRequest;
import com.polsl.yachtclubmanager.models.dto.requests.ReportReservationNoticeRequest;
import com.polsl.yachtclubmanager.models.dto.requests.ReservationRequest;
import com.polsl.yachtclubmanager.models.dto.responses.*;
import com.polsl.yachtclubmanager.services.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationsController {
    private final ReservationService service;
    @GetMapping
    public ResponseEntity<ReservationsListResponse> getReservations() {
        return ResponseEntity.ok(service.getReservations());
    }

    @GetMapping("/my")
    public ResponseEntity<MyReservationsListResponse> getMyReservations(@RequestParam String userId) {
        return ResponseEntity.ok(service.getMyReservations(userId));
    }

    @GetMapping("/details")
    public ResponseEntity<ReservationDetailsResponse> getReservationDetails(@RequestParam String reservationId) {
        return ResponseEntity.ok(service.getReservationDetails(reservationId));
    }

    @PostMapping
    public ResponseEntity<Boolean> addReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(service.addReservation(reservationRequest));
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addYachtReservation(@RequestBody ReservationRequest reservationRequest) {
        return ResponseEntity.ok(service.addYachtReservation(reservationRequest));
    }

    @GetMapping("/history")
    public ResponseEntity<List<ReservationStatusHistoryResponse>> getReservationStatusHistory(@RequestParam Long reservationId) {
        return ResponseEntity.ok(service.getReservationStatusHistory(reservationId));
    }

    @PostMapping("/report-notice")
    public ResponseEntity<Boolean> reportReservationNotice(@RequestBody ReportReservationNoticeRequest request) {
        return ResponseEntity.ok(service.reportReservationNotice(request));
    }

    @GetMapping("/reported-notice")
    public ResponseEntity<ReservationNoticeResponse> getReservationNotice(@RequestParam String reservationId) {
        return ResponseEntity.ok(service.getReservationNotice(reservationId));
    }

    @PostMapping("/change-status")
    public ResponseEntity<Boolean> changeReservationStatus(@RequestBody ChangeStatusRequest request) {
        return ResponseEntity.ok(service.changeReservationStatus(request));
    }

    @PostMapping("/give")
    public ResponseEntity<Boolean> giveReservation(@RequestParam String reservationId) {
        return ResponseEntity.ok(service.giveReservation(reservationId));
    }

    @PostMapping("/complete")
    public ResponseEntity<Boolean> completeReservation(@RequestParam String reservationId) {
        return ResponseEntity.ok(service.completeReservation(reservationId));
    }
}
