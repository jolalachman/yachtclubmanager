package com.polsl.yachtclubmanager.controllers;

import com.polsl.yachtclubmanager.models.dto.requests.ChangeNoticeStatusRequest;
import com.polsl.yachtclubmanager.models.dto.requests.EditNoticeRequest;
import com.polsl.yachtclubmanager.models.dto.responses.NoticeResponse;
import com.polsl.yachtclubmanager.models.dto.responses.NoticesListResponse;
import com.polsl.yachtclubmanager.models.dto.responses.NoticesResponse;
import com.polsl.yachtclubmanager.models.dto.responses.UserDetailsResponse;
import com.polsl.yachtclubmanager.services.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/details")
    public ResponseEntity<NoticeResponse> getNoticeDetails(@RequestParam String noticeId) {
        return ResponseEntity.ok(service.getNoticeDetails(noticeId));
    }
    @PostMapping("/edit")
    public ResponseEntity<Boolean> getNoticeDetails(@RequestBody EditNoticeRequest editNoticeRequest) {
        return ResponseEntity.ok(service.editNotice(editNoticeRequest));
    }

    @PostMapping("/change-status")
    public ResponseEntity<Boolean> changeNoticeStatus(@RequestBody ChangeNoticeStatusRequest request) {
        return ResponseEntity.ok(service.changeNoticeStatus(request));
    }
}
