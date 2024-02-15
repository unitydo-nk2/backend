package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.RegistrationRepository;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServices{

    @Autowired
    private UserRepository userRepository;

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

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!"));
    }

    public UserDetailsDTO getUserDetailByEmail(String email) {
        return mapUserBuild(userRepository.findUserByEmail(email).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "user "+ email + " does not exist !!!")));
    }

    public UserDetailsDTO mapUserBuild(User user) {
        try {
            UserDetailsDTO userResponse = new UserDetailsDTO();
            userResponse.setId(user.getId().longValue());
            userResponse.setUsername(user.getName());
            userResponse.setEmail(user.getEmail());
            userResponse.setAuthorities(user.getRole());
            return userResponse;
        } catch (Exception e) {
            log.error("Could not Map User to UserResponse: " + e.getMessage());
            return UserDetailsDTO.builder().build();
        }
    }

    public User save(@Valid CreateNewUserDTO user) {
        if(user.getRole()==null){
            user.setRole("user");}
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

    public List<UserDTO> getUserList() {
        List<User> userList = userRepository.findAll();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    }

    public RegistrantDetailsDTO getRegistrantDetails(Integer activityId) {
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromRegistrationId(activityId);
        System.out.println("userList : "+userList);
        List<RegistrantDetailsDTO> registrantList = registrantsDetailsMapperServices.mapToRegistrantsDetailsDTO(userList);
        return registrantList.stream()
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "There is no registration id :" + activityId));    }

    public List<RegistrantDTO> getUserRegisteredActivity(Integer activityId) {
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
        System.out.println("userList : "+userList);
        List<RegistrantDTO> registrantList = userMapperServices.mapToRegistrantDTO(userList);
        return registrantList;
    }

//    public List<Object[]> getUserRegisteredActivity(Integer activityId) {
//        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
//        return userList;
//    }

    public UserDTO getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                userId + " does not exist !!!"));
        return modelMapper.map(user, UserDTO.class);
    }


    public Integer delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        userRepository.deleteById(id);
        return id;
    }

    public User update(Integer id, UserDTO updateUser) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        user.setName(updateUser.getName());
        user.setUsername(updateUser.getUsername());
        user.setPassword(updateUser.getPassword());
        user.setSurName(updateUser.getSurName());
        user.setNickName(updateUser.getNickName());
        user.setEmail(updateUser.getEmail());
        user.setGender(updateUser.getGender());
        user.setDateOfBirth(updateUser.getDateOfBirth());
        user.setReligion(updateUser.getReligion());
        user.setTelephoneNumber(updateUser.getTelephoneNumber());
        user.setAddress(updateUser.getAddress());
        user.setRole(updateUser.getRole());
        user.setEmergencyPhoneNumber(updateUser.getEmergencyPhoneNumber());
        user.setProfileImg(updateUser.getProfileImg());
        user.setLine(updateUser.getLine());
        user.setInstagram(updateUser.getInstagram());
        user.setX(updateUser.getX());
        user.setCreateTime(updateUser.getCreateTime());
        user.setUpdateTime(updateUser.getUpdateTime());
        userRepository.saveAndFlush(user);
        return user;
    }

    public Registration updateRegistration(Integer id, String status) {
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        registration.setStatus(status);
        registrationRepository.saveAndFlush(registration);
        return registration;
    }

}
