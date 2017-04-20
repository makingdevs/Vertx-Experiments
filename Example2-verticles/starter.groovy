println "starter on"

def cards = ["1234567890", "234567890", "34567890", "4567890", "567890"]

cards.each{ card ->
    vertx.eventBus().send("com.makingdevs.reader", "${card} ping")
}
