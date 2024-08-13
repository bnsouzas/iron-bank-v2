package com.buddycash.ironbank.infra.configuration;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConfiguration {

    @Bean
    public NewTopic transactionTopic() {
        return TopicBuilder.name("transaction-events")
                .partitions(5)
                .build();
    }
}
