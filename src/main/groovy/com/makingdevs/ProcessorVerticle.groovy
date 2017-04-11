package com.makingdevs

def sd = vertx.sharedData()
def batchIds = sd.getLocalMap("batch_ids")
def cardsIds = sd.getLocalMap("cards_ids")
def cardsProcessed = sd.getLocalMap("cards_processed")

vertx.eventBus().consumer("com.makingdevs.processor"){ message ->
	vertx.fileSystem().readFile("${message.body()}file.txt"){ result ->
    vertx.eventBus().send("com.makingdevs.move", message.body())

		if (result.succeeded()) {
      def batchId = UUID.randomUUID().toString()
      def lines = result.result().toString().split('\n')

			vertx.eventBus().send("com.makingdevs.status", "Start Batch Id ${batchId}" )

      batchIds.put(batchId, [batchId: batchId, count: lines.size(), processed: 0] )
      cardsProcessed.put("batchProcess", [])

      lines.eachWithIndex { line, idx ->
        vertx.eventBus().send("com.makingdevs.each.card", [batchId: batchId, line: line, idx:idx] )
      }

		}
    else{
			vertx.eventBus().send("com.makingdevs.status", "No se puede leer el archivo")
		}

	}
}


