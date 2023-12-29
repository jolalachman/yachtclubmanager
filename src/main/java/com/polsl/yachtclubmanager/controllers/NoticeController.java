package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.responses.NoticesListResponse;
import com.polsl.yachtclubmanager.services.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notices")
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService service;
    @GetMapping("/all")
    public ResponseEntity<NoticesListResponse> getAllNotices() {
        return ResponseEntity.ok(service.getAllNotices());
    }

}
