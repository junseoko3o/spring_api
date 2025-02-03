package com.spring.api.openai.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    USER("user"),
    SYSTEM("system");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

