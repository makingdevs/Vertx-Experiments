//println "[ok] Client card"
def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.client.card"){ message ->

    def body = message.body()

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
            def params = message.body()
            def cards = map.get("cards")
            map.put("cards", cards+ [index:body.index, line:"<${body.index}>  ${body.line} <${reply.result().body()}>"])
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service1 no responde. Reintentado [ok] [${body.index}]")
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            vertx.eventBus().send("com.makingdevs.client.card", [line: body.line, index:body.index])
          }
        }
      }
      //else{ println "---Webservice 1 deployment fail---"}
    }

  }

