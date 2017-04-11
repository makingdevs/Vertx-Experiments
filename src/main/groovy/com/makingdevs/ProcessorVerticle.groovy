package com.makingdevs
import io.vertx.groovy.core.buffer.Buffer

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

vertx.eventBus().consumer("com.makingdevs.batch.card"){ message ->

  def params = message.body()
  def batchId = params.batchId
  def batch = batchIds.get(batchId)
	def card = cardsIds.get(params.line)

  def cardsToSave = cardsProcessed.get("batchProcess")
  def cardReady = "${params.idx + 1} ${params.batchId} ${params.line} <${params.cvv.join('-')}>"
  cardsProcessed.put("batchProcess", cardsToSave+cardReady)

	vertx.eventBus().send("com.makingdevs.status", cardReady)
	batch.processed = batch.processed + 1
  batchIds.put(batchId, batch)

  if( batch.count == batch.processed ){
    println "counter : ${batch.count}"
    vertx.eventBus().send("com.makingdevs.monitor", "Batch finished: ${batchId}")
  }
}


vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  def cardsFinished = cardsProcessed.get("batchProcess")
  vertx.eventBus().send("com.makingdevs.writer", cardsFinished)
  vertx.eventBus().send("com.makingdevs.status", "${message.body()} with ${cardsFinished.size} cards")
}

vertx.eventBus().consumer("com.makingdevs.writer"){ message ->
  def cards = message.body()
  def cardsToSave = cards.unique()
  println "----------------------------------------------"
  println "Cards que llegan:"+cards.size
  println "Cards unique"+cardsToSave.size
  println "----------------------------------------------"
  def fileToWrite = cardsToSave.join("\n")
	vertx.fileSystem().writeFile("/home/carlogilmar/Desktop/Github/medios-pago-maquila/out.txt", Buffer.buffer(fileToWrite)){ result ->
			if(result.succeeded()){
        vertx.eventBus().send("com.makingdevs.status", "Escritura del archivo de salida lista.")
 			}else{
        vertx.eventBus().send("com.makingdevs.status", "Problmeas en escritura del archivo de salida.")
			}
	}
}

