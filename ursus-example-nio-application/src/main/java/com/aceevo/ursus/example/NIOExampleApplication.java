package com.aceevo.ursus.example;

import com.aceevo.ursus.core.UrsusTCPNIOApplication;
import org.glassfish.grizzly.filterchain.*;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.utils.StringFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

public class NIOExampleApplication extends UrsusTCPNIOApplication<NIOExampleConfiguration> {

    public static void main(String[] args) {
        new NIOExampleApplication(args);
    }

    protected NIOExampleApplication(String[] args) {
        super(args);
    }


    @Override
    protected FilterChain boostrap(NIOExampleConfiguration nioExampleConfiguration, FilterChainBuilder filterChainBuilder) {
        return filterChainBuilder.add(new TransportFilter())
                .add(new StringFilter(Charset.forName("UTF-8")))
                .add(new HelloFilter()).build();
    }

    @Override
    protected void run(NIOTransport transport) {
        startWithShutdownHook(transport);
    }

    private static class HelloFilter extends BaseFilter {

        private Logger LOGGER = LoggerFactory.getLogger(HelloFilter.class);

        public NextAction handleRead(final FilterChainContext context) {

            try {
                String message = context.getMessage();
                LOGGER.info("received message " + message);

                // Echo back to client
                context.write(message);
            } catch (Exception ex) {
                LOGGER.debug("exception handle read", ex);
            }

            return context.getStopAction();
        }
    }
}
