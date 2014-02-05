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

import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration;
import com.google.common.util.concurrent.Service;
import org.glassfish.grizzly.filterchain.FilterChain;
import org.glassfish.grizzly.filterchain.FilterChainBuilder;
import org.glassfish.grizzly.nio.NIOTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.util.HashSet;
import java.util.Set;


/**
 * UrsusNIOApplication is the base class for our {@link NIOTransport} application
 *
 * @param <T> type which extends {@link UrsusNIOApplication}
 * @param <N> type which extends {@link NIOTransport}
 */

public abstract class UrsusNIOApplication<T extends UrsusNIOApplicationConfiguration, N extends NIOTransport> {


    private String configurationFile;
    private final Class<T> configurationClass;
    private final T configuration;
    private final Set<Service> managedServices = new HashSet<Service>();
    private final UrsusApplicationHelper<T> ursusApplicationHelper = new UrsusApplicationHelper();
    protected N transport;

    final Logger LOGGER = LoggerFactory.getLogger(UrsusNIOApplication.class);

    protected UrsusNIOApplication(String[] args) {

        // Bridge j.u.l in Grizzly with SLF4J
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        // Use reflection to find our UrsusNIOApplicationConfiguration Class
        this.configurationClass = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];

        parseArguments(args);

        configurationFile = configurationFile != null ? configurationFile : getClass().getSimpleName().toLowerCase() + ".yml";
        this.configuration = ursusApplicationHelper.parseConfiguration(configurationFile, configurationClass);

        ursusApplicationHelper.configureLogging(configuration);
        FilterChain filterChain = boostrap(configuration, FilterChainBuilder.stateless());
        transport = initializeServer(filterChain, configuration);
        run(transport);


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
     * @param t an instance of our @{link UrsusNIOApplicationConfiguration <T>} type
     */
    protected abstract FilterChain boostrap(T t, FilterChainBuilder filterChainBuilder);

    /**
     * Hands an NIOTransport instance initialized and configured ready for any additional
     * programmatic changes user may wish to perform prior to starting.
     *
     * @param transport a fully initialized @{link NIOTransport} instance with our applications configuration.
     */
    protected abstract void run(N transport);

    /**
     * Hands a {@link FilterChain} and return a new instance of {@link N extends NIOTransport}
     *
     * @param filterChain The {@link FilterChain} to be set as the processor for this {@link NIOTransport}
     * @return {@link N extends NIOTransport}
     */
    protected abstract N initializeServer(FilterChain filterChain, T configuration);

    /**
     * Convenience method for starting {@link NIOTransport}
     *
     * @param transport
     */
    protected void startWithShutdownHook(final NIOTransport transport) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping Grizzly NIOTransport...");
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

            UrsusNIOApplicationConfiguration.Server server = configuration.getServer();
            transport.bind(server.getHost(), server.getPort());
            transport.start();
            ursusApplicationHelper.printBanner(LOGGER, getClass().getSimpleName());
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("There was an error while starting Grizzly HTTP server.", e);
        }
    }
}
