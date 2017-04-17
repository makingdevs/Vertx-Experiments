println "[ok] Client card"
def verticleId = UUID.randomUUID().toString()
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->

    def body = message.body()

    //vertx.eventBus().send("com.makingdevs.status", "<${counter}> <${verticleId}> EachCard Verticle run for line ${body.index}")
    counter++

    vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "<linea ${body.index}> :: ws 1 ${body.line} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }

    vertx.eventBus().send("com.makingdevs.ws", body.line){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "<linea ${body.index}> :: ws 2 ${body.line} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }
}
