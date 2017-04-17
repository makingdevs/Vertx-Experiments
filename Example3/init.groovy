println "[ok] Init Verticle... Sending signal to reader verticle for start the process"
def headers = [ headers: [
"path" : "/Users/makingdevs/Desktop"
]]

vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
