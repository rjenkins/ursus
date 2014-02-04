package com.aceevo.ursus.example;

import org.glassfish.grizzly.Connection;
import org.glassfish.grizzly.filterchain.*;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.glassfish.grizzly.utils.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

public class NIOExampleClient {

    private Logger LOGGER = LoggerFactory.getLogger(NIOExampleClient.class);
    private final CountDownLatch countDownLatch = new CountDownLatch(1);

    public NIOExampleClient() {
        Thread clientRunnerThread = new Thread(new ClientRunner());
        clientRunnerThread.start();
    }

    public static void main(String[] args) {
        new NIOExampleClient();
    }

    private class ClientRunner implements Runnable {

        @Override
        public void run() {
            Connection connection = null;
            try {

                final FilterChainBuilder clientFilterBuilder = FilterChainBuilder.stateless()
                        .add(new TransportFilter())
                        .add(new StringFilter(Charset.forName("UTF-8")))
                        .add(new HelloClientFilter(countDownLatch));


                final TCPNIOTransport transport = TCPNIOTransportBuilder.newInstance()
                        .setProcessor(clientFilterBuilder.build())
                        .build();

                try {
                    transport.start();
                    connection = transport.connect("localhost", 20389).get();
                    connection.write("Hello Ursus");
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }

                countDownLatch.await();
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

        private final CountDownLatch countDownLatch;
        private Logger LOGGER = LoggerFactory.getLogger(HelloClientFilter.class);

        public HelloClientFilter(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        public NextAction handleRead(final FilterChainContext context) {
            try {
                String message = context.getMessage();
                LOGGER.info("received message from server: " + message);
                countDownLatch.countDown();
            } catch (Exception ex) {
                LOGGER.debug("exception handle read", ex);
            }

            return context.getStopAction();
        }

    }


}
