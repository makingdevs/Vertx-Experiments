package com.makingdevs

import io.vertx.core.buffer.Buffer

def sd = vertx.sharedData()
def batchIds = sd.getLocalMap("batch_ids")
def cardsIds = sd.getLocalMap("cards_ids")
def cardsProcessed = sd.getLocalMap("cards_processed")

vertx.eventBus().consumer("com.makingdevs.processor"){ message ->
	vertx.fileSystem().readFile("${message.body()}file.txt"){ result ->
    vertx.eventBus().send("com.makingdevs.move", message.body())

		if (result.succeeded()) {
      cardsProcessed.put("cards", [])
      def batchId = UUID.randomUUID().toString()
      def lines = result.result().toString().split('\n')

			vertx.eventBus().send("com.makingdevs.status", "Start Batch Id ${batchId}" )

      batchIds.put(batchId, [batchId: batchId, count: lines.size(), processed: 0] )

      lines.eachWithIndex { line, idx ->
        vertx.eventBus().send("com.makingdevs.each.card", [batchId: batchId, line: line, idx:idx] )
      }

		}
    else{
			vertx.eventBus().send("com.makingdevs.status", "No se puede leer el archivo")
		}

	}
}

vertx.eventBus().consumer("com.makingdevs.batch.card"){ message ->

  def params = message.body()
  def batchId = params.batchId
  def batch = batchIds.get(batchId)
	def card = cardsIds.get(params.line)

  def cardProcessed = "${params.idx + 1} ${params.batchId} ${params.line} <${params.cvv.join('-')}>"

  println "----"*10
  println params.dump()
  println batch
  println card
  println "----"*10

	vertx.eventBus().send("com.makingdevs.status", cardProcessed)
	batch.processed = batch.processed + 1
  batchIds.put(batchId, batch)

  if( batch.count == batch.processed ){
    vertx.eventBus().send("com.makingdevs.writer", "write")
    vertx.eventBus().send("com.makingdevs.status", "Batch finished: ${batchId}")

  }
}


vertx.eventBus().consumer("com.makingdevs.writer"){message ->
  def cardsToWrite = ""
  if(message.body()=="write"){
    println "*-->"*10
    println cardsToWrite
  }else{
    cardsToWrite = cardsToWrite+"${message.body()}\n"
  }
}

vertx.eventBus().consumer("com.makingdevs.write.file"){ message ->
	vertx.fileSystem().writeFile("/Users/makingdevs/maquila/out.txt", Buffer.buffer(message.body()),{ result ->
			if(result.succeeded()){
				println "File written."
			}else{
				println "File written Error"
			}
	})
}

