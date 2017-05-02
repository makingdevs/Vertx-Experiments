import io.vertx.core.json.JsonArray

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
  vertx.eventBus().send("com.makingdevs.database.get.instructions", ""){replyDatabase->
    if(replyDatabase.succeeded()){
      println "1) Obteniendo lista de instrucciones, y enviandolas para generar objetos"
      vertx.eventBus().send("com.makingdevs.get.instructions", replyDatabase.result.body())
    }
  }
}

vertx.eventBus().consumer("com.makingdevs.get.instructions"){message ->
  def instructionsInfo = message.body()
  println "2) Obteniendo objetos"

  def instructionIdList = []
  def instructionJsonList = instructionsInfo.collect{ instruction ->
    Instruction i = new Instruction(spareId:instruction.id, requestTotal:instruction.total, fileName: instruction.fileName)
    instructionIdList << i.id
    i.toJson()
  }

  map.put("instructions", instructionJsonList)

  vertx.eventBus().send("com.makingdevs.process.instructions", instructionIdList)

}


vertx.eventBus().consumer("com.makingdevs.process.instructions"){message ->

  def idList = message.body()
  def jsonList = map.get("instructions")
  println "+ + +"*20
  println "Hay en el shareMap ${jsonList.size}"
  jsonList.each{i ->
    println i.dump()
  }

  /*
  Instruction instruction = InstructionService.toInstruction(jsonList[0])
  println "--------- o ---------"*5
  println instruction.dump()
*/

}







