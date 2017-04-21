println "Main verticle deployed"

vertx.deployVerticle("helper.groovy")
vertx.deployVerticle("checkTotal.groovy")
vertx.deployVerticle("clientCard.groovy", [instances:1])
vertx.deployVerticle("processor.groovy")
vertx.deployVerticle("status.groovy")
vertx.deployVerticle("monitor.groovy")

vertx.deployVerticle("reader.groovy"){deploy->
    if(deploy.succeeded()){
        println "[ok] Init Verticle... Sending signal to reader verticle for start the process"
        def headers = [ headers: ["path" : "/home/carlogilmar/Desktop"]]
        vertx.eventBus().send("com.makingdevs.reader", "Leyendo fichero de texto.", headers)
    }
    else{
        println "Reader Verticle cannot deploy"
    }
}

