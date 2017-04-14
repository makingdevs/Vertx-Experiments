package com.makingdevs

vertx.eventBus().consumer("com.makingdevs.reader"){ message ->

  def path = message.headers()["path"]
	vertx.fileSystem().exists(path+"file.txt"){ result ->

		if (result.succeeded() && result.result()) {
			vertx.eventBus().send("com.makingdevs.status", "El archivo existe!")
			vertx.eventBus().send("com.makingdevs.processor", path)
		} else {
			vertx.eventBus().send("com.makingdevs.status", "No existe el archivo")
		}
	}

}


vertx.eventBus().consumer("com.makingdevs.move"){ message ->
	vertx.fileSystem().move("${message.body()}file.txt", "${message.body()}processed.txt"){ response ->
    if (response.succeeded())
      vertx.eventBus().send("com.makingdevs.status", "Cambio nombre del archivo y moviendo de directorio.")
    else
      vertx.eventBus().send("com.makingdevs.status", "No se pudo mover el archivo al directorio.")
  }
}

