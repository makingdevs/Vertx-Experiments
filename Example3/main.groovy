import io.vertx.ext.shell.ShellService

println "Main verticle deployed"

//vertx.deployVerticle("webserver.groovy")
//vertx.deployVerticle("webService.groovy", [instances:1])
vertx.deployVerticle("retry.groovy")
vertx.deployVerticle("checkTotal.groovy")
vertx.deployVerticle("clientCard.groovy", [instances:2])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("reader.groovy")
vertx.deployVerticle("status.groovy")
vertx.deployVerticle("init.groovy")


def service = ShellService.create(vertx, [
  telnetOptions:[
    host:"localhost",
    port:3000
    ]
  ])
service.start()
