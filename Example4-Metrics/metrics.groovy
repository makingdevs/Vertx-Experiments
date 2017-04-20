import io.vertx.ext.dropwizard.MetricsService

/*
Using the shell, you can put "bus-tail metrics" and then you going to see the map of metrics
 */

def service = MetricsService.create(vertx)

vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  println "Shell request monitoring"
  def metrics = service.getMetricsSnapshot(vertx.eventBus())
  vertx.eventBus().publish("metrics", metrics)
}

vertx.eventBus().consumer("metrics"){ message ->
    def metrics = message.body()
    println " Metrics: \n Messages send: ${metrics['messages.sent'].count} \n Messages reply failures: ${metrics['messages.reply-failures'].count} \n Messages pending: ${metrics['messages.pending'].count} \n Messages received: ${metrics['messages.received'].count}"
}

