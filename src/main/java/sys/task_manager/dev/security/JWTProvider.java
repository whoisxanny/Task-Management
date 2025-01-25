package sys.task_manager.dev.security;

import sys.task_manager.dev.dtos.JwtResponseDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import sys.task_manager.dev.models.User;
import sys.task_manager.dev.services.UserService;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JWTProvider {
    private final JwtProperties jwtProperties;
    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private static SecretKey key;

    @PostConstruct
    public void init() {
        System.out.println("JWT secret: " + jwtProperties.getSecret());
        System.out.println("JWT access duration: " + jwtProperties.getAccess());
        System.out.println("JWT refresh duration: " + jwtProperties.getRefresh());

        if (jwtProperties.getSecret() == null || jwtProperties.getSecret().isEmpty()) {
            throw new IllegalArgumentException("JWT secret key is not configured");
        }
        key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }



    public String createAccessToken( Long userId, String username) {
        Claims claims = Jwts.claims()
                .setSubject(username); // Set the subject (username)
        claims.put("id", userId); // Add user ID to the claims

        Instant validity = Instant.now()
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public String createRefreshToken( Long userId,  String username) {
        Claims claims = Jwts.claims()
                .setSubject(username); // Set the subject (username)
        claims.put("id", userId); // Add user ID to the claims

        Instant validity = Instant.now()
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(Date.from(validity))
                .signWith(key)
                .compact();
    }

    public JwtResponseDTO refreshUserTokens( String refreshToken) {
        JwtResponseDTO jwtResponse = new JwtResponseDTO();
        if (!isValid(refreshToken)) {
            throw new IllegalArgumentException("Invalid refresh token");
        }
        Long userId = Long.valueOf(getId(refreshToken)); // Get the user ID from the refresh token
        User person = userService.getById(userId); // Get user from database
        jwtResponse.setId(userId);
        jwtResponse.setEmail(person.getEmail());
        jwtResponse.setAccessToken(
                createAccessToken(userId, person.getEmail())
        );
        jwtResponse.setRefreshToken(
                createRefreshToken(userId, person.getEmail())
        );
        return jwtResponse;
    }

    public boolean isValid( String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder() // Use parserBuilder for newer versions
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token); // Parse the token using the correct method
            return claims.getBody()
                    .getExpiration()
                    .after(new Date()); // Check expiration
        } catch (Exception e) {
            return false; // If there's any exception, consider the token invalid
        }
    }

    private String getId( String token) {
        Claims claims = Jwts.parserBuilder() // Use parserBuilder for newer versions
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // Parse the token and get claims
                .getBody();
        return claims.get("id", String.class); // Get user ID from the claims
    }

    private String getUsername( String token) {
        Claims claims = Jwts.parserBuilder() // Use parserBuilder for newer versions
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token) // Parse the token and get claims
                .getBody();
        return claims.getSubject(); // Get the subject (username)
    }

    public Authentication getAuthentication( String token) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
