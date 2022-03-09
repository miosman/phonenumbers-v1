package com.mosman.phonenumbers.model.dto;

import com.mosman.phonenumbers.model.PhoneNumberStatus;

/**
 * DTO model for a phoneNumber
 */
public class PhoneNumberDTO {
    private String subscriberNumber;
    private String customerName;
    private PhoneNumberStatus status;

    public PhoneNumberDTO() {
    }

    public PhoneNumberDTO(String subscriberNumber, String customerName, PhoneNumberStatus status) {
        this.subscriberNumber = subscriberNumber;
        this.customerName = customerName;
        this.status = status;
    }

    /**
     * Return the subscriberNumber
     *
     * @return {@link String} subscriberNumber
     */
    public String getSubscriberNumber() {
        return subscriberNumber;
    }

    /**
     * sets the subscriberNumber
     *
     * @param subscriberNumber the subscriber number
     */
    public void setSubscriberNumber(String subscriberNumber) {
        this.subscriberNumber = subscriberNumber;
    }

    /**
     * Returns the customer name that the PhoneNumber belongs to
     *
     * @return {@link String} name of customer that the PhoneNumber belongs to
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Sets the customer name that the PhoneNumber belongs to
     *
     * @param customerName name of customer
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /**
     * Returns the status of the PhoneNumber
     *
     * @return {@link PhoneNumberStatus} the status of the PhoneNumber
     */
    public PhoneNumberStatus getStatus() {
        return status;
    }

    /**
     * Sets the status of the PhoneNumber
     *
     * @param status {@link PhoneNumberStatus} the status of the PhoneNumber
     */
    public void setStatus(PhoneNumberStatus status) {
        this.status = status;
    }
}
