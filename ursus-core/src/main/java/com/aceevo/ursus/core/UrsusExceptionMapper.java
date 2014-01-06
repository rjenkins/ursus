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

import org.glassfish.grizzly.utils.Exceptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class UrsusExceptionMapper implements ExceptionMapper<Exception> {

    final Logger logger = LoggerFactory.getLogger(UrsusApplication.class);


    @Override
    public Response toResponse(Exception ex) {
        if (ex instanceof org.codehaus.jackson.map.exc.UnrecognizedPropertyException) {
            logger.info("unrecognized property", ex);
            return Response.status(400).entity(Exceptions.getStackTraceAsString(ex)).type("text/plain").build();
        } else {
            logger.info("handling unknown exception type returning 500: ", ex);
            return Response.status(500).entity(Exceptions.getStackTraceAsString(ex)).type("text/plain")
                    .build();
        }
    }
}