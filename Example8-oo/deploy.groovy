import io.vertx.ext.shell.ShellService


def shell = ShellService.create(vertx, [telnetOptions:[ host:"localhost", port:3000]])
shell.start()

vertx.deployVerticle("MaquilaVerticle.groovy"){deploy ->
  if(deploy.succeeded){
    println "Maquila verticle ok"
  }
  else{
    println "problema para levantar"
  }
}


vertx.eventBus().consumer("com.makingdevs.undeploy"){ message ->
  vertx.undeploy( message.body()){ res ->
    if(res.succeeded){
      println "${message.body()} ha sido inhabilitado"
    }
    else{
      println "Error al inhabilitar"
    }
  }
}
