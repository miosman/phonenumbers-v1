package com.mosman.phonenumbers.model.dto;

import com.mosman.phonenumbers.model.PhoneNumberStatus;
import jakarta.validation.constraints.NotNull;

/// DTO model for patching a phoneNumber (partial updates)
public class PhoneNumberPatchDTO {
    
    @NotNull(message = "Status is required")
    private PhoneNumberStatus status;

    public PhoneNumberPatchDTO() {
    }

    public PhoneNumberPatchDTO(PhoneNumberStatus status) {
        this.status = status;
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