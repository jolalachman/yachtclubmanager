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

    @GetMapping("/users")
    public ResponseEntity<List<DictionaryResponse>> getUsers() {
        return ResponseEntity.ok(service.getUsers());
    }

}
