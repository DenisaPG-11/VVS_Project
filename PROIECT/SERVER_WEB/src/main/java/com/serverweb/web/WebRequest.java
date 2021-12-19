package com.serverweb.web;

public class WebRequest extends WebMessage{

    private WebMethod method;
    private String request;
    private String version;

    WebRequest() {

    }

    void setMethod(String methodName) throws ParsingException {
        for (WebMethod method: WebMethod.values()){
            if (methodName.equals(method.name())){
                this.method = method;
                return;
            }
        }
        throw new ParsingException(
                StatusCode.SERVER_ERROR_501_METHOD_NOT_IMPLEMENTED
        );
    }

    public WebMethod getMethod() {
        return method;
    }
}
