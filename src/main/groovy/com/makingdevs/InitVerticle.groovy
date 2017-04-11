package com.makingdevs

def options = [instances:1]

vertx.deployVerticle("com/makingdevs/ReaderVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ProcessorVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/ClientWsCardVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/StatusVerticle.groovy")

vertx.setPeriodic(2000){ v ->
	vertx.eventBus().send("com.makingdevs.reader", "any")
}
