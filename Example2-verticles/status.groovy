println "Status on"
vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  println "Status Verticle: ${new Date().toLocaleString()} ::::${message.body()}::::"
}

