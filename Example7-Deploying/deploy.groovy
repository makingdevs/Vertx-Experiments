import io.vertx.ext.shell.ShellService

def shell = ShellService.create(vertx, [
  telnetOptions:[
    host:"localhost",
    port:3000
    ]
  ])
shell.start()

vertx.deployVerticle("verticle1.groovy"){deploy ->
  if(deploy.succeeded()){
    println "Vertcile 1 arriba."
    vertx.eventBus().send("com.makingdevs.v1", "Starting...")
  }
}

vertx.deployVerticle("verticleA.groovy"){deploy ->
  if(deploy.succeeded()){
    println "Vertcile a  arriba."
    vertx.eventBus().send("com.makingdevs.v.a", "Starting... verticles")
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
