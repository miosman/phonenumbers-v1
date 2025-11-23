package com.mosman.phonenumbers.it;

import tools.jackson.databind.ObjectMapper;
import com.mosman.phonenumbers.model.PhoneNumberStatus;
import com.mosman.phonenumbers.model.dto.PhoneNumberPatchDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PhoneNumbersApiIntegrationTest {

    public static final long TOTAL_PHONE_NUMBERS_COUNT = 15L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String SEARCH_PHONE_NUMBERS_RESOURCE_PATH = "/phonenumbers";
    private static final String PATCH_PHONE_NUMBER_RESOURCE_PATH = "/phonenumbers/{subscriberNumber}";

    @Test
    public void testSearchPhoneNumbersWithNoFilters() throws Exception {

        int defaultPageSize = 10;

        mockMvc.perform(get(SEARCH_PHONE_NUMBERS_RESOURCE_PATH))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(TOTAL_PHONE_NUMBERS_COUNT))
                .andExpect(jsonPath("$.records").isArray())
                .andExpect(jsonPath("$.records", hasSize(defaultPageSize)));

    }

    @Test
    public void testSearchPhoneNumbersWithCustomerNameFilterAndPageSizeOf5() throws Exception {

        int pageSize = 5;

        mockMvc.perform(get(SEARCH_PHONE_NUMBERS_RESOURCE_PATH).param("limit",pageSize+"").param("customerName", "Moe"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalCount").value(7L))
                .andExpect(jsonPath("$.records").isArray())
                .andExpect(jsonPath("$.records", hasSize(pageSize)));

    }

    @Test
    public void testPatchPhoneNumberWithNonExistingSubscriberNumber() throws Exception {

        var subscriberNumber = "1234567890";
        var requestBody = new PhoneNumberPatchDTO();
        requestBody.setStatus(PhoneNumberStatus.ACTIVE);

        mockMvc.perform(patch(PATCH_PHONE_NUMBER_RESOURCE_PATH,subscriberNumber)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isNotFound());

    }

    @Test
    public void testPatchPhoneNumberWithExistingSubscriberNumberAndInactiveStatus() throws Exception {

        var subscriberNumber = "455555551";
        var requestBody = new PhoneNumberPatchDTO();
        requestBody.setStatus(PhoneNumberStatus.IN_ACTIVE);

        mockMvc.perform(patch(PATCH_PHONE_NUMBER_RESOURCE_PATH,subscriberNumber)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestBody)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(PhoneNumberStatus.IN_ACTIVE.toString()));

    }
}
