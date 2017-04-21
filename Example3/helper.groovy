import io.vertx.ext.shell.ShellService
import io.vertx.ext.dropwizard.MetricsService

println "[ok] Shell and metrics"

def shell = ShellService.create(vertx, [telnetOptions:[ host:"localhost", port:3000]])
shell.start()

def service = MetricsService.create(vertx)

vertx.eventBus().consumer("com.makingdevs.get.metrics"){message ->
  println "Shell request monitoring"
  def metrics = service.getMetricsSnapshot(vertx.eventBus())
  vertx.eventBus().publish("com.makingdevs.metrics", metrics)
}

vertx.eventBus().consumer("com.makingdevs.metrics"){ message ->
    def metrics = message.body()
    println """
    Metrics: 
    Messages send: ${metrics['messages.sent'].count} 
    Messages reply failures: ${metrics['messages.reply-failures'].count} 
    Messages pending: ${metrics['messages.pending'].count} 
    Messages received: ${metrics['messages.received'].count}
    """
}


