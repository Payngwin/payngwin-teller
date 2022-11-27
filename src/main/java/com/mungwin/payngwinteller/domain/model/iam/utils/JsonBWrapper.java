package com.mungwin.payngwinteller.domain.model.iam.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public class JsonBWrapper<T> {

    private T value;

    @JsonCreator
    public JsonBWrapper(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}
