println "[ok] Init Verticle... Sending signal to reader verticle for start the process"

def headers = [ headers: ["path" : "/home/carlogilmar/Desktop"]]

vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
