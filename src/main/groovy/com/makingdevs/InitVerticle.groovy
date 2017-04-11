package com.makingdevs

def options = [instances:4]

vertx.deployVerticle("com/makingdevs/BatchCardVerticle.groovy")
vertx.deployVerticle("com/makingdevs/WsVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ProcessorVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/ClientWsCardVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/StatusVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ReaderVerticle.groovy")

def headers = [ headers: [
                          "path" : "/home/carlogilmar/Desktop/Github/medios-pago-maquila/"
                         ]]

vertx.setPeriodic(2000){ v ->
	vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
}
