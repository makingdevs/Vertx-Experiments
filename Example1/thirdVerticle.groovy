def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")

vertx.setPeriodic(1000){ v ->
  def itemsToFill = map.get("itemsToFill")
  vertx.eventBus().send("com.makingdevs.coordinator", itemsToFill)
}

vertx.eventBus().consumer("com.makingdevs.coordinator"){message ->
  println "Coordinator works"
  def items = map.get("items")
  def itemsToBeFilled = message.body()
  vertx.eventBus.send("com.makingdevs.status", "Coordinator items size: ${items.size} ItemsToBeFilled size: ${itemsToBeFilled.size}")
  if(itemsToBeFilled.size == items.size){
    vertx.eventBus.send("com.makingdevs.status", "Tenemos todos los items procesados-------------------")
    itemsToBeFilled.each{ item ->
      println item
    }
  }
}
