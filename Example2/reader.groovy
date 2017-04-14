Integer counter = 0

println "Reader on"
vertx.eventBus().consumer("com.makingdevs.reader"){ message ->
    Thread.sleep(1000)
    vertx.eventBus().send("com.makingdevs.status","${counter} => ${message.body()}")
    counter++
}
