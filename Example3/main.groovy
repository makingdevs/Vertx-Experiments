import io.vertx.ext.shell.ShellService

println "Main verticle deployed"

vertx.deployVerticle("checkTotal.groovy")
vertx.deployVerticle("clientCard.groovy")
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("status.groovy")
vertx.deployVerticle("monitor.groovy")

vertx.deployVerticle("reader.groovy"){deploy->
    if(deploy.succeeded()){
        vertx.deployVerticle("init.groovy")
    }
    else{
        println "Reader Verticle cannot deploy"
    }
}

def service = ShellService.create(vertx, [
  telnetOptions:[
    host:"localhost",
    port:3000
    ]
  ])
service.start()
