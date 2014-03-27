/**

 Copyright 2013 Ray Jenkins ray@memoization.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */

package com.aceevo.ursus.config;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Our base class for UrsusApplication configuration
 */
public class UrsusJerseyApplicationConfiguration extends UrsusConfiguration {

    @JsonProperty(required = true)
    @Valid
    @NotNull
    private HttpServer httpServer;

    @JsonProperty
    @Valid
    private UrsusHttpClientConfiguration ursusHttpClient;

    @JsonProperty
    private Tyrus tyrus = new Tyrus();

    public HttpServer getHttpServer() {
        return httpServer;
    }

    public void setHttpServer(HttpServer httpServer) {
        this.httpServer = httpServer;
    }

    public UrsusHttpClientConfiguration getUrsusHttpClient() {
        return ursusHttpClient;
    }

    public void setUrsusHttpClient(UrsusHttpClientConfiguration ursusHttpClient) {
        this.ursusHttpClient = ursusHttpClient;
    }

    public Tyrus getTyrus() {
        return tyrus;
    }

    public void setTyrus(Tyrus tyrus) {
        this.tyrus = tyrus;
    }

    public static class HttpServer {

        // An arbitrary optional name for your HTTPServer
        @JsonProperty
        private String name;

        // Hostname or IPAddress to hind to
        @JsonProperty(required = true)
        @NotNull
        private String host;

        // TCP Port this HttpServer will listen on
        @JsonProperty
        private int port = 8080;

        // RootContext for your HttpServer and listener
        @JsonProperty
        private String rootContext = "/";

        // Enable / Disable JMX
        @JsonProperty(required=true)
        private boolean jmxEnabled;

        // boolean to configure if trace requests will
        // be handled by Grizzly or by a configured
        // HttpHandler.
        @JsonProperty
        private boolean passTraceRequest = true;

        //If <tt>enabled</tt> is <tt>true</tt> the <tt>TRACE</tt> method will be
        // respected and a proper response will be generated.  Otherwise, the
        // method will be considered as not allowed and an HTTP 405 will be returned.
        //
        // This method only comes into effect when <code>setPassTraceRequest(false)</code>
        // has been called.
        @JsonProperty
        private boolean traceEnabled = false;

        @JsonProperty
        private NetworkListener networkListener;

        // Root directory to look for static resources
        @JsonProperty
        private String staticResourceDirectory;

        // Root context for static resources
        @JsonProperty
        private String staticResourceContextRoot;

        public HttpServer() {

        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

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

        public boolean isJmxEnabled() {
            return jmxEnabled;
        }

        public void setJmxEnabled(boolean jmxEnabled) {
            this.jmxEnabled = jmxEnabled;
        }

        public boolean isPassTraceRequest() {
            return passTraceRequest;
        }

        public void setPassTraceRequest(boolean passTraceRequest) {
            this.passTraceRequest = passTraceRequest;
        }

        public boolean isTraceEnabled() {
            return traceEnabled;
        }

        public void setTraceEnabled(boolean traceEnabled) {
            this.traceEnabled = traceEnabled;
        }

        public String getRootContext() {
            return rootContext;
        }

        public void setRootContext(String rootContext) {
            this.rootContext = rootContext;
        }

        public NetworkListener getNetworkListener() {
            return networkListener;
        }

        public void setNetworkListener(NetworkListener networkListener) {
            this.networkListener = networkListener;
        }

        public String getStaticResourceDirectory() {
            return staticResourceDirectory;
        }

        public void setStaticResourceDirectory(String staticResourceDirectory) {
            this.staticResourceDirectory = staticResourceDirectory;
        }

        public String getStaticResourceContextRoot() {
            return staticResourceContextRoot;
        }

        public void setStaticResourceContextRoot(String staticResourceContextRoot) {
            this.staticResourceContextRoot = staticResourceContextRoot;
        }
    }


    public static class NetworkListener {

        @JsonProperty
        private boolean authPassThroughEnabled;

        @JsonProperty
        private int maxFormPostSize = 2 * 1024 * 1024;

        @JsonProperty
        private int maxBufferedPostSize = 2 * 1024 * 1024;

        @JsonProperty
        private int maxHttpHeaderSize = -1;

