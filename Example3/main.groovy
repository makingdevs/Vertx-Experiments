println "Main verticle deployed"

//vertx.deployVerticle("webserver.groovy")
//vertx.deployVerticle("webService.groovy", [instances:1])
vertx.deployVerticle("retry.groovy")
vertx.deployVerticle("checkTotal.groovy")
vertx.deployVerticle("clientCard.groovy", [instances:10])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("reader.groovy")
vertx.deployVerticle("status.groovy", [instances:1])
vertx.deployVerticle("init.groovy")


