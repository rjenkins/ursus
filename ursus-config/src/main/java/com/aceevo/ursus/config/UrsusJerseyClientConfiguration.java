package com.aceevo.ursus.config;


import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class UrsusJerseyClientConfiguration extends UrsusHttpClientConfiguration {

    @Min(1)
    @Max(16 * 1024)
    @JsonProperty
    private int maxTotalThread = 100;

    @Min(1)
    @Max(16 * 1024)
    @JsonProperty
    private int defaultMaxPerRoute = 20;

    @JsonProperty
    private boolean gzipEnabled = true;

    @JsonProperty
    private boolean gzipEnabledForRequests = true;

    @JsonProperty
    private int readTimeout = 2000;

    @JsonProperty
    private int connectTimeout = 500;


    public boolean isGzipEnabled() {
        return gzipEnabled;
    }

    public UrsusJerseyClientConfiguration setGzipEnabled(boolean enabled) {
        this.gzipEnabled = enabled;
        return this;
    }

    public boolean isGzipEnabledForRequests() {
        return gzipEnabledForRequests;
    }

    public UrsusJerseyClientConfiguration setGzipEnabledForRequests(boolean enabled) {
        this.gzipEnabledForRequests = enabled;
        return this;
    }

    public int getMaxTotalThread() {
        return maxTotalThread;
    }

    public void setMaxTotalThread(int maxTotalThread) {
        this.maxTotalThread = maxTotalThread;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getDefaultMaxPerRoute() {
        return defaultMaxPerRoute;
    }

    public void setDefaultMaxPerRoute(int defaultMaxPerRoute) {
        this.defaultMaxPerRoute = defaultMaxPerRoute;
    }
}