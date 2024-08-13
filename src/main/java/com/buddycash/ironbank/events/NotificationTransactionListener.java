package com.buddycash.ironbank.events;

import com.buddycash.ironbank.domain.transactions.data.TransactionResponse;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationTransactionListener implements MessageListener<String, TransactionResponse> {
    @Override
    @KafkaListener(topics = "${app.topics.transaction}", groupId = "iron-bank-notifications")
    public void onMessage(ConsumerRecord<String, TransactionResponse> data) {
        var transaction = data.value();
        System.out.println(String.format("New %s %s with price %7.2f", transaction.type(), transaction.name(), transaction.price()));
    }
}
