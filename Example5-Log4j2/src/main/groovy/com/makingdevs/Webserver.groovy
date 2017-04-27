package com.makingdevs

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

vertx.createHttpServer().requestHandler({ req ->

  req.response()
  .putHeader("content-type", "text/plain")
  .end("Hello from Vert.x!")
}).listen(8080)

Logger logger = LogManager.getRootLogger();

logger.info("web-server ok");
