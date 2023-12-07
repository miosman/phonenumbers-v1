package com.mosman.phonenumbers.service;

import com.mosman.phonenumbers.model.PhoneNumberStatus;
import com.mosman.phonenumbers.model.db.PhoneNumber;
import com.mosman.phonenumbers.model.dto.PhoneNumberDTO;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.repository.PhoneNumberRepository;
import org.apache.commons.lang3.StringUtils;
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
        var phoneNumbersPageResponse = new PhoneNumbersPageResponseDTO();
        Page<PhoneNumber> phoneNumbersPage;
        PageRequest pageRequest = PageRequest.of(page, limit);
        if (!StringUtils.isEmpty(customerName)) {
            phoneNumbersPage = phoneNumberRepository.findPhoneNumberByCustomerName(customerName, pageRequest);
        } else {
            phoneNumbersPage = phoneNumberRepository.findAll(pageRequest);
        }

        phoneNumbersPageResponse.setTotalCount(phoneNumbersPage.getTotalElements());
        phoneNumbersPageResponse.setRecords(phoneNumbersPage.stream().map(dbModel -> new PhoneNumberDTO(dbModel.getSubscriberNumber(), dbModel.getCustomer().getName(), dbModel.getStatus())).collect(Collectors.toList()));

        return phoneNumbersPageResponse;
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
}
