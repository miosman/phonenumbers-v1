package com.mosman.phonenumbers.service;

import com.mosman.phonenumbers.model.PhoneNumberStatus;
import com.mosman.phonenumbers.model.db.PhoneNumber;
import com.mosman.phonenumbers.model.dto.PhoneNumberDTO;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.repository.PhoneNumberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.stream.Collectors;


/**
 * PhoneNumberService class is responsible for searching and updating phone numbers.
 */
@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    /**
     * Searches for phone numbers based on the customer name, page, and limit.
     *
     * @param customerName The name of the customer. Can be empty or null.
     * @param page The page number.
     * @param limit The maximum number of records per page.
     * @return The response containing the total count of records and a list of phone number records.
     */
    public PhoneNumbersPageResponseDTO searchPhoneNumbers(String customerName, int page, int limit) {
        if (page < 0 || limit <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page or limit values");
        }

        try {
            PageRequest pageRequest = PageRequest.of(page, limit);
            Page<PhoneNumber> phoneNumbersPage = Optional.ofNullable(customerName)
                    .filter(name -> !name.trim().isEmpty())
                    .map(name -> phoneNumberRepository.findPhoneNumberByCustomerName(name, pageRequest))
                    .orElseGet(() -> phoneNumberRepository.findAll(pageRequest));

            return new PhoneNumbersPageResponseDTO(
                    phoneNumbersPage.getTotalElements(),
                    phoneNumbersPage.stream()
                            .map(this::mapToPhoneNumberDTO)
                            .collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while fetching phone numbers", e);
        }
    }

    /**
     * Updates the status of a phone number.
     *
     * @param subscriberNumber The subscriber number of the phone number.
     * @param status The new status to be set for the phone number.
     * @return The updated phone number.
     * @throws ResponseStatusException If the phone number with the given subscriber number doesn't exist.
     */
    public PhoneNumberDTO updatePhoneNumberStatus(String subscriberNumber, PhoneNumberStatus status) {
        Optional<PhoneNumber> phoneNumber = phoneNumberRepository.findById(subscriberNumber);
        if (phoneNumber.isPresent()) {
            PhoneNumber phoneNumberEntity = phoneNumber.get();
            phoneNumberEntity.setStatus(status);
            phoneNumberRepository.save(phoneNumberEntity);
            return new PhoneNumberDTO(phoneNumberEntity.getSubscriberNumber(), phoneNumberEntity.getCustomer().getName(), phoneNumberEntity.getStatus());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Subscriber Number not found");
        }
    }

    /**
     * Maps a PhoneNumber entity to its DTO representation.
     *
     * @param phoneNumber The phone number entity to be mapped
     * @return A {@link PhoneNumberDTO} containing the subscriber number, customer name and status
     */
    private PhoneNumberDTO mapToPhoneNumberDTO(PhoneNumber phoneNumber) {
        return new PhoneNumberDTO(
                phoneNumber.getSubscriberNumber(),
                phoneNumber.getCustomer().getName(),
                phoneNumber.getStatus()
        );
    }
}
