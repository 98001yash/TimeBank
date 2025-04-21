package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.EmailRequest;
import com.company.TimeBank.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
@RequiredArgsConstructor
public class EmailController {


    private final EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<ApiResponse<String>>sendTestEmail(@RequestBody EmailRequest emailRequest){
        emailService.sendEmail(emailRequest.getTo(), emailRequest.getSubject(),emailRequest.getBody());

        ApiResponse<String> response = new ApiResponse<>("Email Send successfully");
        return ResponseEntity.ok(response);
    }
}
