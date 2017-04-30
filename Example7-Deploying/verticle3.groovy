println "Verticle 3"
vertx.eventBus().consumer("com.makingdevs.v3"){ message ->
  println "Message v3: ${message.body()}"
  def context = vertx.getOrCreateContext()
  println context.dump()
}
