vertx.deployVerticle("fileSystemVerticle.groovy"){deploy ->
  if(deploy.succeeded()){
    println "Ya levantamos el verticle, ahora enviaremos el mensaje"
    vertx.eventBus().send("create.file", "chamebale")
  }
}
