package com.experts.core.biller.statemachine.api.ddd.infrastructure.persistence.repository;


import com.experts.core.biller.statemachine.api.ddd.domain.entity.PaymentEventRepository;
import com.experts.core.biller.statemachine.api.ddd.domain.event.PaymentEvent;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventId;
import com.experts.core.biller.statemachine.api.ddd.infrastructure.persistence.mapping.PaymentEventTable;
import com.experts.core.biller.statemachine.api.ddd.infrastructure.util.json.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Repository
@Component
class PaymentEventRepositoryImpl implements PaymentEventRepository {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentEventRepositoryImpl.class);

    private  EventStore eventStore;
    private  JsonMapper jsonMapper;



    @Override
    public CompletionStage<PaymentEventId> store(PaymentEvent paymentEvent) {
        LOG.debug("Storing paymentEvent {}", paymentEvent);
        String eventDataAsJson = jsonMapper.write(paymentEvent);
        LOG.debug("eventDataAsJson {}", eventDataAsJson);

        PaymentEventTable paymentEventTable = new PaymentEventTable();
        paymentEventTable.setId(paymentEvent.getEventId().id);

        paymentEventTable.setEventType(paymentEvent.getEventType());
        paymentEventTable.setPaymentId(paymentEvent.getPaymentId().id);
        paymentEventTable.setTimestamp(paymentEvent.getTimestamp());
        paymentEventTable.setEventData(eventDataAsJson);
        eventStore.save(paymentEventTable);
        return CompletableFuture.completedFuture(paymentEvent.getEventId());
    }
}
