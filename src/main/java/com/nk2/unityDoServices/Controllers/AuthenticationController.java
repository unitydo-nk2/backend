package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.User.CreateNewUserDTO;
import com.nk2.unityDoServices.DTOs.User.UserLoginDTO;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Services.AuthenticationServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationServices authenticationServices;

    @Autowired
    private UserServices userServices;

    @PostMapping("/registration")
    public User createNewUser(@RequestPart("user") CreateNewUserDTO user) {
        return userServices.save(user);
    }

    @PostMapping("/login")
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO userLogin,
                                HttpServletResponse response,
                                HttpServletRequest request) throws Exception {
        return authenticationServices.login(userLogin, response, request);
    }

    @PostMapping("/getGoogleToken")
    public ResponseEntity getGoogleToken(@Valid @RequestPart("email") String email,
                                         HttpServletResponse response,
                                         HttpServletRequest request) throws Exception {
        return authenticationServices.emailMatchSystem(email,
                response,
                request);
    }

    @GetMapping("/refreshToken")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getRefreshToken(@Valid HttpServletRequest request) {
        return authenticationServices.refreshToken(request);
    }
}