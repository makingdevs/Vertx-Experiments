println "[ok] Client card"
def map = vertx.sharedData().getLocalMap("cardsReady")
def monitorCounter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->

    def body = message.body()

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
                
            vertx.eventBus().send("com.makingdevs.persistor", 
                    [reply:reply.result().body(), index:body.index, line:body.line, verticle:res.result])

            /*

            def cards = map.get("cards")
            def flags = map.get("flags")
            map.put("cards", cards+ [index:body.index, line:"<${body.index}>  ${body.line} <${reply.result().body()}>"])
            map.put("flags", flags-1)
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            vertx.eventBus().send("com.makingdevs.status", "<ws1> [done] [counter:${flags-1}] [line: ${body.index}")
            */
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

                    
    //[reply:reply.result().body(), index:body.index, line:body.line, verticle:res.result])

    def params = message.body()

    def cards = map.get("cards")
    def flags = map.get("flags")

    monitorCounter++
    
    map.put("cards", cards+ [index:params.index, line:"<${params.index}>  ${params.line} <${params.reply}>"])
    map.put("flags", flags-1)

    vertx.eventBus().send("com.makingdevs.check.total", params.index)
    vertx.eventBus().send("com.makingdevs.undeploy", params.verticle)
    vertx.eventBus().send("com.makingdevs.status", "<ws1> [done] [counter:${flags-1}] [line: ${params.index}")

}
