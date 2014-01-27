package com.aceevo.ursus.example;

import com.google.protobuf.MessageLite;
import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.*;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.utils.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutionException;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 1/24/14
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProtobufExampleClient {

    private Logger LOGGER = LoggerFactory.getLogger(ProtobufExampleClient.class);

    public ProtobufExampleClient() {
        Thread clientRunnerThread = new Thread(new ClientRunner());
        clientRunnerThread.start();
    }

    public static void main(String[] args) {
        new ProtobufExampleClient();
    }


    private class ClientRunner implements Runnable {

        @Override
        public void run() {
            Connection connection = null;
            try {

                final FilterChainBuilder clientFilterBuilder = FilterChainBuilder.stateless()
                        .add(new TransportFilter())
                        .add(new StringFilter(Charset.forName("UTF-8")));


                final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance()
                        .setProcessor(clientFilterBuilder.build())
                        .build();

                try {
                    transport.start();
                    connection = transport.connect("localhost", 20389).get();
                    connection.write("foo");
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                if (connection != null)
                    connection.close();

                try {
                    transport.stop();
                } catch (IOException e) {
                    LOGGER.debug("can't close connect", e);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            } catch (ExecutionException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }


    private static class HelloClientFilter extends BaseFilter {

        /**
         * A storage queue to send the read messages to.
         */
        private Logger LOGGER = LoggerFactory.getLogger(HelloClientFilter.class);

        public NextAction handleRead(final FilterChainContext context) {
            try {
                String message = context.getMessage();
                LOGGER.info("received message from server: " + message);
            } catch (Exception ex) {
                LOGGER.debug("exception handle read", ex);
            }
            return context.getStopAction();
        }

    }


}
