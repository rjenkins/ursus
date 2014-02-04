package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.nio.transport.UDPNIOTransport;
import org.glassfish.grizzly.nio.transport.UDPNIOTransportBuilder;

public abstract class UrsusUDPNIOApplication<T extends UrsusNIOApplicationConfiguration> extends UrsusNIOApplication<T, UDPNIOTransport> {

    protected UrsusUDPNIOApplication(String[] args) {
        super(args);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources and UrsusWebSocketApplication defined
     * in this application.
     */
    protected UDPNIOTransport initializeServer(FilterChain filterChain) {
        transport = UDPNIOTransportBuilder.newInstance()
                .setProcessor(filterChain)
                .build();
        return transport;
    }
}
