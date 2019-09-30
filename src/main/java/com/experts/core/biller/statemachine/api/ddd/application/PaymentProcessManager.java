package com.experts.core.biller.statemachine.api.ddd.application;


import com.experts.core.biller.statemachine.api.ddd.domain.command.PerformPayment;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentId;
import com.experts.core.biller.statemachine.api.ddd.domain.vo.PaymentStatus;
import io.vavr.Tuple2;
import io.vavr.control.Either;

import java.util.concurrent.CompletionStage;

public interface PaymentProcessManager {
    CompletionStage<Either<CommandFailure, Tuple2<PaymentId, PaymentStatus>>> process(PerformPayment command);
}
