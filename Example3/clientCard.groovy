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
            vertx.eventBus().send("com.makingdevs.status", "<ws1> <linea ${body.index}>  ${body.line} <${reply.result().body()}>")
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            def cards = map.get("cards")
            map.put("cards", cards+"<ws1>  ${body.line} <${reply.result().body()}>")
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service1 para ${body.line} sin respuesta, reintentando")
            vertx.eventBus().send("com.makingdevs.retry.ws1", [verticle: res.result, line: body.line])
          }
        }
      }
      else{ println "Webservice 1 deployment fail"}
    }

    vertx.deployVerticle("webService.groovy"){ res ->
      if(res.succeeded){
        vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
          if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "<ws2> <linea ${body.index}> ${body.line} <${reply.result().body()}>")
            vertx.eventBus().send("com.makingdevs.undeploy", res.result)
            def cards = map.get("cards")
            map.put("cards", cards+"<ws2> ${body.line} <${reply.result().body()}>")
            vertx.eventBus().send("com.makingdevs.check.total", body.index)
          }
          else{
            vertx.eventBus().send("com.makingdevs.status", "Web service2 para ${body.line} sin respuesta, reintentando")
            vertx.eventBus().send("com.makingdevs.retry.ws2", [verticle: res.result, line: body.line])
          }
        }

      }
      else{ println "Webservice 2 for ${body.line}deployment fail"}
    }

  }

