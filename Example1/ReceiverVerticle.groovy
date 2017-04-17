def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")
def verticleId = UUID.randomUUID().toString()   

println "[ok] Receiver verticle ${verticleId}"

vertx.eventBus().consumer("com.makingdevs.receiver.item"){message ->
    Thread.sleep(1000)
    def itemsToFill = map.get("itemsToFill")
    map.put("itemsToFill", itemsToFill+message.body())
    vertx.eventBus().send("com.makingdevs.status","<${message.body()}> Item procesado por <${verticleId}>")
    vertx.eventBus().send("com.makingdevs.monitor", "Check")
}
