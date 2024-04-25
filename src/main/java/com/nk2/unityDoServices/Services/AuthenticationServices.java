package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.User.CreateNewUserDTO;
import com.nk2.unityDoServices.DTOs.User.UserLoginDTO;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Models.ErrorClass;
import com.nk2.unityDoServices.Models.JwtResponse;
import com.nk2.unityDoServices.Models.Response;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServices {
    @Autowired
    private UserRepository repository;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Argon2PasswordEncoder argon2PasswordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;

    public AuthenticationServices(UserRepository repository, ListMapper listMapper, ModelMapper modelMapper, Argon2PasswordEncoder argon2PasswordEncoder) {
        this.repository = repository;
        this.listMapper = listMapper;
        this.modelMapper = modelMapper;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    public ResponseEntity emailMatchSystem(@Valid String email, HttpServletResponse response, HttpServletRequest request) throws Exception {
        if(repository.existsByEmail(email)){
            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "A user with the specified email DOES NOT exist"));

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(email);

            final String token = jwtService.generateUserToken(user);

            final String refreshToken = jwtService.generateRefreshToken(token);

            return ResponseEntity.ok(new JwtResponse("Login Success", token, refreshToken));
    }else{
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A user with the specified email DOES NOT exist");
    }
    }

    public ResponseEntity save(@Valid CreateNewUserDTO user, HttpServletResponse response,
                               HttpServletRequest request) throws Exception{
        if(user.getRole()==null){
            user.setRole("user");
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
        newUser.setCreateTime(LocalDateTime.now());
        newUser.setUpdateTime(LocalDateTime.now());
        newUser.setStatus("Active");
        try {
            String hash = argon2.hash(2, 16, 1, password);
            newUser.setPassword(hash);
        } finally {
            argon2.wipeArray(password);
        }
        newUser.setEmail(user.getEmail().trim());
        userRepository.saveAndFlush(newUser);
        UserLoginDTO userLogin = new UserLoginDTO();
        userLogin.setEmail(user.getEmail());
        userLogin.setPassword(user.getPassword());
        return login(userLogin,response,request);
    }


    public ResponseEntity login(@Valid UserLoginDTO userLogin,
                                HttpServletResponse response,
                                HttpServletRequest request) throws Exception {
        Map<String, String> errorMap = new HashMap<>();
        String status;

        if (repository.existsByEmail(userLogin.getEmail())) {
            User user = repository.findByEmail(userLogin.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist"));
            if (argon2PasswordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
//                authenticate(userLogin.getEmail(), userLogin.getPassword());

                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(userLogin.getEmail());

                final String token = jwtService.generateUserToken(user);

                final String refreshToken = jwtService.generateRefreshToken(token);

                return ResponseEntity.ok(new JwtResponse("Login Success", token, refreshToken));
            } else {
                errorMap.put("message", "Password NOT Matched");
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                status = HttpStatus.UNAUTHORIZED.toString();
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
        }

        ErrorClass errors = new ErrorClass(
                Date.from(Instant.now()),
                response.getStatus(),
                status,
                errorMap.get("message"),
                request.getRequestURI());
        return ResponseEntity.status(response.getStatus()).body(errors);

    }

    public ResponseEntity refreshToken(HttpServletRequest request) {
        String requestRefreshToken = request.getHeader("Authorization").substring(7);
        String userRefreshToken = jwtService.getUsernameFromToken(requestRefreshToken);
        UserDetails userDetails = jwtService.getUserFromToken(userRefreshToken);
        User user = repository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist"));
        String accessToken = jwtService.generateUserToken(user);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
//            String getRefreshTokenExpire = jwtTokenUtill.getExpirationDateFromToken(u)
        if (checkExpired(requestRefreshToken).equals(true)) {
            return ResponseEntity.ok(new JwtResponse("Refresh Token Success", accessToken, refreshToken));
        }
        return Response.response(HttpStatus.NOT_FOUND, "Can't find Refresh Token");
    }


//    private void authenticate(String email,String password)throws Exception{
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email,password));
//        } catch ( DisabledException ex){
//            throw new Exception("USER_DISABLED",ex);
//        }catch (BadCredentialsException ex){
//            throw new Exception("INVALID_CREDENTIALS",ex);
//        }
//
//    }

    private Boolean checkExpired(String request) {
        return !jwtService.isTokenExpired(request);
    }

//    public ResponseEntity login(@Valid UserLoginDTO userLogin, HttpServletResponse httpServletResponse, ServletWebRequest request) throws Exception {
//        Map<String, String> errorMap = new HashMap<>();
//        String status;
//
//        if (repository.existsByEmail(userLogin.getEmail())) {
//            if (argon2PasswordEncoder.matches(userLogin.getPassword(), userLogin.getPassword())) {
//                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLogin.getEmail(),userLogin.getPassword()));
//                //user is authenticate
//                var user = repository.findByEmail(userLogin.getEmail())
//                        .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist"));
//
//                var jwtToken = jwtService.generateToken(user);
//                var refreshToken = jwtService.generateRefreshToken(user);
//
//                return ResponseEntity.ok(new JwtResponse("Login Success", jwtToken, refreshToken));
//            } else {
//                errorMap.put("message", "Password NOT Matched");
//                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//                status = HttpStatus.UNAUTHORIZED.toString();
//            }
//        }else {
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist");
//        }
//
//        ErrorClass errors = new ErrorClass(
//                Date.from(Instant.now()),
//                httpServletResponse.getStatus(),
//                status,
//                errorMap.get("message"),
//                request.getRequest().getRequestURI());
//        return ResponseEntity.status(httpServletResponse.getStatus()).body(errors);
//
//    }


//    public ResponseEntity matchCheck(UserLoginDTO userLogin) {
//        authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(userLogin.getEmail(),userLogin.getPassword()));
//        var user = repository.findByEmail(userLogin.getEmail())
//                .orElseThrow();
//        var jwtToken = jwtService.generateToken(user);
//        Map<String, String> errorMap = new HashMap<>();
//        String status;
//        if (repository.existsByEmail(userLogin.getEmail())) {
//            if (argon2PasswordEncoder.matches(String.valueOf(userLogin.getPassword()), repository.findByEmail(userLogin.getEmail()))) {
//                return ResponseEntity.ok("Password match!");
//            }
//            return Response.response(HttpStatus.UNAUTHORIZED, "Password doesn't match");
//        }
//        return Response.response(HttpStatus.NOT_FOUND, "User not found username : " + userLogin.getEmail());
//    }
}
