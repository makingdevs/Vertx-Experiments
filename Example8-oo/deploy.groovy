import io.vertx.ext.shell.ShellService


def shell = ShellService.create(vertx, [telnetOptions:[ host:"localhost", port:3000]])
shell.start()

vertx.deployVerticle("MaquilaVerticle.groovy"){deploy ->
  if(deploy.succeeded){
    println "Maquila verticle ok"
    vertx.eventBus().send("com.makingdevs.starter.process", "init")
  }
  else{
    println "problema para levantar"
  }
}

