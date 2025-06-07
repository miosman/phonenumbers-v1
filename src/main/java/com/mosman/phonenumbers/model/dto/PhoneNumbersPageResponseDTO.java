package com.mosman.phonenumbers.model.dto;

import java.util.List;

/**
 * DTO model representing a collection of phonenumber results
 */
public class PhoneNumbersPageResponseDTO {
    private long totalCount;
    private List<PhoneNumberDTO> records;

    public PhoneNumbersPageResponseDTO(long totalCount, List<PhoneNumberDTO> records) {
        this.totalCount = totalCount;
        this.records = records;
    }

    /**
     * Returns the total count of records matching the criteria
     *
     * @return long total count of records
     */
    public long getTotalCount() {
        return totalCount;
    }

    /**
     * Sets the total count of records matching the criteria
     *
     * @param totalCount total count
     */
    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * Returns the list of records in the current page
     *
     * @return {@link List<PhoneNumberDTO>} list of PhoneNumberDTO records
     */
    public List<PhoneNumberDTO> getRecords() {
        return records;
    }

    /**
     * Sets the list of records in the current page
     *
     * @param records {@link List<PhoneNumberDTO>} list of PhoneNumberDTO records
     */
    public void setRecords(List<PhoneNumberDTO> records) {
        this.records = records;
    }

}
