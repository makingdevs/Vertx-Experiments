println "verticle b ok"

vertx.eventBus().consumer("com.makingdevs.v.b"){ message ->
  println message.body()
  println "* b"
  vertx.deployVerticle("verticleC.groovy")
}
