package com.vicente.microservicioproductos.service;

import com.vicente.microservicioproductos.model.Photo;
import com.vicente.microservicioproductos.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PhotoServiceImpl implements  PhotoService {

    private final PhotoRepository photoRepository;

    public PhotoServiceImpl(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @Override
    public Photo save(Photo photo) {
        return photoRepository.save(photo);
    }

    @Override
    public List<Photo> saveAll(List<Photo> photos) {
        return photoRepository.saveAll(photos);
    }

    @Override
    public void deleteById(String id) {
        photoRepository.deleteById(id);
    }
}
