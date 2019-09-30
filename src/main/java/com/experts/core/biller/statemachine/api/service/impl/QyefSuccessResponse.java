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
public class QyefSuccessResponse implements Serializable {

    @JsonProperty(value = "success")
    private String success;

    public static QyefSuccessResponse create() {
        return QyefSuccessResponse.builder().success("sucess").build();
    }

    public static QyefSuccessResponse fail() {
        return QyefSuccessResponse.builder().success("fail").build();
    }

    public static QyefSuccessResponse phoneConditions() {
        return QyefSuccessResponse.builder().success("Mobile Exist").build();
    }
}
