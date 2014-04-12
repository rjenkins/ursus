package com.aceevo.ursus.example.api

import javax.ws.rs.{GET, Produces, Path}
import javax.ws.rs.core.{Response, MediaType}
import com.aceevo.ursus.example.model.Hello

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 4/12/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
@Path("foo")
@Produces(Array(MediaType.APPLICATION_JSON))
class Foo {

  @GET
  @Produces(Array(MediaType.APPLICATION_JSON))
  def hello: Response = {
    Response.ok(new Hello("foo")).build
  }
}
