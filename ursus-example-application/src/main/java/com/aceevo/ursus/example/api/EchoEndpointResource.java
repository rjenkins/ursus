package com.aceevo.ursus.example.api;


import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/echo")
public class EchoEndpointResource {

    Logger logger = LoggerFactory.getLogger(EchoEndpointResource.class);

    @OnMessage
    public String onMessage(String message, Session session) {
        logger.info("Received message from client: " + message);
        return message;
    }

}