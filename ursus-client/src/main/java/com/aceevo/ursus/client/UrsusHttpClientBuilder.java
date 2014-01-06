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

import com.aceevo.ursus.config.UrsusHttpClientConfiguration;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.NoConnectionReuseStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;

/**
 * A HttpClient wrapper class, setStrategies method and configuration classes.
 * <p/>
 * Among other things,
 * <ul>
 * <li>Disables stale connection checks</li>
 * <li>Disables Nagle's algorithm</li>
 * <li>Disables cookie management by default</li>
 * </ul>
 */
public class UrsusHttpClientBuilder {

    private static final HttpRequestRetryHandler NO_RETRIES = new HttpRequestRetryHandler() {
        @Override
        public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
            return false;
        }
    };

    /**
     * Builds the {@link HttpClient}.
     *
     * @return an {@link HttpClient}
     */
    public HttpClient build(String name, UrsusHttpClientConfiguration configuration) {
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        setStrategiesForClient(httpClientBuilder, configuration);

        SocketConfig socketConfig = SocketConfig.copy(SocketConfig.DEFAULT)
                .setSoTimeout(configuration.getTimeout())
                .setTcpNoDelay(true)
                .setSoReuseAddress(true).build();

        ConnectionConfig connectionConfig = ConnectionConfig.copy(ConnectionConfig.DEFAULT)
                .setBufferSize(configuration.getBufferSize()).build();

        httpClientBuilder.setDefaultSocketConfig(socketConfig);
        httpClientBuilder.setDefaultConnectionConfig(connectionConfig);
        return httpClientBuilder.build();
    }

    /**
     * Add strategies to client such as ConnectionReuseStrategy and KeepAliveStrategy Note that this
     * method mutates the client object by setting the strategies
     *
     * @param httpClientBuilder The InstrumentedHttpClient that should be configured with strategies
     */
    protected void setStrategiesForClient(HttpClientBuilder httpClientBuilder, UrsusHttpClientConfiguration configuration) {
        final long keepAlive = configuration.getKeepAlive();

        // don't keep alive the HTTP connection and thus don't reuse the TCP socket
        if (keepAlive == 0) {
            httpClientBuilder.setConnectionReuseStrategy(new NoConnectionReuseStrategy());
        } else {
            httpClientBuilder.setConnectionReuseStrategy(new DefaultConnectionReuseStrategy());
            // either keep alive based on response header Keep-Alive,
            // or if the server can keep a persistent connection (-1), then override based on client's configuration
            httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                    final long duration = super.getKeepAliveDuration(response, context);
                    return (duration == -1) ? keepAlive : duration;
                }
            });
        }

        if (configuration.getRetries() == 0) {
            httpClientBuilder.setRetryHandler(NO_RETRIES);
        } else {
            httpClientBuilder.setRetryHandler(new DefaultHttpRequestRetryHandler(configuration.getRetries(),
                    false));
        }
    }

}