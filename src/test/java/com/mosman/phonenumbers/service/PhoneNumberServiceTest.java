package com.mosman.phonenumbers.service;

import com.mosman.phonenumbers.model.PhoneNumberStatus;
import com.mosman.phonenumbers.model.db.Customer;
import com.mosman.phonenumbers.model.db.PhoneNumber;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
public class PhoneNumberServiceTest {

    @MockBean
    private PhoneNumberRepository mockPhoneNumberRepository;

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Test
    @DisplayName("")
    public void testSearchPhoneNumbersWithEmptyCustomerName()
    {
        //Given
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        Page<PhoneNumber> phoneNumberPage = new PageImpl<>(phoneNumbers);
        when(mockPhoneNumberRepository.findAll(Mockito.any(Pageable.class))).thenReturn(phoneNumberPage);

        //Test
        PhoneNumbersPageResponseDTO actual = phoneNumberService.searchPhoneNumbers("",0,10);

        //Assert
        verify(mockPhoneNumberRepository,times(1)).findAll(any(Pageable.class));
        Assertions.assertEquals(actual.getTotalCount(),0L);
        Assertions.assertEquals(actual.getRecords().size(),0L);
    }

    @Test
    @DisplayName("")
    public void testSearchPhoneNumbersWithNonEmptyCustomerName()
    {
        //Given
        var subscriberNumber = "459908";
        var status = PhoneNumberStatus.ACTIVE;
        var customerName = "Moe";
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        phoneNumbers.add(createPhoneNumberEntity(subscriberNumber,customerName,status));
        Page<PhoneNumber> phoneNumberPage = new PageImpl<>(phoneNumbers);
        when(mockPhoneNumberRepository.findPhoneNumberByCustomerName(eq(customerName),Mockito.any(Pageable.class))).thenReturn(phoneNumberPage);

        //Test
        PhoneNumbersPageResponseDTO actual = phoneNumberService.searchPhoneNumbers(customerName,0,10);

        //Assert
        verify(mockPhoneNumberRepository,times(1)).findPhoneNumberByCustomerName(eq(customerName),any(Pageable.class));
        Assertions.assertEquals(actual.getTotalCount(),1L);
        Assertions.assertEquals(actual.getRecords().size(),1L);
        Assertions.assertEquals(actual.getRecords().get(0).getSubscriberNumber(),subscriberNumber);
        Assertions.assertEquals(actual.getRecords().get(0).getCustomerName(),customerName);
        Assertions.assertEquals(actual.getRecords().get(0).getStatus(),status);
    }

    @Test
    @DisplayName("")
    public void testUpdatePhoneNumberStatusWithNonExistingSubscriberNumber()
    {
        //Given
        var subscriberNumber = "123";
        var status = PhoneNumberStatus.ACTIVE;
        var exceptionMessage = "Subscriber Number not found";
        when(mockPhoneNumberRepository.findById(subscriberNumber)).thenReturn(Optional.empty());

        //Test
        //Assert
        Assertions.assertThrows(ResponseStatusException.class, () -> phoneNumberService.updatePhoneNumberStatus(subscriberNumber,status),exceptionMessage);
    }

    @Test
    @DisplayName("")
    public void testUpdatePhoneNumberStatusWithExistingSubscriberNumber()
    {
        //Given
        var subscriberNumber = "123";
        var status = PhoneNumberStatus.ACTIVE;
        var updatedStatus = PhoneNumberStatus.IN_ACTIVE;
        var customerName = "Moe";
        when(mockPhoneNumberRepository.findById(subscriberNumber)).thenReturn(Optional.of(createPhoneNumberEntity(subscriberNumber,customerName,status)));

        //Test
        var actual = phoneNumberService.updatePhoneNumberStatus(subscriberNumber,updatedStatus);

        //Assert
        Assertions.assertEquals(updatedStatus,actual.getStatus());
    }


    private PhoneNumber createPhoneNumberEntity(String subscriberNumber, String customerName, PhoneNumberStatus status)
    {
        var phoneNumber = new PhoneNumber();
        phoneNumber.setSubscriberNumber(subscriberNumber);
        phoneNumber.setStatus(status);
        var customer = new Customer();
        customer.setId(1L);
        customer.setName(customerName);
        phoneNumber.setCustomer(customer);
        return phoneNumber;
    }
}
