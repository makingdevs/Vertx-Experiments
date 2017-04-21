package com.makingdevs

import io.vertx.core.AbstractVerticle
import io.vertx.core.DeploymentOptions

class MainVerticle extends AbstractVerticle {

  def options = [instances:1]

  @Override
  void start(){
    vertx.deployVerticle("src/main/groovy/com/makingdevs/InitVerticle.groovy")
  }
}
