package com.makingdevs

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions

@groovy.util.logging.Log4j2
class MainVerticle extends AbstractVerticle {
  @Override
  void start(){
    vertx.deployVerticle("src/main/groovy/com/makingdevs/Webserver.groovy")
	log.trace("the built-in asdasdasdTRACE level");
    log.debug("the built-in DEBUG level");
    log.info("the built-in INFO level");
    log.warn("the built-in WARN level");

  }
}


/*
* For example:
* You can deploy a main verticle with this:
* Remember write the complete path to the verticles

def options = [instances:4]

vertx.deployVerticle("com/makingdevs/BatchCardVerticle.groovy")
vertx.deployVerticle("com/makingdevs/WsVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ProcessorVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/ClientWsCardVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/StatusVerticle.groovy")

*/
