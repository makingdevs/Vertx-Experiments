vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  println "${new Date().toLocaleString()} Status: ${message.body()}"
}
