+----------------+           +----------------+           +----------------+
| Content-Service|           |  Message Queue |           |  GPT-Service   |
|----------------|  Event    |----------------|  Event    |----------------|
| ArticleCreated |---------> | GenerateSummary|---------> |   Consume      |
| Publish Event  |           | Request Queue  |           |  Generate NLP  |
| Listen Response| <-------- | GenerateSummary| <-------- | Publish Result |
+----------------+           | Response Queue |           +----------------+
+----------------+


# Microservices Communication Architecture: Content-Service & GPT-Service

This document describes the recommended communication pattern between the `content-service` (managing articles) and the `gpt-service` (generating summaries or NLP tasks), optimized for high throughput and scalability.

---

## 🔹 Communication Pattern

For handling thousands of requests, **asynchronous messaging** via a message broker is recommended. This approach allows services to scale independently and process requests without blocking.

### Flow:

1. **Content-Service** creates or updates an article and publishes an event, e.g., `ArticleCreated` or `GenerateSummaryRequest`, to a message queue (RabbitMQ or Kafka).
2. **GPT-Service** listens to the queue, consumes the event, generates the response (e.g., summary or NLP output), and publishes the result back to a response queue `GenerateSummaryResponse`.
3. **Content-Service** listens to the response queue and updates the article in the database with the generated content.

---

## 🔹 ASCII Diagram




---

## 🔹 Advantages

- Handles thousands of requests without blocking.
- Services are decoupled → easier scaling.
- Retry mechanisms and dead-letter queues can handle failures gracefully.
- Independent deployment of services.

---

## 🔹 Technologies

- **Broker**: RabbitMQ or Kafka
- **Serialization**: JSON or Avro (Kafka)
- **Spring Boot Integration**: `spring-boot-starter-amqp` (RabbitMQ) or `spring-kafka`
- **Asynchronous Processing**: `@Async`, listeners, pollers

---

This architecture ensures that the `content-service` and `gpt-service` can handle high loads efficiently while remaining loosely coupled.



# Kafka Integration for Content-Service & GPT-Service

This document describes the Kafka setup and configuration for enabling event-driven communication between **Content-Service** and **GPT-Service**.

---

## 1️⃣ Kafka Setup (Local Docker)

Run Kafka and Zookeeper using Docker:

```bash
docker network create kafka-net

docker run -d --name zookeeper --network kafka-net -p 2181:2181 zookeeper:3.8

docker run -d --name kafka --network kafka-net -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  wurstmeister/kafka:latest
