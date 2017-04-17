println "Main verticle deployed"

//vertx.deployVerticle("webserver.groovy")
//vertx.deployVerticle("webService.groovy", [instances:1])
vertx.deployVerticle("retry.groovy")
vertx.deployVerticle("clientCard.groovy", [instances:10])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("reader.groovy")
vertx.deployVerticle("status.groovy", [instances:1])
vertx.deployVerticle("init.groovy")

/*
 * Esto esta en init.groovy

def headers = [ headers: [
    "path" : "/Users/makingdevs/Desktop"
]]

vertx.setPeriodic(2000){ v ->
    vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
}
*/
