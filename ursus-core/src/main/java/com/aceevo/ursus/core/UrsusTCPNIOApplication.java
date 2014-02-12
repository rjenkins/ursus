package com.aceevo.ursus.core;

import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.nio.transport.TCPNIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class UrsusTCPNIOApplication<T extends UrsusNIOApplicationConfiguration> extends UrsusNIOApplication<T, TCPNIOTransport> {

    final Logger LOGGER = LoggerFactory.getLogger(UrsusTCPNIOApplication.class);

    protected UrsusTCPNIOApplication(String[] args) {
        super(args);
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources and UrsusWebSocketApplication defined
     * in this application.
     */
    @Override
    protected TCPNIOTransport initializeServer(FilterChain filterChain, T t) {

        if (t.getTcpnioApplication() == null) {
            return TCPNIOTransportBuilder.newInstance()
                    .setProcessor(filterChain)
                    .build();
        }

        UrsusNIOApplicationConfiguration.TCPNIOApplication tcpnioApplication = t.getTcpnioApplication();

        TCPNIOTransportBuilder tcpnioTransportBuilder = TCPNIOTransportBuilder.newInstance()
                .setClientSocketSoTimeout(tcpnioApplication.getClientSocketSoTimeout())
                .setConnectionTimeout(tcpnioApplication.getConnectionTimeout())
                .setKeepAlive(tcpnioApplication.isKeepAlive())
                .setLinger(tcpnioApplication.getLinger())
                .setReuseAddress(tcpnioApplication.isReuseAddress())
                .setServerConnectionBackLog(tcpnioApplication.getServerConnectionBacklog())
                .setServerSocketSoTimeout(tcpnioApplication.getServerSocketSoTimeout())
                .setTcpNoDelay(tcpnioApplication.isTcpNoDelay())
                .setOptimizedForMultiplexing(tcpnioApplication.isOptimizedForMultiplexing());

        if (tcpnioApplication.isMaxAyncWriteQueueSizeOverride())
            tcpnioTransportBuilder.setMaxAsyncWriteQueueSizeInBytes(tcpnioApplication.getMaxAsyncWriteQueueSize());

        return tcpnioTransportBuilder.build();

    }

}
