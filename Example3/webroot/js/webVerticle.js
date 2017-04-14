var eb = new EventBus('http://localhost:8080/eventbus');
eb.onopen = function() {
  eb.registerHandler('com.makingdevs.web.monitor', function(error, message) {
        $("#log").append(message.body+"<br>")
    });
}
