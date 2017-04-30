println "Verticle 2"
vertx.eventBus().consumer("com.makingdevs.v2"){ message ->
  println "Message v2: ${message.body()}"
  def context = vertx.getOrCreateContext()
  println context.dump()
}
