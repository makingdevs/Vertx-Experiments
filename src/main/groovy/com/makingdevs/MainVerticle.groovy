package com.makingdevs

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions

class MainVerticle extends AbstractVerticle {
  @Override
  void start(){
    vertx.deployVerticle("src/main/groovy/com/makingdevs/Webserver.groovy")
  }
}
