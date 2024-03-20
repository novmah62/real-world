package net.novmah.realworld.consumer;

import net.novmah.realworld.entity.WikimediaData;
import net.novmah.realworld.repository.WikimediaDataRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DatabaseConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConsumer.class);

    private WikimediaDataRepository dataRepository;

    public DatabaseConsumer(WikimediaDataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    @KafkaListener(
            topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String eventMessage) {

        LOGGER.info(String.format("Event message received -> %s", eventMessage));

        WikimediaData wikimediaData = new WikimediaData();
        wikimediaData.setWikiEventData(eventMessage);
        dataRepository.save(wikimediaData);

    }

}
