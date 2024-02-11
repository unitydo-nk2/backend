package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.*;
import com.nk2.unityDoServices.Entities.Registration;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Services.ActivityServices;
import com.nk2.unityDoServices.Services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServices userServices;

    @Autowired
    private ActivityServices activityServices;

    @GetMapping("/list")
    public List<UserDTO> getActivityList() {
        return userServices.getUserList();
    }

    @GetMapping("/{userId}")
    public UserDTO getActivityDetailsById(@PathVariable Integer userId) {
        return userServices.getUserById(userId);
    }

    @GetMapping("/{id}/activities")
    public List<ActivityWithStatusDTO> getRegisteredActivity(@PathVariable Integer id) {
        return activityServices.getRegisteredActivity(id);
    }

    @GetMapping("/registration/{id}")
    public RegistrantDetailsDTO getActivityRegistrants(@PathVariable Integer id) {
        return userServices.getRegistrantDetails(id);
    }

    @DeleteMapping("/{id}")
    public Integer deleteUser(@PathVariable Integer id) {
        return userServices.delete(id);
    }

    @PostMapping("")
    public User createNewUser(@RequestPart("user") CreateNewUserDTO user) {
        return userServices.save(user);
    }

    @PatchMapping("/registration/{id}")
    public Registration updateUserRegistration(@Valid @RequestPart("status") String status
            , @PathVariable Integer id) {
        return userServices.updateRegistration(id, status);
    }

    @PatchMapping("/{id}")
    public User updateUser(@Valid @RequestPart("updateUser") UserDTO updateUser
            , @PathVariable Integer id) {
        return userServices.update(id, updateUser);
    }
}
