package io.mhj.truckcrm.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.mhj.truckcrm.service.TruckJourneyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
//@KafkaListener
public class TruckConsumer {

    @Autowired
    private TruckJourneyService truckJourneyService;

    @KafkaListener(topics = {"trucks-events"})
    public void onMessage(ConsumerRecord<String, String> consumerRecord) throws JsonProcessingException {

        log.info("ConsumerRecord : {} ", consumerRecord);
        truckJourneyService.processEvent(consumerRecord);

    }
}
