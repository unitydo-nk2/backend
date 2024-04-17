package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.Activity.*;
import com.nk2.unityDoServices.DTOs.Image.CreateNewImageDTO;
import com.nk2.unityDoServices.DTOs.Image.ImageDTO;
import com.nk2.unityDoServices.DTOs.Location.LocationDTO;
import com.nk2.unityDoServices.DTOs.Activity.ActivityReviewDTO;
import com.nk2.unityDoServices.DTOs.User.RegistrantDTO;
import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.Image;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Services.ImageServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
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
    public List<ActivityListDTO> getActivityList(HttpServletRequest httpServletRequest) {
        return activityServices.getActivityList(httpServletRequest);
    }

    @GetMapping("/management")
    public List<ActivityListDTO> getActivityManagement(HttpServletRequest httpServletRequest) {
        return activityServices.getActivityManagement(httpServletRequest);
    }

    @GetMapping("/popular")
    public List<PopularActivityDTO> getActivityPopularActivity() {
        return activityServices.getPopularActivity();
    }

    @GetMapping("/comingSoon")
    public List<ActivityCardSliderListDTO> getActivityComingSoonActivity() {
        return activityServices.getComingSoonActivity();
    }

    @GetMapping("/similar/{activityId}")
    public List<ActivityCardSliderListDTO> getActivityPopularActivity(@PathVariable Integer activityId) {
        return activityServices.getSimilarActivity(activityId);
    }

    @GetMapping("/recommends")
    public List<ActivityCardSliderListDTO> getRecommendActivity(HttpServletRequest request) {
        return activityServices.getRecommendsActivity(request) ;
    }

    @GetMapping("/personalActivity")
    public List<ActivityCardSliderListDTO> getPersonalRecommendActivity(HttpServletRequest request) {
        return activityServices.getPersonalRecommendActivity(request) ;
    }

    @GetMapping("/userRegistration")
    public List<ActivityWithRegistrationNumberDTO> getActivityWithRegistrationNumber() {
        return activityServices.getActivityRegistrationNumberDTO();
    }

    @GetMapping("/{activityId}")
    public ActivityDTO getActivityDetailById(@PathVariable Integer activityId) {
        return activityServices.getActivityByID(activityId);
    }

    @GetMapping("/{activityId}/images")
    public List<ImageDTO> getImageByActivityId(@PathVariable Integer activityId) {
        return imageServices.getImagesByActivityId(activityId);
    }

    @GetMapping("/registration/{activityId}")
    public CommonActivityDTO getActivityDetailForRegistrationPage(@PathVariable Integer activityId) {
        return activityServices.getActivityDetailForRegistrationPage(activityId);
    }

    @PostMapping("/{activityId}/registration")
    public Registration createActivityRegistration(HttpServletRequest request, @Valid @RequestBody Integer userId, @PathVariable Integer activityId) {
        return activityServices.registerActivity(request, userId, activityId);
    }

    @GetMapping("/favorite")
    public List<ActivityListDTO> getActivityFavorite() {
        return activityServices.getActivityFavorite();
    }

    @GetMapping("/{activityId}/registrants")
    public List<RegistrantDTO> getActivityRegistrant(@PathVariable Integer activityId) {
        return userServices.getUserRegisteredActivity(activityId);
    }

    @GetMapping("/review/{activityId}")
    public List<ActivityReviewListDTO> getActivityReviews(@PathVariable Integer activityId) {
        return activityServices.getActivityReviews(activityId);
    }

//    @GetMapping("/{activityId}/registrants")
//    public List<Object[]> getActivityRegistrants(@PathVariable Integer activityId) {
//        return userServices.getUserRegisteredActivity(activityId);
//    }

    @DeleteMapping("/{id}")
    public Integer deleteActivity(HttpServletRequest httpServletRequest, @PathVariable Integer id) {
        return activityServices.delete(httpServletRequest, id);
    }

    @PostMapping("")
    public ActivityDTO createActivity(
            @Valid @RequestPart("activity") CreateNewActivityDTO activity
            , @Valid @RequestPart("location") LocationDTO location) {
        return activityServices.save(activity, location);
    }

    @PostMapping("/review/{activityId}")
    public ActivityReviewDTO reviewActivity(HttpServletRequest httpServletRequest, @PathVariable Integer activityId, @RequestPart("review") ActivityReviewDTO review) {
        return activityServices.reviewActivity(activityId,review);
    }

    @PatchMapping("/review/{activityReviewId}")
    public ActivityReviewDTO updateActivityReview(@PathVariable Integer activityReviewId,@Valid @RequestPart("review") ActivityReviewDTO review) {
        return activityServices.updateReviewActivity(activityReviewId,review);
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

    @PatchMapping("/updateToDone/{id}")
    public Activity updateActivityToDone(@PathVariable Integer id) {
        return activityServices.updateActivityToDone(id);
    }

    @PatchMapping("/{id}")
    public ActivityDTO updateActivity(
            HttpServletRequest httpServletRequest
            , @Valid @RequestPart("updateActivity") UpdateActivityDTO updateActivity
            , @Valid @RequestPart("updateLocation") LocationDTO updateLocation
            , @PathVariable Integer id) {
        return activityServices.update(httpServletRequest, id, updateActivity, updateLocation);
    }
}
