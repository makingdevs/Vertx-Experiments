println "[ok] Client card"
def verticleId = UUID.randomUUID().toString()
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->
    

    vertx.eventBus().send("com.makingdevs.status", "<${counter}> <${verticleId}> EachCard Verticle run")
    counter++

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "ws 1 ${message.body()} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "ws 2 ${message.body()} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }
}
