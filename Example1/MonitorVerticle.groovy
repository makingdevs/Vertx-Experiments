def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")

println "[ok] Monitor Verticle"

vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  def items = map.get("items")
  def itemsToBeFilled = message.body()
  println "Monitor Verticle: ${message.body()}"
  
  if(itemsToBeFilled == items.size){
    vertx.eventBus.send("com.makingdevs.status", "Tenemos todos los items procesados-------------------")
  }

}
