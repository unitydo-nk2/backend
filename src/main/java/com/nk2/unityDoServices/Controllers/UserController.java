package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.Activity.RegisteredActivityDTO;
import com.nk2.unityDoServices.DTOs.User.*;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Services.AuthenticationServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private AuthenticationServices authenticationServices;

    @GetMapping("")
    public UserDetailsDTO getUserFromEmail() {
        return userServices.getUserByEmail();
    }

    @GetMapping("/list")
    public List<UserDTO> getUserList(HttpServletRequest httpServletRequest) {
        return userServices.getUserList(httpServletRequest);
    }

    @GetMapping("/{userId}")
    public UserDTO getUserDetailsById(HttpServletRequest request, @PathVariable Integer userId) {
        return userServices.getUserById(request, userId);
    }

    @GetMapping("/registered")
    public List<RegisteredActivityDTO> getRegisteredActivity(HttpServletRequest httpServletRequest) {
        return userServices.getRegisteredActivity();
    }

    @GetMapping("/isRegistered/{activityId}")
    public Boolean isUserRegistered(@PathVariable Integer activityId) {
        return userServices.isUserRegistered(activityId);
    }

    @GetMapping("/registration/{id}")
    public RegistrantDetailsDTO getActivityRegistrants(
            @PathVariable Integer id) {
        return userServices.getRegistrantDetails(id);
    }

    @DeleteMapping("/{id}")
    public Integer deleteUser(HttpServletRequest httpServletRequest,@PathVariable Integer id) {
        return userServices.delete(httpServletRequest, id);
    }


    @PatchMapping("/registration/{id}")
    public Registration updateUserRegistration(HttpServletRequest httpServletRequest,
                                               @Valid @RequestPart("status") String status
            , @PathVariable Integer id) {
        return userServices.updateRegistration(httpServletRequest,id, status);
    }

    @PatchMapping("/{id}")
    public User updateUser(HttpServletRequest httpServletRequest,
                           @Valid @RequestPart("updateUser") UpdatedUserDTO updateUser
            , @PathVariable Integer id) {
        return userServices.update(httpServletRequest,id, updateUser);
    }
}
