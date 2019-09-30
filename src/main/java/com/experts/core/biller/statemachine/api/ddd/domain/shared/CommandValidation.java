package com.experts.core.biller.statemachine.api.ddd.domain.shared;

import io.vavr.control.Either;

public interface CommandValidation<C extends Command> {

    Either<CommandFailure, C> acceptOrReject(C command);
}
