package com.mosman.phonenumbers.model.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity Model representing a customer
 */
@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "id")
    private Long id;

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
