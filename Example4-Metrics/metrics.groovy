import io.vertx.ext.dropwizard.MetricsService

def service = MetricsService.create(vertx)

    // Send a metrics events every second
    vertx.setPeriodic(1000, { t ->
              def metrics = service.getMetricsSnapshot(vertx.eventBus())
                vertx.eventBus().publish("metrics", metrics)
                })

// Send some messages
def random = new java.util.Random()
    vertx.eventBus().consumer("whatever", { msg ->
              vertx.setTimer(10 + random.nextInt(50), { id ->
                          vertx.eventBus().send("whatever", "hello")
                            })
              })
vertx.eventBus().send("whatever", "hello")

vertx.eventBus().consumer("metrics"){ message ->
    println "Metrics here"
    def m = message.body()
    println m.dump()
}


