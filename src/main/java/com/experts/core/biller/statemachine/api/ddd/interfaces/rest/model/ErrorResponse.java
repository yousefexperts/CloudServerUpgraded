package com.experts.core.biller.statemachine.api.ddd.interfaces.rest.model;


import com.experts.core.biller.statemachine.api.ddd.infrastructure.util.i18n.I18nMessage;
import lombok.Data;

import java.util.Set;

@Data
public class ErrorResponse {
    private Set<I18nMessage> errors;
}
