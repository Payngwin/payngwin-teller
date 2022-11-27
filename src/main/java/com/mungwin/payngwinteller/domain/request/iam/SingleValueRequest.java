package com.mungwin.payngwinteller.domain.request.iam;

public class SingleValueRequest<T> {
    private T value;

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
