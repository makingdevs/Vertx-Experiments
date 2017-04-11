package com.makingdevs
println "reader on"
vertx.eventBus().consumer("com.makingdevs.reader"){ message ->

	vertx.fileSystem().exists("/home/carlogilmar/Desktop/Github/medios-pago-maquila/file.txt", { result ->
		if (result.succeeded() && result.result()) {
			vertx.eventBus().send("com.makingdevs.status", "El archivo existe!")
			vertx.eventBus().send("com.makingdevs.processor", "/Users/makingdevs/maquila/file.txt")
		} else {
			vertx.eventBus().send("com.makingdevs.status", "No existe el archivo")
		}
	})

}


vertx.eventBus().consumer("com.makingdevs.move"){ message ->
	vertx.fileSystem().move("/home/carlogilmar/Desktop/Github/medios-pago-maquila/file.txt","/home/carlogilmar/Desktop/Github/medios-pago-maquila/processed.txt", {})
}

