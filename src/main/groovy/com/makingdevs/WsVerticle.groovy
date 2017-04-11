package com.makingdevs

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
  def line = message.body()
  Thread.sleep(new Random().nextInt(1000))
  message.reply(line.hashCode().abs())
}
