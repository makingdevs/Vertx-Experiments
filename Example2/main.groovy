def options = [instances:5]

println "Main verticle deployed"
vertx.deployVerticle("reader.groovy", options)
vertx.deployVerticle("status.groovy")
vertx.deployVerticle("starter.groovy")


