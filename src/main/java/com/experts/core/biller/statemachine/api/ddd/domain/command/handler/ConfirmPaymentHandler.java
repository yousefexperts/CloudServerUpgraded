package com.experts.core.biller.statemachine.api.ddd.domain.command.handler;

import com.experts.core.biller.statemachine.api.ddd.domain.command.ConfirmPayment;
import com.experts.core.biller.statemachine.api.ddd.domain.entity.PaymentEventRepository;
import com.experts.core.biller.statemachine.api.ddd.domain.event.PaymentConfirmed;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandHandler;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentId;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletionStage;

@Component
public class ConfirmPaymentHandler implements CommandHandler<ConfirmPayment, PaymentConfirmed, PaymentId> {

    private static final Logger LOG = LoggerFactory.getLogger(ConfirmPaymentHandler.class);

    private final PaymentEventRepository paymentEventRepository;


    public ConfirmPaymentHandler(PaymentEventRepository paymentEventRepository) {
        this.paymentEventRepository = paymentEventRepository;
    }

    @Override
    public CompletionStage<Either<CommandFailure, PaymentConfirmed>> handle(ConfirmPayment command, PaymentId entityId) {

        LOG.debug("Handle command {}", command);

        PaymentConfirmed event = PaymentConfirmed.eventOf(
                entityId,
                command.getCustomerId(),
                command.getTimestamp()
        );
        CompletionStage<PaymentEventId> storePromise = paymentEventRepository.store(event);
        return storePromise.thenApply(paymentEventId -> Either.right(event));

    }
}
