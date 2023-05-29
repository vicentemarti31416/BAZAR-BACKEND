package com.vicente.microservicioproductos.repository;

import com.vicente.microservicioproductos.model.Photo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhotoRepository extends MongoRepository<Photo, String> {
}
