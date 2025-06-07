package com.mosman.phonenumbers.model.db;

import com.mosman.phonenumbers.model.PhoneNumberStatus;

import jakarta.persistence.*;


@Entity
@Table(name = "phonenumbers")
public class PhoneNumber {

    @Id
    @Column(name = "subscriber_number")
    private String subscriberNumber;

    @JoinColumn(name = "customer_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PhoneNumberStatus status;

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
     * Returns the {@link Customer} that the PhoneNumber belongs to
     *
     * @return {@link Customer} customer that the PhoneNumber belongs to
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * sets the {@link Customer} that the PhoneNumber belongs to
     *
     * @param customer {@link Customer} customer that the PhoneNumber belongs to
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
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
