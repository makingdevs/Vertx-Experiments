import io.vertx.ext.shell.ShellService

def shell = ShellService.create(vertx, [telnetOptions:[ host:"localhost", port:3000]])
shell.start()

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

vertx.eventBus().consumer("com.makingdevs.supervisor.processor"){ message ->
  println "Supervisor here"
  println "undeploying"
  vertx.eventBus().send("com.makingdevs.undeploy", message.body())
  vertx.deployVerticle("processor.groovy"){ deploy ->
  if(deploy.succeeded()){
    println "Processor its on again"
  }
  }
}

vertx.deployVerticle("processor.groovy"){ deploy ->
  def context = vertx.currentContext()
  if(deploy.succeeded()){
    vertx.eventBus().send("com.makingdevs.processor", "Primer proceso de Processor Verticle")
    println "Deployment Id's: "+vertx.deploymentIDs()
  }
  else{
    println "Processor verticle cannot be deployed."
  }
}
