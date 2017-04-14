import io.vertx.ext.web.handler.StaticHandler
import io.vertx.ext.web.Router
import io.vertx.core.Vertx
import io.vertx.ext.web.handler.sockjs.SockJSHandler

println "[ok] webserver"

def server = vertx.createHttpServer()

def router = Router.router(vertx)

def opts = [
  outboundPermitteds:[
      [ address:"com.makingdevs.web.monitor" ]
    ]
]

def ebHandler = SockJSHandler.create(vertx).bridge(opts)
router.route("/eventbus/*").handler(ebHandler)

router.route("/static/*").handler(StaticHandler.create().setCachingEnabled(false))

server.requestHandler(router.&accept).listen(8080)
    
