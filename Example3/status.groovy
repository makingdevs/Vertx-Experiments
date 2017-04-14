println "[ok] Status"
vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  println "Status Verticle: ${new Date().toLocaleString()} ::::${message.body()}::::"
}

