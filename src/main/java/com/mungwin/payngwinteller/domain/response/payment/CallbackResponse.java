package com.mungwin.payngwinteller.domain.response.payment;

public class CallbackResponse {
    private String body;
    private Integer statusCode;

    public CallbackResponse() {
    }

    public CallbackResponse(String body, int statusCodeValue) {
        this.body = body;
        this.statusCode = statusCodeValue;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }
}
