println "Main verticle deployed"
vertx.deployVerticle("webService.groovy", [instances:20])
vertx.deployVerticle("clientCard.groovy", [instances:5])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("reader.groovy")
vertx.deployVerticle("status.groovy")

def headers = [ headers: [
    "path" : "/home/carlogilmar/Desktop"
]]

vertx.setPeriodic(2000){ v ->
    vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
}
