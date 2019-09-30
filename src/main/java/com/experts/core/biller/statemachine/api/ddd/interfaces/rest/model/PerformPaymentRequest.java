package com.experts.core.biller.statemachine.api.ddd.interfaces.rest.model;

import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentIntent;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentMethod;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.Transaction;
import com.experts.core.biller.statemachine.api.ddd.infrastructure.util.validation.ValidEnum;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
public class PerformPaymentRequest {
    @NotNull
    private String customerId;
    @ValidEnum(conformsTo = PaymentIntent.class)
    private String paymentIntent;
    @ValidEnum(conformsTo = PaymentMethod.class)
    private String paymentMethod;
    @Valid
    private Transaction transaction;
}
