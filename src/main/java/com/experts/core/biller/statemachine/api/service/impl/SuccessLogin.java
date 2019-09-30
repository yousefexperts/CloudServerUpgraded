package com.experts.core.biller.statemachine.api.service.impl;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SuccessLogin {

    private String j_username;

    private String j_password;
}
