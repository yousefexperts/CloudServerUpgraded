package com.experts.core.biller.statemachine.api.rovo.awsxray.exceptions;

import com.experts.core.biller.statemachine.api.rovo.awsxray.utils.ErrorCodes;

public class FileNotFoundException extends APIException{

    public FileNotFoundException(String error)
    {
        super(ErrorCodes.FILE_NOT_FOUND, error);
    }
}
