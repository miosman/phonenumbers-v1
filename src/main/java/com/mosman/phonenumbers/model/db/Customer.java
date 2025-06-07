package com.mosman.phonenumbers.model.db;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size; // Import Size annotation

/**
 * Entity Model representing a customer
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "Customer name cannot be blank") // Ensures name is not null and not just whitespace
    @Size(min = 2, max = 100, message = "Customer name must be between 2 and 100 characters") // Optional: Enforces length constraints
    @Column(name = "name")
    private String name;

    /**
     * Returns customer id
     *
     * @return {@link Long} the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the Customer Id
     *
     * @param id customer id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Returns the Customer name
     *
     * @return {@link String} the Customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the Customer name
     *
     * @param name the Customer name
     */
    public void setName(String name) {
        this.name = name;
    }

}