println "[ok] Webservice"
Integer counter = 0

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    def verticleId = UUID.randomUUID().toString()
    println "[${counter}] [${verticleId}] Web Service verticle"
    counter++

    def line = message.body()
    Thread.sleep(new Random().nextInt(1000))
    message.reply(line.hashCode().abs())
}
