package io.mhj.truckcrm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.mhj.truckcrm.domain.TruckJourney;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class TruckProducer {

    @Value("${spring.kafka.topic}")
    public String topic;

    private final KafkaTemplate<Integer, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public TruckProducer(KafkaTemplate<Integer, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTruckJourney(List<TruckJourney> truckJourney) throws JsonProcessingException {

        for(int i=0;i<truckJourney.size();i++) {
            var key = i;
            var value = objectMapper.writeValueAsString(truckJourney.get(i));
            var producerRecord = buildProducerRecord(i, value);
            var completableFuture = kafkaTemplate.send(producerRecord);
            completableFuture
                    .whenComplete((sendResult, throwable) -> {
                        if (throwable != null) {
                            handleFailure(key, value, throwable);
                        } else {
                            handleSuccess(key, value, sendResult);
                        }
                    });
        }

    }


    private ProducerRecord<Integer, String> buildProducerRecord(Integer key, String value) {
        List<Header> recordHeaders = List.of(new RecordHeader("event-source","scanner".getBytes()));
        return new ProducerRecord<>(topic, null, key, value, recordHeaders);
    }


    private void handleSuccess(Integer key, String value, SendResult<Integer, String> sendResult) {
        log.info("Message Sent Successfully for the key : {} and the value : {} , partition is {} ",
                key, value, sendResult.getRecordMetadata().partition());
    }

    private void handleFailure(Integer key, String value, Throwable ex) {
        log.error("Error sending the message and the exception is {} ", ex.getMessage(), ex);
    }
}
