# rabbitmq-consumer #

## About ##

Main purpose of this code is present how to consume RabbitMQ messages by Java UI application.

## Requirements ##

1. Access to [RabbitMQ server](http://www.rabbitmq.com/download.html)
2. [Maven](http://maven.apache.org/)

## Usaeg ##

Clone this, navigate to cloned repo and launch maven command: 

```bash
mvn clean package assembly:single
```

Navigate to ```target``` directory and run command:

```bash
java -jar consumer-1.0-jar-with-dependencies.jar
```

It execute GUI application allows You to consume messages from RabbitMQ.
Enjoy.
