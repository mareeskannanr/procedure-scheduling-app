package com.study.scheduling.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum Status {

    @JsonProperty("Planned")
    PLANNED("Planned"),

    @JsonProperty("In Progress")
    INPROGRESS("In Progress"),

    @JsonProperty("Finished")
    FINISHED("Finished");

    private final String name;

    Status(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
