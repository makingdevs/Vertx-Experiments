package com.makingdevs

vertx.eventBus().consumer("com.makingdevs.status"){ message ->
  def text = message.body()
  println text
}
