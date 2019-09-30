package com.experts.core.biller.statemachine.api.ddd.domain.command;

import com.experts.core.biller.statemachine.api.ddd.domain.vo.CustomerId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentIntent;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentMethod;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.Transaction;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "commandOf")
public class PerformPayment implements PaymentCommand {
    private final CustomerId customerId;
    private final PaymentIntent paymentIntent;
    private final PaymentMethod paymentMethod;
    private final Transaction transaction;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
