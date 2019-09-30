package com.experts.core.biller.statemachine.api.ddd.domain.vo;

import com.experts.core.biller.statemachine.api.ddd.domain.shared.RandomUUID;


public class PaymentEventId extends RandomUUID {

    @Override
    protected String getPrefix() {
        return "PEV-%s";
    }
}
