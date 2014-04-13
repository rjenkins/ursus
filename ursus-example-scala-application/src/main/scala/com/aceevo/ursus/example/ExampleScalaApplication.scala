package com.aceevo.ursus.example

import com.aceevo.ursus.core.UrsusScalaJerseyApplication
import com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration
import org.glassfish.grizzly.http.server.HttpServer
import com.fasterxml.jackson.annotation.JsonProperty
import com.aceevo.ursus.example.api.{Foo, HelloWordResource}

object ExampleScalaApplication {
  final def main(args: Array[String]) {
    new ExampleScalaApplication(args)
  }
}

class ExampleScalaApplication(args: Array[String]) extends UrsusScalaJerseyApplication[ExampleScalaApplicationConfiguration](args: Array[String]) {

  protected def boostrap(t: ExampleScalaApplicationConfiguration) {
    registerScalaInstance(new HelloWordResource(t))
    registerScalaClass(classOf[Foo])
  }

  protected def run(httpServer: HttpServer) {
    startWithShutdownHook(httpServer);
  }
}

class ExampleScalaApplicationConfiguration extends UrsusJerseyApplicationConfiguration {

  @JsonProperty private var name: String = null

  def getName: String = {
    return name
  }

  def setName(name: String) {
    this.name = name
  }
}