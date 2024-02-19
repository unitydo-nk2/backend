package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtAuthenticationFilter;
import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.Activity.ActivityListDTO;
import com.nk2.unityDoServices.DTOs.Activity.ActivityWithStatusDTO;
import com.nk2.unityDoServices.DTOs.Activity.RegisteredActivityDTO;
import com.nk2.unityDoServices.DTOs.User.*;
import com.nk2.unityDoServices.Entities.Activity;
import com.nk2.unityDoServices.Entities.Image;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.ActivityRepository;
import com.nk2.unityDoServices.Repositories.ImageRepository;
import com.nk2.unityDoServices.Repositories.RegistrationRepository;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Services.Mappers.ActivityMapperService;
import com.nk2.unityDoServices.Services.Mappers.RegistrantsDetailsMapperServices;
import com.nk2.unityDoServices.Services.Mappers.UserMapperServices;
import com.nk2.unityDoServices.Utils.ListMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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

    public UserDetailsDTO getUserByEmail() {
        System.out.println("getUserByEmail");
        String token  = jwtAuthenticationFilter.getJwtToken();
        System.out.println("token "+token);
        String email = jwtService.extractUsername(token);
        System.out.println("email "+email);
        User user = userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!"));
        return modelMapper.map(user, UserDetailsDTO.class);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!"));
    }

    public User save(@Valid CreateNewUserDTO user) {
        System.out.println("role "+user.getRole());
        if(user.getRole()==null){
            user.setRole("User");
        }
        if(userRepository.existsByEmail(user.getEmail())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "This email have been used!");
        }
        Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id, 16, 29);
        char[] password = user.getPassword().toCharArray();
        User newUser = modelMapper.map(user, User.class);
        newUser.setName(user.getName());
        newUser.setUsername(user.getEmail().trim());
        newUser.setSurName(user.getSurName());
        newUser.setNickName(user.getNickName());
        newUser.setEmail(user.getEmail());
        newUser.setGender(user.getGender());
        newUser.setDateOfBirth(user.getDateOfBirth());
        newUser.setReligion(user.getReligion());
        newUser.setTelephoneNumber(user.getTelephoneNumber());
        newUser.setAddress(user.getAddress());
        newUser.setRole(user.getRole());
        newUser.setEmergencyPhoneNumber(user.getEmergencyPhoneNumber());
        newUser.setProfileImg(user.getProfileImg());
        newUser.setLine(user.getLine());
        newUser.setInstagram(user.getInstagram());
        newUser.setX(user.getX());
        newUser.setCreateTime(Instant.now());
        newUser.setUpdateTime(Instant.now());
        try {
            String hash = argon2.hash(2, 16, 1, password);
            newUser.setPassword(hash);
        } finally {
            argon2.wipeArray(password);
        }
        newUser.setEmail(user.getEmail().trim());
        userRepository.saveAndFlush(newUser);
        return modelMapper.map(newUser, User.class);
    }

    public List<UserDTO> getUserList(HttpServletRequest httpServletRequest) {
        List<User> userList = userRepository.findAll();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    }

    public RegistrantDetailsDTO getRegistrantDetails(Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "activity id "+activityId + " does not exist !!!"));
        if (targetUser.getRole().equals("ActivityOwner")) {
            if(activity.getActivityOwner().getId() != targetUser.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromRegistrationId(activityId);
        System.out.println("userList : "+userList);
        List<RegistrantDetailsDTO> registrantList = registrantsDetailsMapperServices.mapToRegistrantsDetailsDTO(userList);
        return registrantList.stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no registration id :" + activityId));    }

    public List<RegistrantDTO> getUserRegisteredActivity(Integer activityId) {
        String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
        User targetUser = findUserByEmail(email);
        Activity activity = activityRepository.findById(activityId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "activity id "+activityId + " does not exist !!!"));
        if (targetUser.getRole().equals("ActivityOwner")) {
            if(activity.getActivityOwner().getId() != targetUser.getId()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this activity is not belongs to you !");
            }
        }
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
        System.out.println("userList : "+userList);
        List<RegistrantDTO> registrantList = userMapperServices.mapToRegistrantDTO(userList);
        return registrantList;
    }

//    public List<Object[]> getUserRegisteredActivity(Integer activityId) {
//        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
//        return userList;
//    }

    public UserDTO getUserById(HttpServletRequest httpServletRequest, Integer userId) {
        System.out.println("getUserById");
        User user = jwtService.getUserFromToken(jwtAuthenticationFilter.getJwtToken());
        System.out.println("user "+user.getId());
        System.out.println("user role "+user.getRole());
        System.out.println("user in role user "+ user.getRole().equals("User"));
        System.out.println("user in role activityOwner "+ user.getRole().equals("ActivityOwner"));
        if(user.getRole().equals("User") || user.getRole().equals("ActivityOwner")){
            System.out.println("user is null "+ user == null);
            System.out.println("userId "+userId);
            System.out.println("user.getId() "+user.getId());
            System.out.println(user != null || userId != user.getId());
            if(user != null || userId != user.getId()){
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete event which you didn't own");
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

    private void validateIdBelong(HttpServletRequest httpServletRequest, Integer id) {
        if(httpServletRequest.isUserInRole("ActivityOwner") || httpServletRequest.isUserInRole("User")){
            String email = jwtService.extractUsername(jwtAuthenticationFilter.getJwtToken());
            User targetUser = findUserByEmail(email);
            if(targetUser.getId() != id){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "this user is not belongs to you !");
            }
        }
    }

    public Integer delete(HttpServletRequest httpServletRequest,Integer id) {
        validateIdBelong(httpServletRequest, id);
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        userRepository.deleteById(id);
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
            targetUser.setUpdateTime(Instant.now());
            return  targetUser;
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        userRepository.saveAndFlush(user);
        return user;
    }

    public Registration updateRegistration(HttpServletRequest httpServletRequest, Integer id, String status) {
        if(httpServletRequest.isUserInRole("User")){
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
