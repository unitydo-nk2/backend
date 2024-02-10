package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.*;
import com.nk2.unityDoServices.Repositories.ActivityRepository;
import com.nk2.unityDoServices.Repositories.ImageRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ImageServices {

    @Autowired
    private ImageRepository repository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    public Image save(CreateNewImageDTO image) {
        Image newImage = new Image();
        Activity activity = activityRepository.findById(image.getActivityId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + image.getActivityId() + " Does Not Exist !!!"));
        newImage.setActivityId(activity);
        newImage.setLabel(image.getLabel());
        newImage.setAlt(image.getAlt());
        newImage.setImagepath(image.getImagePath());
        return repository.saveAndFlush(newImage);
    }

    public Image update(Integer id, String updatedPath) {
        Image editedImage = repository.findById(id).map(image -> {
            image.setImagepath(updatedPath);
            return image;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + id + " Does Not Exist !!!"));
        repository.saveAndFlush(editedImage);
        return editedImage;
    }

    public List<ImageDTO> getImagesByActivityId(Integer activityId) {
        List<Image> imageList = repository.getImageListbyActivityId(activityId);
        return listMapper.mapList(imageList, ImageDTO.class, modelMapper);
    }
}
