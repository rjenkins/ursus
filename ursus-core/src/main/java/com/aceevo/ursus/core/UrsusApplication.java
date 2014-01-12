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
package com.aceevo.ursus.core;

import ch.qos.logback.core.FileAppender;
import com.aceevo.ursus.config.UrsusApplicationConfiguration;
import com.aceevo.ursus.config.UrsusConfigurationFactory;
import com.aceevo.ursus.websockets.GrizzlyServerFilter;
import com.aceevo.ursus.websockets.TyrusAddOn;
import com.aceevo.ursus.websockets.UrsusTyrusServerContainer;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.Service;
import org.glassfish.grizzly.http.CompressionConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.server.NetworkListener;
import org.glassfish.grizzly.http.server.ServerConfiguration;
import org.glassfish.grizzly.http.server.StaticHttpHandler;
import org.glassfish.grizzly.ssl.SSLContextConfigurator;
import org.glassfish.grizzly.ssl.SSLEngineConfigurator;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpContainer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpoint;
import javax.ws.rs.ext.ExceptionMapper;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;

/**
 * UrsusApplication is the base class for our application
 *
 * @param <T> our type of configuration class
 */
public abstract class UrsusApplication<T extends UrsusApplicationConfiguration> extends ResourceConfig {

    private Class exceptionMapperClass = UrsusExceptionMapper.class;
    final HttpServer httpServer = new HttpServer();
    private String configurationFile;
    private final Class<T> configurationClass;
    private final T configuration;
    private final Set<Service> managedServices = new HashSet<Service>();
    private final String[] args;

    final Logger LOGGER = LoggerFactory.getLogger(UrsusApplication.class);

    protected UrsusApplication(String[] args) {

        this.args = args;

        // Bridge j.u.l in Grizzly with SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Use reflection to find our UrsusApplicationConfiguration Class
        this.configurationClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        parseArguments();
        this.configuration = parseConfiguration();

        configureLogging(configuration);
        boostrap(configuration);
        run(initializeServer());
    }

    /**
     * Parse command line arguments for handling
     */
    private void parseArguments() {
        if(args == null || args.length == 0)
            return;

        if("server".equals(args[0]) && args.length >= 2) {
            configurationFile = args[1];
        }
    }

    /**
     * Bootstrap method for configuring resources required for this application.
     *
     * @param t an instance of our @{link UrsusApplicationConfiguration<T>} type
     */
    protected abstract void boostrap(T t);

    /**
     * Hands an UrsusApplication a fully initialized and configured @{link HttpServer} instance for any additional
     * programmatic configuration user may with to perform prior to starting.
     *
     * @param httpServer a fully initialized @{link HttpServer} instance with our applications configuration.
     */
    protected abstract void run(HttpServer httpServer);

    /**
     * Provide support for defining a custom {@link ExceptionMapper}
     *
     * @param clazz Class of our custom @{link ExceptionMapper}
     * @param <T>   a type that extends @{link ExceptionMapper}
     */
    protected <T extends ExceptionMapper> void setExceptionMapper(Class<T> clazz) {
        exceptionMapperClass = clazz;
    }

    /**
     * Provide support for registering instances of {@link Service} whose lifecycle
     * will be tied to this Grizzly HTTP Server
     * @param service @{link Service} to register with this Grizzly HTTP Server instance
     */
    protected void register(Service service) {
        managedServices.add(service);
    }

    /**
     * Determine our default YAML configuration file name and parse
     * @return and instance of type T which extends {@link UrsusApplicationConfiguration}
     */
    private T parseConfiguration() {

        //Fetch Server Configuration
        configurationFile = configurationFile != null ? configurationFile : getClass().getSimpleName().toLowerCase() + ".yml";
        UrsusConfigurationFactory ursusConfigurationFactory = new UrsusConfigurationFactory(configurationFile, configurationClass);
        return (T) ursusConfigurationFactory.getConfiguration();
    }

