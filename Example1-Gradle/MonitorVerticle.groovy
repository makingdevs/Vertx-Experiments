def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")

println "[ok] Monitor Verticle"

vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  def items = map.get("items")
  def itemsToBeFilled = map.get("itemsToFill")
  println "Monitor here!"
  println items.size
  println itemsToBeFilled.size

  /*
  println "Monitor Verticle Checking ..."
  println "-----------------------------"
  println itemsToBeFilled
  println "-----------------------------"
  */

  if(itemsToBeFilled.size == items.size){
    vertx.eventBus.send("com.makingdevs.status", "Tenemos todos los items procesados-------------------")
  }

}
