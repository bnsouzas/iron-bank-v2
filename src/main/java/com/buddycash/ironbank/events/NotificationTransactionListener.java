package com.buddycash.ironbank.events;

import com.buddycash.ironbank.domain.transactions.mappers.TransactionMapper;
import com.buddycash.ironbank.domain.transactions.models.Transaction;
import com.buddycash.ironbank.domain.transactions.repositories.TransactionSearchRepository;
import com.buddycash.ironbank.infra.events.TransactionDataEvent;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationTransactionListener
    implements MessageListener<String, TransactionDataEvent> {

  private final TransactionSearchRepository transactionSearchRepository;
  private final TransactionMapper transactionMapper;

  public NotificationTransactionListener(TransactionSearchRepository transactionSearchRepository, TransactionMapper transactionMapper) {
    this.transactionSearchRepository = transactionSearchRepository;
    this.transactionMapper = transactionMapper;
  }

  @Override
  @KafkaListener(topics = "${app.topics.transaction}", groupId = "iron-bank-notifications", concurrency = "3")
  public void onMessage(ConsumerRecord<String, TransactionDataEvent> data) {
    var event = data.value();
    var transaction = event.data();

    this.transactionSearchRepository.save(transaction);

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
