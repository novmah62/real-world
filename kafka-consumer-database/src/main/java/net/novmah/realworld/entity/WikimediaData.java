package net.novmah.realworld.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(value = "wikimedia_data")
@Getter
@Setter
public class WikimediaData {

    @Id
    private String id;

    private String wikiEventData;

}
