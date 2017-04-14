def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")

vertx.eventBus().consumer("com.makingdevs.get.lista"){message ->
  println "Verticle get lista works"
  def lista = message.body()
  lista.each{ item ->
    vertx.eventBus().send("com.makingdevs.send.item", item)
    vertx.eventBus().send("com.makingdevs.status","Iterando : ${message.body()}")
  }
}

vertx.eventBus().consumer("com.makingdevs.send.item"){message ->
  /*
    println "verticle send item works"
    def itemsToFill = map.get("itemsToFill")
    if(itemsToFill.contains(message.body())){
      vertx.eventBus().send("com.makingdevs.status","Elemento ya procesado")
    }else{
      map.put("itemsToFill", itemsToFill+message.body())
      vertx.eventBus().send("com.makingdevs.status","Tamanio del mapa compartido : ${itemsToFill.size}")
    }
    */
    def itemsToFill = map.get("itemsToFill")
    map.put("itemsToFill", itemsToFill+message.body())
    vertx.eventBus().send("com.makingdevs.status","Tamanio del mapa compartido : ${itemsToFill.size}")

}
