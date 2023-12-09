package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityServices activityServices;

    @Autowired
    private UserServices userServices;

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

    @GetMapping("/registration/{activityId}")
    public CommonActivityDTO getCommonActivityInfoByID(@PathVariable Integer activityId) {
        return activityServices.getCommonActivityInfoByID(activityId);
    }

//    @PostMapping("/{activityId}/registration")
//    public Registration createActivityRegistration(@Valid @RequestPart("registration") ActivityRegisterDTO activityRegistration) {
//        return activityServices.registerActivity(activityRegistration);
//    }

    @PostMapping("/{activityId}/registration")
    public Registration createActivityRegistration(@Valid @RequestPart("user") UserRegistrationDTO user,@PathVariable Integer activityId) {
        return activityServices.registerActivity(user,activityId);
    }

    @GetMapping("/{activityId}/registrants")
    public List<RegistrantDTO> getActivityRegistrants(@PathVariable Integer activityId) {
        return userServices.getUserRegisteredActivity(activityId);
    }

    @DeleteMapping("/{id}")
    public Integer deleteActivity(@PathVariable Integer id) {
        return activityServices.delete(id);
    }

    @PostMapping("")
    public ActivityDTO createActivity(@RequestPart("activity") CreateNewActivityDTO activity
            , @RequestPart("location") LocationDTO location
            , @RequestPart("user") String userName) {
        return activityServices.save(activity, location, userName);
    }

    @PatchMapping("/{id}")
    public ActivityDTO updateActivity(@Valid @RequestPart("updateActivity") UpdateActivityDTO updateActivity
            , @Valid @RequestPart("updateLocation") LocationDTO updateLocation
            , @PathVariable Integer id) {
        return activityServices.update(id, updateActivity, updateLocation);
    }
}