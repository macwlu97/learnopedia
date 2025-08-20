
# Running Kafka with Content-Service and GPT-Service

## 1. Create Docker Network
```bash
docker network create kafka-net
````

## 2. Start ZooKeeper

```bash
docker run -d --name zookeeper --network kafka-net -p 2181:2181 zookeeper:3.8
```

## 3. Start Kafka

```bash
docker run -d --name kafka --network kafka-net -p 9092:9092 \
  -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  wurstmeister/kafka:latest
```

## 4. Create Kafka Topic

```bash
docker exec -it kafka bash
```

Inside the container:

```bash
kafka-topics.sh --create \
  --topic articles-to-gpt \
  --bootstrap-server localhost:9092 \
  --partitions 1 \
  --replication-factor 1
```

Verify:

```bash
kafka-topics.sh --list --bootstrap-server localhost:9092
```

## 5. Start Content-Service

```bash
cd content-service
mvn spring-boot:run
```

## 6. Start GPT-Service

```bash
cd gpt-service
mvn spring-boot:run
```

## 7. Test the Flow

Send a request to fetch an article:

```bash
curl -X POST "http://localhost:8080/articles?title=Albert_Einstein"
```

### What happens:

1. **content-service** fetches the article from Wikipedia.
2. Saves it to the database.
3. Publishes its ID to the Kafka topic `articles-to-gpt`.
4. **gpt-service** consumes the event, fetches the article, calls GPT, and logs the processed response.

## 8. Check GPT-Service Logs

```bash
docker logs -f <gpt_service_container_id>
```

Or check the Spring Boot console output.

```
kafka-console-consumer.sh \
  --bootstrap-server localhost:9092 \
  --topic articles-to-gpt \
  --from-beginning
```

or
```
kafka-console-consumer.sh \
--bootstrap-server localhost:9092 \
--topic articles-to-gpt \
--from-beginning \
--property print.key=true \
--property print.value=true
```