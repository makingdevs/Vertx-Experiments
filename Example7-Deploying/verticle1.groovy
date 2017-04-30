println "Verticle 1"
vertx.eventBus().consumer("com.makingdevs.v1"){ message ->
  println "Subiendo v2 y v3"
  println message.body()
  vertx.deployVerticle("verticle2.groovy")
  vertx.deployVerticle("verticle3.groovy")
  def context = vertx.getOrCreateContext()
  println context.dump()
}
