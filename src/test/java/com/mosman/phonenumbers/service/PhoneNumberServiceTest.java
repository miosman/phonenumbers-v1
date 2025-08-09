package com.mosman.phonenumbers.service;

import com.mosman.phonenumbers.model.PhoneNumberStatus;
import com.mosman.phonenumbers.model.db.Customer;
import com.mosman.phonenumbers.model.db.PhoneNumber;
import com.mosman.phonenumbers.model.dto.PhoneNumbersPageResponseDTO;
import com.mosman.phonenumbers.repository.PhoneNumberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PhoneNumberServiceTest {

    @Mock
    private PhoneNumberRepository mockPhoneNumberRepository;

    @InjectMocks
    private PhoneNumberService phoneNumberService;

    @Test
    @DisplayName("Should return empty list when no phone numbers found")
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
        Assertions.assertEquals(0L, actual.getTotalCount());
        Assertions.assertEquals(0L, actual.getRecords().size());
    }

    @Test
    @DisplayName("Should return phone numbers when customer name is not empty")
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
        Assertions.assertEquals(1L, actual.getTotalCount());
        Assertions.assertEquals(1L, actual.getRecords().size());
        Assertions.assertEquals(subscriberNumber, actual.getRecords().getFirst().getSubscriberNumber());
        Assertions.assertEquals(customerName, actual.getRecords().getFirst().getCustomerName());
        Assertions.assertEquals(status, actual.getRecords().getFirst().getStatus());
    }

    @Test
    @DisplayName("Should throw exception when subscriber number is not found")
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
    @DisplayName("Should successfully update status for existing subscriber number")
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

    @Test
    @DisplayName("Should throw exception when page number is negative")
    public void testSearchPhoneNumbersWithNegativePage() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> phoneNumberService.searchPhoneNumbers("customer", -1, 10),
            "Invalid page or limit values");
    }

    @Test
    @DisplayName("Should throw exception when limit is zero or negative")
    public void testSearchPhoneNumbersWithInvalidLimit() {
        Assertions.assertThrows(ResponseStatusException.class,
            () -> phoneNumberService.searchPhoneNumbers("customer", 0, 0),
            "Invalid page or limit values");
    }

    @Test
    @DisplayName("Should handle repository exception during search")
    public void testSearchPhoneNumbersWithRepositoryException() {
        when(mockPhoneNumberRepository.findAll(any(Pageable.class)))
            .thenThrow(new RuntimeException("Database error"));

        Assertions.assertThrows(ResponseStatusException.class,
            () -> phoneNumberService.searchPhoneNumbers(null, 0, 10));
    }

    @Test
    @DisplayName("Should handle null customer name")
    public void testSearchPhoneNumbersWithNullCustomerName() {
        List<PhoneNumber> phoneNumbers = new ArrayList<>();
        Page<PhoneNumber> phoneNumberPage = new PageImpl<>(phoneNumbers);
        when(mockPhoneNumberRepository.findAll(any(Pageable.class))).thenReturn(phoneNumberPage);

        phoneNumberService.searchPhoneNumbers(null, 0, 10);

        verify(mockPhoneNumberRepository, times(1)).findAll(any(Pageable.class));
        verify(mockPhoneNumberRepository, never()).findPhoneNumberByCustomerName(any(), any());
    }

    @Test
    @DisplayName("Should verify save operation when updating status")
    public void testUpdatePhoneNumberStatusSaveOperation() {
        var subscriberNumber = "123";
        var status = PhoneNumberStatus.ACTIVE;
        var phoneNumber = createPhoneNumberEntity(subscriberNumber, "customer", PhoneNumberStatus.IN_ACTIVE);

        when(mockPhoneNumberRepository.findById(subscriberNumber)).thenReturn(Optional.of(phoneNumber));

        phoneNumberService.updatePhoneNumberStatus(subscriberNumber, status);

        verify(mockPhoneNumberRepository, times(1)).save(phoneNumber);
        Assertions.assertEquals(status, phoneNumber.getStatus());
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
