import io.vertx.ext.shell.ShellService
import io.vertx.ext.dropwizard.MetricsService

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

def shell = ShellService.create(vertx, [
  telnetOptions:[
    host:"localhost",
    port:3000
    ]
  ])
shell.start()

def service = MetricsService.create(vertx)
    vertx.setPeriodic(1000, { t ->
              def metrics = service.getMetricsSnapshot(vertx.eventBus())
                vertx.eventBus().publish("metrics", metrics)
                })

vertx.eventBus().consumer("metrics"){ message ->
    def metrics = message.body()
        println "*-*-"*10
    println "Messages delivered:  "+metrics["messages.sent"]
}


