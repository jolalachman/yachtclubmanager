package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ChangeStatusRequest;
import com.polsl.yachtclubmanager.models.dto.requests.EditYachtRequest;
import com.polsl.yachtclubmanager.models.dto.responses.YachtResponse;
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

    @PostMapping
    public ResponseEntity<Boolean> editYacht(@RequestBody EditYachtRequest editYachtRequest) {
        return ResponseEntity.ok(service.editYacht(editYachtRequest));
    }

    @PostMapping("/deactivate")
    public ResponseEntity<Boolean> deactivateYacht(@RequestParam Long yachtId) {
        return ResponseEntity.ok(service.deactivateYacht(yachtId));
    }

    @PostMapping("/change-status")
    public ResponseEntity<Boolean> changeYachtStatus(@RequestBody ChangeStatusRequest changeStatusRequest) {
        return ResponseEntity.ok(service.changeYachtStatus(changeStatusRequest));
    }
}
