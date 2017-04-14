vertx.deployVerticle("secondVerticle.groovy", [instances:10, worker:true])
vertx.deployVerticle("thirdVerticle.groovy", [instances:1])
vertx.deployVerticle("statusVerticle.groovy", [instances:1])
vertx.deployVerticle("firstVerticle.groovy",  [instances:1])
