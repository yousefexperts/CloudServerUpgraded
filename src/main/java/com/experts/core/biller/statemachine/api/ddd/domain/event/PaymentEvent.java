package com.experts.core.biller.statemachine.api.ddd.domain.event;

import com.experts.core.biller.statemachine.api.ddd.domain.shared.Event;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventType;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentId;

import java.time.LocalDateTime;

public interface PaymentEvent extends Event {
    PaymentEventId getEventId();

    PaymentEventType getEventType();

    PaymentId getPaymentId();

    LocalDateTime getTimestamp();
}
