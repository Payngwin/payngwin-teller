package com.mungwin.payngwinteller.domain.response;

import java.util.List;

public class ApiResponse<T> {
    private T content;
    private List<T> errors;

    public ApiResponse() {
    }

    private ApiResponse(T content) {
        this.content = content;
    }
    public static <O> ApiResponse<O> from(O content) {
        return new ApiResponse<>(content);
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public List<T> getErrors() {
        return errors;
    }

    public void setErrors(List<T> errors) {
        this.errors = errors;
    }
}
