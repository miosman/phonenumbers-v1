package com.mosman.phonenumbers.controller;

import com.mosman.phonenumbers.model.dto.PhoneNumberDTO;
import com.mosman.phonenumbers.model.dto.PhoneNumberPatchDTO;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.service.PhoneNumberService;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;

/**
 * Controller for /phonenumbers resources
 */
@RestController
@RequestMapping("phonenumbers")
@Validated
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
                                                          @RequestParam(defaultValue = "0", required = false) @Min(value = 0, message = "Page number must be non-negative") int page,
                                                          @RequestParam(defaultValue = "10", required = false) @Min(value = 1, message = "Limit must be at least 1") int limit) {
        return phoneNumberService.searchPhoneNumbers(customerName, page, limit);
    }

    /**
     * Patches an existing phonenumber resource by updating the status
     *
     * @param subscriberNumber the subscriberNumber
     * @param requestBody      {@link PhoneNumberPatchDTO} representing the fields to patch in the phonenumber resource. Only status is supported.
     * @return {@link PhoneNumberDTO} representing the updated phonenumber resource
     */
    @PatchMapping(value = "/{subscriberNumber}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public PhoneNumberDTO patchPhoneNumber(@PathVariable @NotBlank(message = "Subscriber number is required") String subscriberNumber, 
                                          @Valid @RequestBody PhoneNumberPatchDTO requestBody) {
        return phoneNumberService.updatePhoneNumberStatus(subscriberNumber, requestBody.getStatus());
    }
}
