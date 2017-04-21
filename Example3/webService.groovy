println "[ok] Webservice verticle"

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
    def timerID = vertx.setTimer(1000) { id ->
        println "ws [replying]"
        def line = message.body()
        message.reply(line.hashCode().abs())
    }
}
