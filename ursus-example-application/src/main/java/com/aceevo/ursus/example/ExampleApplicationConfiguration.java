package com.aceevo.ursus.example;

import com.aceevo.ursus.config.UrsusApplicationConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class ExampleApplicationConfiguration extends UrsusApplicationConfiguration {

    @NotNull
    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
