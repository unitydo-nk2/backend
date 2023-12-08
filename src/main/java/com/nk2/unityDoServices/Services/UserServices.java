package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.DTOs.RegistrantDTO;
import com.nk2.unityDoServices.DTOs.UserDTO;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.RegistrationRepository;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserServices {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ListMapper listMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private UserMapperService userMapperService;

    public User save(UserDTO user) {
        User isUser = userRepository.findUserbyUserName(user.getUsername());
        if(isUser == null){
            System.out.println("create new user");
            User newUser = modelMapper.map(user, User.class);
            newUser.setName(user.getName());
            newUser.setUsername(user.getUsername());
            newUser.setPassword(user.getPassword());
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
            newUser.setCreateTime(user.getCreateTime());
            newUser.setUpdateTime(user.getUpdateTime());
            userRepository.save(newUser);
            System.out.println("created user : "+newUser.getUsername());
            return modelMapper.map(newUser, User.class);
        }else{
            System.out.println("Username used in ID : "+ isUser.getId());
            return isUser;
        }
    };

    public List<UserDTO> getUserList(){
        List<User> userList = userRepository.findAll();
        return listMapper.mapList(userList, UserDTO.class, modelMapper);
    };

    public List<RegistrantDTO> getUserRegisteredActivity(Integer activityId){
        List<Object[]> userList = userRepository.findRegisteredUserWithStatusFromActivityId(activityId);
        List<RegistrantDTO> registrantList = userMapperService.mapToRegistrantDTO(userList);
        return registrantList;
    };

    public UserDTO getUserById(Integer userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                userId + " does not exist !!!"));
        return modelMapper.map(user, UserDTO.class);
    };


    public Integer delete(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        userRepository.deleteById(id);
        return id;
    };

    public User update(Integer id, UserDTO updateUser ){
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

    public Registration updateRegistration(Integer id, String status ){
        Registration registration = registrationRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                id + " does not exist !!!"));
        registration.setStatus(status);
        registrationRepository.saveAndFlush(registration);
        return registration;
    }
}
