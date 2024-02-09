package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.*;
import com.nk2.unityDoServices.Repositories.ImageRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;

@Service
public class ImageServices {
    private ImageRepository repository;

    public Image save(Image image) {
        return repository.saveAndFlush(image);
    }

    public Image update(Integer id, Image updatedImage) {
        Image editedImage = repository.findById(id).map(image -> {
            image.setImagepath(updatedImage.getImagepath());
            return image;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + id + " Does Not Exist !!!"));
        repository.saveAndFlush(editedImage);
        return editedImage;
    }

}
