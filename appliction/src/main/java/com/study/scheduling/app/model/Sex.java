package com.study.scheduling.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Sex {

    @JsonProperty("Male")
    MALE("Male"),

    @JsonProperty("Female")
    FEMALE("Female"),

    @JsonProperty("Other")
    OTHER("Other");

    private final String name;

    Sex(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
