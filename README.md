# kafka-example
Spring Kafka Steam WebAPI example

This example uses Java 15.

## Setup

This guide is more about how to use Kafka with Spring Apache Kafka, for more details about Apache Kafka itself please check https://kafka.apache.org/

### Download Kafka

To run this example download Kafka at https://kafka.apache.org/downloads.

To setup Kafka after download check https://kafka.apache.org/quickstart

### Create the topic used in the example

For this example a specific topic will be used named `player_count_stats`.
To create the topic, run the command below. (Please make sure you're running the correct script based on your environment Linux/Windows)

```kafka-topics.bat --bootstrap-server 127.0.0.1:9092 --create --topic player_count_stats --partition 3```

After the topic is created, run the `kafka-example-producer` project, and after it the `kafka-example-consumer`.
The producer should start getting the information of the specified games in `games.json (Producer config file)`, query the current player count for those games and send the result to the consumer. In order to keep this example simple and more focused on Kafka, the consumer will simply log the result.
