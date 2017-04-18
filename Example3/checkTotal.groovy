import io.vertx.core.buffer.Buffer as Buffer

def map = vertx.sharedData().getLocalMap("cardsReady")

println "[ok] Check total verticle"

Integer counter= 0

vertx.eventBus().consumer("com.makingdevs.check.total"){message ->
  counter++
  def total = map.get("totalCards")
  List cards = map.get("cards")
  println "${counter} Checking ... ${cards.size}"
  if((total*2) == cards.size ){
    println ">------->"*5
    println "Finished ${counter} elements (Y)"
    println "Cards to write: ${cards.size}"
    def fileToWrite = cards.join("\n")

    vertx.fileSystem().writeFile("/Users/makingdevs/Desktop/out.txt", Buffer.buffer(fileToWrite)){ result ->
      if(result.succeeded()){
        vertx.eventBus().send("com.makingdevs.status", "Escritura del archivo de salida lista.")
      }else{
        vertx.eventBus().send("com.makingdevs.status", "Problemas en escritura del archivo de salida.")
      }
    }
  }
}
