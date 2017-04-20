println "Verticle on"
Integer counter = 1

vertx.eventBus().consumer("com.makingdevs.verticle"){message ->
    println "Verticle works ${counter} times"
        counter++
}
