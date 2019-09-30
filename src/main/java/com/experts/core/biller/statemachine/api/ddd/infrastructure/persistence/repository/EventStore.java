package com.experts.core.biller.statemachine.api.ddd.infrastructure.persistence.repository;

import com.experts.core.biller.statemachine.api.ddd.infrastructure.persistence.mapping.PaymentEventTable;
import org.springframework.data.repository.CrudRepository;

public interface EventStore extends CrudRepository<PaymentEventTable, String> {
}
