package com.mosman.phonenumbers.controller;

import com.mosman.phonenumbers.model.dto.PhoneNumberDTO;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.service.PhoneNumberService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for /phonenumbers resources
 */
@RestController
@RequestMapping("phonenumbers")
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    public PhoneNumberController(PhoneNumberService phoneNumberService) {
        this.phoneNumberService = phoneNumberService;
    }

    /**
     * provides search capability for phonenumbers with pagination support and optinonal filtering
     *
     * @param customerName customer name
     * @param page         page index for pagination results
     * @param limit        maximum number of records to return per page
     * @return {@link PhoneNumbersPageResponseDTO} representing the search response
     */
    @GetMapping
    public PhoneNumbersPageResponseDTO searchPhoneNumbers(@RequestParam(required = false) String customerName,
                                                          @RequestParam(defaultValue = "0", required = false) int page,
                                                          @RequestParam(defaultValue = "10", required = false) int limit) {
        return phoneNumberService.searchPhoneNumbers(customerName, page, limit);
    }

    /**
     * Patches an existing phonenumber resource by updating the status
     *
     * @param subscriberNumber the subscriberNumber
     * @param requestBody      {@link PhoneNumberDTO} representing the fields to patch in the phonenumber resource. Only status is supported.
     * @return {@link PhoneNumberDTO} representing the updated phonenumber resource
     */
    @PatchMapping(value = "/{subscriberNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PhoneNumberDTO patchPhoneNumber(@PathVariable String subscriberNumber, @RequestBody PhoneNumberDTO requestBody) {
        return phoneNumberService.updatePhoneNumberStatus(subscriberNumber, requestBody.getStatus());
    }
}
