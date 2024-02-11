package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.Image;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Services.ImageServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityServices activityServices;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ImageServices imageServices;

    @GetMapping("/list")
    public List<ActivityListDTO> getActivityList() {
        return activityServices.getActivityList();
    }

    @GetMapping("/poster")
    public List<ActivityImageDTO> getActivityPoster() {
        return activityServices.getActivityPoster();
    }

    @GetMapping("/popular")
    public List<ActivityBannerDTO> getActivityPopularActivities() {
        return activityServices.getPopularActivity();
    }

    @GetMapping("/recommends")
    public List<ActivityRecommendationDTO> getActivityRecommendsActivities() {
        return activityServices.getRecommendedActivity();
    }
    @GetMapping("/userRegistration")
    public List<ActivityWithRegistrationNumberDTO> getActivityRegistrationNumber() {
        return activityServices.getActivityRegistrationNumberDTO();
    }

    @GetMapping("/{activityId}")
    public ActivityDTO getActivityDetailsById(@PathVariable Integer activityId) {
        return activityServices.getActivityByID(activityId);
    }

    @GetMapping("/{activityId}/images")
    public List<ImageDTO> getImagesByActivityId(@PathVariable Integer activityId) {
        return imageServices.getImagesByActivityId(activityId);
    }

    @GetMapping("/registration/{activityId}")
    public CommonActivityDTO getCommonActivityInfoByID(@PathVariable Integer activityId) {
        return activityServices.getCommonActivityInfoByID(activityId);
    }

    @PostMapping("/{activityId}/registration")
    public Registration createActivityRegistration(@Valid @RequestPart("user") UserRegistrationDTO user,@PathVariable Integer activityId) {
        return activityServices.registerActivity(user,activityId);
    }

    @GetMapping("/{activityId}/registrants")
    public List<RegistrantDTO> getActivityRegistrants(@PathVariable Integer activityId) {
        return userServices.getUserRegisteredActivity(activityId);
    }

//    @GetMapping("/{activityId}/registrants")
//    public List<Object[]> getActivityRegistrants(@PathVariable Integer activityId) {
//        return userServices.getUserRegisteredActivity(activityId);
//    }

    @DeleteMapping("/{id}")
    public Integer deleteActivity(@PathVariable Integer id) {
        return activityServices.delete(id);
    }

    @PostMapping("")
    public ActivityDTO createActivity(@Valid @RequestPart("activity") CreateNewActivityDTO activity
            , @Valid @RequestPart("location") LocationDTO location
            , @Valid @RequestPart("user") String userName) {
        return activityServices.save(activity, location, userName);
    }

    @PostMapping("/images")
    public Image imageUpload(@Valid @RequestPart("image") CreateNewImageDTO image) {
        return imageServices.save(image);
    }

    @PatchMapping("/images/{id}")
    public Image updateImageUpload(@Valid @RequestPart("updateImage") String updatePath
            , @PathVariable Integer id) {
        return imageServices.update(id, updatePath);
    }

    @PatchMapping("/{id}")
    public ActivityDTO updateActivity(@Valid @RequestPart("updateActivity") UpdateActivityDTO updateActivity
            , @Valid @RequestPart("updateLocation") LocationDTO updateLocation
            , @PathVariable Integer id) {
        return activityServices.update(id, updateActivity, updateLocation);
    }
}
