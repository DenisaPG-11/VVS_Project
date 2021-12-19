package com.serverweb.webserver.config;

public class WebConfigurationException extends  RuntimeException{
    public WebConfigurationException() {
    }

    public WebConfigurationException(String message) {
        super(message);
    }

    public WebConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebConfigurationException(Throwable cause) {
        super(cause);
    }

    public WebConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
