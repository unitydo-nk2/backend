package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtAuthenticationFilter;
import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.Activity.*;
import com.nk2.unityDoServices.DTOs.Location.LocationDTO;
import com.nk2.unityDoServices.DTOs.Activity.ActivityReviewDTO;
import com.nk2.unityDoServices.DTOs.User.UserDetailsDTO;
import com.nk2.unityDoServices.Entities.*;
import com.nk2.unityDoServices.Enums.ActivityStatus;
import com.nk2.unityDoServices.Enums.RegistrationStatus;
import com.nk2.unityDoServices.Repositories.*;
import com.nk2.unityDoServices.Services.Mappers.ActivityMapperService;
import com.nk2.unityDoServices.Utils.ListMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Autowired
    private ActivityReviewRepository activityReviewRepository;

    public List<ActivityListDTO> getActivityList(HttpServletRequest httpServletRequest) {
        List<Activity> activityList = repository.findAllAvailableActivity();
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

    public List<ActivityReviewListDTO> getActivityReviews(Integer id){
        List<ActivityReview> activityReviews = activityReviewRepository.findAllActivityReviewByActivityId(id);
        List<ActivityReviewListDTO> activityReviewList = new ArrayList<>();

        for (ActivityReview review : activityReviews) {
            ActivityReviewListDTO activityReviewListDTO = new ActivityReviewListDTO(review.getRegistrationId().getUserId(),review);
            activityReviewList.add(activityReviewListDTO);
        }
        return activityReviewList;
    }

    public List<ActivityListDTO> getActivityManagement(HttpServletRequest httpServletRequest) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);
        List<Activity> activityList = new ArrayList<>();

        if (targetUser.getRole().equals("activityOwner")) {
            activityList = repository.findByActivityOwner(targetUser.getId());

        } else if (targetUser.getRole().equals("admin")) {
            activityList = repository.findAllActiveActivity();
        }

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

//    public List<Object[]> getPopularActivity() {
//        List<Object[]> activitiesQuery = repository.FindAllByFromUserCount();
//        List<ActivityWithUserCountDTO> activityList = activityMapperService.mapToActivityWithUserCountDTO(activitiesQuery);
//        List<PopularActivityDTO> popularActivityList = new ArrayList<>();
//
//        for(ActivityWithUserCountDTO activity:activityList){
//            PopularActivityDTO popularActivity = modelMapper.map(activity,PopularActivityDTO.class);
//            List<Image> img = imageRepository.getImagePosterbyActivityId(popularActivity.getActivityId());
//            if (img.isEmpty()) {
//                popularActivity.setImagePath(null);
//            } else {
//                popularActivity.setImagePath(img.get(0).getImagepath());
//            }
//            popularActivityList.add(popularActivity);
//        }
//        return activitiesQuery;
//    }

    public List<PopularActivityDTO> getPopularActivity() {
        List<Object[]> activitiesQuery = repository.FindAllByFromUserCount();
        List<ActivityWithUserCountDTO> activityList = activityMapperService.mapToActivityWithUserCountDTO(activitiesQuery);
        List<PopularActivityDTO> popularActivityList = new ArrayList<>();

        for(ActivityWithUserCountDTO activity:activityList){
            PopularActivityDTO popularActivity = modelMapper.map(activity,PopularActivityDTO.class);
            List<Image> img = imageRepository.getImagePosterbyActivityId(popularActivity.getActivityId());
            if (img.isEmpty()) {
                popularActivity.setImagePath(null);
            } else {
                popularActivity.setImagePath(img.get(0).getImagepath());
            }
            popularActivityList.add(popularActivity);
        }
        return popularActivityList;
    }

    public List<ActivityCardSliderListDTO> getComingSoonActivity() {
        List<Activity> activityList = repository.findUpComingActivity();
        List<ActivityCardSliderListDTO> activityListDTOs = new ArrayList<>();

        for (Activity activity : activityList) {
            System.out.println("activity "+activity.getId());
            ActivityCardSliderListDTO upComingActivity = new ActivityCardSliderListDTO(activity);
            System.out.println("upComingActivity "+upComingActivity.getActivityId());
            List<Image> img = imageRepository.getImagePosterbyActivityId(upComingActivity.getActivityId());
            if (img.isEmpty()) {
                upComingActivity.setImagePath(null);
            } else {
                upComingActivity.setImagePath(img.get(0).getImagepath());
            }
            activityListDTOs.add(upComingActivity);
        }

        return activityListDTOs;
    }

    public List<ActivityCardSliderListDTO> getPersonalRecommendActivity(HttpServletRequest httpServletRequest){
        System.out.println("do getRecommendsActivity");
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);

        if (!targetUser.getRole().equals("user")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "only user can get recommends activities");
        }

        // URL to fetch data from
        String url = "http://172.26.0.2:5050/api/recommendActivities/"+targetUser.getId();
