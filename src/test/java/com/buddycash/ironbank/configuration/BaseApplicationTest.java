package com.buddycash.ironbank.configuration;

import com.buddycash.ironbank.events.NotificationTransactionListener;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.elasticsearch.ElasticsearchContainer;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

public abstract class BaseApplicationTest {
  static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17-alpine");

  static final KafkaContainer kafka = new KafkaContainer("apache/kafka:3.8.0");

  static final ElasticsearchContainer elastic = new ElasticsearchContainer(
      DockerImageName.parse("docker.elastic.co/elasticsearch/elasticsearch:7.9.2"));

  @BeforeAll
  static void beforeAll() {
    postgres.start();
    kafka.start();
    elastic.start();
  }

  @AfterAll
  static void afterAll() {
  }

  @DynamicPropertySource
  static void configureProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.url", postgres::getJdbcUrl);
    registry.add("spring.datasource.username", postgres::getUsername);
    registry.add("spring.datasource.password", postgres::getPassword);
    registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
    registry.add("spring.elasticsearch.uris", elastic::getHttpHostAddress);
    registry.add("spring.elasticsearch.username", null);
  }
}
