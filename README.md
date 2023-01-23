# Kafka producer example

**Project to send a message to a Kafka topic using spring from Apache Kafka.**

## Prerequisites
- **Java 17**.
- **Apache Kafka cluster**, in order to create a Kafka Cluster locally we can use a docker image as described in https://hub.docker.com/r/bitnami/kafka. This will create a Kafka Cluster that will be available at localhost:9092.

## Run Locally

Execute following commands to build and run the Kafka-producer project.

```bash
# Build project & install dependencies
./gradlew build

# Run project 
./gradlew bootRun
```


## Testing

This project will automatically create a Kafka topic called _kfk-message-topic_, this topic will be used to store all message sent by this producer.

Endpoint _publish/_ is going to be available at http://localhost:8088/publish/. Using this endpoint we can send a message having the following structure.

RequestBody Example:

```
{
    "from" : "dmontoyane@gmail.com",
    "to": "test@gmail.com",
    "body": "Hi, Message sent to Kafka topic" 
}
```

Request example using Postman

![postman01.png](img%2Fpostman01.png)

To read messages sent to this topic, we can use a kafka consumer implementation. Check following repository with a kafka consumer implementation. https://github.com/DenilssonMontoya/kafka-consumer-spring/

We can also look into our kafka topic and validate that the message sent by producer has been stored there. To do so, we need to use a tool like **Offset Explorer** .(See https://www.kafkatool.com/documentation/connecting.html )

![offset01.png](img%2Foffset01.png)





