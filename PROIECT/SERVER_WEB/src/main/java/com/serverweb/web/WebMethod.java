package com.serverweb.web;

public enum WebMethod {
    GET, HEAD;

    public static final int MAXIM_LENGTH ;

    static {
        int maxLength = -1;
        for (WebMethod method : values()){
            if (method.name().length() > maxLength) {
                maxLength = method.name().length();
            }
        }
        MAXIM_LENGTH = maxLength ;
    }
}
