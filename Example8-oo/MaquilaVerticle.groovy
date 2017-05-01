def map = vertx.sharedData().getLocalMap("maquila")

//Simulating Db responses

vertx.eventBus().consumer("com.makingdevs.database.get.instructions"){message ->
  println "getting instructions list"
  def instructionList =[
                         [id:1234, total:6, fileName:"inst1"],
                         [id:5678, total:6, fileName:"inst2"],
                         [id:3456, total:6, fileName:"inst3"],
                         [id:3263, total:6, fileName:"inst4"]
                       ]
 message.reply(instructionList)
}

vertx.eventBus().consumer("com.makingdevs.database.get.products"){message ->
  println "getting product list"
  def productList = [
                      [fileIdenfier:"01", totalSize:"2"],
                      [fileIdenfier:"02", totalSize:"2"],
                      [fileIdenfier:"03", totalSize:"6"]
                    ]
  message.reply(productList)
}

vertx.eventBus().consumer("com.makingdevs.database.get.cards"){message ->
  println "getting card list"
  def cardList = [
                   [pan:"1234567890"],
                   [pan:"0987654321"]
                 ]
  message.reply(cardList)
}

//Starter
vertx.eventBus().consumer("com.makingdevs.starter.process"){message ->
  println "Iniciando proceso"
  vertx.eventBus().send("com.makingdevs.database.get.instructions", ""){replySimulator->
    if(replySimulator.succeeded()){
      println "1) Obteniendo lista de instrucciones, y procesandolas"
      map.put("instructions", [])
      vertx.eventBus().send("com.makingdevs.get.instructions", replySimulator.result.body())
    }
  }
}

vertx.eventBus().consumer("com.makingdevs.get.instructions"){message ->
  def instructionsInfo = message.body()
  println "2) Obteniendo objetos"
  instructionsInfo.each{ instruction ->
    def instructionsList = map.get("instructions")
    Instruction i = new Instruction(spareId:instruction.id, requestTotal:instruction.total, fileName: instruction.fileName)
    map.put("instructions", instructionsList + i)
  }
  vertx.eventBus().send("com.makingdevs.process.instructions", "finished")

}


vertx.eventBus().consumer("com.makingdevs.process.instructions"){message ->
  def instructionsObj = map.get("instructions")
  println "+ + +"*20
  println "todas las instructions fueron convertidas en objectos"

}







