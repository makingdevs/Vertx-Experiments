println "[ok] Main Verticle: Deploy de verticles"
vertx.deployVerticle("ReceiverVerticle.groovy", [instances:6])
vertx.deployVerticle("MonitorVerticle.groovy")
vertx.deployVerticle("statusVerticle.groovy")
vertx.deployVerticle("GetListVerticle.groovy")
vertx.deployVerticle("Sending.groovy")
