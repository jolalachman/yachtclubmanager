package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.UserRequest;
import com.polsl.yachtclubmanager.models.dto.requests.YachtRequest;
import com.polsl.yachtclubmanager.models.dto.responses.UserResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsListResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsResponse;
import com.polsl.yachtclubmanager.services.YachtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/yachts")
@RequiredArgsConstructor
public class YachtsController {
    private final YachtService service;
    @GetMapping
    public ResponseEntity<YachtsListResponse> getYachts() {
        return ResponseEntity.ok(service.getYachts());
    }

    @PostMapping
    public ResponseEntity<Boolean> addYacht(@RequestBody YachtRequest yachtRequest) {
        return ResponseEntity.ok(service.addYacht(yachtRequest));
    }
}
