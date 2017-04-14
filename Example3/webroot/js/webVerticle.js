var eb = new EventBus('http://localhost:8000/eventbus');
eb.onopen = function() {
  eb.registerHandler('com.makingdevs.email.success', function(error, message) {
        console.log("Verticle message"+message.body);
    });
}
