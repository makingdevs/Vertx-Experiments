def timerID = vertx.setTimer(1000) { id ->
    println "And one second later this is printed"
}
println "Print first"
