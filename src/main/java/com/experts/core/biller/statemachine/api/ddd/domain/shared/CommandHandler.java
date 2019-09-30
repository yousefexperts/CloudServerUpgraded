package com.experts.core.biller.statemachine.api.ddd.domain.shared;

import io.vavr.control.Either;

import java.util.concurrent.CompletionStage;

public interface CommandHandler<C extends Command, E extends Event, ID> {
    CompletionStage<Either<CommandFailure, E>> handle(C command, ID entityId);
}
