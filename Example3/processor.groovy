println "[ok] Procesor"

vertx.eventBus().consumer("com.makingdevs.processor"){ message ->
        vertx.fileSystem().readFile("${message.body()}/file.txt"){ result ->
                if (result.succeeded()) {
                        vertx.eventBus().send("com.makingdevs.move", message.body())

                        def batchId = UUID.randomUUID().toString()
                        def lines = result.result().toString().split('\n')

                        vertx.eventBus().send("com.makingdevs.status", "Procesor Verticle: Iniciando lectura y proceso****************************************************************************************************" )

                        lines.eachWithIndex { line, idx ->
                            vertx.eventBus().send("com.makingdevs.each.card", line)
                        }

                }
                else{
                    vertx.eventBus().send("com.makingdevs.status", "Processor Verticle: No se puede leer el archivo")
                }

        }
    }
