package com.aceevo.ursus.example.api;

import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import com.aceevo.ursus.example.model.Hello;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Path("hello")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Inject
    ExampleApplicationConfiguration exampleApplicationConfiguration;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello() {
        return Response.ok(new Hello(exampleApplicationConfiguration.getName())).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createHello(@Valid Hello hello, @Context UriInfo uriInfo) {
        URI uri = UriBuilder.fromUri(uriInfo.getRequestUri()).path(hello.getName()).build();
        return Response.created(uri).build();
    }

    @GET
    @Path("/async")
    @Produces(MediaType.APPLICATION_JSON)
    public void sayHelloAsync(final @Suspended AsyncResponse asyncResponse) throws Exception {

        Future<Hello> helloFuture = executorService.submit(new Callable<Hello>() {
            @Override
            public Hello call() throws Exception {
                Thread.currentThread().sleep(5000);
                return new Hello(exampleApplicationConfiguration.getName());
            }
        });

        Hello hello = helloFuture.get();
        asyncResponse.resume(Response.ok(hello).build());
    }
}
