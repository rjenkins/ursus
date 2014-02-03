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
import com.aceevo.ursus.config.UrsusConfigurationFactory;
import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration;
import com.google.common.base.Charsets;
import com.google.common.io.Resources;
import com.google.common.util.concurrent.Service;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.nio.NIOTransport;
import org.glassfish.grizzly.nio.transport.TCPNIOTransportBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;


public abstract class UrsusNIOApplication<T extends UrsusNIOApplicationConfiguration, N extends NIOTransport> {

    /**
     * UrsusNIOApplication is the base class for our application
     *
     * @param <T> our type of configuration class
     */

    private String configurationFile;
    private final Class<T> configurationClass;
    private final T configuration;
    private final Set<Service> managedServices = new HashSet<Service>();
    protected N transport;

    final Logger LOGGER = LoggerFactory.getLogger(UrsusNIOApplication.class);

    protected UrsusNIOApplication(String[] args) {

        // Bridge j.u.l in Grizzly with SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Use reflection to find our UrsusJerseyApplicationConfiguration Class
        this.configurationClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        parseArguments(args);
        this.configuration = parseConfiguration();

        configureLogging(configuration);
        FilterChain filterChain = boostrap(configuration, FilterChainBuilder.stateless());
        run(initializeServer(filterChain));
    }

    /**
     * Parse command line arguments for handling
     */
    private void parseArguments(String[] args) {
        if (args == null || args.length == 0)
            return;

        if ("server".equals(args[0]) && args.length >= 2) {
            configurationFile = args[1];
        }
    }

    /**
     * Bootstrap method for configuring resources required for this application.
     *
     * @param t an instance of our @{link UrsusJerseyApplicationConfiguration<T>} type
     */
    protected abstract FilterChain boostrap(T t, FilterChainBuilder filterChainBuilder);

    /**
     * Hands an UrsusJerseyApplication a fully initialized and configured @{link HttpServer} instance for any additional
     * programmatic configuration user may with to perform prior to starting.
     *
     * @param transport a fully initialized @{link HttpServer} instance with our applications configuration.
     */
    protected abstract void run(NIOTransport transport);

    /**
     * Determine our default YAML configuration file name and parse
     *
     * @return and instance of type T which extends {@link com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration}
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
    protected abstract N initializeServer(FilterChain filterChain);

    /**
     * Convenience method for starting this Grizzly HttpServer
     *
     * @param transport
     */
    protected void startWithShutdownHook(final NIOTransport transport) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping Grizzly HttpServer...");
                try {
                    transport.stop();
                    LOGGER.info("Stopping all managed services...");
                    for (Service service : managedServices) {
                        service.stopAsync();
                    }
                } catch (IOException e) {
                    LOGGER.error("failed to stop transport", e);
                }
            }
        }, "shutdownHook"));

        try {
            LOGGER.info("Starting all managed services...");
            for (Service service : managedServices) {
                service.startAsync();
            }

            transport.bind("localhost", 20389);
            transport.start();
            printBanner(getClass().getSimpleName());
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("There was an error while starting Grizzly HTTP server.", e);
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
