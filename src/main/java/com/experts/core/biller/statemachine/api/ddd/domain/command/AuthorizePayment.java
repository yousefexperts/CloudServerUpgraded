package com.experts.core.biller.statemachine.api.ddd.domain.command;

import com.experts.core.biller.statemachine.api.ddd.domain.vo.CustomerId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentMethod;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.Transaction;
import lombok.Value;

import java.time.LocalDateTime;

@Value(staticConstructor = "commandOf")
public class AuthorizePayment implements PaymentCommand {
    private final PaymentId paymentId;
    private final CustomerId customerId;
    private final PaymentMethod paymentMethod;
    private final Transaction transaction;
    private final LocalDateTime timestamp = LocalDateTime.now();
}
