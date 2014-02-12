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

package com.aceevo.ursus.client;

import com.aceevo.ursus.config.UrsusJerseyClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.glassfish.jersey.apache.connector.ApacheClientProperties;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

/**
 * A convenience class for building {@link Client} instances.
 * <ul>
 * <li>Sets READ_TIMEOUT</li>
 * <li>Sets CONNECTION_TIMEOUT</li>
 * <li>Disables cookie management by default</li>
 * </ul>
 */
public class UrsusJerseyClientBuilder {

    private UrsusJerseyClientConfiguration configuration = new UrsusJerseyClientConfiguration();

    /**
     * Uses the given {@link UrsusJerseyClientConfiguration}.
     *
     * @param configuration a configuration object
     * @return {@code this}
     */
    public UrsusJerseyClientBuilder using(UrsusJerseyClientConfiguration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * Builds the {@link Client} instance.
     *
     * @return a fully-configured {@link Client}
     */

    public Client build() {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.property(ClientProperties.READ_TIMEOUT, configuration.getReadTimeout());
        clientConfig.property(ClientProperties.CONNECT_TIMEOUT, configuration.getConnectTimeout());
        clientConfig.property(ApacheClientProperties.DISABLE_COOKIES, true);

        PoolingHttpClientConnectionManager poolingClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingClientConnectionManager.setMaxTotal(configuration.getMaxTotalThread());
        poolingClientConnectionManager.setDefaultMaxPerRoute(configuration.getDefaultMaxPerRoute());

        clientConfig.property(ApacheClientProperties.CONNECTION_MANAGER, poolingClientConnectionManager);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.registerModule(new GuavaModule());

        // create JsonProvider to provide custom ObjectMapper
        JacksonJaxbJsonProvider provider = new JacksonJaxbJsonProvider();
        provider.setMapper(mapper);

        return ClientBuilder.newBuilder()
                .register(provider)
                .withConfig(clientConfig)
                .build();
    }
}
