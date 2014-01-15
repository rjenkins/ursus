package com.aceevo.ursus.example.api;


import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import java.io.IOException;

public class EchoEndpointResource extends Endpoint {

    Logger logger = LoggerFactory.getLogger(EchoEndpointResource.class);
    private ExampleApplicationConfiguration exampleApplicationConfiguration;

    @OnOpen
    public void onOpen(final Session session, EndpointConfig config) {
        exampleApplicationConfiguration = (ExampleApplicationConfiguration) config.getUserProperties().get("exampleApplicationConfiguration");
        session.addMessageHandler(new MessageHandler.Whole<String>() {

            @Override
            public void onMessage(String message) {
                logger.info("exampleApplicationConfiguration.getName is: " + exampleApplicationConfiguration.getName());
                logger.info("Received message from client: " + message);
                try {
                    session.getBasicRemote().sendText(message);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
            }
        });
    }
}