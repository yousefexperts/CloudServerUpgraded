package com.experts.core.biller.statemachine.api.ddd.domain.shared;

import com.experts.core.biller.statemachine.api.ddd.infrastructure.util.i18n.I18nCode;
import lombok.AllArgsConstructor;

import java.util.Set;

@AllArgsConstructor
public class CommandFailure {
    public final Set<I18nCode> codes;
}
