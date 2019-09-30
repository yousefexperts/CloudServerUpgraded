package com.experts.core.biller.statemachine.api.ddd.domain.vo;

import com.experts.core.biller.statemachine.api.ddd.domain.shared.RandomUUID;

public class CustomerId extends RandomUUID {

    public CustomerId(String id) {
        super(id);
    }

    @Override
    protected String getPrefix() {
        return "CST-%s";
    }
}
