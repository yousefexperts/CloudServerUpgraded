package com.experts.core.biller.statemachine.api.ddd.domain.command.validation;

import com.experts.core.biller.statemachine.api.ddd.domain.command.PerformPayment;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandValidation;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class PerformPaymentValidator implements CommandValidation<PerformPayment> {

    @Override
    public Either<CommandFailure, PerformPayment> acceptOrReject(PerformPayment command) {

        return Either.right(command);
    }
}
