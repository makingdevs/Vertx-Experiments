def sd = vertx.sharedData()
def map = sd.getLocalMap("mySharedMap")

def lista = ["carlogilmar", "josejuan", "jorgeacosta", "gamalielJimenez", "brandonVergara", "temoPalma"]
map.put("items", lista)
map.put("itemsToFill", [])

vertx.eventBus().send("com.makingdevs.get.list", lista)


