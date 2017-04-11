package com.makingdevs

vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  println "${new Date().toLocaleString()} Maquila Status: ${message.body()}"
}
