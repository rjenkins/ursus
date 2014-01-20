package com.aceevo.ursus.example;

import com.aceevo.ursus.core.UrsusApplication;
import com.aceevo.ursus.example.api.EchoEndpointResource;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import org.glassfish.grizzly.http.server.HttpServer;

import javax.websocket.server.ServerEndpointConfig;

public class ExampleApplication extends UrsusApplication<ExampleApplicationConfiguration> {

    public ExampleApplication(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        new ExampleApplication(args);
    }

    @Override
    protected void boostrap(ExampleApplicationConfiguration exampleApplicationConfiguration) {
        packages("com.aceevo.ursus.example.api");
        registerEndpoint(EchoEndpointResource.class, "/echo", "exampleApplicationConfiguration", exampleApplicationConfiguration);
    }

    @Override
    protected void run(HttpServer httpServer) {
        startWithShutdownHook(httpServer);
    }
}
