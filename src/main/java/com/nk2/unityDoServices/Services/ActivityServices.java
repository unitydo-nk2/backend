package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtAuthenticationFilter;
import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.*;
import com.nk2.unityDoServices.Repositories.*;
import com.nk2.unityDoServices.Services.Mappers.ActivityMapperService;
import com.nk2.unityDoServices.Utils.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServices {

    @Autowired
    private ActivityRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LocationServices locationServices;

    @Autowired
    private ActivityMapperService activityMapperService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private UserServices userServices;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtService jwtService;

    //    public List<ActivityListDTO> getActivityList() {
//        List<Activity> activityList = repository.findAll();
//        return listMapper.mapList(activityList, ActivityListDTO.class, modelMapper);
//    };
    public List<ActivityListDTO> getActivityList() {
        List<Activity> activityList = repository.findAll();
        List<ActivityListDTO> activityListDTOs = new ArrayList<>();

        for (Activity activity : activityList) {
            ActivityListDTO dto = new ActivityListDTO(activity);
            List<Image> img = imageRepository.getImagePosterbyActivityId(dto.getId());
            if (img.isEmpty()) {
                dto.setImagePath(null);
            } else {
                dto.setImagePath(img.get(0).getImagepath());
            }
            activityListDTOs.add(dto);
        }

        return activityListDTOs;
    }


    public List<ActivityBannerDTO> getPopularActivity() {
        List<Object[]> activitiesQuery = repository.FindAllByFromUserCount();
        List<ActivityWithUserCountDTO> activityList = activityMapperService.mapToActivityWithUserCountDTO(activitiesQuery);
        return listMapper.mapList(activityList, ActivityBannerDTO.class, modelMapper);
    }

    public ActivityDTO getActivityByID(Integer id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
        return new ActivityDTO(activity);
    }

    public List<ActivityRecommendationDTO> getRecommendedActivity() {
        List<Activity> activityList = repository.findAll();
        List<ActivityRecommendationDTO> activityRecommendation = new ArrayList<>();
        for (Activity activity : activityList) {
            ActivityRecommendationDTO dto = new ActivityRecommendationDTO(activity);
            activityRecommendation.add(dto);
        }
        return activityRecommendation;
    }

    public List<ActivityImageDTO> getActivityPoster() {
        List<Image> poster = imageRepository.findActivityPoster();
        return listMapper.mapList(poster, ActivityImageDTO.class, modelMapper);
    }

    public Instant convertDateTimeInstant(String dateTimeLocalString) {
        System.out.println("dateTimeLocalString = " + dateTimeLocalString);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeLocalString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        ZoneId zoneId = ZoneId.of("UTC");
        Instant instant = localDateTime.atZone(zoneId).toInstant();
        System.out.println("toInstant = " + instant);
        return instant;
    }



    public ActivityDTO update(HttpServletRequest httpServletRequest, Integer id, UpdateActivityDTO updateActivity, LocationDTO updateLocation) {
        if(httpServletRequest.isUserInRole("User")){
            String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
            User targetUser = userServices.findUserByEmail(email);
            if(targetUser.getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }
        Location location = locationServices.save(updateLocation);
        Activity editActivity = repository.findById(id).map(activity -> {
            activity.setActivityName(updateActivity.getActivityName());
            activity.setActivityDate(convertDateTimeInstant(updateActivity.getActivityDate()));
            activity.setActivityFormat(updateActivity.getActivityFormat());
            activity.setActivityBriefDescription(updateActivity.getActivityBriefDescription());
            activity.setActivityDescription(updateActivity.getActivityDescription());
            activity.setRegisterStartDate(convertDateTimeInstant(updateActivity.getRegisterStartDate()));
            activity.setRegisterEndDate(convertDateTimeInstant(updateActivity.getRegisterEndDate()));
            activity.setAmount(updateActivity.getAmount());
            activity.setLocationId(location);
            activity.setAnnouncementDate(convertDateTimeInstant(updateActivity.getAnnouncementDate()));
            activity.setActivityStatus(updateActivity.getActivityStatus());
            activity.setSuggestionNotes(updateActivity.getSuggestionNotes());
            activity.setLastUpdate(Instant.now());
            activity.setActivityStatus("Active");
            return activity;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + id + " Does Not Exist !!!"));
        repository.saveAndFlush(editActivity);
        return new ActivityDTO(editActivity);
    }

    public ActivityDTO save(CreateNewActivityDTO activity, LocationDTO location) {

        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User activityOwner = userServices.findUserByEmail(email);
        Activity newActivity = new Activity();
        System.out.println("getAmount  : " + activity.getAmount());
//        User activityOwner = userRepository.findById(activity.getUserId()) .orElseThrow(() -> new ResponseStatusException(
//                HttpStatus.NOT_FOUND, "Event id " + activity.getCategoryId() + " Does Not Exist !!!"));
        Category activityCategory = categoryRepository.findById(activity.getCategoryId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Event id " + activity.getCategoryId() + " Does Not Exist !!!"));
        Location activityLocation = locationServices.save(location);
        newActivity.setActivityName(activity.getActivityName());
        newActivity.setActivityDate(convertDateTimeInstant(activity.getActivityDate()));
        newActivity.setActivityFormat(activity.getActivityFormat());
        newActivity.setActivityOwner(activityOwner);
        newActivity.setActivityBriefDescription(activity.getActivityBriefDescription());
        newActivity.setActivityDescription(activity.getActivityDescription());
        newActivity.setRegisterStartDate(convertDateTimeInstant(activity.getRegisterStartDate()));
        newActivity.setRegisterEndDate(convertDateTimeInstant(activity.getRegisterEndDate()));
        newActivity.setAmount(activity.getAmount());
        newActivity.setLocationId(activityLocation);
        newActivity.setAnnouncementDate(convertDateTimeInstant(activity.getAnnouncementDate()));
        newActivity.setActivityStatus(activity.getActivityStatus());
        newActivity.setIsGamification(activity.getIsGamification());
        newActivity.setSuggestionNotes(activity.getSuggestionNotes());
        newActivity.setCategoryId(activityCategory);
        newActivity.setLastUpdate(Instant.now());
        newActivity.setCreateTime(Instant.now());
        newActivity.setActivityEndDate(convertDateTimeInstant(activity.getActivityEndDate()));
        newActivity.setActivityStatus("Active");
        System.out.println(activity.getActivityDate() + " new activity getActivityDate " + newActivity.getActivityDate());
        System.out.println(activity.getActivityEndDate() + " new activity getActivityEndDate " + newActivity.getActivityEndDate());
        System.out.println(activity.getRegisterStartDate() + " new activity getRegisterStartDate " + newActivity.getRegisterStartDate());
        System.out.println(activity.getRegisterEndDate() + " new activity getRegisterEndDate " + newActivity.getRegisterEndDate());
        System.out.println(activity.getAnnouncementDate() + " new activity getAnnouncementDate " + newActivity.getAnnouncementDate());
        repository.saveAndFlush(newActivity);
        return new ActivityDTO(newActivity);
    }

    public Integer delete(HttpServletRequest httpServletRequest, Integer id) {
        if(httpServletRequest.isUserInRole("ActivityOwner")){
            User targetUser = userRepository.findActivityOwner(id);
            if(targetUser.getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }
        Activity activity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        repository.deleteById(id);
        return id;
    }


    public Registration registerActivity(HttpServletRequest httpServletRequest, UserRegistrationDTO user, Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        if (httpServletRequest.isUserInRole("User")) {
            if (email.compareTo(user.getEmail()) != 0) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The booking email must be the same as the student's email");
            }
        } else if (httpServletRequest.isUserInRole("Admin") || httpServletRequest.isUserInRole("ActivityOwner")) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                    "Only user can create event");
        }
        Registration registration = new Registration();
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Activity ID " + activityId + " does not exist !!!"));
//        User user = userRepository.findById(activityRegistration.getUserId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                "User ID " + activityRegistration.getActivityId() + " does not exist !!!"));
        registration.setRegistrationDate(Instant.now());
        registration.setActivityId(activity);
        registration.setUserId(userServices.findUserByEmail(email));
        registration.setStatus("registered");
        return registrationRepository.saveAndFlush(registration);
    }

    public Registration updateActivityStatusOfUser(String status, Integer userId, Integer activityId) {
        Registration registration = registrationRepository.FindAllByActivityAndUserID(activityId, userId);
        registration.setStatus(status);
        registrationRepository.saveAndFlush(registration);
        return registration;
    }

    public List<Registration> getAllRegistrationActivity(Integer activityId) {
        List<Registration> registrations = registrationRepository.FindAllRegistrationFromActivityId(activityId);
        return registrations;
    }

    public List<ActivityWithStatusDTO> getActivityByStatusAndUserId(Integer id, String status) {
        List<ActivityWithStatusDTO> activities = repository.FindActivityByStatusAndUserId(id, status);
        return activities;
    }

    public CommonActivityDTO getActivityDetailForRegistrationPage(Integer activityId) {
        Activity activity = repository.findById(activityId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
        return new CommonActivityDTO(activity);
    }

    public List<ActivityWithRegistrationNumberDTO> getActivityRegistrationNumberDTO() {
        List<Object[]> activitiesQuery = repository.FindActivityWithRegisterAmount();
        List<ActivityWithRegistrationNumberDTO> activityList = activityMapperService.mapToActivityWithRegistrationNumberDTO(activitiesQuery);
        return activityList;
    }
}

