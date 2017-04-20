import io.vertx.ext.shell.ShellService

vertx.deployVerticle("metrics.groovy"){deploy ->
    if(deploy.succeeded()){
        vertx.deployVerticle("verticle.groovy"){ deploy2 ->
            if(deploy2.succeeded()){
               println "Mandando mensaje a verticle "
                   (1..5).each{
                   vertx.eventBus().send("com.makingdevs.verticle", "work")
                   }
            }
        }
    }
}
println "[ok] Shell and metrics"
def shell = ShellService.create(vertx, [
  telnetOptions:[
    host:"localhost",
    port:3000
    ]
  ])
shell.start()

