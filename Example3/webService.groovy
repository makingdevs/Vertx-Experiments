println "[ok] Webservice"
Integer counter = 0
def verticleId = UUID.randomUUID().toString()

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->

    vertx.eventBus().send("com.makingdevs.status", "Web Service Verticle works <${counter}>")
    counter++
    def line = message.body()
    //Thread.sleep(new Random().nextInt(1000))
    message.reply(line.hashCode().abs())
}
