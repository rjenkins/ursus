package com.aceevo.ursus.example.simplejerseyclient;

import com.aceevo.ursus.client.UrsusJerseyClientBuilder;
import com.aceevo.ursus.config.UrsusJerseyClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.net.URI;

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 1/2/14
 * Time: 10:31 PM
 * To change this template use File | Settings | File Templates.
 */
public class SimpleJerseyClientExample {

    public SimpleJerseyClientExample() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {

            UrsusJerseyClientConfiguration ursusJerseyClientConfiguration =
                    mapper.readValue(open("jerseyClient.yml"), UrsusJerseyClientConfiguration.class);
            Client client = new UrsusJerseyClientBuilder().using(ursusJerseyClientConfiguration).build();

            Invocation.Builder invocationBuilder = client.target(URI.create("http://localhost:8080/hello"))
                    .request(MediaType.APPLICATION_JSON_TYPE);

            Hello hello = invocationBuilder.get().readEntity(Hello.class);
            System.out.println("Name is: " + hello.getName());


        } catch (IOException e) {
            throw new RuntimeException("Error parsing config file: " + e);
        }
    }

    public static void main(String[] args) {
        new SimpleJerseyClientExample();
    }

    private InputStream open(String configurationFile) throws IOException {
        final File file = new File(configurationFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }





}
