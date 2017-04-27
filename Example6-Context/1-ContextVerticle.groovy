vertx.eventBus().consumer("com.makingdevs.sample"){ msg ->

  println "${new Date()} Sample Verticle ok"
  def context = vertx.getOrCreateContext()
  println context.dump()
  context.put("data", "hello verticle")
  context.runOnContext{ v ->
    def hello = context.get("data")
    println "${new Date()} Share data at context: ${hello}"
    println "${new Date()} This will be executed asynchronously in the same context"
  }
}

vertx.eventBus().send("com.makingdevs.sample", "message")
