package com.aceevo.ursus.example.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class Hello {

    @JsonProperty
    private String name;

    public Hello(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
