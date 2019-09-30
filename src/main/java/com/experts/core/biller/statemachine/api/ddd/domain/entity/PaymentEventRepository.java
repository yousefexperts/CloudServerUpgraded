package com.experts.core.biller.statemachine.api.ddd.domain.entity;


import com.experts.core.biller.statemachine.api.ddd.domain.event.PaymentEvent;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventId;

import java.util.concurrent.CompletionStage;

public interface PaymentEventRepository {
    CompletionStage<PaymentEventId> store(PaymentEvent paymentEvent);
}
