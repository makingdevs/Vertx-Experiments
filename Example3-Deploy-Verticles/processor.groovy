println "[ok] Procesor"

def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.processor"){ message ->
        vertx.fileSystem().readFile("${message.body()}/file.txt"){ result ->
                if (result.succeeded()) {

                        List lines = result.result().toString().split("\n")
                        vertx.eventBus().send("com.makingdevs.status", "${lines.size} Procesor Verticle: Iniciando lectura y proceso *************" )
                        map.put("cards", [])
                        map.put("totalCards", lines.size)
                        map.put("initTime", "${new Date().toLocaleString()}" )

                        lines.eachWithIndex { line, idx ->
                            vertx.eventBus().send("com.makingdevs.client.card", [line:line, index:idx])
                        }
                }
                else{
                    vertx.eventBus().send("com.makingdevs.status", "Processor Verticle: No se puede leer el archivo")
                }

        }
    }
