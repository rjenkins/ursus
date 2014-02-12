package com.aceevo.ursus.example.websocketclient;

import org.glassfish.tyrus.client.ClientManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleWebSocketClient {
    private CountDownLatch messageLatch;
    private static final String SENT_MESSAGE = "Hello WebSocket";

    final Logger logger = LoggerFactory.getLogger(SimpleWebSocketClient.class);

    public SimpleWebSocketClient() {
        // Bridge j.u.l in Grizzly with SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();
        logger.info("starting");
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleWebSocketClient simpleWebSocketClient = new SimpleWebSocketClient();

        if (args.length > 0 && "annotated".equals(args[0]))
            simpleWebSocketClient.run("ws://localhost:8080/annotatedEcho");
        else
            simpleWebSocketClient.run("ws://localhost:8080/echo");
        if (Boolean.valueOf(System.getProperty("wait"))) {
            System.out.println("Press CTRL^C to exit..");
            Thread.currentThread().join();
        }
    }

    public void run(String uri) {
        try {
            messageLatch = new CountDownLatch(1);

            final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create().build();
            ClientManager client = ClientManager.createClient();
            client.connectToServer(new Endpoint() {

                @Override
                public void onOpen(final Session session, EndpointConfig config) {
                    try {
                        session.addMessageHandler(new MessageHandler.Whole<String>() {
                            @Override
                            public void onMessage(String message) {
                                logger.info("Server replied with : " + message);
                                try {
                                    session.close();
                                    messageLatch.countDown();
                                } catch (IOException ex) {
                                    logger.debug(ex.getMessage(), ex);
                                }

                            }
                        });
                        session.getBasicRemote().sendText(SENT_MESSAGE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, cec, new URI(uri));
            messageLatch.await(100, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
