println "[ok] Client card"
def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.client.card"){ message ->

    def body = message.body()

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.persistor", 
                    [reply:reply.result().body(), index:body.index, line:body.line, verticle:res.result])
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service1 no responde. Reintentado [ok] [${body.index}]")
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            vertx.eventBus().send("com.makingdevs.each.card", [line: body.line, index:body.index])
          }
        }
      }
      else{ println "---Webservice 1 deployment fail---"}
    }

  }

vertx.eventBus().consumer("com.makingdevs.persistor"){ message ->

    def params = message.body()
    def cards = map.get("cards")
    
    map.put("cards", cards+ [index:params.index, line:"<${params.index}>  ${params.line} <${params.reply}>"])

    vertx.eventBus().send("com.makingdevs.check.total", params.index)
    vertx.eventBus().send("com.makingdevs.undeploy", params.verticle)
    vertx.eventBus().send("com.makingdevs.status", "<ws1> [done] [counter:${flags-1}] [line: ${params.index}")

}
