println "verticle a ok"

vertx.eventBus().consumer("com.makingdevs.v.a"){ message ->
  println message.body()
  println "* a"
  println "---"*10
  def context = vertx.getOrCreateContext()
  println context.dump()
  println "---"*10
  vertx.deployVerticle("verticleB.groovy"){ deploy ->
    if(deploy.succeeded())
      vertx.eventBus().send("com.makingdevs.v.b", "hola")
  }
}
