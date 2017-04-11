package com.makingdevs

vertx.eventBus().consumer("com.makingdevs.reader"){ message ->

  def path = message.headers()["path"]

	vertx.fileSystem().exists(path+"file.txt", { result ->
		if (result.succeeded() && result.result()) {
			vertx.eventBus().send("com.makingdevs.status", "El archivo existe!")
			vertx.eventBus().send("com.makingdevs.processor", path)
		} else {
			vertx.eventBus().send("com.makingdevs.status", "No existe el archivo")
		}
	})

}


vertx.eventBus().consumer("com.makingdevs.move"){ message ->
	vertx.fileSystem().move(message.body(), "${message.body()}processed.txt", {})
  vertx.eventBus().send("com.makingdevs.status", "Cambiando nombre del archivo y moviendo de directorio.")
}