    /**
     * Starts Grizzly HTTP server exposing JAX-RS resources and UrsusWebSocketApplication defined
     * in this application.
     */
    private HttpServer initializeServer() {

        //Register Jackson support
        packages("org.glassfish.jersey.examples.jackson").register(JacksonFeature.class);

        //Add Grizzled ExceptionMapper
        register(exceptionMapperClass);
        GrizzlyHttpContainer grizzlyHttpContainer = ContainerFactory.createContainer(GrizzlyHttpContainer.class, this);

        // Set our ServerConfiguration options
        final ServerConfiguration config = httpServer.getServerConfiguration();
        config.addHttpHandler(grizzlyHttpContainer, configuration.getHttpServer().getRootContext());
        config.setPassTraceRequest(configuration.getHttpServer().isPassTraceRequest());
        config.setTraceEnabled(configuration.getHttpServer().isTraceEnabled());
        config.setJmxEnabled(configuration.getHttpServer().isJmxEnabled());

        //Configure static resource handler if required
        if(configuration.getHttpServer().getStaticResourceDirectory() != null &&
                configuration.getHttpServer().getStaticResourceContextRoot() != null)
            config.addHttpHandler(new StaticHttpHandler(configuration.getHttpServer().getStaticResourceDirectory()),
                    configuration.getHttpServer().getStaticResourceContextRoot());

        // Now an HttpServer and NetworkListener
        final NetworkListener listener = new NetworkListener("grizzly",
                configuration.getHttpServer().getHost(), configuration.getHttpServer().getPort());

        configureListener(listener);
        configureTyrus(configuration, listener);

        httpServer.addListener(listener);

        return httpServer;
    }

