package com.vicente.microservicioproductos.service;

import com.vicente.microservicioproductos.model.Photo;

import java.util.List;

public interface PhotoService {

    Photo save(Photo photo);
    List<Photo> saveAll(List<Photo> photos);
    void deleteById(String id);
}
