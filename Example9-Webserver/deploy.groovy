
vertx.deployVerticle("webserver.groovy"){deploy ->
  if(deploy.succeeded())
    println "Webserver arriba"
  else
    println "Problemas para levantar webserver"
}