    /**
     * Convenience method for starting this Grizzly HttpServer
     *
     * @param httpServer
     */
    protected void startWithShutdownHook(final HttpServer httpServer) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping Grizzly HttpServer...");
                httpServer.stop();
                LOGGER.info("Stopping all managed services...");
                for(Service service : managedServices) {
                    service.stopAsync();
                }
            }
        }, "shutdownHook"));

        try {
            LOGGER.info("Starting all managed services...");
            for(Service service : managedServices) {
                service.startAsync();
            }
            httpServer.start();
            printBanner(getClass().getSimpleName());
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("There was an error while starting Grizzly HTTP server.", e);
        }
    }


    /**
     * Configures the NetworkListener from properties defined in the UrsusApplicationConfiguration
     *
     * @param listener
     */
    private void configureListener(NetworkListener listener) {

        // Fetch our UrsusApplicationConfiguration for NetworkListener and configure
        UrsusApplicationConfiguration.NetworkListener networkListenerConfig = configuration.getHttpServer().getNetworkListener();

        if (networkListenerConfig != null) {
            listener.setAuthPassThroughEnabled(networkListenerConfig.isAuthPassThroughEnabled());
            listener.setMaxFormPostSize(networkListenerConfig.getMaxFormPostSize());
            listener.setMaxBufferedPostSize(networkListenerConfig.getMaxBufferedPostSize());
            listener.setChunkingEnabled(networkListenerConfig.isChunkingEnabled());

            // Handle SSL Configuration
            if (networkListenerConfig.isSecure()) {
                configureSSLForListener(networkListenerConfig, listener);
            }

            // Handle Compression Configuration
            if (networkListenerConfig.getCompression() != null) {
                configureCompressionForListener(networkListenerConfig, listener);
            }
        }
    }

    private void configureCompressionForListener(UrsusApplicationConfiguration.NetworkListener networkListenerConfig, NetworkListener listener) {
        UrsusApplicationConfiguration.Compression compression = networkListenerConfig.getCompression();
        CompressionConfig compressionConfig = listener.getCompressionConfig();

        compressionConfig.setCompressionMode(CompressionConfig.CompressionMode.fromString(compression.getCompressionMode()));
        compressionConfig.setCompressionMinSize(compression.getCompressionMinSize());
        compressionConfig.setCompressableMimeTypes(new HashSet(compression.getCompressableMimeTypes()));
        compressionConfig.setNoCompressionUserAgents(new HashSet(compression.getNoCompressionUserAgents()));
    }

    private void configureSSLForListener(UrsusApplicationConfiguration.NetworkListener networkListenerConfig, NetworkListener listener) {

        if (networkListenerConfig.getSslContext() == null) {
            throw new RuntimeException("Ursus Application Configuration error: secure set to true and SSLContext is null");
        }

        SSLContextConfigurator sslContext = new SSLContextConfigurator();
        sslContext.setKeyStoreFile(networkListenerConfig.getSslContext().getKeyStoreFile());
        sslContext.setKeyStorePass(networkListenerConfig.getSslContext().getKeyStorePass());
        sslContext.setTrustStoreFile(networkListenerConfig.getSslContext().getTrustStoreFile());
        sslContext.setTrustStorePass(networkListenerConfig.getSslContext().getTrustStorePass());

        listener.setSecure(true);

        if (networkListenerConfig.getSslEngine() == null) {
            // Use defaults
            listener.setSSLEngineConfig(new SSLEngineConfigurator(sslContext));
        } else {
            UrsusApplicationConfiguration.SSLEngine sslEngine =
                    networkListenerConfig.getSslEngine();

            SSLEngineConfigurator sslEngineConfigurator = new SSLEngineConfigurator(sslContext);

            sslEngineConfigurator.setEnabledCipherSuites(sslEngine.getEnabledCipherSuites());
            sslEngineConfigurator.setEnabledProtocols(sslEngine.getEnabledProtocols());
            sslEngineConfigurator.setCipherConfigured(sslEngine.isCipherConfigured());
            sslEngineConfigurator.setProtocolConfigured(sslEngine.isProtocolConfigured());
            sslEngineConfigurator.setClientMode(sslEngine.isClientMode());
            sslEngineConfigurator.setNeedClientAuth(sslEngine.isNeedClientAuth());
            sslEngineConfigurator.setWantClientAuth(sslEngine.isWantClientAuth());

            listener.setSSLEngineConfig(sslEngineConfigurator);
        }
    }

    private void configureLogging(T configuration) {

        // set logging level and file programmatically if defined
        if (configuration.getLogging() != null) {
            ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
            FileAppender fileAppender = (FileAppender) rootLogger.getAppender("FILE");

            rootLogger.detachAppender(fileAppender);
            rootLogger.setLevel(configuration.getLogging().getLevel());

            if (configuration.getLogging().getFileName() != null) {
                fileAppender.setFile(configuration.getLogging().getFileName());
            }
            rootLogger.addAppender(fileAppender);
            fileAppender.start();
        }
    }

    private void configureTyrus(T configuration, NetworkListener listener) {

        Set<Class<?>> classes = getClasses();
        Set<Class<?>> tyrusEndpoints = new HashSet<Class<?>>();
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(ServerEndpoint.class))
                tyrusEndpoints.add(clazz);
        }

        if (tyrusEndpoints.size() > 0) {
            UrsusTyrusServerContainer ursusTyrusServerContainer = null;
            try {
                ursusTyrusServerContainer = new UrsusTyrusServerContainer(tyrusEndpoints,
                        configuration.getHttpServer().getRootContext(), configuration.getTyrus().getIncomingBufferSize());
                GrizzlyServerFilter grizzlyServerFilter = new GrizzlyServerFilter(ursusTyrusServerContainer);
                listener.registerAddOn(new TyrusAddOn(grizzlyServerFilter));
            } catch (DeploymentException e) {
                throw new RuntimeException("Unable to deploy WebSocket endpoints", e);
            }
        }
    }

    protected void printBanner(String name) {
        try {
            final String banner = Resources.toString(Resources.getResource("banner.txt"),
                    Charsets.UTF_8)
                    .replace("\n", String.format("%n"));
            LOGGER.info(String.format("Starting {}%n{}"), name, banner);
        } catch (Exception ex) {
            // don't display the banner if there isn't one
            LOGGER.info("Starting {}", name);
        }
    }
}
