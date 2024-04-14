package net.arkx.userservice.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import net.arkx.userservice.entities.Jwt;
import net.arkx.userservice.entities.RefreshToken;
import net.arkx.userservice.entities.User;
import net.arkx.userservice.repository.JwtRepository;
import net.arkx.userservice.service.UserService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Key;
import java.sql.SQLOutput;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Transactional
@Slf4j
@Service
public class JwtService {
    public static final String BEARER = "bearer";
    public static final String REFRESH = "refresh";
    public static final String INVALID_TOKEN = "invalid token";
    private final String ENCRYPTION_KEY = "5c9d1578a084e3248fa5cc2ac169002a242121a6dd7c66376609e14e5667a041";
    private final UserService userService;
    private final JwtRepository jwtRepository;

    public JwtService(UserService userService, JwtRepository jwtRepository) {
        this.userService = userService;
        this.jwtRepository = jwtRepository;
    }
    // Method to retrieve Jwt object by its value
    public Jwt tokenByValue(String value) {
       return jwtRepository.findByValueAndDeactivateAndExpire(
                       value,
               false,
               false)
               .orElseThrow(() -> new RuntimeException("Unknown Token"));
    }
    // Method to generate JWT for a given username

    public Map<String, String> generate(String username) {

        User user = userService.loadUserByUsername(username);
        this.disableTokens(user);
        final Map<String, String> jwtMap = new java.util.HashMap<>( generateJwt(user));
        RefreshToken refreshToken=RefreshToken.builder()
                .value(UUID.randomUUID().toString())
                .expire(false)
                .creation(Instant.now())
                .expiration(Instant.now().plusMillis(30 * 60 * 1000))
                .build();

        final Jwt jwt = Jwt
                .builder()
                .value(jwtMap.get(BEARER))
                .deactivate(false)
                .expire(false)
                .user(user)
                .refreshToken(refreshToken)
                .build();

        jwtRepository.save(jwt);
        jwtMap.put(REFRESH, refreshToken.getValue());
        return jwtMap;
    }
    // Method to deactivate all tokens associated with a user
    private void disableTokens(User user) {
        Stream<Jwt> user1 = jwtRepository.findUser(user.getUsername());
        System.out.println(user1);
        final List<Jwt> jwtList =  jwtRepository.findUser(user.getUsername()).peek(
                jwt -> {
                    jwt.setDeactivate(true);
                    jwt.setExpire(true);
                }
        ).collect(Collectors.toList());

        this.jwtRepository.saveAll(jwtList);
    }
    // Method to extract username from JWT
    public String extractUsername(String token) {
        return getClaims(token, Claims::getSubject);
    }
    // Method to check if a token is expired
    public boolean isTokenExpired(String token) {
        Date expirationDate = getClaims(token, Claims::getExpiration);
        return expirationDate.before(new Date());
    }

    // Method to retrieve claims from JWT
    private <T> T getClaims(String token, Function<Claims, T> function) {
        Claims claims = getAllClaims(token);
        return function.apply(claims);
    }
    // Method to parse JWT and retrieve all claims
    private Claims getAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();

    }
    // Method to generate JWT for a user
    private Map<String, String> generateJwt(User user) {
        final long currentTime = System.currentTimeMillis();
        final long expirationTime = currentTime + 5 * 60 * 1000;
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
        return Map.of(BEARER, bearer);
    }
    // Method to retrieve cryptographic key for JWT
    private Key getKey() {
        final byte[] decoder = Decoders.BASE64.decode(ENCRYPTION_KEY);
        return Keys.hmacShaKeyFor(decoder);
    }
    // Method to deactivate and expire tokens upon user logout
    public void deconnexion() {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Jwt jwt = jwtRepository.findUserValidToken(
                user.getUsername(),
                false,
                false
        ).orElseThrow(()->new RuntimeException(INVALID_TOKEN));
        jwt.setExpire(true);
        jwt.setDeactivate(true);
        jwtRepository.save(jwt);
    }
    //Remove Expired Token
    @Scheduled(cron = "0 */12 * * * *")
    public void removeUselessJwt(){
        /*Iterable<Jwt> jwt = jwtRepository.findAll();
        Iterator<Jwt> iterator = jwt.iterator();
        if(iterator.hasNext()) {
            log.info("Deleting token at {}", Instant.now());*/
            jwtRepository.deleteAllByExpireAndDeactivate(true, true);
      //  }

    }

    public Map<String, String> refreshToken(Map<String, String> refreshTokenRequest) {
       final Jwt jwt = jwtRepository.findByRefreshToken(refreshTokenRequest.get(REFRESH)).orElseThrow(() -> new RuntimeException(INVALID_TOKEN));
       if(jwt.getRefreshToken().isExpire() || jwt.getRefreshToken().getExpiration().isBefore(Instant.now())){
           throw new RuntimeException(INVALID_TOKEN);
       }
       this.disableTokens(jwt.getUser());
       return this.generate(jwt.getUser().getUsername());
    }
}
