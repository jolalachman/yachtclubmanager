package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.YachtRequest;
import com.polsl.yachtclubmanager.models.dto.responses.YachtResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsListResponse;
import com.polsl.yachtclubmanager.models.dto.responses.YachtsResponse;
import com.polsl.yachtclubmanager.services.YachtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/yacht")
@RequiredArgsConstructor
public class YachtController {
    private final YachtService service;
    @GetMapping
    public ResponseEntity<YachtResponse> getYacht(@RequestParam Long yachtId) {
        return ResponseEntity.ok(service.getYacht(yachtId));
    }

}
