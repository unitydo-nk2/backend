package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtAuthenticationFilter;
import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.Activity.ActivityWithStatusDTO;
import com.nk2.unityDoServices.DTOs.Activity.RegisteredActivityDTO;
import com.nk2.unityDoServices.DTOs.Category.CategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.FavoriteCategoryDTO;
import com.nk2.unityDoServices.DTOs.Category.SetFavoriteCategoryDTO;
import com.nk2.unityDoServices.DTOs.User.*;
import com.nk2.unityDoServices.Entities.*;
import com.nk2.unityDoServices.Repositories.*;
import com.nk2.unityDoServices.Services.Mappers.ActivityMapperService;
import com.nk2.unityDoServices.Services.Mappers.RegistrantsDetailsMapperServices;
import com.nk2.unityDoServices.Services.Mappers.UserMapperServices;
import com.nk2.unityDoServices.Utils.ListMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.file.FileSystems;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServices{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ActivityMapperService activityMapperService;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserMapperServices userMapperServices;

    @Autowired
    private RegistrantsDetailsMapperServices registrantsDetailsMapperServices;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private FavoriteCategoryRepository favoriteCategoryRepository;

    @Autowired
    private MainCategoryRepository mainCategoryRepository;

    @Autowired
    private ActivityReviewRepository activityReviewRepository;

    @Autowired
    private UserCategoryRankingRepository userCategoryRankingRepository;

    @Autowired
    private CSVDownloadServices csvDownloadServices;

    public UserDetailsDTO getUserByEmail() {
        String token  = jwtAuthenticationFilter.getJwtToken();
        String email = jwtService.extractUsername(token);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!"));
        return modelMapper.map(user, UserDetailsDTO.class);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!"));
    }

    public List<UserDTO> getUserList(HttpServletRequest httpServletRequest) {
        List<User> userList = userRepository.findAllActiveUser();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    }

    public RegistrantDetailsDTO getRegistrantDetails(Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);

        Registration registration = registrationRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "registration id "+activityId + " does not exist !!!"));

        Activity activity = activityRepository.findById(registration.getActivityId().getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "activity id "+activityId + " does not exist !!!"));

        if (targetUser.getRole().equals("activityOwner")) {
            if(activity.getActivityOwner().getId() != targetUser.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromRegistrationId(activityId);
        List<RegistrantDetailsDTO> registrantList = registrantsDetailsMapperServices.mapToRegistrantsDetailsDTO(userList);
        return registrantList.stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no registration id :" + activityId));
    }
//    public List<Object[]> getUserRegisteredActivity(Integer activityId) {
//        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
//        User targetUser = findUserByEmail(email);
//        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
//                "activity id "+activityId + " does not exist !!!"));
//        if (targetUser.getRole().equals("activityOwner")) {
//            if(activity.getActivityOwner().getId() != targetUser.getId()){
//                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
//                        "this activity is not belongs to you !");
//            }
//        }
//        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
//        return userList;
//    }

    public List<RegistrantDTO> getUserRegisteredActivity(Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "activity id "+activityId + " does not exist !!!"));
        if (targetUser.getRole().equals("activityOwner")) {
            if(activity.getActivityOwner().getId() != targetUser.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
        List<RegistrantDTO> registrantList = userMapperServices.mapToRegistrantDTO(userList);
        return registrantList;
    }

    public List<RegistrantDTO> setRegistrantsForActivityDone(Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "activity id "+activityId + " does not exist !!!"));
        if (targetUser.getRole().equals("activityOwner")) {
            if(activity.getActivityOwner().getId() != targetUser.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }
       List<Registration> registrationList  = registrationRepository.FindAllRegistrationFromActivityId(activityId);

        for (Registration registration : registrationList) {
            // Check if the registration status is "passed"
            if (registration.getStatus().equals("passed")) {
                // Update status to "success"
                registration.setStatus("success");
                // Save the updated registration
                registrationRepository.save(registration);
            }
        }
        return listMapper.mapList(registrationList, RegistrantDTO.class, modelMapper) ;
    }

    public FavoriteCategory setUserFavoriteCategory(SetFavoriteCategoryDTO favoriteCategory) {
        User user = findUserByEmail(favoriteCategory.getUserEmail());
        MainCategory mainCategory = mainCategoryRepository.findById(favoriteCategory.getMainCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "category id "+favoriteCategory.getMainCategoryId() + " does not exist !!!"));

        FavoriteCategory userFavoriteCategory = new FavoriteCategory();
        userFavoriteCategory.setUserId(user);
        userFavoriteCategory.setMainCategoryId(mainCategory);
        userFavoriteCategory.setCategoryRank(favoriteCategory.getCategoryRank());
        return favoriteCategoryRepository.saveAndFlush(userFavoriteCategory);
    }

    public FavoriteCategory updateUserFavoriteCategory(HttpServletRequest httpServletRequest, Integer id, FavoriteCategoryDTO favoriteCategory) {
        FavoriteCategory targetFavoriteCategory = favoriteCategoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        User user = targetFavoriteCategory.getUserId();
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        if(!user.getId().equals(targetUser.getId())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "this favorite category is not belongs to you !");
        }
        MainCategory mainCategory = mainCategoryRepository.findById(favoriteCategory.getMainCategoryId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "category id "+favoriteCategory.getMainCategoryId() + " does not exist !!!"));
        FavoriteCategory updatedFavoriteCategory = favoriteCategoryRepository.findById(id).map(f -> {
            f.setMainCategoryId(mainCategory);
            return  f;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        favoriteCategoryRepository.saveAndFlush(updatedFavoriteCategory);
        return updatedFavoriteCategory;
    }

    public UserDTO getUserById(HttpServletRequest httpServletRequest, Integer userId) {
        User user = jwtService.getUserFromToken(jwtAuthenticationFilter.getJwtToken());
        if(user.getRole().equals("user") || user.getRole().equals("activityOwner")){
            if(userId != user.getId()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot get users which you didn't own");
            }
        }
        User newUser = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                userId + " does not exist !!!"));
        return modelMapper.map(newUser, UserDTO.class);
    }

    public List<RegisteredActivityDTO> getRegisteredActivity() {
        // dothiskub
        User user = jwtService.getUserFromToken(jwtAuthenticationFilter.getJwtToken());
        Integer id = user.getId();
        List<Object[]> activitiesQuery = activityRepository.FindActivityRegisteredByUserId(id);
        List<ActivityWithStatusDTO> activityList = activityMapperService.mapToActivityWithUserStatusDTO(activitiesQuery);
        List<RegisteredActivityDTO> registeredActivityDTO = new ArrayList<>();

        for (ActivityWithStatusDTO activity : activityList) {
            RegisteredActivityDTO dto = new RegisteredActivityDTO(activity);
            List<Image> img = imageRepository.getImagePosterbyActivityId(activity.getActivityId());
            if (img.isEmpty()) {
                dto.setImagePath(null);
            } else {
                dto.setImagePath(img.get(0).getImagepath());
            }
            registeredActivityDTO.add(dto);
        }
        return registeredActivityDTO;
    }

    public void validateIdBelong(HttpServletRequest httpServletRequest, Integer id) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        if(targetUser.getRole().equals("activityOwner") || targetUser.getRole().equals("user")){
            if(!id.equals(targetUser.getId().intValue())){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }
    }

    public Integer delete(HttpServletRequest httpServletRequest,Integer id) {
        validateIdBelong(httpServletRequest, id);

        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));

        if (user.getRole().equals("activityOwner")) {
            List<Activity> activityList = activityRepository.findByActivityOwner(user);
            for (Activity activity : activityList) {
                activity.setActivityStatus("Inactive");
                activityRepository.saveAndFlush(activity);
            }
        } else if (user.getRole().equals("user")){
            List<Registration> registrationList = registrationRepository.FindAllRegistrationFromUserId(id);
            for (Registration registration : registrationList) {
                registration.setStatus("activityInactive");
                registrationRepository.saveAndFlush(registration);
            }
        }
        user.setStatus("Inactive");
        userRepository.saveAndFlush(user);
        return id;
    }

    public User update(HttpServletRequest httpServletRequest,Integer id, UpdatedUserDTO updateUser) {
        validateIdBelong(httpServletRequest, id);
        User user = userRepository.findById(id).map(targetUser -> {
            targetUser.setName(updateUser.getName().trim());
            targetUser.setSurName(updateUser.getSurName().trim());
            targetUser.setNickName(updateUser.getNickName().trim());
            targetUser.setGender(updateUser.getGender().trim());
            targetUser.setDateOfBirth(updateUser.getDateOfBirth());
            targetUser.setReligion(updateUser.getReligion());
            targetUser.setTelephoneNumber(updateUser.getTelephoneNumber());
            targetUser.setAddress(updateUser.getAddress().trim());
            targetUser.setEmergencyPhoneNumber(updateUser.getEmergencyPhoneNumber());
            targetUser.setProfileImg(updateUser.getProfileImg());
            targetUser.setLine(updateUser.getLine());
            targetUser.setInstagram(updateUser.getInstagram());
            targetUser.setX(updateUser.getX());
            targetUser.setUpdateTime(LocalDateTime.now());
            return  targetUser;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        userRepository.saveAndFlush(user);
        return user;
    }

    public Registration updateRegistration(HttpServletRequest httpServletRequest, Integer id, String status) {
        if(httpServletRequest.isUserInRole("user")){
            String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
            User targetUser = findUserByEmail(email);
            if(targetUser.getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        registration.setStatus(status);
        registrationRepository.saveAndFlush(registration);
        return registration;
    }

}
