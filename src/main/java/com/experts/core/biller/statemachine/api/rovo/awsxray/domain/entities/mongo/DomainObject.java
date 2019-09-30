package com.experts.core.biller.statemachine.api.rovo.awsxray.domain.entities.mongo;

import org.bson.types.ObjectId;

public interface DomainObject {
    ObjectId getId();

    String getUuid();
}