//        String url = "http://127.0.0.1:5050/api/recommendActivities/"+targetUser.getId();

        System.out.println("fetch to "+url);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Fetch data from the URL and deserialize into a list of Activity objects
        ResponseEntity<ActivityReceiverDTO[]> response = restTemplate.getForEntity(url, ActivityReceiverDTO[].class);
        System.out.println("response : "+response);

        List<ActivityReceiverDTO> activityList = Arrays.asList(response.getBody());
        System.out.println(activityList);
        List<ActivityCardSliderListDTO> activityListDTOs = new ArrayList<>();

        for (ActivityReceiverDTO activity : activityList) {
            System.out.println("activity : "+activity.getActivityId());

            Activity targetActivity = repository.findById(activity.getActivityId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
            System.out.println("targetActivity : "+targetActivity.getId());

            ActivityCardSliderListDTO upComingActivity = new ActivityCardSliderListDTO(targetActivity);

            System.out.println("upComingActivity : "+upComingActivity.getActivityId());


            List<Image> img = imageRepository.getImagePosterbyActivityId(upComingActivity.getActivityId());
            if (img.isEmpty()) {
                upComingActivity.setImagePath(null);
            } else {
                upComingActivity.setImagePath(img.get(0).getImagepath());
            }
            activityListDTOs.add(upComingActivity);
        }
        System.out.println(activityListDTOs);
        return activityListDTOs;
    }

    public List<ActivityCardSliderListDTO> getRecommendsActivity(HttpServletRequest httpServletRequest){
        System.out.println("do getRecommendsActivity");
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);

        if (!targetUser.getRole().equals("user")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "only user can get recommends activities");
        }

        // URL to fetch data from
        String url = "http://172.26.0.2:5050/api/recommendActivities/"+targetUser.getId();
