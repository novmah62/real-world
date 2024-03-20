package net.novmah.realworld.repository;

import net.novmah.realworld.entity.WikimediaData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface WikimediaDataRepository extends MongoRepository<WikimediaData, String> {
}
