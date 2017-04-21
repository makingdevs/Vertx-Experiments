println "[ok] Webservice verticle"
Integer counter = 1

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    def timerID = vertx.setTimer(1000) { id ->
        println "ws [replying] ${counter}"
        counter++
        def line = message.body()
        message.reply(line.hashCode().abs())
    }
}
