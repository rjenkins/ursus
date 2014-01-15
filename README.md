# Ursus

### Overview

Ursus is a [Dropwizard](http://dropwizard.codahale.com/) inspired framework for developing lightweight
production-ready web services on the JVM and [Grizzly](https://grizzly.java.net/). Ursus combines Grizzly's
NIO/Event Driven framework and popular libraries such as [Jersey 2.5](https://jersey.java.net/) and
[Project Tyrus - JSR 356: Java API for WebSocket - Reference Implementation](https://tyrus.java.net/) with
the simple conventions found in Dropwizard, allowing you to focus on developing your services rather
than managing their accompanying dependencies, reducing time-to-market and maintenance.

#### Grizzly HTTP Container

Grizzly is a multi-protocol framework built on top of Java NIO. Grizzly aims to simplify development of
robust and scalable servers. Jersey provides a container extension module that enables support for using
Grizzly as a plain vanilla HTTP container that runs JAX-RS applications. Starting a Grizzly server to
run a JAX-RS or Jersey application is one of the most lightweight and easy ways how to expose a
functional RESTful services application.

#### REST / Jersey

Jersey is an open source, production quality, framework for developing RESTful Web Services in Java that
provides support for JAX-RS APIs and serves as a JAX-RS (JSR 311 & JSR 339) Reference Implementation.
Developing RESTful Web services with Jersey is a snap and the latest Jersey implementation included in
Ursus provides support for both asynchronous clients and server side services with [AsyncResponse](https://jax-rs-spec.java.net/nonav/2.0/apidocs/javax/ws/rs/container/AsyncResponse.html)

#### WebSockets / Tyrus

Tyrus is the open source [JSR 356](https://java.net/projects/websocket-spec) Java reference implementation of
WebSockets. The WebSocket protocol provides full-duplex communications between server and remote hosts.
WebSocket is designed to be implemented in web browsers and web servers, but it can be used by any client or server application.
The WebSocket protocol makes possible more interaction between a browser and a web site, facilitating live content
and the creation of real-time applications and services.

#### Additional Libraries

Ursus also includes many libraries found in Dropwizard and other frameworks to help speed up development including,

* [Guava](https://code.google.com/p/guava-libraries/)
* [Joda time](www.joda.org/joda-time/)
* [Hibernate Validator](http://hibernate.org/validator/)
* [Jackson](http://jackson.codehaus.org/) for YAML and JSON support]
* [Logback](http://logback.qos.ch/) and SLF4J(http://www.slf4j.org/) for bridging of Grizzly's java.util.logging
* An Apache based HTTPClient and a Jersey 2.5 client

### Using Ursus
Ursus releases artifacts are hosted on the central repository, to get started with ursus simply add the ```ursus-core```
module to your pom.xml as a dependency.

```xml
 <dependencies>
    <dependency>
        <groupId>com.aceevo</groupId>
        <artifactId>ursus-core</artifactId>
        <version>0.1</version>
    </dependency>
 </dependencies>
```

#### Quick Start
Let's take a look at the ```ursus-example-application``` project a simple HelloWorld service. It's fully implemented, but we're going
to walk through the steps it took to build this project. If it helps you might want to clone the Ursus repro, [https://github.com/rjenkins/ursus.git](https://github.com/rjenkins/ursus.git)
and modify ```ursus-example-application``` as we progress through the quick start building up the application yourself.

Ursus applications require 2 classes, a subclass of ```UrsusApplication``` and a subclass of ```UrsusApplicationConfiguration```. The com.aceevo.ursus.example package contains both.
Let's start by looking at the ```ExampleApplicationConfiguration``` class.

```java
package com.aceevo.ursus.example;

import com.aceevo.ursus.config.UrsusApplicationConfiguration;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

public class ExampleApplicationConfiguration extends UrsusApplicationConfiguration {

    @NotEmpty
    @JsonProperty
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```
Just like Dropwizard we will specify our environment specific configuration parameters in a [YAML](http://www.yaml.org/) configuration
file and these parameters will be deserialized by Jackson and validated. By default Ursus will look for a file whose name is the class name of
your application with a ```.yml``` extension, for this example application that file is named ```exampleapplication.yml```.

In addition to your own environment specific configuration parameters [UrsusApplicationConfiguration](https://github.com/rjenkins/ursus/blob/master/ursus-config/src/main/java/com/aceevo/ursus/config/UrsusApplicationConfiguration.java)
defines a large set of configuration properties that allow you to modify all of the granular configuration options available with Grizzly and many of the other
included libraries simply by adding lines of YAML (more on that later).

#### Creating our YAML File

Let's allow our ExampleApplication the ability to specify who it should say Hello to. For this we'll use the name property we created in
```ExampleApplicationConfiguration```. Now we can define this parameter in ```exampleapplication.yml```

```yaml
httpServer:
  host: localhost
name: Ray
```

Great! Let's move on to creating our first application.

#### Creating an UrsusApplication

We'll name our application ```ExampleApplication``` and subclass ```UrsusApplication``` we also need to parameterize
ExampleApplication with the type of our UrsusApplicationConfiguration class, that's named ```ExampleApplicationConfiguration```.
If your using an IDE like IntelliJ you can select implement methods and you'll get the shell of our application. We also
need to add a ```public static void main(String[] args)``` method and instantiate an instance of ExampleApplication.

```java
package com.aceevo.ursus.example;

import com.aceevo.ursus.core.UrsusApplication;
import org.glassfish.grizzly.http.server.HttpServer;

public class ExampleApplication extends UrsusApplication<ExampleApplicationConfiguration> {

    protected ExampleApplication(String[] args) {
        super(args);
    }

    public static void main(String[] args) throws Exception {
        new ExampleApplication(args);
    }

    @Override
    protected void boostrap(ExampleApplicationConfiguration exampleApplicationConfiguration) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected void run(HttpServer httpServer) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
```

ExampleApplication in the ```ursus-example-application``` project is filled out and have more data than this, but let's ignore that for now.
If you like you can always overwrite ```ExampleApplication``` with the above and follow


#### Building our first artifact and running our application.

Just like Dropwizard Ursus recommends building a "fat" jar that contains all the ```.class``` files needed to run our service.
For the ```ursus-example-application``` we've already added this to the pom.xml, here's the relavent parts of our configuration.

```xml
    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>2.1</version>
                <configuration>
                    <createDependencyReducedPom>true</createDependencyReducedPom>
                    <filters>
                        <filter>
                            <artifact>*:*</artifact>
                            <excludes>
                                <exclude>META-INF/*.SF</exclude>
                                <exclude>META-INF/*.DSA</exclude>
                                <exclude>META-INF/*.RSA</exclude>
                            </excludes>
                        </filter>
                    </filters>
                </configuration>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ServicesResourceTransformer" />
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.aceevo.ursus.example.ExampleApplication</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            ...
```

If you've cloned the ursus git repo and have modified the ```ursus-example-application``` we should be able to build and test
our project, build by running ```mvn clean; mvn package``` from the ursus director. Now we can cd into ursus-example-application and
start our server with the following command ```java -jar ./target/ursus-example-application-0.2-SNAPSHOT.jar```

Unfortunately our application starts and then quickly exists, what's up?

```
boundray:ursus-example-application rayjenkins$ java -jar ./target/ursus-example-application-0.2-SNAPSHOT.jar
19:16:17.488 [main] INFO  o.h.validator.internal.util.Version - HV000001: Hibernate Validator 5.0.1.Final
19:16:18.009 [main] INFO  o.g.jersey.server.ApplicationHandler - Initiating Jersey application, version Jersey: 2.5 2013-12-18 14:27:29...
boundray:ursus-example-application rayjenkins$
```

### Starting the HttpServer

We need to start our HttpServer instance to start our application. The ```protected void run(HttpServer httpServer)``` run method passes us
our configured HttpServer (after bootstrap has been called) allowing us to make any additional modifications to the Grizzly Http Server before
we start our application. When we're ready to start Ursus provides a helper method ```protected void startWithShutdownHook(final HttpServer httpServer)```
that handles registering a shutdown hook for our application.

```java
    /**
     * Convenience method for starting this Grizzly HttpServer
     *
     * @param httpServer
     */
    protected void startWithShutdownHook(final HttpServer httpServer) {

        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            @Override
            public void run() {
                LOGGER.info("Stopping Grizzly HttpServer...");
                httpServer.stop();
                LOGGER.info("Stopping all managed services...");
                for (Service service : managedServices) {
                    service.stopAsync();
                }
            }
        }, "shutdownHook"));

        try {
            LOGGER.info("Starting all managed services...");
            for (Service service : managedServices) {
                service.startAsync();
            }
            httpServer.start();
            printBanner(getClass().getSimpleName());
            LOGGER.info("Press CTRL^C to exit..");
            Thread.currentThread().join();
        } catch (Exception e) {
            LOGGER.error("There was an error while starting Grizzly HTTP server.", e);
        }
    }
```

Let's add that to our ExampleApplication run method, build again and retry.

```java
 @Override
    protected void run(HttpServer httpServer) {
        startWithShutdownHook(httpServer);
    }
```

```java -jar ./target/ursus-example-application-0.2-SNAPSHOT.jar```

```
boundray:ursus-example-application rayjenkins$ java -jar ./target/ursus-example-application-0.2-SNAPSHOT.jar
19:32:04.643 [main] INFO  o.h.validator.internal.util.Version - HV000001: Hibernate Validator 5.0.1.Final
19:32:05.150 [main] INFO  o.g.jersey.server.ApplicationHandler - Initiating Jersey application, version Jersey: 2.5 2013-12-18 14:27:29...
19:32:05.504 [main] INFO  c.aceevo.ursus.core.UrsusApplication - Starting all managed services...
19:32:05.557 [main] INFO  o.g.g.http.server.NetworkListener - Started listener bound to [localhost:8080]
19:32:05.561 [main] INFO  o.g.grizzly.http.server.HttpServer - [HttpServer] Started.
19:32:05.570 [main] INFO  c.aceevo.ursus.core.UrsusApplication - Starting ExampleApplication
 __  __   ______    ______   __  __   ______
/_/\/_/\ /_____/\  /_____/\ /_/\/_/\ /_____/\
\:\ \:\ \\:::_ \ \ \::::_\/_\:\ \:\ \\::::_\/_
 \:\ \:\ \\:(_) ) )_\:\/___/\\:\ \:\ \\:\/___/\
  \:\ \:\ \\: __ `\ \\_::._\:\\:\ \:\ \\_::._\:\
   \:\_\:\ \\ \ `\ \ \ /____\:\\:\_\:\ \ /____\:\
    \_____\/ \_\/ \_\/ \_____\/ \_____\/ \_____\/


19:32:05.570 [main] INFO  c.aceevo.ursus.core.UrsusApplication - Press CTRL^C to exit..
```








