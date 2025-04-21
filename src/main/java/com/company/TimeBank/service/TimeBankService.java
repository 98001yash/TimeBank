package com.company.TimeBank.service;


import com.company.TimeBank.dtos.TimeBankRequestDto;
import com.company.TimeBank.dtos.TimeBankResponseDto;
import com.company.TimeBank.entities.TimeBank;
import com.company.TimeBank.entities.User;
import com.company.TimeBank.enums.BarterStatus;
import com.company.TimeBank.exceptions.ResourceNotFoundException;
import com.company.TimeBank.repository.TimeBankRepository;
import com.company.TimeBank.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class TimeBankService {

    private final TimeBankRepository timeBankRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    public TimeBankResponseDto createBarterTransaction(TimeBankRequestDto requestDto){
        try {
            log.info("Creating new barter transaction....");

            User fromUser = userRepository.findById(requestDto.getFromUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getFromUserId()));

            User toUser = userRepository.findById(requestDto.getToUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getToUserId()));

            TimeBank timeBank = new TimeBank();
            timeBank.setFromUser(fromUser);
            timeBank.setToUser(toUser);
            timeBank.setSkillOffered(requestDto.getSkillOffered());
            timeBank.setSkillRequired(requestDto.getSkillRequired());
            timeBank.setTimeExchanged(requestDto.getTimeExchanged());
            timeBank.setStatus(BarterStatus.PENDING);
            timeBank.setCreatedAt(LocalDateTime.now());
            timeBank.setUpdatedAt(LocalDateTime.now());

            TimeBank saved = timeBankRepository.save(timeBank);
            log.info("Barter transaction created successfully with ID: {}", saved.getId());


            TimeBankResponseDto responseDto = new TimeBankResponseDto();
            responseDto.setId(saved.getId());
            responseDto.setFromUserId(saved.getFromUser().getId());
            responseDto.setToUserId(saved.getToUser().getId());
            responseDto.setFromUserName(saved.getFromUser().getName());
            responseDto.setToUserName(saved.getToUser().getName());
            responseDto.setSkillOffered(saved.getSkillOffered());
            responseDto.setSkillRequired(saved.getSkillRequired());
            responseDto.setTimeExchanged(saved.getTimeExchanged());
            responseDto.setStatus(saved.getStatus());
            responseDto.setCreatedAt(saved.getCreatedAt());
            responseDto.setUpdatedAt(saved.getUpdatedAt());

            return responseDto;

        } catch (Exception e) {
            log.error("Error occurred while creating barter transaction: {}", e.getLocalizedMessage());
            throw new RuntimeException("Failed to create barter transaction", e);
        }
    }



    public TimeBankResponseDto getBarterTransactionById(Long id) {
        try {
            log.info("Fetching barter transaction with ID: {}", id);

            TimeBank timeBank = timeBankRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("TimeBank transaction not found with ID: " + id));

            TimeBankResponseDto responseDto = new TimeBankResponseDto();
            responseDto.setId(timeBank.getId());
            responseDto.setFromUserId(timeBank.getFromUser().getId());
            responseDto.setToUserId(timeBank.getToUser().getId());
            responseDto.setFromUserName(timeBank.getFromUser().getName());
            responseDto.setToUserName(timeBank.getToUser().getName());
            responseDto.setSkillOffered(timeBank.getSkillOffered());
            responseDto.setSkillRequired(timeBank.getSkillRequired());
            responseDto.setTimeExchanged(timeBank.getTimeExchanged());
            responseDto.setStatus(timeBank.getStatus());
            responseDto.setCreatedAt(timeBank.getCreatedAt());
            responseDto.setUpdatedAt(timeBank.getUpdatedAt());

            log.info("Successfully fetched barter transaction with ID: {}", id);
            return responseDto;

        } catch (Exception e) {
            log.error("Error occurred while fetching barter transaction with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to fetch barter transaction", e);
        }
    }



        public List<TimeBankResponseDto> getAllBarterTransactions(){
            try{
                log.info("Fetching all barter transactions...");

                List<TimeBank> transactions = timeBankRepository.findAll();
                List<TimeBankResponseDto> responseList = transactions.stream().map(txn -> {
                    TimeBankResponseDto dto = new TimeBankResponseDto();
                    dto.setId(txn.getId());
                    dto.setFromUserId(txn.getFromUser().getId());
                    dto.setToUserId(txn.getToUser().getId());
                    dto.setFromUserName(txn.getFromUser().getName());
                    dto.setToUserName(txn.getToUser().getName());
                    dto.setSkillOffered(txn.getSkillOffered());
                    dto.setSkillRequired(txn.getSkillRequired());
                    dto.setTimeExchanged(txn.getTimeExchanged());
                    dto.setStatus(txn.getStatus());
                    dto.setCreatedAt(txn.getCreatedAt());
                    dto.setUpdatedAt(txn.getUpdatedAt());
                    return dto;
                }).toList();

                return responseList;
            }catch(Exception e){
                log.error("Error occurred while fetching all barter transactions: {}",e.getMessage());
                throw new RuntimeException("failed to fetch all barter transactions",e);
            }
        }


    public TimeBankResponseDto updateBarterTransaction(Long id, TimeBankRequestDto requestDto) {
        try {
            log.info("Updating barter transaction with ID: {}", id);

            TimeBank existing = timeBankRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Transaction not found with ID: " + id));

            User fromUser = userRepository.findById(requestDto.getFromUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getFromUserId()));

            User toUser = userRepository.findById(requestDto.getToUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + requestDto.getToUserId()));

            existing.setFromUser(fromUser);
            existing.setToUser(toUser);
            existing.setSkillOffered(requestDto.getSkillOffered());
            existing.setSkillRequired(requestDto.getSkillRequired());
            existing.setTimeExchanged(requestDto.getTimeExchanged());
            existing.setStatus(requestDto.getStatus());
            existing.setUpdatedAt(LocalDateTime.now());

            TimeBank updated = timeBankRepository.save(existing);

            TimeBankResponseDto responseDto = new TimeBankResponseDto();
            responseDto.setId(updated.getId());
            responseDto.setFromUserId(fromUser.getId());
            responseDto.setToUserId(toUser.getId());
            responseDto.setFromUserName(fromUser.getName());
            responseDto.setToUserName(toUser.getName());
            responseDto.setSkillOffered(updated.getSkillOffered());
            responseDto.setSkillRequired(updated.getSkillRequired());
            responseDto.setTimeExchanged(updated.getTimeExchanged());
            responseDto.setStatus(updated.getStatus());
            responseDto.setCreatedAt(updated.getCreatedAt());
            responseDto.setUpdatedAt(updated.getUpdatedAt());

            log.info("Updated barter transaction with ID: {}", id);
            return responseDto;

        } catch (Exception e) {
            log.error("Error occurred while updating barter transaction with ID {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update barter transaction", e);
        }
    }


    public void deleteTransaction(Long id){
        try{
            log.info("Deleting barter transaction with ID: {}",id);
            TimeBank transaction = timeBankRepository.findById(id)
                    .orElseThrow(()->new ResourceNotFoundException("Transaction not found with ID: "+id));

            timeBankRepository.delete(transaction);
            log.info("Barter transaction with ID: {} deleted successfully",id);
        }catch(Exception e){
            log.error("Error occurred while deleting barter transaction with ID {}: {}",id,e.getMessage());
            throw new RuntimeException("Failed to delete barter transaction",e);
        }
    }

    public TimeBankResponseDto acceptBarterRequest(Long barterId, Long userId) {
        TimeBank barter = timeBankRepository.findById(barterId)
                .orElseThrow(() -> new ResourceNotFoundException("Barter not found with ID: " + barterId));

        if (barter.getStatus() != BarterStatus.PENDING) {
            throw new IllegalStateException("This barter is not available for acceptance");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));

        barter.setAcceptedBy(user);
        barter.setStatus(BarterStatus.COMPLETED);

        TimeBank updatedBarter = timeBankRepository.save(barter);

        TimeBankResponseDto dto = new TimeBankResponseDto();
        dto.setId(updatedBarter.getId());


        if (updatedBarter.getFromUser() != null) {
            dto.setFromUserId(updatedBarter.getFromUser().getId());
        }
        if (updatedBarter.getToUser() != null) {
            dto.setToUserId(updatedBarter.getToUser().getId());
        }

        dto.setSkillOffered(updatedBarter.getSkillOffered());
        dto.setSkillRequired(updatedBarter.getSkillRequired());
        dto.setTimeExchanged(updatedBarter.getTimeExchanged());
        dto.setStatus(updatedBarter.getStatus());
        dto.setCreatedAt(updatedBarter.getCreatedAt());
        dto.setUpdatedAt(updatedBarter.getUpdatedAt());

        updatedBarter.populateUserNames();
        dto.setFromUserName(updatedBarter.getFromUserName());
        dto.setToUserName(updatedBarter.getToUserName());

        return dto;
    }




    public TimeBankResponseDto completeBarterTransaction(Long barterId, Long userId) {
        TimeBank transaction = timeBankRepository.findById(barterId)
                .orElseThrow(() -> new ResourceNotFoundException("Barter not found with ID: " + barterId));

        if (!transaction.getFromUser().getId().equals(userId) && !transaction.getToUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Only the participants in the barter can complete the transaction.");
        }

        if (transaction.getStatus() != BarterStatus.PENDING) {
            throw new IllegalStateException("Barter must be in the 'PENDING' status to be completed.");
        }

        transaction.setStatus(BarterStatus.COMPLETED);
        TimeBank saved = timeBankRepository.save(transaction);

        TimeBankResponseDto responseDto = new TimeBankResponseDto();

        responseDto.setId(saved.getId());
        responseDto.setFromUserId(saved.getFromUser() != null ? saved.getFromUser().getId() : null);
        responseDto.setToUserId(saved.getToUser() != null ? saved.getToUser().getId() : null);
        responseDto.setSkillOffered(saved.getSkillOffered());
        responseDto.setSkillRequired(saved.getSkillRequired());
        responseDto.setTimeExchanged(saved.getTimeExchanged());
        responseDto.setStatus(saved.getStatus());
        responseDto.setCreatedAt(saved.getCreatedAt());
        responseDto.setUpdatedAt(saved.getUpdatedAt());

        // Manually set the user names
        responseDto.setFromUserName(saved.getFromUser() != null ? saved.getFromUser().getName() : null);
        responseDto.setToUserName(saved.getToUser() != null ? saved.getToUser().getName() : null);

        return responseDto;
    }


    public TimeBankResponseDto rejectBarterTransaction(Long barterId, Long userId) {
        TimeBank transaction = timeBankRepository.findById(barterId)
                .orElseThrow(() -> new ResourceNotFoundException("Barter not found with ID: " + barterId));

        if (!transaction.getToUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Only the receiver can reject the barter.");
        }

        if (transaction.getStatus() != BarterStatus.PENDING) {
            throw new IllegalStateException("Barter must be in the 'PENDING' status to be rejected");
        }

        transaction.setStatus(BarterStatus.CANCELLED);

        TimeBank saved = timeBankRepository.save(transaction);

        TimeBankResponseDto responseDto = modelMapper.map(saved, TimeBankResponseDto.class);

        saved.populateUserNames();
        responseDto.setFromUserName(saved.getFromUserName());
        responseDto.setToUserName(saved.getToUserName());

        return responseDto;
    }

    public List<TimeBankResponseDto> getBarterHistoryForUser(Long userId) {
        List<TimeBank> transactions = timeBankRepository.findByFromUserIdOrToUserId(userId, userId);

        return transactions.stream().map(transaction -> {
            TimeBankResponseDto dto = new TimeBankResponseDto();

            dto.setId(transaction.getId());
            dto.setFromUserId(transaction.getFromUser().getId());
            dto.setToUserId(transaction.getToUser().getId());
            dto.setFromUserName(transaction.getFromUser().getName()); // or getUsername()
            dto.setToUserName(transaction.getToUser().getName());     // or getUsername()
            dto.setSkillOffered(transaction.getSkillOffered());
            dto.setSkillRequired(transaction.getSkillRequired());
            dto.setTimeExchanged(transaction.getTimeExchanged());
            dto.setStatus(transaction.getStatus());
            dto.setCreatedAt(transaction.getCreatedAt());
            dto.setUpdatedAt(transaction.getUpdatedAt());

            return dto;
        }).collect(Collectors.toList());
    }


}

