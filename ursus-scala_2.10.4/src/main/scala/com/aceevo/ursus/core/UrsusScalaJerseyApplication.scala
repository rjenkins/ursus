/**

Copyright 2013 Ray Jenkins ray@memoization.com

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

  */

package com.aceevo.ursus.core

import com.aceevo.ursus.config.UrsusJerseyApplicationConfiguration
import com.fasterxml.jackson.databind.{SerializationFeature, ObjectMapper}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import com.fasterxml.jackson.datatype.guava.GuavaModule
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider
import scala.collection.JavaConverters._
import scala.collection.mutable

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

  def registerScalaInstance(resource: Object) {
    registerInstances(Set(resource).asJava)
  }

  def registerScalaClass(clazz: Class[_]) {
    val classes = new mutable.HashSet[Class[_]]
    classes.add(clazz)
    registerClasses(classes.asJava)
  }
}
