package com.buddycash.ironbank.events;

import com.buddycash.ironbank.domain.transactions.repositories.TransactionMongoRepository;
import com.buddycash.ironbank.infra.events.DataEventType;
import com.buddycash.ironbank.infra.events.TransactionDataEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationTransactionListener
    implements MessageListener<String, TransactionDataEvent> {
  private final TransactionMongoRepository transactionMongoRepository;

  public NotificationTransactionListener(TransactionMongoRepository transactionMongoRepository) {
    this.transactionMongoRepository = transactionMongoRepository;
  }

  @Override
  @KafkaListener(topics = "${app.topics.transaction}", groupId = "${spring.kafka.consumer.group-id}")
  public void onMessage(ConsumerRecord<String, TransactionDataEvent> data) {
    var event = data.value();
    var transaction = event.data();
    if (!data.value().type().equals(DataEventType.DELETE)) {
      transactionMongoRepository.save(data.value().data());
    } else {
      transactionMongoRepository.deleteById(data.value().data().id());
    }
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
