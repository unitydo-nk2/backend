package com.nk2.unityDoServices.Controllers;

import com.nk2.unityDoServices.DTOs.CreateNewUserDTO;
import com.nk2.unityDoServices.DTOs.UserLoginDTO;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Services.AuthenticationServices;
import com.nk2.unityDoServices.Services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.ServletWebRequest;
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

    @GetMapping("/getGoogleToken")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getGoogleToken(@Valid @RequestBody String userName,
                                         HttpServletResponse response,
                                         HttpServletRequest request) throws Exception {
        return authenticationServices.emailMatchSystem(userName,
                response,
                request);
    }

    @GetMapping("/refreshToken")
    @ResponseStatus(code = HttpStatus.OK)
    public ResponseEntity getRefreshToken(@Valid HttpServletRequest request) {
        return authenticationServices.refreshToken(request);
    }
}