//        String url = "http://127.0.0.1:5050/api/recommendActivities/"+targetUser.getId();

        System.out.println("fetch to "+url);

        // Create a RestTemplate instance
        RestTemplate restTemplate = new RestTemplate();

        // Fetch data from the URL and deserialize into a list of Activity objects
        ResponseEntity<ActivityReceiverDTO[]> response = restTemplate.getForEntity(url, ActivityReceiverDTO[].class);
        System.out.println("response : "+response);

        List<ActivityReceiverDTO> activityList = Arrays.asList(response.getBody());
        System.out.println(activityList);
        List<ActivityCardSliderListDTO> activityListDTOs = new ArrayList<>();

        for (ActivityReceiverDTO activity : activityList) {
            System.out.println("activity : "+activity.getActivityId());

            Activity targetActivity = repository.findById(activity.getActivityId()).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
            System.out.println("targetActivity : "+targetActivity.getId());

            ActivityCardSliderListDTO upComingActivity = new ActivityCardSliderListDTO(targetActivity);

            System.out.println("upComingActivity : "+upComingActivity.getActivityId());


            List<Image> img = imageRepository.getImagePosterbyActivityId(upComingActivity.getActivityId());
            if (img.isEmpty()) {
                upComingActivity.setImagePath(null);
            } else {
                upComingActivity.setImagePath(img.get(0).getImagepath());
            }
            activityListDTOs.add(upComingActivity);
        }
        System.out.println(activityListDTOs);
        return activityListDTOs;
    }

    public List<ActivityCardSliderListDTO> getSimilarActivity(Integer activityId) {
        List<Activity> activityList = repository.findSimilarActivities(activityId);
        List<ActivityCardSliderListDTO> activityListDTOs = new ArrayList<>();

        for (Activity activity : activityList) {
            ActivityCardSliderListDTO upComingActivity = modelMapper.map(activity, ActivityCardSliderListDTO.class);
            List<Image> img = imageRepository.getImagePosterbyActivityId(upComingActivity.getActivityId());
            if (img.isEmpty()) {
                upComingActivity.setImagePath(null);
            } else {
                upComingActivity.setImagePath(img.get(0).getImagepath());
            }
            activityListDTOs.add(upComingActivity);
        }

        return activityListDTOs;
    }

    public ActivityDTO getActivityByID(Integer id) {
        Activity activity = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity is not found"));
        return new ActivityDTO(activity);
    }

    public LocalDateTime convertDateTime(String dateTimeLocalString) {
        System.out.println("dateTimeLocalString = " + dateTimeLocalString);
        LocalDateTime localDateTime = LocalDateTime.parse(dateTimeLocalString, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
        ZoneId zoneId = ZoneId.of("UTC");
        ZonedDateTime finaleDate = localDateTime.atZone(zoneId);
        return finaleDate.toLocalDateTime();
    }


    public ActivityDTO update(HttpServletRequest httpServletRequest, Integer id, UpdateActivityDTO updateActivity, LocationDTO updateLocation) {

        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);

        if (targetUser.getRole().equals("user")) {
            if (!targetUser.getId().equals(id)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }

        Location location = locationServices.save(updateLocation);
        Activity editActivity = repository.findById(id).map(activity -> {
            activity.setActivityName(updateActivity.getActivityName());
            activity.setActivityDate(convertDateTime(updateActivity.getActivityDate()));
            activity.setActivityFormat(updateActivity.getActivityFormat());
            activity.setActivityBriefDescription(updateActivity.getActivityBriefDescription());
            activity.setActivityDescription(updateActivity.getActivityDescription());
            activity.setRegisterStartDate(convertDateTime(updateActivity.getRegisterStartDate()));
            activity.setRegisterEndDate(convertDateTime(updateActivity.getRegisterEndDate()));
            activity.setAmount(updateActivity.getAmount());
            activity.setLocationId(location);
            activity.setAnnouncementDate(convertDateTime(updateActivity.getAnnouncementDate()));
            activity.setActivityStatus(updateActivity.getActivityStatus());
            activity.setSuggestionNotes(updateActivity.getSuggestionNotes());
            activity.setLastUpdate(LocalDateTime.now());
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
        newActivity.setActivityDate(convertDateTime(activity.getActivityDate()));
        newActivity.setActivityFormat(activity.getActivityFormat());
        newActivity.setActivityOwner(activityOwner);
        newActivity.setActivityBriefDescription(activity.getActivityBriefDescription());
        newActivity.setActivityDescription(activity.getActivityDescription());
        newActivity.setRegisterStartDate(convertDateTime(activity.getRegisterStartDate()));
        newActivity.setRegisterEndDate(convertDateTime(activity.getRegisterEndDate()));
        newActivity.setAmount(activity.getAmount());
        newActivity.setLocationId(activityLocation);
        newActivity.setAnnouncementDate(convertDateTime(activity.getAnnouncementDate()));
        newActivity.setActivityStatus(activity.getActivityStatus());
        newActivity.setIsGamification(activity.getIsGamification());
        newActivity.setSuggestionNotes(activity.getSuggestionNotes());
        newActivity.setCategoryId(activityCategory);
        newActivity.setLastUpdate(LocalDateTime.now());
        newActivity.setCreateTime(LocalDateTime.now());
        newActivity.setActivityEndDate(convertDateTime(activity.getActivityEndDate()));
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
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User activityOwner = userServices.findUserByEmail(email);
        Activity activity = repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));

        if (activityOwner.getRole().equals("activityOwner")) {
            if (!activity.getActivityOwner().getId().equals(activityOwner.getId())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }

        activity.setActivityStatus("Inactive");

        List<Registration> registrationList = registrationRepository.FindAllRegistrationFromActivityId(id);

        for (Registration registration : registrationList) {
            registration.setStatus("activityInactive");
            registrationRepository.saveAndFlush(registration);
        }

        repository.saveAndFlush(activity);
        return id;
    }

    public ActivityReviewDTO reviewActivity(Integer id, ActivityReviewDTO review){
        UserDetailsDTO user = userServices.getUserByEmail();
        Registration registration = registrationRepository.FindAllByActivityAndUserID(id,user.getUserId());
        System.out.println("registration "+ registration.getId());
        System.out.println("registration is null "+ registration == null );
        System.out.println("registration status "+ registration.getStatus() );
        System.out.println("registration status is success "+ registration.getStatus().equals(RegistrationStatus.success.name()) );

        if(registration == null || !(registration.getStatus().equals(RegistrationStatus.success.name()))){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Only participators can review the activity. You haven't register to this activity yet.");
        }

        ActivityReview activityReview = activityReviewRepository.findAllByRegistrationId(registration.getId());
        if(activityReview != null || activityReview.getId() != null){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "You already reviewed the activity.");

        }
        ActivityReview newActivityReview = modelMapper.map(review, ActivityReview.class);
        newActivityReview.setDescription(review.getDescription());
        newActivityReview.setRates(review.getRates());
        newActivityReview.setRegistrationId(registration);
        newActivityReview.setCreateTime(LocalDateTime.now());
        newActivityReview.setUpdateTime(LocalDateTime.now());
        registration.setStatus("review");
        activityReviewRepository.saveAndFlush(newActivityReview);
        registrationRepository.saveAndFlush(registration);
        return modelMapper.map(newActivityReview, ActivityReviewDTO.class);
    }

    public ActivityReviewDTO updateReviewActivity(Integer id, ActivityReviewDTO review){
        UserDetailsDTO user = userServices.getUserByEmail();
        ActivityReview targetActivityReview = activityReviewRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));

        if(!(user.getUserId().equals(targetActivityReview.getRegistrationId().getUserId()))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "you not belong to this review");
        }

        ActivityReview activityReview = activityReviewRepository.findById(id).map(a -> {
            a.setDescription(review.getDescription());
            a.setRates(review.getRates());
            a.setUpdateTime(LocalDateTime.now());
            return  a;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        activityReviewRepository.saveAndFlush(activityReview);
        return modelMapper.map(activityReview, ActivityReviewDTO.class);
    }

    public Activity updateActivityToDone(Integer id){
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);

        Activity targetActivity = repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + id + " Does Not Exist !!!"));

        if(!targetUser.getId().equals(targetActivity.getActivityOwner().getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "this activity is not belongs to you !");
        }

        if(!targetActivity.getActivityEndDate().isBefore(LocalDateTime.now())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "cannot update to done since activity haven't arrived");
        }

        Activity updateActivity = repository.findById(id).map(activity -> {
            activity.setActivityStatus(ActivityStatus.Done.name());
            return activity;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity id " + id + " Does Not Exist !!!"));
        activityRepository.saveAndFlush(updateActivity);

        List<Registration> registrations = registrationRepository.FindAllRegistrationFromActivityId(id);
        for (Registration registration : registrations) {
            registration.setStatus("success");
            registrationRepository.save(registration);
        }

        return updateActivity;
    }

    public Registration registerActivity(HttpServletRequest httpServletRequest, Integer userId, Integer activityId) {
        System.out.println("registerActivity called");
        UserDetailsDTO user = userServices.getUserByEmail();
        System.out.println("userid : "+userId+",, user id from token : "+user.getUserId());
        if (user != null) {
            if (!(user.getUserId().equals(userId.intValue()))) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "The register email must be the same as user email");
            }
            if (!user.getRole().equals("user")) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN,
                        "Only user can register event");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "user with does not exist !!!");
        }
        Registration isContainRegistration = registrationRepository.FindAllByActivityAndUserID(activityId, userId);
        if (isContainRegistration != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User id " + userId + " have been registered.");
        }
        Registration registration = new Registration();
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Activity ID " + activityId + " does not exist !!!"));
        User registeredUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST,
                "User ID " + userId + " does not exist !!!"));
        registration.setRegistrationDate(LocalDateTime.now());
        registration.setActivityId(activity);
        registration.setUserId(registeredUser);
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
        System.out.println("getActivityRegistrationNumberDTO");

        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);
        List<Object[]> activitiesQuery = new ArrayList<>();

        if (targetUser.getRole().equals("activityOwner")) {
            System.out.println("get activity as ActivityOwner");
            activitiesQuery = repository.FindActivityByStatusAndUserIdOwnedByActivityOwner(targetUser.getId());

        } else if (targetUser.getRole().equals("admin")) {
            System.out.println("get activity as Admin");
            activitiesQuery = repository.FindActivityWithRegisterAmount();
        }

        List<ActivityWithRegistrationNumberDTO> activityList = activityMapperService.mapToActivityWithRegistrationNumberDTO(activitiesQuery);
        return activityList;
    }

    public List<ActivityListDTO> getActivityFavorite () {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = userServices.findUserByEmail(email);
        List<Activity> activityList = repository.findActivityFavorite(targetUser.getId());
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
}

