package com.aceevo.ursus.example.simplehttpclient;

import com.aceevo.ursus.client.UrsusHttpClientBuilder;
import com.aceevo.ursus.config.UrsusHttpClientConfiguration;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.*;

/**
 *
 * A Simple HttpClient example
 *
 * Make sure to start the ursus-simple-rest-application before running
 *
 */
public class SimpleHttpClientExample {

    public SimpleHttpClientExample() {

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        try {
            UrsusHttpClientConfiguration ursusHttpClientConfiguration =
                    mapper.readValue(open("httpClient.yml"), UrsusHttpClientConfiguration.class);
            HttpClient httpClient = new UrsusHttpClientBuilder().build("example", ursusHttpClientConfiguration);
            HttpResponse httpResponse = httpClient.execute(new HttpGet("http://localhost:8080/hello"));

            // Convert to POJO
            Hello hello = new ObjectMapper().readValue(httpResponse.getEntity().getContent(), Hello.class);
            System.out.println("Name is: " + hello.getName());


        } catch (IOException e) {
            throw new RuntimeException("Error parsing config file: " + e);
        }

    }

    public static void main(String[] args) {
        new SimpleHttpClientExample();
    }

    private InputStream open(String configurationFile) throws IOException {
        final File file = new File(configurationFile);
        if (!file.exists()) {
            throw new FileNotFoundException("File " + file + " not found");
        }

        return new FileInputStream(file);
    }
}
