package com.experts.core.biller.statemachine.api.model.domain.jpa;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;



@AllArgsConstructor
@Getter
public class CustomErrorResponse implements Serializable {

	private static final long serialVersionUID = -7755563009111273632L;

	private String errorCode;

	private String errorMessage;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
