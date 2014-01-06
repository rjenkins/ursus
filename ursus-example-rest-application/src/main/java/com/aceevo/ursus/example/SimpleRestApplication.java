package com.aceevo.ursus.example;

import com.aceevo.ursus.core.UrsusApplication;
import com.aceevo.ursus.example.api.EchoEndpointResource;
import org.glassfish.grizzly.http.server.HttpServer;

public class SimpleRestApplication extends UrsusApplication<SimpleRestApplicationConfiguration> {

    public static void main(String[] args) {
        new SimpleRestApplication();
    }

    @Override
    protected void boostrap(SimpleRestApplicationConfiguration simpleRestApplicationConfiguration) {
        packages("com.aceevo.ursus.example.api");

        // We have to either manually register @ServerEndpoint classes in our ResourceConfig or annotate them
        // with @Provider because they are not picked up by the ResourceConfig scanner
        register(EchoEndpointResource.class);
    }

    @Override
    protected void run(HttpServer httpServer) {
        startWithShutdownHook(httpServer);
    }
}
