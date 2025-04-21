package com.company.TimeBank.controller;


import com.company.TimeBank.advices.ApiError;
import com.company.TimeBank.advices.ApiResponse;
import com.company.TimeBank.dtos.TimeBankRequestDto;
import com.company.TimeBank.dtos.TimeBankResponseDto;
import com.company.TimeBank.entities.TimeBank;
import com.company.TimeBank.exceptions.ResourceNotFoundException;
import com.company.TimeBank.service.TimeBankService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/timebank")
@RequiredArgsConstructor
public class TimeBankController {

    private final TimeBankService timeBankService;


    @PostMapping
    public ResponseEntity<TimeBankResponseDto> createTransaction(@RequestBody TimeBankRequestDto requestDto){
        TimeBankResponseDto response = timeBankService.createBarterTransaction(requestDto);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<TimeBankResponseDto> getBarterTransactionById(@PathVariable Long id){
        TimeBankResponseDto response = timeBankService.getBarterTransactionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TimeBankResponseDto>>> getAllBarterTransactions() {
        List<TimeBankResponseDto> transactions = timeBankService.getAllBarterTransactions();
        return ResponseEntity.ok(new ApiResponse<>(transactions));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TimeBankResponseDto>> updateBarterTransaction(
            @PathVariable Long id,
            @RequestBody TimeBankRequestDto requestDto
    ) {
        TimeBankResponseDto updatedTransaction = timeBankService.updateBarterTransaction(id, requestDto);
        return ResponseEntity.ok(new ApiResponse<>(updatedTransaction));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteBarterTransaction(@PathVariable Long id) {
        timeBankService.deleteTransaction(id);
        return ResponseEntity.ok(new ApiResponse<>("Barter transaction deleted successfully"));
    }


    @PutMapping("/{barterId}/accept")
    public ResponseEntity<ApiResponse<TimeBankResponseDto>> acceptBarter(
            @PathVariable Long barterId,
            @RequestParam Long userId
    ) {
        try {
            TimeBankResponseDto response = timeBankService.acceptBarterRequest(barterId, userId);
            return ResponseEntity.ok(new ApiResponse<>(response));
        } catch (IllegalStateException e) {
            // Create ApiError and pass it to ApiResponse
            ApiError error = ApiError.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .message("This barter is not available for acceptance")
                    .subErrors(List.of(e.getMessage()))  // List of detailed error messages
                    .build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(error));
        } catch (ResourceNotFoundException e) {
            // Create ApiError and pass it to ApiResponse
            ApiError error = ApiError.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("User or Barter not found")
                    .subErrors(List.of(e.getMessage()))  // List of detailed error messages
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(error));
        } catch (Exception e) {
            // Create ApiError and pass it to ApiResponse
            ApiError error = ApiError.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .message("An unexpected error occurred")
                    .subErrors(List.of(e.getMessage()))  // List of detailed error messages
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(error));
        }
    }

    @PostMapping("/{barterId}/complete/{userId}")
    public ResponseEntity<TimeBankResponseDto> completeBarter(@PathVariable Long barterId, @PathVariable Long userId) {
        TimeBankResponseDto response = timeBankService.completeBarterTransaction(barterId, userId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{barterId}/reject")
    public ResponseEntity<ApiResponse<TimeBankResponseDto>> rejectBarterTransaction(
            @PathVariable Long barterId,
            @RequestParam Long userId
    ){
        TimeBankResponseDto response = timeBankService.rejectBarterTransaction(barterId, userId);
        return ResponseEntity.ok(new ApiResponse<>(response));
    }

    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<List<TimeBankResponseDto>>> getBarterHistory(@PathVariable Long userId) {
        List<TimeBankResponseDto> history = timeBankService.getBarterHistoryForUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(history));
    }
}
