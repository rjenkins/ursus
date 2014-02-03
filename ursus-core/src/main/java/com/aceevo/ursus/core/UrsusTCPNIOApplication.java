package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;

public abstract class UrsusTCPNIOApplication<T extends UrsusNIOApplicationConfiguration> extends UrsusNIOApplication<T, TCPNIOTransport> {

    protected UrsusTCPNIOApplication(String[] args) {
        super(args);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources and UrsusWebSocketApplication defined
     * in this application.
     */
    protected TCPNIOTransport initializeServer(FilterChain filterChain) {
        transport = TCPNIOTransportBuilder.newInstance()
                .setProcessor(filterChain)
                .build();
        return transport;
    }

}
