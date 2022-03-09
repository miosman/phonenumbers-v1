package com.mosman.phonenumbers.repository;

import com.mosman.phonenumbers.model.db.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * SpringDataJPA repository for {@link PhoneNumber} entity
 */
@Repository
public interface PhoneNumberRepository extends PagingAndSortingRepository<PhoneNumber, String> {

    /**
     * finds a PhoneNumber by provided customerName and supports pagination
     *
     * @param customerName name of customer
     * @param pageable     {@link Pageable} representing a page request (offset,size)
     * @return {@link Page<PhoneNumber>} representing a paged response
     */
    Page<PhoneNumber> findPhoneNumberByCustomerName(String customerName, Pageable pageable);
}
