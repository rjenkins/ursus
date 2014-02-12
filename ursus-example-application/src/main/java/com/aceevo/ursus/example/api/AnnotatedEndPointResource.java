package com.aceevo.ursus.example.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/annotatedEcho")
public class AnnotatedEndPointResource {

    private final Logger LOGGER = LoggerFactory.getLogger(AnnotatedEndPointResource.class);

    @OnMessage
    public String sayHello(String message, Session session) {
        LOGGER.info("Received message from client: " + message);
        return message;
    }
}
