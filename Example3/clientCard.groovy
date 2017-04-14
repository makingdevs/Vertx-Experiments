println "[ok] Client card"
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->
    
    def verticleId = UUID.randomUUID().toString()
    println "Each Card Verticle ${counter} <${verticleId}>"
    counter++

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            println "1 ${message.body()} <${reply.result().body()}>"
        }
        else{
            println "Ws sin respuesta"
        }
    }

    vertx.eventBus().send("com.makingdevs.ws", message.body()){ reply ->
        if(reply.succeeded()){
            println "2 ${message.body()} <${reply.result().body()}>"
        }
        else{
            println "Ws sin respuesta"
        }
    }
}
