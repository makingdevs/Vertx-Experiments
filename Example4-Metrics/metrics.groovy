import io.vertx.ext.dropwizard.MetricsService

def service = MetricsService.create(vertx)

    // Send a metrics events every second
    vertx.setPeriodic(1000, { t ->
              def metrics = service.getMetricsSnapshot(vertx.eventBus())
                vertx.eventBus().publish("metrics", metrics)
                })

vertx.eventBus().consumer("metrics"){ message ->
    def metrics = message.body()
    println """
        Metrics:
        Messages send: ${metrics['messages.sent'].count}
        Messages reply failures: ${metrics['messages.reply-failures'].count}
        Messages pending: ${metrics['messages.pending'].count}
        Messages received: ${metrics['messages.received'].count}
    """
}

