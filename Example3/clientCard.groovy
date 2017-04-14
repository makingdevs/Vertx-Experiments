println "[ok] Client card"
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->
    
    def verticleId = UUID.randomUUID().toString()
    vertx.eventBus().send("com.makingdevs.status", "Each Card Verticle ${counter} <${verticleId}>")
    counter++

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "1 ${message.body()} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            vertx.eventBus().send("com.makingdevs.status", "2 ${message.body()} <${reply.result().body()}>")
        }
        else{
            vertx.eventBus().send("com.makingdevs.status", "Web service sin respuesta")
        }
    }
}
