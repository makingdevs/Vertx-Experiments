//println "[ok] Status"
Integer undeployCounter = 1

vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  println "${new Date().toLocaleString()}: ${message.body()}"
  //vertx.eventBus().send("com.makingdevs.web.monitor","Status Verticle: ${new Date().toLocaleString()} -- ${message.body()} ")
}

vertx.eventBus().consumer("com.makingdevs.webserver"){ message ->
    vertx.eventBus().send("com.makingdevs.web.monitor", message.body())
}

vertx.eventBus().consumer("com.makingdevs.status.ws"){ message ->
  //println "[Webservice Status] ${new Date().toLocaleString()}  ${message.body()}"
}

vertx.eventBus().consumer("com.makingdevs.undeploy"){ message ->
  vertx.undeploy( message.body()){ res ->
    if(res.succeeded){
      //println "*** Undeploying verticle #[${undeployCounter}] [done] ***"
      undeployCounter++
    }
    else{
      //println "*** Undeploying verticle [fail] ***"
    }
  }
}
