package com.aceevo.ursus.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UrsusHttpClientConfiguration {

    // Determines the default socket timeout value for non-blocking I/O operations.
    @JsonProperty
    private int timeout = 500;

    @JsonProperty
    private int connectionTimeout = 500;

    @JsonProperty
    private int timeToLive = 1000 * 3600;

    @JsonProperty
    private boolean cookiesEnabled = false;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty
    private int maxConnections = 1024;

    @Min(1)
    @Max(Integer.MAX_VALUE)
    @JsonProperty
    private int maxConnectionsPerRoute = 1024;

    @JsonProperty
    private int keepAlive = 2000;

    @Min(0)
    @Max(1000)
    @JsonProperty
    private int retries = 0;

    @JsonProperty
    private int bufferSize = 8192;

    public int getKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(int keepAlive) {
        this.keepAlive = keepAlive;
    }

    public int getMaxConnectionsPerRoute() {
        return maxConnectionsPerRoute;
    }

    public void setMaxConnectionsPerRoute(int maxConnectionsPerRoute) {
        this.maxConnectionsPerRoute = maxConnectionsPerRoute;
    }

    public int getTimeout() {
        return timeout;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getTimeToLive() {
        return timeToLive;
    }

    public boolean isCookiesEnabled() {
        return cookiesEnabled;
    }

    public void setTimeout(int duration) {
        this.timeout = duration;
    }

    public void setConnectionTimeout(int duration) {
        this.connectionTimeout = duration;
    }

    public void setTimeToLive(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void setCookiesEnabled(boolean enabled) {
        this.cookiesEnabled = enabled;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public int getRetries() {
        return retries;
    }

    public void setRetries(int retries) {
        this.retries = retries;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }
}

