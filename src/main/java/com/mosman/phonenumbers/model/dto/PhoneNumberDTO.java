package com.mosman.phonenumbers.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.mosman.phonenumbers.model.PhoneNumberStatus;

/// DTO model for a phoneNumber
@Valid
public class PhoneNumberDTO {
    @NotBlank(message = "Subscriber number is required")
    @Pattern(regexp = "^\\d{10}$", message = "Subscriber number must be a 10-digit number")
    private String subscriberNumber;

    @NotBlank(message = "Customer name is required")
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters")
    private String customerName;

    @NotNull(message = "Status is required")
    private PhoneNumberStatus status;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String subscriberNumber, String customerName, PhoneNumberStatus status) {
        this.subscriberNumber = subscriberNumber;
        this.customerName = customerName;
        this.status = status;
    }

    /// Return the subscriberNumber
    ///
    /// @return [String] subscriberNumber
    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    /// sets the subscriberNumber
    ///
    /// @param subscriberNumber the subscriber number
    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    /// Returns the customer name that the PhoneNumber belongs to
    ///
    /// @return [String] name of customer that the PhoneNumber belongs to
    public String getCustomerName() {
        return customerName;
    }

    /// Sets the customer name that the PhoneNumber belongs to
    ///
    /// @param customerName name of customer
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /// Returns the status of the PhoneNumber
    ///
    /// @return [PhoneNumberStatus] the status of the PhoneNumber
    public PhoneNumberStatus getStatus() {
        return status;
    }

    /// Sets the status of the PhoneNumber
    ///
    /// @param status [PhoneNumberStatus] the status of the PhoneNumber
    public void setStatus(PhoneNumberStatus status) {
        this.status = status;
    }
}