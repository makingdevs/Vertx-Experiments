vertx.eventBus.consumer("com.makingdevs.processor"){message ->
  def context = vertx.getOrCreateContext()
  def processArgs= context.processArgs()

  println "Processor Verticle working"
  println message.body()
  println processArgs.properties

  if(!message.body()){
    println "The verticle has a null message, error. Undeploying verticle"
    vertx.eventBus().send("com.makingdevs.supervisor.processor", context.deploymentID)
  }
}
