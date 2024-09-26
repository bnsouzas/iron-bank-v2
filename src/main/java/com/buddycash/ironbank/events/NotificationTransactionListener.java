package com.buddycash.ironbank.events;

import com.buddycash.ironbank.infra.events.TransactionDataEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationTransactionListener
    implements MessageListener<String, TransactionDataEvent> {
  @Override
  @KafkaListener(topics = "${app.topics.transaction}", groupId = "iron-bank-notifications")
  public void onMessage(ConsumerRecord<String, TransactionDataEvent> data) {
    var event = data.value();
    var transaction = event.data();
    System.out.println(
        String.format(
            "%s %s %s %s with price %7.2f",
            event.eventTime(),
            event.type(),
            transaction.type(),
            transaction.name(),
            transaction.price()));
  }
}
