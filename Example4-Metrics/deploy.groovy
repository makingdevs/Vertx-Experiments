

vertx.deployVerticle("metrics.groovy"){deploy ->
    if(deploy.succeeded()){
        vertx.deployVerticle("verticle.groovy"){ deploy2 ->
            if(deploy2.succeeded()){
               println "Mandando mensaje a verticle " 
                   (1..5).each{
                   vertx.eventBus().send("com.makingdevs.verticle", "work")
                   }
            }
        }
    }
}
