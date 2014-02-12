package com.aceevo.ursus.example.simplejerseyclient;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Hello {

    @JsonProperty
    private String name;

    public Hello() {

    }

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
