package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.ActivityDTO;
import com.nk2.unityDoServices.DTOs.CreateNewActivityDTO;
import com.nk2.unityDoServices.DTOs.CreateNewUserDTO;
import com.nk2.unityDoServices.DTOs.LocationDTO;
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

}
