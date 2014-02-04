package com.aceevo.ursus.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 1/26/14
 * Time: 4:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class UrsusNIOApplicationConfiguration extends UrsusConfiguration {

    @JsonProperty(required = true)
    private Server server;

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public static class Server {

        @JsonProperty(required = true)
        private String host;

        @JsonProperty(required = false)
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
}
