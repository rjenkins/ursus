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

package com.aceevo.ursus.websockets;

import org.glassfish.tyrus.core.TyrusWebSocketEngine;
import org.glassfish.tyrus.server.TyrusServerContainer;
import org.glassfish.tyrus.spi.WebSocketEngine;

import javax.websocket.DeploymentException;
import javax.websocket.server.ServerEndpointConfig;
import java.util.Set;

public class UrsusTyrusServerContainer extends TyrusServerContainer {

    private final Set<Class<?>> classes;
    private final WebSocketEngine engine;
    private final String contextPath;
    private Set<ServerEndpointConfig> serverEndpointConfigs;

    public UrsusTyrusServerContainer(Set<Class<?>> classes, String contextPath, int incomingBufferSize) throws DeploymentException {
        super(classes);
        this.classes = classes;
        this.contextPath = contextPath;
        this.engine = new TyrusWebSocketEngine(this, incomingBufferSize, null);
        init();
    }

    public UrsusTyrusServerContainer(Set<Class<?>> classes, Set<ServerEndpointConfig> serverEndpointConfigs, String contextPath,
                                     int incomingBufferSize) throws DeploymentException {
        super(classes);
        this.classes = classes;
        this.contextPath = contextPath;
        this.engine = new TyrusWebSocketEngine(this, incomingBufferSize, null);
        this.serverEndpointConfigs = serverEndpointConfigs;
        init();
    }


    @Override
    public void register(Class<?> endpointClass) throws DeploymentException {
        engine.register(endpointClass, contextPath);
    }

    @Override
    public void register(ServerEndpointConfig serverEndpointConfig) throws DeploymentException {
        engine.register(serverEndpointConfig, contextPath);
    }

    @Override
    public WebSocketEngine getWebSocketEngine() {
        return engine;
    }

    public void init() throws DeploymentException {
        // deploy all the annotated endpoints
        for (Class<?> endpointClass : classes) {
            register(endpointClass);
        }

        for(ServerEndpointConfig serverEndpointConfig : serverEndpointConfigs) {
            register(serverEndpointConfig);
        }
    }

}
