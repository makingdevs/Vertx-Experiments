package com.makingdevs

def options = [instances:1]

vertx.deployVerticle("com/makingdevs/reader.groovy")
vertx.deployVerticle("com/makingdevs/processor.groovy", options)
vertx.deployVerticle("com/makingdevs/client_ws_card.groovy", options)
vertx.deployVerticle("com/makingdevs/status.groovy")

vertx.setPeriodic(2000){ v ->
	vertx.eventBus().send("com.makingdevs.reader", "any")
}
