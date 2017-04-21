import io.vertx.ext.shell.ShellService
import io.vertx.ext.dropwizard.MetricsService

println "[ok] Shell and metrics"

def shell = ShellService.create(vertx, [telnetOptions:[ host:"localhost", port:3000]])
shell.start()

def service = MetricsService.create(vertx)

vertx.setPeriodic(1000) { t ->
    def metrics = service.getMetricsSnapshot(vertx.eventBus())
    vertx.eventBus().publish("com.makingdevs.metrics", metrics)
}

vertx.eventBus().consumer("com.makingdevs.metrics"){ message ->
    def metrics = message.body()
    println "*-*-"*10
    println "Messages delivered:  "+metrics["messages.sent"]
}


