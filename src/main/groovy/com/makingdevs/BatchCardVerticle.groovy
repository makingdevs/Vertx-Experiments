package com.makingdevs
import io.vertx.groovy.core.buffer.Buffer

def sd = vertx.sharedData()
def batchIds = sd.getLocalMap("batch_ids")
def cardsIds = sd.getLocalMap("cards_ids")
def cardsProcessed = sd.getLocalMap("cards_processed")

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
  println "Counter size wrong: ${batch.count}"

  if( batch.count == batch.processed ){
    println "Counter size: ${batch.count}"
    vertx.eventBus().send("com.makingdevs.monitor", "Batch finished: ${batchId}")
  }
}


vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  def cardsFinished = cardsProcessed.get("batchProcess")
  println "Monitor size: ${cardsFinished.size}"
  vertx.eventBus().send("com.makingdevs.writer", cardsFinished)
  vertx.eventBus().send("com.makingdevs.status", "${message.body()} with ${cardsFinished.size} cards")
}

vertx.eventBus().consumer("com.makingdevs.writer"){ message ->

  def cards = message.body()
  //def cardsToSave = cards.unique()
  println "----------------------------------------------"
  println "Cards que llegan:"+cards.size
  //println "Cards unique"+cardsToSave.size
  println "----------------------------------------------"
  def fileToWrite = cards.join("\n")

	vertx.fileSystem().writeFile("/home/carlogilmar/Desktop/Github/medios-pago-maquila/out.txt", Buffer.buffer(fileToWrite)){ result ->
			if(result.succeeded()){
        vertx.eventBus().send("com.makingdevs.status", "Escritura del archivo de salida lista.")
 			}else{
        vertx.eventBus().send("com.makingdevs.status", "Problemas en escritura del archivo de salida.")
			}
	}
}

