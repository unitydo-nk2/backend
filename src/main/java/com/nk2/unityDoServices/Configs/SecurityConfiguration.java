package com.nk2.unityDoServices.Configs;

import com.nk2.unityDoServices.Enums.Role;
import com.nk2.unityDoServices.Services.UserServices;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.firewall.DefaultHttpFirewall;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private static final String[] GUEST_PATH_AVAILABLE = {
            "/api/auth/**"
    };

    private static final String[] GUEST_GET_AVAILABLE = {
            "/api/activities/list","/api/activities/poster","/api/activities/popular",
            "/api/activities/{activityId}","/api/activities/{activityId}/images","/api/categories/**"
    };

    private static final String[] ADMIN_PATH_AVAILABLE = {
            "/api/categories/**",
            "/api/users/list","/api/users/{userId}", "/api/users/{id}/activities","/api/users/registration/{id}",
            "/api/activities/list","/api/activities/userRegistration","/api/activities/{activityId}"
            ,"/api/activities/{activityId}/images"
            ,"/api/activities/registration/{activityId}"
            ,"/api/activities/{activityId}/images"
            ,"/api/activities/{activityId}/registration"//get
            ,"/api/activities"
            ,"/api/activities/images" ,"/api/activities/images" ,"/api/activities/images/{id}"
    };

    private static final String[] USER_PATH_AVAILABLE = {
            "/api/categories/**",
            "/api/users/list","/api/users/{userId}","/api/users/{id}/activities",
            "/api/activities/list","/api/activities/userRegistration",
            "/api/activities/{activityId}", // get
            "/api/activities/{activityId}/images"
            ,"/api/activities/{activityId}/images"
            ,"/api/activities/{activityId}/registration" //post
            ,"/api/activities/images/{id}"
    };

    private static final String[] ACTIVITY_OWNER_PATH_AVAILABLE = {
            "/api/categories/**",
            "/api/users/list","/api/users/{userId}","/api/users/registration/{id}",
            "/api/activities/list","/api/activities/userRegistration","/api/activities/userRegistration",
            "/api/activities/{activityId}", // get , delete
            "/api/{activityId}/images"
            ,"/api/activities/{activityId}/images"
            ,"/api/activities/registration/{activityId}"
            ,"/api/activities/{activityId}/registration" //get
            ,"/api/activities/images/{id}"
    };


    final JwtEntryPoint jwtEntryPoint;
    final JwtAuthenticationFilter jwtFilter;
    final UserServices userService;

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*")); // Allow all headers
        configuration.setAllowCredentials(true);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(exception -> exception.authenticationEntryPoint(jwtEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(request -> request
                        .requestMatchers(GUEST_PATH_AVAILABLE).permitAll()
                        .requestMatchers(HttpMethod.GET,GUEST_GET_AVAILABLE).permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/activities/userRegistration","/api/activities/registration/{activityId}"
                                ,"api/users/{id}/activities","api/users/registration/{id}","api/users/{activityId}/registrants").hasAnyRole(Role.Admin.name(),Role.ActivityOwner.name())
                        .requestMatchers(HttpMethod.POST,"/api/activities","/api/activities/images","api/activities/{activityId}/registration").hasAnyRole(Role.Admin.name(),Role.ActivityOwner.name())
                        .requestMatchers(HttpMethod.DELETE,"/api/activities/{id}","api/users/{id}").hasAnyRole(Role.Admin.name(),Role.ActivityOwner.name())
                        .requestMatchers(HttpMethod.PATCH,"/api/activities/{id}","/api/activities/images/{id}","api/users/registration/{id}").hasAnyRole(Role.Admin.name(),Role.ActivityOwner.name())
                        .requestMatchers(HttpMethod.POST,"/api/activities/{activityId}/registration").hasAuthority(Role.User.name())
                        .requestMatchers(HttpMethod.PATCH,"/api/users/{id}").hasAnyRole(Role.Admin.name(),Role.User.name())
                        .requestMatchers(HttpMethod.POST,"/api/activities/{activityId}/registration").hasAnyAuthority(Role.Admin.name())
                        .requestMatchers(HttpMethod.GET,"api/activities/recommends").hasAuthority(Role.User.name())
                        .anyRequest().authenticated()
                );
        http.authenticationProvider(authenticationProvider());
        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


    @SneakyThrows
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return userService::findUserByEmail;
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        MessageDigestPasswordEncoder md5PE = new MessageDigestPasswordEncoder("MD5");

        Argon2PasswordEncoder argon2PE = new Argon2PasswordEncoder(16, 32, 1, 1 << 17, 5);

        Map<String, PasswordEncoder> encoders = Map.of("argon2", argon2PE);

        DelegatingPasswordEncoder delegatingPasswordEncoder = new DelegatingPasswordEncoder(
                "argon2", encoders);
        delegatingPasswordEncoder.setDefaultPasswordEncoderForMatches(md5PE);
        return delegatingPasswordEncoder;
    }


    @Bean
    public Argon2PasswordEncoder argaon2PasswordEncoder() {
        return new Argon2PasswordEncoder(16, 29, 1, 16, 2);
    }

    @Bean
    public WebSecurityCustomizer configure() {
        return web -> web.httpFirewall(allowUrlEncodedSlashHttpFirewall());
    }

    @Bean
    public HttpFirewall allowUrlEncodedSlashHttpFirewall() {
        DefaultHttpFirewall firewall = new DefaultHttpFirewall();
        firewall.setAllowUrlEncodedSlash(true);
        return firewall;
    }
}
