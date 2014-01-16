package com.aceevo.ursus.example.api;

import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import com.aceevo.ursus.example.model.Hello;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HelloWorldResourceTest {

    private final ExampleApplicationConfiguration exampleApplicationConfiguration = new ExampleApplicationConfiguration();
    private final HelloWorldResource resource = new HelloWorldResource();

    @Before
    public void setUp() throws Exception {
        // Oh cool, injection...
        exampleApplicationConfiguration.setName("Ray");
        Field field = resource.getClass().getDeclaredField("exampleApplicationConfiguration");
        field.setAccessible(true);
        field.set(resource, exampleApplicationConfiguration);
    }

    @Test
    public void getHello() {
        Response response = resource.hello();
        assertEquals(200, response.getStatus());
        assertEquals("Ray", ((Hello) response.getEntity()).getName());
    }

    @Test
    public void sayHello() {
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getRequestUri()).thenReturn( UriBuilder.fromPath("http://localhost:8080/hello").build());
        Response response = resource.createHello(new Hello("Bob"),uriInfo);
        assertEquals(201, response.getStatus());
        assertEquals("http://localhost:8080/hello/Bob", response.getLocation().toString());
    }
}
