# Run this example with:

> vertx run deploy.groovy -Dvertx.metrics.options.enabled=true

# Change the path to text file

# Check your file.txt and your out.txt

# When the deploy verticle runs...

Please check the follow instructions:

Open many vertx shell 

> telnet localhost 3000

And use for the following verticles:

- com.makingdevs.get.metrics: Check the metrics about event bus.
- com.makingdevs.monitor: Check the shared map.

Send a simple message with:

> bus-sent com...
