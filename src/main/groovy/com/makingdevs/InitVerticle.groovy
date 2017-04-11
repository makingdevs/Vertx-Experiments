package com.makingdevs

def options = [instances:2]

vertx.deployVerticle("com/makingdevs/ReaderVerticle.groovy")
vertx.deployVerticle("com/makingdevs/ProcessorVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/ClientWsCardVerticle.groovy", options)
vertx.deployVerticle("com/makingdevs/StatusVerticle.groovy")

def headers = [ headers: [
                          "path" : "/home/carlogilmar/Desktop/Github/medios-pago-maquila/"
                         ]]

vertx.setPeriodic(2000){ v ->
	vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
}
