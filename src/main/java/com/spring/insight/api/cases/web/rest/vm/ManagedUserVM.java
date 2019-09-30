package com.spring.insight.api.cases.web.rest.vm;


import javax.validation.constraints.Size;

import com.spring.insight.api.cases.service.dto.UserDTO;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;

public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;


    public ManagedUserVM(String id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String imageUrl, String langKey,
                         String createdBy, LocalDate createdDate, String lastModifiedBy, LocalDate lastModifiedDate,
                         Set<String> authorities) {

        super(id, login, firstName, lastName, email, activated, imageUrl, langKey, createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities);
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
