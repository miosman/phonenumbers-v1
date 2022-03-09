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
 * PhoneNumberService encapsulates service functionality for PhoneNumber actions
 * depends on {@link PhoneNumberRepository}
 */
@Service
public class PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;

    public PhoneNumberService(PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberRepository = phoneNumberRepository;
    }

    /**
     * Search for phoneNumbers in repository and transform results into {@link PhoneNumbersPageResponseDTO}
     *
     * @param customerName {@link String} name of customer
     * @param page         int page number
     * @param limit        int maximum number of results per page
     * @return {@link PhoneNumbersPageResponseDTO} object representing a single page of results
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
     * Updates the status of a PhoneNumber by subscriberNumber, throws {@link ResponseStatusException} if the PhoneNumber is not found
     *
     * @param subscriberNumber String subscriber number
     * @param status           {@link PhoneNumberStatus} status for update
     * @return {@link PhoneNumberDTO} of the updated PhoneNumber
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
