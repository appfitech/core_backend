package com.fitech.app.users.domain.model;

import com.fitech.app.users.domain.entities.User;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

/**
 * DTO for {@link User}
 */
public class PersonDto implements Serializable {
    private Integer id;
    @Size(max = 45)
    private String firstName;
    @Size(max = 45)
    private String lastName;
    private String documentNumber;
    private String phoneNumber;
    private String email;
    private String documentType = "DNI";

    public PersonDto() {
    }

    public PersonDto(Integer id, String firstName, String lastName, String documentNumber, String phoneNumber, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.documentNumber = documentNumber;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public boolean hasDifferentDocumentNumber(String documentNumber){
        return this.getDocumentNumber()!= null && !this.getDocumentNumber().equals(documentNumber);
    }
}