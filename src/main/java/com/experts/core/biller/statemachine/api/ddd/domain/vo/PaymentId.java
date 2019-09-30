package com.experts.core.biller.statemachine.api.ddd.domain.vo;

import com.experts.core.biller.statemachine.api.ddd.domain.shared.RandomUUID;

public class PaymentId extends RandomUUID {

    public PaymentId() {
        super();
    }

    public PaymentId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return "PAY-%s";
    }
}
