println "[ok] Webservice"
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    def verticleId = UUID.randomUUID().toString()
    vertx.eventBus().send("com.makingdevs.status", "[${counter}] [id ws:${verticleId}] Web Service verticle")
    counter++

    def line = message.body()
    Thread.sleep(new Random().nextInt(1000))
    message.reply(line.hashCode().abs())
}
