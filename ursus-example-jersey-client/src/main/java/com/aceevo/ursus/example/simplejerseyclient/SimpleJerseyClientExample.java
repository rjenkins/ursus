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

public class SimpleJerseyClientExample {

    public SimpleJerseyClientExample(String configFile) {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {

            UrsusJerseyClientConfiguration ursusJerseyClientConfiguration =
                    mapper.readValue(open(configFile), UrsusJerseyClientConfiguration.class);
            Client client = new UrsusJerseyClientBuilder().using(ursusJerseyClientConfiguration).build();

            Invocation.Builder invocationBuilder = client.target(URI.create("http://localhost:8080/hello"))
                    .request(MediaType.APPLICATION_JSON_TYPE);

            Hello hello = invocationBuilder.get().readEntity(Hello.class);
            System.out.println("Name is: " + hello.getName());


        } catch (IOException e) {
            throw new RuntimeException("Error parsing config file: " + e);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.printf("Usage: %s <config.yml>%n", SimpleJerseyClientExample.class.getName());
            System.exit(1);
        }
        new SimpleJerseyClientExample(args[0]);
        if (Boolean.valueOf(System.getProperty("wait"))) {
            System.out.println("Press CTRL^C to exit..");
            Thread.currentThread().join();
        }
    }

    private InputStream open(String configurationFile) throws IOException {
        final File file = new File(configurationFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }





}
