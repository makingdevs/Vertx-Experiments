Integer counter = 0
def verticleId = UUID.randomUUID().toString()
println "[ok] Webservice ${verticleId}"

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    //vertx.eventBus().send("com.makingdevs.status.ws", "Web Service Verticle works <${counter}>")
    counter++
    def line = message.body()
    Thread.sleep(1000)
    //Thread.sleep(new Random().nextInt(1000))
    message.reply(line.hashCode().abs())
}
