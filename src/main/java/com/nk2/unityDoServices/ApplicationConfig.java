package com.nk2.unityDoServices;

import com.nk2.unityDoServices.Utils.ListMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.MessageDigestPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;


@Configuration
public class ApplicationConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
    @Bean
    public ListMapper listMapper() {return ListMapper.getInstance(); }
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
    public Argon2PasswordEncoder argaon2PasswordEncoder(){
        return new Argon2PasswordEncoder(16,29,1,16,2);
    }
}