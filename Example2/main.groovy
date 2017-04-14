def options = [instances:2]

println "Main verticle deployed"
vertx.deployVerticle("reader.groovy", options)
vertx.deployVerticle("status.groovy")
vertx.deployVerticle("starter.groovy")


