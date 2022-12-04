package com.mungwin.payngwinteller.exception;

public class ValidationErrorResponse extends ErrorResponse {
    private String field;

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }
}
