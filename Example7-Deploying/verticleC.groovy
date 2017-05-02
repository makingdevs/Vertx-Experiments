println "verticle c ok"

vertx.eventBus().consumer("com.makingdevs.v.c"){ message ->
  println message.body()
  println "* c"
}
