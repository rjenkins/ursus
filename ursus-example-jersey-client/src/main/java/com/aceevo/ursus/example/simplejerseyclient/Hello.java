package com.aceevo.ursus.example.simplejerseyclient;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 1/4/14
 * Time: 12:35 AM
 * To change this template use File | Settings | File Templates.
 */
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