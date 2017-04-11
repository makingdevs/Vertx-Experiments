package com.makingdevs

def sd = vertx.sharedData()
def cardsIds = sd.getLocalMap("cards_ids")

vertx.eventBus().consumer("com.makingdevs.each.card"){ message ->

  def params = message.body()

	cardsIds.put(params.line, [cvv1:null,cvv2:null,cvv3:null])

  def cvv = []

  vertx.eventBus().send("com.makingdevs.ws", params.line) { r1 ->
    if(r1.succeeded()){
			def card = cardsIds.get(params.line)
			cardsIds.put(params.line, card + [cvv1: r1.result().body()])
			vertx.eventBus().send("com.makingdevs.card.coordinator", params)
		}
    else{
			vertx.eventBus().send("com.makingdevs.status", "Error al invocar llamada a webservice")
    }
  }

  vertx.eventBus().send("com.makingdevs.ws", params.line) { r2 ->
    if(r2.succeeded()){
			def card = cardsIds.get(params.line)
			cardsIds.put(params.line, card + [cvv2: r2.result().body()])
			vertx.eventBus().send("com.makingdevs.card.coordinator", params)
		}else{
			vertx.eventBus().send("com.makingdevs.status", "Error al invocar llamada a webservice")
    }
  }

  vertx.eventBus().send("com.makingdevs.ws", params.line) { r3 ->
    if(r3.succeeded()){
			def card = cardsIds.get(params.line)
			cardsIds.put(params.line, card + [cvv3: r3.result().body()])
			vertx.eventBus().send("com.makingdevs.card.coordinator", params)
		}
    else{
			vertx.eventBus().send("com.makingdevs.status", "Error al invocar llamada a webservice")
    }
  }
}

vertx.eventBus().consumer("com.makingdevs.ws"){ message ->
  def line = message.body()
  Thread.sleep(new Random().nextInt(1000))
  message.reply(line.hashCode().abs())
}

vertx.eventBus().consumer("com.makingdevs.card.coordinator"){ message ->
	def params = message.body()
	def card = cardsIds.get(params.line)

	if(card.every { k, v -> v != null }){
		def cvv = card.collect { k,v -> v }
		vertx.eventBus().send("com.makingdevs.batch.card", params + [cvv: cvv])
	}
}