        // Flag indicating whether listener is secured, MUST be set to true
        // to enable SSL support
        @JsonProperty
        private boolean secure;

        // Enable/disable chunking of an HTTP response body if no content length has been explicitly specified.
        // Chunking is enabled by default.
        @JsonProperty
        private boolean chunkingEnabled = true;

        @JsonProperty
        private SSLContext sslContext = null;

        @JsonProperty
        private SSLEngine sslEngine = null;

        @JsonProperty
        private Compression compression = null;

        // KeepAlive in seconds
        @JsonProperty
        private int idleTimeout = 30;

        @JsonProperty
        private int maxRequests = 256;

        @JsonProperty
        // Default of GrizzlyHttpListener is -1 which is indefinite
        // setting it to something reasonable (10s)
        private int transactionTimeout = 10;


        public NetworkListener() {

        }

        public boolean isAuthPassThroughEnabled() {
            return authPassThroughEnabled;
        }

        public void setAuthPassThroughEnabled(boolean authPassThroughEnabled) {
            this.authPassThroughEnabled = authPassThroughEnabled;
        }

        public int getMaxFormPostSize() {
            return maxFormPostSize;
        }

        public void setMaxFormPostSize(int maxFormPostSize) {
            this.maxFormPostSize = maxFormPostSize;
        }

        public int getMaxBufferedPostSize() {
            return maxBufferedPostSize;
        }

        public void setMaxBufferedPostSize(int maxBufferedPostSize) {
            this.maxBufferedPostSize = maxBufferedPostSize;
        }

        public boolean isSecure() {
            return secure;
        }

        public void setSecure(boolean secure) {
            this.secure = secure;
        }

        public boolean isChunkingEnabled() {
            return chunkingEnabled;
        }

        public void setChunkingEnabled(boolean chunkingEnabled) {
            this.chunkingEnabled = chunkingEnabled;
        }

        public SSLContext getSslContext() {
            return sslContext;
        }

        public void setSslContext(SSLContext sslContext) {
            this.sslContext = sslContext;
        }

        public SSLEngine getSslEngine() {
            return sslEngine;
        }

        public void setSslEngine(SSLEngine sslEngine) {
            this.sslEngine = sslEngine;
        }

        public Compression getCompression() {
            return compression;
        }

        public void setCompression(Compression compression) {
            this.compression = compression;
        }

        public int getIdleTimeout() {
            return idleTimeout;
        }

        public void setIdleTimeout(int idleTimeout) {
            this.idleTimeout = idleTimeout;
        }

        public int getMaxRequests() {
            return maxRequests;
        }

        public void setMaxRequests(int maxRequests) {
            this.maxRequests = maxRequests;
        }

        public int getTransactionTimeout() {
            return transactionTimeout;
        }

        public void setTransactionTimeout(int transactionTimeout) {
            this.transactionTimeout = transactionTimeout;
        }

        public int getMaxHttpHeaderSize() {
            return maxHttpHeaderSize;
        }

        public void setMaxHttpHeaderSize(int maxHttpHeaderSize) {
            this.maxHttpHeaderSize = maxHttpHeaderSize;
        }
    }

    /**
     * Wrapper configuration class for configuring {@link javax.net.ssl.SSLEngine}
     */

    public static class SSLEngine {

        @JsonProperty
        private String[] enabledCipherSuites = null;

        @JsonProperty
        private String[] enabledProtocols = null;

        @JsonProperty
        private boolean clientMode = true;

        @JsonProperty
        private boolean needClientAuth = false;

        @JsonProperty
        private boolean wantClientAuth = false;

        @JsonProperty
        private boolean protocolConfigured = false;

        @JsonProperty
        private boolean cipherConfigured = false;


        public String[] getEnabledCipherSuites() {
            return enabledCipherSuites;
        }

        public void setEnabledCipherSuites(String[] enabledCipherSuites) {
            this.enabledCipherSuites = enabledCipherSuites;
        }

        public String[] getEnabledProtocols() {
            return enabledProtocols;
        }

        public void setEnabledProtocols(String[] enabledProtocols) {
            this.enabledProtocols = enabledProtocols;
        }

