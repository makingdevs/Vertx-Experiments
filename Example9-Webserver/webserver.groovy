import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.Router
import io.vertx.core.Vertx
import io.vertx.ext.web.handler.sockjs.SockJSHandler
import io.vertx.ext.dropwizard.MetricsService

println "Webserver ok"

def service = MetricsService.create(vertx)
def server = vertx.createHttpServer()
def router = Router.router(vertx)
def sharedMap = vertx.sharedData().getLocalMap("cards")

def opts = [
            outboundPermitteds:[
              [ address:"com.makingdevs.web.monitor" ]
              ]
           ]

def ebHandler = SockJSHandler.create(vertx).bridge(opts)

router.route("/eventbus/*").handler(ebHandler)
router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false))

Integer counter = 0

vertx.setPeriodic(1000){ t ->
  println "Enviando a web monitor"
  vertx.eventBus().send("com.makingdevs.web.monitor", counter)
  counter++
}

server.requestHandler(router.&accept).listen(8080)

