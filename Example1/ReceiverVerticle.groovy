def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")
def verticleId = UUID.randomUUID().toString()   

println "[ok] Receiver verticle ${verticleId}"

vertx.eventBus().consumer("com.makingdevs.receiver.item"){message ->

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
    vertx.eventBus().send("com.makingdevs.status","< items ${itemsToFill.size}> Item procesado por <${verticleId}>")
    vertx.eventBus().send("com.makingdevs.monitor", itemsToFill.size+1)

}
