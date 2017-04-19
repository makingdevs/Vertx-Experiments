println "[ok] Monitor"

def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.monitor"){
  def total = map.get("totalCards")
  List cards = map.get("cards")
  def flags = map.get("flags")
  def indexList = map.get("index")

  println """
  ---------------------------------------->
  ---------------------------------------->
  Total of lines:${total}
  Total of cards processed: ${cards.size}
  Flag counter:${flags}
  Index list lines: ${indexList.size} 
  ---------------------------------------->
  ---------------------------------------->
  """
}
