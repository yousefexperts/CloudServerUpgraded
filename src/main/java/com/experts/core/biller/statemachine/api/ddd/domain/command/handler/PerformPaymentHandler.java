package com.experts.core.biller.statemachine.api.ddd.domain.command.handler;

import com.experts.core.biller.statemachine.api.ddd.domain.command.PerformPayment;
import com.experts.core.biller.statemachine.api.ddd.domain.command.validation.PerformPaymentValidator;
import com.experts.core.biller.statemachine.api.ddd.domain.entity.PaymentEventRepository;
import com.experts.core.biller.statemachine.api.ddd.domain.event.PaymentRequested;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandHandler;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentEventId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentId;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@Component
public class PerformPaymentHandler implements
        CommandHandler<PerformPayment, PaymentRequested, PaymentId> {

    private static final Logger LOG = LoggerFactory.getLogger(PerformPaymentHandler.class);

    private final PaymentEventRepository paymentEventRepository;
    private final PerformPaymentValidator performPaymentValidator;

    PerformPaymentHandler(PaymentEventRepository paymentEventRepository,
                          PerformPaymentValidator performPaymentValidator) {
        this.paymentEventRepository = paymentEventRepository;
        this.performPaymentValidator = performPaymentValidator;
    }

    @Override
    public CompletionStage<Either<CommandFailure, PaymentRequested>> handle(PerformPayment command, PaymentId entityId) {

        LOG.debug("Handle command {}", command);

        return performPaymentValidator.acceptOrReject(command).fold(
                reject -> CompletableFuture.completedFuture(Either.left(reject)),
                accept -> {
                    PaymentRequested event = PaymentRequested.eventOf(
                            entityId,
                            command.getCustomerId(),
                            command.getPaymentIntent(),
                            command.getPaymentMethod(),
                            command.getTransaction(),
                            command.getTimestamp()
                    );
                    CompletionStage<PaymentEventId> storePromise = paymentEventRepository.store(event);
                    return storePromise.thenApply(paymentEventId -> Either.right(event));
                }
        );
    }


}
