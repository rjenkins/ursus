package com.aceevo.ursus.core

import com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration
import com.fasterxml.jackson.databind.{SerializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider

/**
 * Created with IntelliJ IDEA.
 * User: rayjenkins
 * Date: 4/11/14
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */
abstract class UrsusScalaJerseyApplication[T <: UrsusJerseyApplicationConfiguration](args: Array[String])
  extends UrsusJerseyApplication[T](args: Array[String]) {

  protected override def registerJacksonSupport() {
    val instances = new java.util.HashSet[AnyRef]
    val mapper = new ObjectMapper();
    mapper.enable(SerializationFeature.INDENT_OUTPUT);
    mapper.registerModule(DefaultScalaModule)
    mapper.registerModule(new GuavaModule());

    val provider = new JacksonJaxbJsonProvider();
    provider.setMapper(mapper)

    instances.add(provider)
    registerInstances(instances)
  }

}
