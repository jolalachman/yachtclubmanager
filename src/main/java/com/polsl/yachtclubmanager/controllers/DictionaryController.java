package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.responses.DictionaryResponse;
import com.polsl.yachtclubmanager.services.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/dictionary")
@RequiredArgsConstructor
public class DictionaryController {
    private final DictionaryService service;

    @GetMapping("/yachts")
    public ResponseEntity<List<DictionaryResponse>> getYachts() {
        return ResponseEntity.ok(service.getYachts());
    }

    @GetMapping("/all-yachts")
    public ResponseEntity<List<DictionaryResponse>> getAllYachts() {
        return ResponseEntity.ok(service.getAllYachts());
    }

    @GetMapping("/all-reservations")
    public ResponseEntity<List<DictionaryResponse>> getAllReservations() {
        return ResponseEntity.ok(service.getAllReservations());
    }

    @GetMapping("/users")
    public ResponseEntity<List<DictionaryResponse>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

    @GetMapping("/club-members")
    public ResponseEntity<List<DictionaryResponse>> getClubMembers() {
        return ResponseEntity.ok(service.getClubMembers());
    }

    @GetMapping("/reserving-users")
    public ResponseEntity<List<DictionaryResponse>> getReservingUsers() {
        return ResponseEntity.ok(service.getReservingUsers());
    }

    @GetMapping("/notice-statuses")
    public ResponseEntity<List<DictionaryResponse>> getNoticeStatuses() {
        return ResponseEntity.ok(service.getNoticeStatuses());
    }

}
