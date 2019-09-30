package com.experts.core.biller.statemachine.api.ddd.domain.command.validation;

import com.experts.core.biller.statemachine.api.ddd.domain.command.AuthorizePayment;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandFailure;
import com.experts.core.biller.statemachine.api.ddd.domain.shared.CommandValidation;
import io.vavr.control.Either;
import org.springframework.stereotype.Component;

@Component
public class AuthorizePaymentValidator implements CommandValidation<AuthorizePayment> {

    @Override
    public Either<CommandFailure, AuthorizePayment> acceptOrReject(AuthorizePayment command) {
        return Either.right(command);
    }
}
