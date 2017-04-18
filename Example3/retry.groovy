println "[ok] Retry verticle"
def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.retry.ws1"){ message ->
  def params = message.body()
  vertx.eventBus().send("com.makingdevs.undeploy", params.verticle)

  vertx.deployVerticle("webService.groovy"){ res ->
    if(res.succeeded){

      vertx.eventBus().send("com.makingdevs.ws", params.line){ reply ->
        if(reply.succeeded()){
          vertx.eventBus().send("com.makingdevs.status", "<ws1> <retry> line <${params.index}>")
          def cards = map.get("cards")
          map.put("cards", cards+"<ws1> ${params.line} <${reply.result().body()}>")
          vertx.eventBus().send("com.makingdevs.check.total", params.index)
          vertx.eventBus().send("com.makingdevs.undeploy", res.result)
        }
        else{
          vertx.eventBus().send("com.makingdevs.status", "Web service1 sin respuesta, reintentando... de nuevo")
          vertx.eventBus().send("com.makingdevs.retry.ws1", [verticle: res.result, line: params.line, index: params.index])
        }
      }
    }
    else{
      println "Retrying Webservice 1 deployment fail"
    }
  }
}

vertx.eventBus().consumer("com.makingdevs.retry.ws2"){ message ->

  def params = message.body()
  vertx.eventBus().send("com.makingdevs.undeploy", params.verticle)

  vertx.deployVerticle("webService.groovy"){ res ->
    if(res.succeeded){

      vertx.eventBus().send("com.makingdevs.ws", params.line){ reply ->
        if(reply.succeeded()){
          vertx.eventBus().send("com.makingdevs.status", "<ws2> <retry> for line ${params.index}")
          def cards = map.get("cards")
          map.put("cards", cards+"<ws2> ${params.line} <${reply.result().body()}>")
          vertx.eventBus().send("com.makingdevs.check.total", params.index)
          vertx.eventBus().send("com.makingdevs.undeploy", res.result)
        }
        else{
          vertx.eventBus().send("com.makingdevs.status", "Web service2 sin respuesta, reintentando... de nuevo")
          vertx.eventBus().send("com.makingdevs.retry.ws2", [verticle: res.result, line: params.line, index: params.index])
        }
      }
    }
    else{
      println "Retrying Webservice 2 deployment fail"
    }
  }
}
