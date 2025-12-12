package com.mosman.phonenumbers.repository;

import com.mosman.phonenumbers.model.db.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/// SpringDataJPA repository for [PhoneNumber] entity
@Repository
public interface PhoneNumberRepository extends PagingAndSortingRepository<PhoneNumber, String>, CrudRepository<PhoneNumber,String> {

    /// finds a PhoneNumber by provided customerName and supports pagination
    ///
    /// @param customerName name of customer
    /// @param pageable     [Pageable] representing a page request (offset,size)
    /// @return [Page<PhoneNumber>] representing a paged response
    @Query("SELECT p FROM PhoneNumber p JOIN p.customer c WHERE c.name = :customerName")
    Page<PhoneNumber> findPhoneNumberByCustomerName(@Param("customerName") String customerName, Pageable pageable);
}
