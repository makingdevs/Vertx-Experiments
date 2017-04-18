println "[ok] Client card"
def map = vertx.sharedData().getLocalMap("cardsReady")
def verticleId = UUID.randomUUID().toString()
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->

    def body = message.body()
    //vertx.eventBus().send("com.makingdevs.status", "<${counter}> <${verticleId}> EachCard Verticle run for line ${body.index}")
    //counter++

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
            def cards = map.get("cards")
            def flags = map.get("flags")
            map.put("cards", cards+"<ws1>  ${body.line} <${reply.result().body()}>")
            map.put("flags", flags-1)
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            vertx.eventBus().send("com.makingdevs.status", "<ws1> [ok] flag: ${flags-1}")
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service1 no responde. Reintentado [ok]")
            vertx.eventBus().send("com.makingdevs.retry.ws1", [verticle: res.result, line: body.line, index:body.index])
          }
        }
      }
      else{ println "---Webservice 1 deployment fail---"}
    }

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
            def cards = map.get("cards")
            def flags = map.get("flags")
            map.put("flags", flags-1)
            map.put("cards", cards+"<ws2> ${body.line} <${reply.result().body()}>")
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            vertx.eventBus().send("com.makingdevs.status", "<ws2> [ok] flag: ${flags-1}")
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service2 no responde. Reintentado [ok]")
            vertx.eventBus().send("com.makingdevs.retry.ws2", [verticle: res.result, line: body.line, index: body.index])
          }
        }

      }
      else{ println "--- Webservice 2 deployment fail ---"}
    }

  }

