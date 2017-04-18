Integer counter = 0
println "[ok] Webservice"

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    counter++
    def line = message.body()
    Thread.sleep(new Random().nextInt(1000))
    message.reply(line.hashCode().abs())
}
