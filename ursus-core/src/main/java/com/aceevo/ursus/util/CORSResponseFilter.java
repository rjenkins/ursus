package com.aceevo.ursus.util;

import com.google.common.base.Joiner;
import com.google.common.net.HttpHeaders;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import java.io.IOException;

public class CORSResponseFilter implements ContainerResponseFilter {

    private String ALLOWED_METHODS = "GET, OPTIONS, PUT, POST, DELETE";
    private String ALLOWED_HEADERS = Joiner.on(",").join(HttpHeaders.ORIGIN, HttpHeaders.ACCEPT, HttpHeaders.ACCEPT_ENCODING, HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE, HttpHeaders.X_REQUESTED_WITH);

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
        responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
        responseContext.getHeaders().add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.LOCATION);
    }
}

