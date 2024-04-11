package net.arkx.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.JwkParserBuilder;
import io.jsonwebtoken.security.Keys;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    private final String ENCRYPTION_KEY = "5c9d1578a084e3248fa5cc2ac169002a242121a6dd7c66376609e14e5667a041";
    private final UserService userService;

    public JwtService(UserService userService) {
        this.userService = userService;
    }

    public Map<String, String> generate(String username) {
        User user = userService.loadUserByUsername(username);
        return generateJwt(user);
    }
    public String extractUsername(String token){
        return getClaims(token, Claims::getSubject);
    }

    public boolean isTokenExpired(String token) {
        Date expirationDate =  getClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }



    private <T> T getClaims(String token, Function<Claims, T> function){
    Claims claims = getAllClaims(token);
    return function.apply(claims);
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }

    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 30 * 60 * 1000;

        final Map<String, Object> claims = Map.of(
                "name", user.getFirstName(),
                "email", user.getEmail(),
                Claims.EXPIRATION, new Date(expirationTime),
                Claims.SUBJECT, user.getUsername()
        );


        final String bearer = Jwts.builder()
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(expirationTime))
                .setSubject(user.getUsername())
                .setClaims(claims)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
        return Map.of("bearer", bearer);
    }

    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }

}
