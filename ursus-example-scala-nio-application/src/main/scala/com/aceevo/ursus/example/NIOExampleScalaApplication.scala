package com.aceevo.ursus.example

import com.aceevo.ursus.core.UrsusScalaTCPNIOApplication
import com.aceevo.ursus.config.UrsusNIOApplicationConfiguration
import org.glassfish.grizzly.nio.transport.TCPNIOTransport
import org.glassfish.grizzly.filterchain._
import org.glassfish.grizzly.utils.StringFilter
import java.nio.charset.Charset
import org.slf4j.LoggerFactory;


object NIOExampleScalaApplication {
  def main(args: Array[String]) {
    new NIOExampleScalaApplication(args);
  }

}

class NIOExampleScalaApplication(args: Array[String]) extends UrsusScalaTCPNIOApplication[NIOExampleScalaApplicationConfiguration](args: Array[String]) {

  protected def boostrap(configuration: NIOExampleScalaApplicationConfiguration, filterChainBuilder: FilterChainBuilder): FilterChain = {
    filterChainBuilder.add(new TransportFilter).add(new StringFilter(Charset.forName("UTF-8"))).add(new HelloFilter).build()
  }

  protected def run(transport: TCPNIOTransport) {
    startWithShutdownHook(transport)
  }

}

class NIOExampleScalaApplicationConfiguration extends UrsusNIOApplicationConfiguration {

}

class HelloFilter extends BaseFilter {

  val LOGGER = LoggerFactory.getLogger(classOf[HelloFilter])

  override def handleRead(context: FilterChainContext): NextAction = {
    try {
      val message = context.getMessage.asInstanceOf[String]
      context.write(message)
    } catch {
      case e: Exception => {
        LOGGER.debug("exception handling read", e)
      }
    }
    context.getStopAction
  }
}