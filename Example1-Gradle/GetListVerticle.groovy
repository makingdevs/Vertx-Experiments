println "[ok] Get Lista Verticle"

vertx.eventBus().consumer("com.makingdevs.get.list"){message ->
  def list = message.body()
  list.each{ item ->
    vertx.eventBus().send("com.makingdevs.receiver.item", item)
    vertx.eventBus().send("com.makingdevs.status","Elemento enviado al receiver: ${item}")
  }
}
