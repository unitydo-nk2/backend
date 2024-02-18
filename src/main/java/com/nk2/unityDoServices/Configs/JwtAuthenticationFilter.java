package com.nk2.unityDoServices.Configs;

import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    UserRepository userRepository;

    @Setter
    @Getter
    String jwtToken;

    final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            System.out.println("token: " + token);
            if (token.startsWith("Bearer ")) {
                token = token.substring(7);
                setJwtToken(token);
                System.out.println("token.startsWith(Bearer) token: " + token);
            }
            try {
                String username = jwtService.extractUsername(token);
                System.out.println("username " + username);
                User user = userRepository.findByEmail(username).orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "No user with email")));
                System.out.println("user " + user.getUsername() + " "+user.getRole());
                System.out.println("is user null " + user != null );
                System.out.println("is token valid " + jwtService.isTokenValid(token, user));
                System.out.println("do if " + user != null && jwtService.isTokenValid(token, user));

                if (user != null && jwtService.isTokenValid(token, user)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), null, user.getAuthorities());
                    System.out.println("authentication " + authentication);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    System.out.println("authentication update " + authentication);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    System.out.println("SecurityContextHolder " + SecurityContextHolder.getContext());
                } else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Token is invalid");
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        filterChain.doFilter(request, response);
    }
}