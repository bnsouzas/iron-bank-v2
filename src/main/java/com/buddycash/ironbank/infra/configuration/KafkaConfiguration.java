package com.buddycash.ironbank.infra.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfiguration {

  @Value("${app.topics.transaction}")
  private String topicName;

  @Bean
  public NewTopic transactionTopic() {
    return TopicBuilder.name(topicName).partitions(5).replicas(3).build();
  }
}
