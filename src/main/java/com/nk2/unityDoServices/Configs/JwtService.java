package com.nk2.unityDoServices.Configs;

import com.nk2.unityDoServices.Entities.User;
import com.nk2.unityDoServices.Repositories.UserRepository;
import com.nk2.unityDoServices.Services.UserServices;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.*;
import java.util.function.Function;


@Service
public class JwtService {

    @Autowired
    UserRepository userRepository;

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;
    @Value("${application.security.jwt.refresh-token.expiration}")
    private long refreshExpiration;

    static final String USERNAME_KEY = "sub";
    static final String CREATED_DATE_KEY = "created on";
    static final String ROLE_KEY = "role";
    static final String NAME_KEY = "name";
    static final String ID_KEY = "id";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateUserToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(USERNAME_KEY, user.getEmail());
        claims.put(ID_KEY, user.getId());
        claims.put(ROLE_KEY, user.getRole());
        claims.put(NAME_KEY, user.getName() +" "+ user.getSurName());
        claims.put(CREATED_DATE_KEY, new Date());

        String token = generateToken(claims, jwtExpiration);
        return token;
    }

    public String generateRefreshToken(String token) {
//        Date expirationDate = generateExpirationDate(jwtExpiration);
        Claims claims = getClaimsFromToken(token);
        claims.put(CREATED_DATE_KEY, new Date());
        return generateToken(claims, refreshExpiration);
    }

    public String generateToken(Map<String, Object> claims, Long expiratioDate) {
//        final Key key = new SecretKeySpec(SECRET.getBytes(), SignatureAlgorithm.HS512.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiratioDate))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getUsernameFromToken(String token) {
        return getClaimsFromToken(token).getSubject();
    }

    public User getUserFromToken(String token) {
        return userRepository.findByEmail(getUsernameFromToken(token)).orElseThrow((() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                "No user with email "+getUsernameFromToken(token))));
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimsFromToken(token).getExpiration();
    }

    public Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean isTokenValid(String token, User user) throws Exception {
        final String username = getUsernameFromToken(token);
        return (username.equals(user.getEmail()) && !isTokenExpired(token));
    }

    public String generateRefreshToken(
            UserDetails userDetails
    ) {
        return buildToken(new HashMap<>(), userDetails, refreshExpiration);
    }

    private String buildToken(
            Map<String, Object> extraClaims,
            UserDetails userDetails,
            long expiration
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}