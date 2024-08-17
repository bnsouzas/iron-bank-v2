package com.buddycash.ironbank.infra.events;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventProducer {

    @Value("${app.topics.transaction}")
    private String topicName;

    @Autowired
    private KafkaTemplate<String, TransactionDataEvent> template;

    public void publish(DataEventType type, TransactionResponse saved) {
        var dataEvent = new TransactionDataEvent(type, saved);
        var event = template.send(topicName, dataEvent);
        event.whenComplete((result, ex) -> {
            if (ex != null)
                System.out.println(ex.getMessage());
        });
    }
}
