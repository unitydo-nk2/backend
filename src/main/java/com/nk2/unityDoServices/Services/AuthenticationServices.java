package com.nk2.unityDoServices.Services;

import com.nk2.unityDoServices.Configs.JwtService;
import com.nk2.unityDoServices.DTOs.User.UserLoginDTO;
import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Models.ErrorClass;
import com.nk2.unityDoServices.Models.JwtResponse;
import com.nk2.unityDoServices.Models.Response;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Utils.ListMapper;
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
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

    public AuthenticationServices(UserRepository repository, ListMapper listMapper, ModelMapper modelMapper, Argon2PasswordEncoder argon2PasswordEncoder) {
        this.repository = repository;
        this.listMapper = listMapper;
        this.modelMapper = modelMapper;
        this.argon2PasswordEncoder = argon2PasswordEncoder;
    }

    public ResponseEntity emailMatchSystem(@Valid String email, HttpServletResponse response, HttpServletRequest request) throws Exception {
        System.out.println("username "+email);
        System.out.println("email exists "+repository.existsByEmail(email));
        if(repository.existsByEmail(email)){
            User user = repository.findByEmail(email)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT, "A user with the specified email DOES NOT exist"));
            System.out.println("user "+ user);

            final UserDetails userDetails = userDetailsService
                    .loadUserByUsername(email);

            final String token = jwtService.generateUserToken(user);
            System.out.println("token : " + token);

            final String refreshToken = jwtService.generateRefreshToken(token);
            System.out.println("refreshToken : " + refreshToken);

            return ResponseEntity.ok(new JwtResponse("Login Success", token, refreshToken));
    }else{
        throw new ResponseStatusException(HttpStatus.NO_CONTENT, "A user with the specified email DOES NOT exist");
    }
    }


    public ResponseEntity login(@Valid UserLoginDTO userLogin,
                                HttpServletResponse response,
                                HttpServletRequest request) throws Exception {
        Map<String, String> errorMap = new HashMap<>();
        String status;
        System.out.println("login start");
        System.out.println("email exists : " + repository.existsByEmail(userLogin.getEmail()));

        if (repository.existsByEmail(userLogin.getEmail())) {
            User user = repository.findByEmail(userLogin.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "A user with the specified email DOES NOT exist"));
            System.out.println("password matches : " + argon2PasswordEncoder.matches(userLogin.getPassword(), user.getPassword()));
            if (argon2PasswordEncoder.matches(userLogin.getPassword(), user.getPassword())) {
                System.out.println(" : do true : ");

//                authenticate(userLogin.getEmail(), userLogin.getPassword());

                final UserDetails userDetails = userDetailsService
                        .loadUserByUsername(userLogin.getEmail());

                final String token = jwtService.generateUserToken(user);
                System.out.println("token : " + token);

                final String refreshToken = jwtService.generateRefreshToken(token);
                System.out.println("refreshToken : " + refreshToken);

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
