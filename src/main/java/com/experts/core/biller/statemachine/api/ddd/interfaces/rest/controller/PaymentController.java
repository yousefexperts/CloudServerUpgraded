package com.experts.core.biller.statemachine.api.ddd.interfaces.rest.controller;

import com.experts.core.biller.statemachine.api.ddd.application.PaymentProcessManager;
import com.experts.core.biller.statemachine.api.ddd.domain.command.PerformPayment;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.*;
import com.experts.core.biller.statemachine.api.ddd.interfaces.rest.model.PerformPaymentRequest;
import com.experts.core.biller.statemachine.api.ddd.interfaces.rest.model.PerformPaymentResponse;
import io.vavr.Tuple2;
import io.vavr.control.Either;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionStage;

@RestController("/v1/payments")
public class PaymentController {

    private static final Logger LOG = LoggerFactory.getLogger(PaymentController.class);

    private final PaymentProcessManager paymentProcessManager;

    public PaymentController(PaymentProcessManager paymentProcessManager) {
        this.paymentProcessManager = paymentProcessManager;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Callable<CompletionStage<ResponseEntity<?>>> process(@Valid @RequestBody PerformPaymentRequest request) {

        LOG.debug("Request {}", request);

        return () -> {
            LOG.debug("Callable...");

            PerformPayment performPayment = PerformPayment.commandOf(
                    new CustomerId(request.getCustomerId()),
                    PaymentIntent.valueOf(request.getPaymentIntent()),
                    PaymentMethod.valueOf(request.getPaymentMethod()),
                    request.getTransaction());

            CompletionStage<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> promise = paymentProcessManager.process(performPayment);
            return promise.thenApply(acceptOrReject -> acceptOrReject.fold(
                    reject -> ResponseEntity.badRequest().body(reject),
                    accept -> ResponseEntity.accepted().body(new PerformPaymentResponse(accept._1.id, accept._2.name()))
            ));
        };


    }
}
