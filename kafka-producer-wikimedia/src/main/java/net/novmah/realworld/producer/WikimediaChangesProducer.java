package net.novmah.realworld.producer;

import com.launchdarkly.eventsource.ConnectStrategy;
import com.launchdarkly.eventsource.EventSource;
import com.launchdarkly.eventsource.background.BackgroundEventHandler;
import com.launchdarkly.eventsource.background.BackgroundEventSource;
import net.novmah.realworld.handler.WikimediaChangesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.concurrent.TimeUnit;

@Component
public class WikimediaChangesProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WikimediaChangesProducer.class);

    @Value("${apis.wikimedia.url}")
    private String url;

    @Value("${spring.kafka.topic.name}")
    private String topicName;
    private KafkaTemplate<String, String> kafkaTemplate;

    public WikimediaChangesProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage() {

        // to read real time stream data from wikimedia, we use event source
        BackgroundEventHandler eventHandler = new WikimediaChangesHandler(kafkaTemplate, topicName);

        BackgroundEventSource eventSource = new BackgroundEventSource.Builder(eventHandler,
                new EventSource.Builder(ConnectStrategy.http(URI.create(url)))).build();
        eventSource.start();

        try {
            TimeUnit.MINUTES.sleep(10);
        } catch (InterruptedException e) {
            LOGGER.error(String.format("Error: %s", e.getMessage()));
            throw new RuntimeException("An error has occurred");
        }


    }
}
