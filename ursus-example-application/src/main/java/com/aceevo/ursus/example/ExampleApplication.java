package com.aceevo.ursus.example;

import com.aceevo.ursus.core.UrsusJerseyApplication;
import com.aceevo.ursus.example.api.AnnotatedEndPointResource;
import com.aceevo.ursus.example.api.EchoEndpointResource;
import org.glassfish.grizzly.http.server.HttpServer;

public class ExampleApplication extends UrsusJerseyApplication<ExampleApplicationConfiguration> {

    public ExampleApplication(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        new ExampleApplication(args);
    }

    @Override
    protected void boostrap(ExampleApplicationConfiguration exampleApplicationConfiguration) {
        packages("com.aceevo.ursus.example.api");
        register(AnnotatedEndPointResource.class);
        registerEndpoint(EchoEndpointResource.class, "/echo", "exampleApplicationConfiguration", exampleApplicationConfiguration);
    }

    @Override
    protected void run(HttpServer httpServer) {
        startWithShutdownHook(httpServer);
    }
}
