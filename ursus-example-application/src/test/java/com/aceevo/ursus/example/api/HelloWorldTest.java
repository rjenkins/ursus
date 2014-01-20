package com.aceevo.ursus.example.api;

import com.aceevo.ursus.core.UrsusApplicationBinder;
import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import com.aceevo.ursus.example.model.Hello;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static junit.framework.Assert.assertEquals;

public class HelloWorldTest extends JerseyTest {

    @Override
    protected Application configure() {
        ResourceConfig resourceConfig = new ResourceConfig();
        ExampleApplicationConfiguration exampleApplicationConfiguration = new ExampleApplicationConfiguration();
        exampleApplicationConfiguration.setName("Ray");
        resourceConfig.registerInstances(new UrsusApplicationBinder(exampleApplicationConfiguration));
        resourceConfig.register(HelloWorldResource.class);
        return resourceConfig;
    }

    @Test
    public void helloTest() {
        Response response =  target("/hello").request().get();
        assertEquals(200, response.getStatus());
        assertEquals("Ray", response.readEntity(Hello.class).getName());
    }

    @Test
    public void sayHello() {
        Entity<Hello> helloEntity = Entity.entity(new Hello("Bob"), MediaType.APPLICATION_JSON_TYPE);
        Response response = target("/hello").request().post(helloEntity);
        assertEquals(201, response.getStatus());
        assertEquals("http://localhost:9998/hello/Bob", response.getLocation().toString());
    }
}
