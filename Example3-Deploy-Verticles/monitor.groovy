println "[ok] Monitor"

def map = vertx.sharedData().getLocalMap("cardsReady")

vertx.eventBus().consumer("com.makingdevs.monitor"){message ->
  def total = map.get("totalCards")
  List cards = map.get("cards")
  println """
  -------------------------------
  -------------------------------
  Monitor map: Share Map [totalCards]: ${cards.size} Total cards: ${total}
  -------------------------------
  -------------------------------
  """
}
