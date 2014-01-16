package com.aceevo.ursus.example.api;

import com.aceevo.ursus.example.ExampleApplicationConfiguration;
import com.aceevo.ursus.example.model.Hello;
import org.junit.Test;

import javax.ws.rs.core.Response;
import java.lang.reflect.Field;

import static junit.framework.Assert.assertEquals;

public class HelloWorldResourceTest {

    private final ExampleApplicationConfiguration exampleApplicationConfiguration = new ExampleApplicationConfiguration();
    private final HelloWorldResource resource = new HelloWorldResource();

    @Test
    public void getHello() throws Exception {
        exampleApplicationConfiguration.setName("Ray");
        Field field = resource.getClass().getDeclaredField("exampleApplicationConfiguration");
        field.setAccessible(true);
        field.set(resource, exampleApplicationConfiguration);
        Response response = resource.hello();
        assertEquals(200, response.getStatus());
        assertEquals("Ray", ((Hello) response.getEntity()).getName());
    }
}
