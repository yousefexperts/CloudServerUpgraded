package com.experts.core.biller.statemachine.api.service.impl;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class QuefRequestBody implements Serializable {


    @JsonProperty
    private String fullName;

    @JsonProperty
    private String phone;

    @JsonProperty
    private String amount;

    @JsonProperty
    private String location;

    @JsonProperty
    private String email;

}
