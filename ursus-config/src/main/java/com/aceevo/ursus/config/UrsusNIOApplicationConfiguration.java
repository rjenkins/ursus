package com.aceevo.ursus.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

public class UrsusNIOApplicationConfiguration extends UrsusConfiguration {

    @JsonProperty
    @Valid
    @NotNull
    private Server server;

    @JsonProperty
    private TCPNIOApplication tcpnioApplication;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public TCPNIOApplication getTcpnioApplication() {
        return tcpnioApplication;
    }

    public void setTcpnioApplication(TCPNIOApplication tcpnioApplication) {
        this.tcpnioApplication = tcpnioApplication;
    }

    public static class Server {

        @JsonProperty
        @Valid
        @NotNull
        private String host;

        @JsonProperty
        private int port = 8080;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }
    }

    public static class TCPNIOApplication {

        // Enable/disable SO_TIMEOUT with the specified timeout, in milliseconds (client mode).
        @JsonProperty
        private int clientSocketSoTimeout = -1;

        // Time in milliseconds for how long establishing a connection can
        // take before the operation times out.
        @JsonProperty
        private int connectionTimeout = 30000;

        // Enable/disable SO_KEEPALIVE.
        @JsonProperty
        private boolean keepAlive = true;

        // Enable/disable SO_LINGER with the specified linger time in seconds.
        // The maximum timeout value is platform specific.
        // The setting only affects socket close.
        @JsonProperty
        private int linger = -1;

        // Enable/disable the SO_REUSEADDR socket option. When a TCP connection is closed the
        // connection may remain in a timeout state for a period of time after the connection is closed
        // (typically known as the TIME_WAIT state or 2MSL wait state).
        // For applications using a well known socket address or port it may not be possible to bind
        // a socket to the required SocketAddress if there is a connection in the timeout state
        // involving the socket address or port.
        @JsonProperty
        private boolean reuseAddress = true;

        // Specifies the maximum pending connection queue length.
        @JsonProperty
        private int serverConnectionBacklog = 4096;

        // Enable/disable SO_TIMEOUT with the specified timeout, in milliseconds (server mode).
        @JsonProperty
        private int serverSocketSoTimeout = 0;

        // Enable/disable TCP_NODELAY (disable/enable Nagle's algorithm).
        @JsonProperty
        private boolean tcpNoDelay = true;

        // Controls the behavior of writing to a connection. If enabled, then all writes regardless
        // if the current thread can write directly to the connection or not, will be passed to the async
        // write queue. When the write actually occurs, the transport will attempt to write as much content
        // from the write queue as possible. This option is disabled by default.
        @JsonProperty
        private boolean optimizedForMultiplexing = false;

        // maxAsyncWriteQueueSize is usually set at runtime, set this override property and maxAyncWriteQueueSizeOverride
        // to set maxAsyncWriteQueueSize
        @JsonProperty
        private boolean maxAyncWriteQueueSizeOverride;

        // Specifies the size, in bytes, of the async write queue on a per-connection basis.
        // If not specified, the value will be configured to be four times the size of the system's
        // socket write buffer size. Setting this value to -1 will allow the queue to be unbounded.
        @JsonProperty
        private int maxAsyncWriteQueueSize;

        public int getClientSocketSoTimeout() {
            return clientSocketSoTimeout;
        }

        public void setClientSocketSoTimeout(int clientSocketSoTimeout) {
            this.clientSocketSoTimeout = clientSocketSoTimeout;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public boolean isKeepAlive() {
            return keepAlive;
        }

        public void setKeepAlive(boolean keepAlive) {
            this.keepAlive = keepAlive;
        }

        public int getLinger() {
            return linger;
        }

        public void setLinger(int linger) {
            this.linger = linger;
        }

        public boolean isReuseAddress() {
            return reuseAddress;
        }

        public void setReuseAddress(boolean reuseAddress) {
            this.reuseAddress = reuseAddress;
        }

        public int getServerConnectionBacklog() {
            return serverConnectionBacklog;
        }

        public void setServerConnectionBacklog(int serverConnectionBacklog) {
            this.serverConnectionBacklog = serverConnectionBacklog;
        }

        public int getServerSocketSoTimeout() {
            return serverSocketSoTimeout;
        }

        public void setServerSocketSoTimeout(int serverSocketSoTimeout) {
            this.serverSocketSoTimeout = serverSocketSoTimeout;
        }

        public boolean isTcpNoDelay() {
            return tcpNoDelay;
        }

        public void setTcpNoDelay(boolean tcpNoDelay) {
            this.tcpNoDelay = tcpNoDelay;
        }

        public boolean isOptimizedForMultiplexing() {
            return optimizedForMultiplexing;
        }

        public void setOptimizedForMultiplexing(boolean optimizedForMultiplexing) {
            this.optimizedForMultiplexing = optimizedForMultiplexing;
        }


        public int getMaxAsyncWriteQueueSize() {
            return maxAsyncWriteQueueSize;
        }

        public void setMaxAsyncWriteQueueSize(int maxAsyncWriteQueueSize) {
            this.maxAsyncWriteQueueSize = maxAsyncWriteQueueSize;
        }

        public boolean isMaxAyncWriteQueueSizeOverride() {
            return maxAyncWriteQueueSizeOverride;
        }

        public void setMaxAyncWriteQueueSizeOverride(boolean maxAyncWriteQueueSizeOverride) {
            this.maxAyncWriteQueueSizeOverride = maxAyncWriteQueueSizeOverride;
        }
    }

}
