package com.aceevo.ursus.example.api

import javax.ws.rs.{GET, Path, Produces}
import javax.ws.rs.core.{Response, MediaType}
import com.fasterxml.jackson.databind.ObjectMapper
import com.aceevo.ursus.example.model.Hello
import com.aceevo.ursus.example.ExampleScalaApplicationConfiguration
import javax.inject.Inject

@Path("hello")
@Produces(Array(MediaType.APPLICATION_JSON))
class HelloWordResource {

  @Inject
  var exampleApplicationConfiguration: ExampleScalaApplicationConfiguration = null

  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  def hello : Response = {
    Response.ok(new Hello(exampleApplicationConfiguration.name)).build
  }
}
