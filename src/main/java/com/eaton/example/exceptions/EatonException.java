package com.eaton.example.exceptions;

import com.eaton.example.enums.ErrorCode;

public class EatonException extends  Exception{
    private ErrorCode errorCode;

    public EatonException(ErrorCode code) {
        super();
        this.errorCode = code;
    }

    public EatonException(String errorMessage) {
        super(errorMessage);
    }

    public EatonException(String errorMessage, ErrorCode errorCodes) {
        super(errorMessage);
        this.errorCode = errorCodes;
    }

    public EatonException(String errorMessage, Throwable cause , ErrorCode errorCodes) {
        super(errorMessage,cause);
        this.errorCode = errorCodes;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }
}
