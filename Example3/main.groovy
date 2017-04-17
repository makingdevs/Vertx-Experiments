println "Main verticle deployed"
vertx.deployVerticle("webserver.groovy")
vertx.deployVerticle("webService.groovy", [instances:400])
vertx.deployVerticle("clientCard.groovy", [instances:50])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("reader.groovy")
vertx.deployVerticle("status.groovy", [instances:20])
vertx.deployVerticle("init.groovy")

/*
def headers = [ headers: [
    "path" : "/Users/makingdevs/Desktop"
]]

vertx.setPeriodic(2000){ v ->
    vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
}
*/
