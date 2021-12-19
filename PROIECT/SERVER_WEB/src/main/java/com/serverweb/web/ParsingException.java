package com.serverweb.web;

public class ParsingException extends Exception{

    private final StatusCode errorCode;

    public ParsingException(StatusCode errorCode) {
        super(errorCode.MESSAGE);
        this.errorCode = errorCode;
    }

    public StatusCode getErrorCode() {
        return errorCode;
    }

}