        public boolean isClientMode() {
            return clientMode;
        }

        public void setClientMode(boolean clientMode) {
            this.clientMode = clientMode;
        }

        public boolean isNeedClientAuth() {
            return needClientAuth;
        }

        public void setNeedClientAuth(boolean needClientAuth) {
            this.needClientAuth = needClientAuth;
        }

        public boolean isWantClientAuth() {
            return wantClientAuth;
        }

        public void setWantClientAuth(boolean wantClientAuth) {
            this.wantClientAuth = wantClientAuth;
        }

        public boolean isProtocolConfigured() {
            return protocolConfigured;
        }

        public void setProtocolConfigured(boolean protocolConfigured) {
            this.protocolConfigured = protocolConfigured;
        }

        public boolean isCipherConfigured() {
            return cipherConfigured;
        }

        public void setCipherConfigured(boolean cipherConfigured) {
            this.cipherConfigured = cipherConfigured;
        }

        public SSLEngine() {

        }
    }

    /**
     * Configuration Wrapper for SSLContextConfigurator
     */
    public static class SSLContext {

        // Sets key store file name, also makes sure that if other key store
        // configuration parameters are not set to set them to default values.
        // Method resets key store bytes if any have been set before via

        @JsonProperty(required = true)
        private String keyStoreFile;

        // Password of keystore to set.
        @JsonProperty(required = true)
        private String keyStorePass;

        // Sets trust store file name, also makes sure that if other trust store
        // configuration parameters are not set to set them to default values.
        // Method resets trust store bytes if any have been set before via
        @JsonProperty(required = true)
        private String trustStoreFile;

        // Passwword for the trustStore
        @JsonProperty(required = true)
        private String trustStorePass;

        public SSLContext() {

        }

        public String getKeyStoreFile() {
            return keyStoreFile;
        }

        public void setKeyStoreFile(String keyStoreFile) {
            this.keyStoreFile = keyStoreFile;
        }

        public String getKeyStorePass() {
            return keyStorePass;
        }

        public void setKeyStorePass(String keyStorePass) {
            this.keyStorePass = keyStorePass;
        }

        public String getTrustStoreFile() {
            return trustStoreFile;
        }

        public void setTrustStoreFile(String trustStoreFile) {
            this.trustStoreFile = trustStoreFile;
        }

        public String getTrustStorePass() {
            return trustStorePass;
        }

        public void setTrustStorePass(String trustStorePass) {
            this.trustStorePass = trustStorePass;
        }
    }

    public static class Compression {

        @JsonProperty(required = true)
        private String compressionMode = "OFF"; // ON, OFF or FORCE

        // the min size of the entities, which will be compressed
        @JsonProperty
        private int compressionMinSize = 1;

        // mime types of the enitties, which will be compressed
        @JsonProperty
        private List<String> compressableMimeTypes = new ArrayList<String>(0);

        // the user-agents, for which the payload will never be compressed
        @JsonProperty
        private List<String> noCompressionUserAgents = new ArrayList<String>(0);

        public Compression() {

        }

        public String getCompressionMode() {
            return compressionMode;
        }

        public void setCompressionMode(String compressionMode) {
            this.compressionMode = compressionMode;
        }

        public int getCompressionMinSize() {
            return compressionMinSize;
        }

        public void setCompressionMinSize(int compressionMinSize) {
            this.compressionMinSize = compressionMinSize;
        }

        public List<String> getCompressableMimeTypes() {
            return compressableMimeTypes;
        }

        public void setCompressableMimeTypes(List<String> compressableMimeTypes) {
            this.compressableMimeTypes = compressableMimeTypes;
        }

        public List<String> getNoCompressionUserAgents() {
            return noCompressionUserAgents;
        }

        public void setNoCompressionUserAgents(List<String> noCompressionUserAgents) {
            this.noCompressionUserAgents = noCompressionUserAgents;
        }
    }


    public static class Tyrus {

        @JsonProperty
        private int incomingBufferSize = 4194315;

        public int getIncomingBufferSize() {
            return incomingBufferSize;
        }

        public void setIncomingBufferSize(int incomingBufferSize) {
            this.incomingBufferSize = incomingBufferSize;
        }
    }
}
